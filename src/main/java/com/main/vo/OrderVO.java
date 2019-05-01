package com.main.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class OrderVO extends BaseVO{

    @NotBlank(message = "Order status is required")
    private String status;

    private Long orderDate;

    private Long paymentReceivedDate;

    private Long paymentDueDate;

    private String orderCode;

    private List<CartVO> itemList;
}
