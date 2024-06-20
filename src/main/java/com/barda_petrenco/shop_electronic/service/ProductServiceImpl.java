package com.barda_petrenco.shop_electronic.service;

import com.barda_petrenco.shop_electronic.dao.ProductRepository;
import com.barda_petrenco.shop_electronic.domain.Bucket;
import com.barda_petrenco.shop_electronic.domain.User;
import com.barda_petrenco.shop_electronic.dto.ProductDTO;
import com.barda_petrenco.shop_electronic.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper = ProductMapper.MAPPER;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final BucketService bucketService;
    public ProductServiceImpl(ProductRepository productRepository, UserService userService, BucketService bucketService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;
    }
    @Override
    public List<ProductDTO> getAll() {
        return mapper.fromProductList(productRepository.findAll());
    }
    @Override
    @Transactional
    public void addToUserBucket(Long productId, String username){
        User user = userService.findByName(username);
        if (user == null){
            throw new RuntimeException("User not found - " + username);
        }
        Bucket bucket = user.getBucket();
        if (bucket == null){
            Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
            user.setBucket(newBucket);
            userService.save(user);
        }else {
            bucketService.addProducts(bucket, Collections.singletonList(productId));
        }
    }
}
