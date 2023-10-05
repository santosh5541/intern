package com.inventory.repository;

import com.inventory.model.Product;
import com.inventory.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier,Integer> {

}
