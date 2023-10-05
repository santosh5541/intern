package com.inventory.service;

import com.inventory.dto.ProductDTO;
import com.inventory.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService{
    public ProductDTO createProduct(ProductDTO productDTO, int supplierId);
    public List<ProductDTO> viewAll();



    public Optional<ProductDTO> viewProductById(int id);

    public void deleteProductById(int id);

    public ProductDTO updateProduct(int id, ProductDTO productDTO);

    public Product toEntity(ProductDTO productDTO);

    public ProductDTO toDTO(Product product);

    List<ProductDTO> findProductBySupplier(int supplierId);

}
