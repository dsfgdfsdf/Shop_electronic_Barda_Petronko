package com.barda_petrenco.shop_electronic.service;

import com.barda_petrenco.shop_electronic.domain.Bucket;
import com.barda_petrenco.shop_electronic.domain.User;
import com.barda_petrenco.shop_electronic.dto.BucketDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> productIds);
    void addProducts(Bucket bucket, List<Long> productIds);

    @Transactional
    void removeProducts(Bucket bucket, List<Long> productIds);

    BucketDTO getBucketByUser(String name);
    void removeFromUserBucket(Long productId, String username);
}
