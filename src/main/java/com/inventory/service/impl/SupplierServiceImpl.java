package com.inventory.service.impl;

import com.inventory.dto.SupplierDTO;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.Supplier;
import com.inventory.repository.SupplierRepository;
import com.inventory.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SupplierDTO create(SupplierDTO supplierDTO) {
        Supplier supplier = modelMapper.map(supplierDTO, Supplier.class);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return modelMapper.map(savedSupplier, SupplierDTO.class);
    }

    @Override
    public SupplierDTO update(int id, SupplierDTO supplierDTO) {
        Optional<Supplier> oldSupplier = supplierRepository.findById(id);
        if (oldSupplier.isPresent()) {
            Supplier updatedSupplier = oldSupplier.get();
            modelMapper.map(supplierDTO, updatedSupplier);
            Supplier savedSupplier = supplierRepository.save(updatedSupplier);
            return modelMapper.map(savedSupplier, SupplierDTO.class);
        } else {
            throw new ResourceNotFoundException("Supplier not found with id: " + id);
        }
    }

    @Override
    public void delete(int supplierId) {
        if (!supplierRepository.existsById(supplierId)) {
            throw new ResourceNotFoundException("Supplier not found with id: " + supplierId);
        }
        supplierRepository.deleteById(supplierId);
    }

    @Override
    public SupplierDTO getCategoryById(int supplierId) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(supplierId);
        return supplierOptional.map(supplier -> modelMapper.map(supplier, SupplierDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + supplierId));
    }

    @Override
    public List<SupplierDTO> getAll() {
        List<Supplier> suppliers = supplierRepository.findAll();
        return suppliers.stream()
                .map(supplier -> modelMapper.map(supplier, SupplierDTO.class))
                .collect(Collectors.toList());
    }
}
