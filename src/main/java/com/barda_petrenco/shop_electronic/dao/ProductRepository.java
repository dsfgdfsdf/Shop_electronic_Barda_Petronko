package com.barda_petrenco.shop_electronic.dao;

import com.barda_petrenco.shop_electronic.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
