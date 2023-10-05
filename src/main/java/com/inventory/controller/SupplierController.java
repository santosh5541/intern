package com.inventory.controller;

import com.inventory.dto.SupplierDTO;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @PostMapping("/create")
    public ResponseEntity<SupplierDTO> create(@RequestBody SupplierDTO supplierDTO) {
        SupplierDTO createdSupplierDTO = supplierService.create(supplierDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSupplierDTO);
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAll() {
        List<SupplierDTO> supplierDTOS = supplierService.getAll();
        return ResponseEntity.ok(supplierDTOS);
    }

    @GetMapping("{id}")
    public ResponseEntity<SupplierDTO> getCategoryById(@PathVariable("id") int id) {
        try {
            SupplierDTO supplierDTO = supplierService.getCategoryById(id);
            return ResponseEntity.ok(supplierDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") int id) {
        try {
            supplierService.delete(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<SupplierDTO> updateCategory(@RequestBody SupplierDTO supplierDTO, @PathVariable("id") int id) {
        try {
            SupplierDTO updatedSupplierDto = supplierService.update(id, supplierDTO);
            return ResponseEntity.ok(updatedSupplierDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
