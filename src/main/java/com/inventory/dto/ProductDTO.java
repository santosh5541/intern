package com.inventory.dto;

import com.inventory.model.Supplier;
import com.inventory.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private int product_id;
    private String product_name;
    private double product_prize;
    private boolean stock;
    private int product_quantity;
    private boolean live;
    private String product_imageName;
    private String product_desc;
    private SupplierDTO supplier;
}
