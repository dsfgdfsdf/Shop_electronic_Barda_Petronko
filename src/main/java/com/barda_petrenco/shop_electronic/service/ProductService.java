package com.barda_petrenco.shop_electronic.service;

import com.barda_petrenco.shop_electronic.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();
    void addToUserBucket(Long productId, String username);

}
