package org.example.finalproject33.repository;

import org.example.finalproject33.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}