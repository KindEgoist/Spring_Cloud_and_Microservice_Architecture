package ru.gb.reserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.reserve.model.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
