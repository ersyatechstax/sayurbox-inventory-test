package com.main.vo;

import com.main.persistence.domain.Base;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CartVO extends BaseVO{

    private String orderId;

    @NotBlank(message = "Item id is required")
    private String itemId;

    private String itemName;

    private Integer pricePerQty;

    @Min(value = 1, message = "Quantity of item should be at least 1")
    private Integer quantity;

    private String note;

    private boolean itemAvailable;

    private Integer totalPrice;

}
