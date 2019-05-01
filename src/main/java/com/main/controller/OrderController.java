package com.main.controller;

import com.main.services.OrderService;
import com.main.vo.CartVO;
import com.main.vo.OrderVO;
import com.main.vo.ResponsePageVO;
import com.main.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/add-to-cart")
    public ResponseEntity<ResponseVO> addToCart(@Valid @RequestBody CartVO vo, Errors errors){
        AbstractRequestHandler handler = new AbstractRequestHandler() {
            @Override
            public Object processRequest() {
                return orderService.addToCart(vo);
            }
        };
        return handler.getResultWithValidation(errors);
    }

    @PutMapping("/decrease-item-qty-in-cart")
    public ResponseEntity<ResponseVO> decreaseItemQtyInCart(@Valid @RequestBody CartVO vo, Errors errors){
        AbstractRequestHandler handler = new AbstractRequestHandler() {
            @Override
            public Object processRequest() {
                return orderService.decreaseItemQtyInCart(vo);
            }
        };
        return handler.getResultWithValidation(errors);
    }

    @PutMapping("/update-order-status")
    public ResponseEntity<ResponseVO> updateOrderStatus(@Valid @RequestBody OrderVO vo, Errors errors){
        AbstractRequestHandler handler = new AbstractRequestHandler() {
            @Override
            public Object processRequest() {
                return orderService.updateOrderStatus(vo);
            }
        };
        return handler.getResultWithValidation(errors);
    }

    @GetMapping("/get-my-cart")
    public ResponseEntity<ResponseVO> getMyCart(){
        AbstractRequestHandler handler = new AbstractRequestHandler() {
            @Override
            public Object processRequest() {
                return orderService.getMyCart();
            }
        };
        return handler.getResult();
    }

    @GetMapping("/get-list-my-order")
    public ResponseEntity<ResponsePageVO> getListMyOrder(
            @RequestParam(value = "page",defaultValue = "0") Integer page,
            @RequestParam(value = "limit",defaultValue = "25") Integer limit,
            @RequestParam(value = "sort-by", defaultValue = "createdDate") String sortBy,
            @RequestParam(value = "sort-direction", defaultValue = "desc") String sortDirection
    ){
        AbstractPageRequestHandler handler = new AbstractPageRequestHandler() {
            @Override
            public Map<String,Object> processRequest() {
                return orderService.getListMyOrder(page,limit,sortBy,sortDirection);
            }
        };
        return handler.getResult();
    }
}
