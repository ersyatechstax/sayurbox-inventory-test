package com.main.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ItemVO extends BaseVO {

    @NotBlank(message = "Item name is required")
    @Size(max = 40,min = 2,message = "Item name must be between 2-40 chars")
    private String name;

    private Integer stock;

    @NotBlank(message = "Item description is required")
    @Size(min = 10, max = 255, message = "Item description must be between 10-255 chars")
    private String description;

    private Integer reservedStock;

    private Integer availableStock;
}
