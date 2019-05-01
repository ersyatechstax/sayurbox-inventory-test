package com.main.mapper.converter;

import com.main.enums.OrderStatus;
import com.main.enums.ParameterCode;
import com.main.persistence.domain.Order;
import com.main.persistence.domain.OrderDetail;
import com.main.persistence.domain.Parameter;
import com.main.persistence.repository.ParameterRepository;
import com.main.vo.OrderVO;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class OrderVOConverter extends AbstractBaseVOConverter<Order,OrderVO> {

    @Autowired
    ParameterRepository parameterRepository;

    @Autowired
    OrderDetailVOConverter orderDetailVOConverter;

    public Order transferVOToModelForCheckout(Order model){
        model.setOrderDate(new Date());
        Parameter parameter = parameterRepository.findByCode(ParameterCode.PAYMENT_EXPIRY_IN_HOUR.name());
        model.setPaymentDueDate(DateUtils.addHours(model.getOrderDate(),Integer.parseInt(parameter.getValue())));
        model.setOrderStatus(OrderStatus.ORDER_RECEIVED);
        return model;
    }

    public OrderVO transferModelToVO(Order model, OrderVO vo, List<OrderDetail> orderDetails) {
        if(vo == null) vo = new OrderVO();
        super.transferModelToVO(model,vo);
        vo.setStatus(model.getOrderStatus().name());
        vo.setOrderCode(model.getOrderCode());
        vo.setOrderDate(model.getOrderDate().getTime());
        vo.setPaymentDueDate(model.getPaymentDueDate().getTime());
        if(!model.getOrderStatus().equals(OrderStatus.ORDER_RECEIVED) && !model.getOrderStatus().equals(OrderStatus.CANCELLED)){
            vo.setPaymentReceivedDate(model.getPaymentReceivedDate().getTime());
        }
        vo.setItemList(orderDetailVOConverter.transferListOfModelToListOfVO(orderDetails));
        return vo;
    }
}
