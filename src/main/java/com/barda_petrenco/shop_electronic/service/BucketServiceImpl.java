package com.barda_petrenco.shop_electronic.service;

import com.barda_petrenco.shop_electronic.dao.BucketRepository;
import com.barda_petrenco.shop_electronic.dao.ProductRepository;
import com.barda_petrenco.shop_electronic.dao.UserRepository;
import com.barda_petrenco.shop_electronic.domain.Bucket;
import com.barda_petrenco.shop_electronic.domain.Product;
import com.barda_petrenco.shop_electronic.domain.User;
import com.barda_petrenco.shop_electronic.dto.BucketDTO;
import com.barda_petrenco.shop_electronic.dto.BucketDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BucketServiceImpl implements BucketService {
    private final BucketRepository bucketRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(BucketServiceImpl.class);

    public BucketServiceImpl(BucketRepository bucketRepository, ProductRepository productRepository, UserService userService, UserRepository userRepository) {
        this.bucketRepository = bucketRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Bucket createBucket(User user, List<Long> productIds) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> productList = getCollectRefProductsById(productIds);
        bucket.setProducts(productList);
        return bucketRepository.save(bucket);
    }

    private List<Product> getCollectRefProductsById(List<Long> productIds) {
        return productIds.stream()
                .map(productRepository::getReferenceById)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addProducts(Bucket bucket, List<Long> productIds) {
        List<Product> products = bucket.getProducts();
        Set<Long> existingProductIds = products.stream().map(Product::getId).collect(Collectors.toSet());
        List<Product> newProductList = new ArrayList<>(products);

        productIds.stream()
                .filter(productId -> !existingProductIds.contains(productId))
                .map(productRepository::getReferenceById)
                .forEach(newProductList::add);

        bucket.setProducts(newProductList);
        bucketRepository.save(bucket);
    }
    @Override
    @Transactional
    public void removeProducts(Bucket bucket, List<Long> productIds) {
        List<Product> products = bucket.getProducts();
        if (products != null) {
            products.removeIf(product -> productIds.contains(product.getId()));
            bucket.setProducts(products);
            bucketRepository.save(bucket);
        }
    }

    @Override
    public BucketDTO getBucketByUser(String name) {
        User user = userService.findByName(name);
        if (user == null || user.getBucket() == null) {
            return new BucketDTO();
        }
        BucketDTO bucketDTO = new BucketDTO();
        Map<Long, BucketDetailDTO> mapByProductId = new HashMap<>();

        List<Product> products = user.getBucket().getProducts();
        for (Product product : products) {
            BucketDetailDTO detail = mapByProductId.get(product.getId());
            if (detail == null) {
                detail = new BucketDetailDTO();
                detail.setAmount(BigDecimal.ONE);
                detail.setSum(Double.valueOf(product.getPrice().toString()));
                mapByProductId.put(product.getId(), detail);
            } else {
                detail.setAmount(detail.getAmount().add(BigDecimal.ONE));
                detail.setSum(detail.getSum() + Double.valueOf(product.getPrice().toString()));
            }
        }
        bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucketDTO.aggregate();
        logger.debug("Bucket details for user {}: {}", name, bucketDTO);
        return bucketDTO;
    }

    @Override
    @Transactional
    public void removeFromUserBucket(Long productId, String username) {
        User user = userService.findByName(username);
        if (user != null && user.getBucket() != null) {
            Bucket bucket = user.getBucket();
            removeProducts(bucket, Collections.singletonList(productId));
        }
    }
}
