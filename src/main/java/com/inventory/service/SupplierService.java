package com.inventory.service;

import com.inventory.dto.SupplierDTO;

import java.util.List;

public interface SupplierService {
public SupplierDTO create(SupplierDTO supplierDTO);
public SupplierDTO update(int id, SupplierDTO supplierDTO);
public void delete(int supplierId);
public SupplierDTO getCategoryById(int supplierId);
public List<SupplierDTO> getAll();

//public Supplier toEntity(SupplierDTO categoryDTO);
//
//public SupplierDTO toDTO(Supplier category);
}
