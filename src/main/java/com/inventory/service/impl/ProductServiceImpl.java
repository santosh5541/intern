package com.inventory.service.impl;

import com.inventory.dto.ProductDTO;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.Product;
import com.inventory.model.Supplier;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.SupplierRepository;
import com.inventory.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO, int supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + supplierId));

        Product product = modelMapper.map(productDTO, Product.class);
        product.setSupplier(supplier);

        Product saved = productRepository.save(product);

        return modelMapper.map(saved, ProductDTO.class);
    }

    @Override
    public List<ProductDTO> viewAll() {
        List<Product> allProducts = productRepository.findAll();

        List<ProductDTO> productDTOs = allProducts.stream()
                .filter(Product::isLive)
                .map(this::toDTO)
                .collect(Collectors.toList());

        // Return the list of ProductDTO directly
        return productDTOs;
    }

    @Override
    public Optional<ProductDTO> viewProductById(int id) {
        Optional<Product> productOptional = productRepository.findById(id);

        return productOptional.map(product -> modelMapper.map(product, ProductDTO.class));
    }

    @Override
    public void deleteProductById(int id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO updateProduct(int id, ProductDTO productDTO) {
        Optional<Product> oldProduct = productRepository.findById(id);
        if (oldProduct.isPresent()) {
            Product updatedProduct = oldProduct.get();
            modelMapper.map(productDTO, updatedProduct);
            Product savedProduct = productRepository.save(updatedProduct);
            return modelMapper.map(savedProduct, ProductDTO.class);
        } else {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
    }

    public Product toEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    @Override
    public ProductDTO toDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }


    @Override
    public List<ProductDTO> findProductBySupplier(int supplierId) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(supplierId);
        if (supplierOptional.isPresent()) {
            Supplier supplier = supplierOptional.get();
            List<Product> bySupplier = productRepository.findBySupplier(supplier);
            return bySupplier.stream()
                    .map(product -> modelMapper.map(product, ProductDTO.class))
                    .collect(Collectors.toList());
        } else {
            throw new ResourceNotFoundException("Supplier not found with id: " + supplierId);
        }
    }

}

