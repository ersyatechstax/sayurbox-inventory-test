package com.main.services;

import com.main.enums.AccountGroup;
import com.main.enums.OrderStatus;
import com.main.exception.GeneralException;
import com.main.mapper.converter.OrderDetailVOConverter;
import com.main.mapper.converter.OrderVOConverter;
import com.main.persistence.domain.*;
import com.main.persistence.repository.*;
import com.main.util.PageBuilder;
import com.main.vo.OrderVO;
import com.main.vo.ResponseWithMessageVO;
import com.main.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemViewRepository itemViewRepository;

    @Autowired
    OrderVOConverter orderVOConverter;

    @Autowired
    OrderDetailVOConverter orderDetailVOConverter;

    @Autowired
    AuthService authService;

    @Transactional
    public ResponseWithMessageVO addToCart(CartVO cartVO){
        String responseMessage;

        OrderDetail orderDetail;

        ItemView itemCheck = itemViewRepository.findBySecureId(cartVO.getItemId()).orElseThrow(() ->new GeneralException("Item is not found"));
        //TODO: Check if quantity is sufficient
        if(cartVO.getQuantity() > itemCheck.getAvailableStock()) throw new GeneralException("Item quantity you put is not sufficient with the current stock");
        Item item = itemRepository.findById(itemCheck.getId()).get();
        //TODO: Check if there is pending order(not checkout yet)
        Optional<Order> order = orderRepository.findByUserAndOrderStatus(authService.getCurrentUser(), OrderStatus.PENDING);
        if(order.isPresent()){
            //TODO: Check if item exists in the cart
            Optional<OrderDetail> orderDetailFound = orderDetailRepository.findByOrderAndItem(order.get(),item);
            if(orderDetailFound.isPresent()){
                //TODO: Check quantity in cart+vo and stock
                if(orderDetailFound.get().getQuantity()+cartVO.getQuantity() > itemCheck.getAvailableStock()) throw new GeneralException("Item quantity you put is not sufficient with the current stock");
                //TODO: Update item quantity
                orderDetailFound.get().setQuantity(orderDetailFound.get().getQuantity()+cartVO.getQuantity());
                orderDetail = orderDetailFound.get();
            }
            else{
                //TODO: Add new item in cart
                orderDetail = new OrderDetail(order.get(),item,cartVO.getQuantity(),cartVO.getNote());
            }
            responseMessage = item.getName()+" has been added to your cart";
        }
        else {
            //TODO: Create new order
            order = Optional.of(orderRepository.save(new Order(authService.getCurrentUser(),null,null,null,OrderStatus.PENDING,null, null)));
            orderDetail = new OrderDetail(order.get(),item,cartVO.getQuantity(),cartVO.getNote());
            responseMessage = "New order has been created, "+ item.getName()+" has been added to your cart";
        }
        //TODO: Add item to cart
        orderDetail = orderDetailRepository.save(orderDetail);
        return new ResponseWithMessageVO(orderDetail.getSecureId(),orderDetail.getVersion(),responseMessage);
    }

    @Transactional
    public ResponseWithMessageVO decreaseItemQtyInCart(CartVO vo){
        OrderDetail orderDetail = orderDetailRepository.findByOrderUserAndOrderOrderStatusAndItemSecureId(
                authService.getCurrentUser(),OrderStatus.PENDING,vo.getItemId())
                .orElseThrow(() -> new GeneralException("The item is not found in your cart"));

        //TODO: Check if quantity is valid
        if(vo.getQuantity() > orderDetail.getQuantity())throw new GeneralException("Item quantity you remove must be smaller than in cart");
        String responseMessage;
        if(vo.getQuantity().equals(orderDetail.getQuantity())){
            Order order = orderDetail.getOrder();
            orderDetailRepository.delete(orderDetail);
            List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);
            //TODO: Delete the order if there is no item remaining
            if(orderDetails.size() == 0){
                orderRepository.delete(order);
            }
            responseMessage = orderDetail.getItem().getName() + " has been removed from your cart";
        }
        else{
            orderDetail.setQuantity(orderDetail.getQuantity()-vo.getQuantity());
            orderDetailRepository.save(orderDetail);
            responseMessage = orderDetail.getItem().getName() + " quantity has been decreased";
        }
        return new ResponseWithMessageVO(null,null,responseMessage);
    }

    @Transactional
    public ResponseWithMessageVO updateOrderStatus(OrderVO vo){
        //TODO: Check if order exists
        Order order = orderRepository.findBySecureId(vo.getId()).orElseThrow(() -> new GeneralException("Order is not found"));
        //TODO: Check if the person is itself or admin
        if(order.getUser() != authService.getCurrentUser() && !authService.getCurrentUser().getAccountGroup().equals(AccountGroup.ADMIN))throw new GeneralException("You can't update the other's order");
        String message;
        if(vo.getStatus().equals(OrderStatus.ORDER_RECEIVED.name())){
            //TODO: Check if order status is PENDING
            if(!order.getOrderStatus().equals(OrderStatus.PENDING)) throw new GeneralException("You can't checkout the order that has been checkout");
            List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);
            //TODO: Check if there is item in cart
            if(orderDetails.size() == 0)throw new GeneralException("You can't checkout the order without any item in the cart");
            List<ItemView> items = itemViewRepository.findBySecureIdIn(orderDetails.stream().map(o -> o.getItem().getSecureId()).collect(Collectors.toList()));
            for(ItemView item : items){
                //TODO: Check if all items are available
                if(item.getAvailableStock() < orderDetails.stream().filter(orderDetail -> orderDetail.getItem().getSecureId().equals(item.getSecureId())).findAny().orElse(null).getQuantity()){
                    throw new GeneralException("You can't checkout the "+ item.getName()+ " because stock is not sufficient");
                }
            }
            order = orderVOConverter.transferVOToModelForCheckout(order);
            order.setTotalPrice(orderDetails.stream().mapToInt(o -> (o.getItem().getPrice() * o.getQuantity())).sum());
            message = "Your order has been checkout successfully";
        }
        else if(vo.getStatus().equals(OrderStatus.PAYMENT_RECEIVED.name())){
            //TODO: Check if payment due date is not expired
            if(order.getPaymentDueDate().getTime() < new Date().getTime()) throw new GeneralException("The order payment due date is expired");
            //TODO: Check if order status is ORDER_RECEIVED / has been checkout / payment has been confirmed
            if(!order.getOrderStatus().equals(OrderStatus.ORDER_RECEIVED)) throw new GeneralException("You can't confirm payment if the order hasn't been checkout or the payment has been confirmed");
            order.setOrderStatus(OrderStatus.PAYMENT_RECEIVED);
            order.setPaymentReceivedDate(new Date());
            List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);
            List<Item> items = itemRepository.findBySecureIdIn(orderDetails.stream().map(o-> o.getItem().getSecureId()).collect(Collectors.toList()));
            //TODO: Decrease actual stock in inventory
            for(Item item : items){
                item.setStock(item.getStock() - orderDetails.stream().filter(o -> o.getItem().getId().equals(item.getId())).findAny().orElse(null).getQuantity());
            }
            itemRepository.saveAll(items);
            message = "The order payment has been confirmed successfully";
        }
        else{
            throw new GeneralException("Invalid order status");
        }
        order = orderRepository.save(order);
        return new ResponseWithMessageVO(order.getSecureId(),order.getVersion(),message);
    }

    public OrderVO getMyCart(){
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderUserAndOrderOrderStatus(authService.getCurrentUser(),OrderStatus.PENDING);
        if(orderDetails.size() > 0){
            OrderVO orderVO = new OrderVO();
            orderVO.setId(orderDetails.get(0).getOrder().getSecureId());
            orderVO.setVersion(orderDetails.get(0).getOrder().getVersion());
            orderVO.setStatus(orderDetails.get(0).getOrder().getOrderStatus().name());
            orderVO.setOrderCode(orderDetails.get(0).getOrder().getOrderCode());
            List<CartVO> itemList = new ArrayList<>();
            List<ItemView> itemsCheck = itemViewRepository.findBySecureIdIn(orderDetails.stream().map(o -> o.getItem().getSecureId()).collect(Collectors.toList()));
            Integer totalPrice = 0;
            for(OrderDetail item : orderDetails){
                CartVO cartVO = orderDetailVOConverter.transferModelToVO(item,null);
                //TODO: Set flag for item is available or not based on available_stock
                if(item.getQuantity() > itemsCheck.stream().filter(i -> i.getSecureId().equals(item.getItem().getSecureId())).findAny().orElse(null).getAvailableStock()){
                    cartVO.setItemAvailable(false);
                }
                else{
                    cartVO.setItemAvailable(true);
                }
                itemList.add(cartVO);
                totalPrice += cartVO.getTotalPrice();
            }
            orderVO.setItemList(itemList);
            orderVO.setTotalPrice(totalPrice);
            return orderVO;
        }
        else{
            return null;
        }
    }

    public Map<String, Object> getListMyOrder(Integer page, Integer limit, String sortBy, String sortDirection){
        Pageable pageable = PageBuilder.buildPage(page,limit,sortBy,sortDirection);
        List<OrderStatus> orderStatuses = new ArrayList<>(asList(OrderStatus.ORDER_RECEIVED,OrderStatus.PAYMENT_RECEIVED,OrderStatus.CANCELLED));
        Page<Order> orders = orderRepository.findByUserAndOrderStatusIn(authService.getCurrentUser(), orderStatuses, pageable);
        List<OrderVO> orderVOS = new ArrayList<>();
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderIn(orders.getContent());
        for(Order order : orders){
            OrderVO orderVO = orderVOConverter.transferModelToVO(order,null,
                    orderDetails.stream().filter(od -> od.getOrder().equals(order)).collect(Collectors.toList()));
            orderVOS.add(orderVO);
        }
        return AbstractBaseService.contructPageToMap(orderVOS,orders.getNumber(),orders.getTotalElements(),orders.getTotalPages());
    }
}
