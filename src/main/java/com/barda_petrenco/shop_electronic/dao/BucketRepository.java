package com.barda_petrenco.shop_electronic.dao;

import com.barda_petrenco.shop_electronic.domain.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
}
