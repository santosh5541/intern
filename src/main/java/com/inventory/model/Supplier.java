package com.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int supplierId;
    private String supplierName;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "supplier",cascade = CascadeType.ALL)
    private Set<Product> product;


    @Override
    public int hashCode() {
        return Objects.hash(supplierId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Supplier other = (Supplier) obj;
        return Objects.equals(supplierId, other.supplierId);
    }
}
