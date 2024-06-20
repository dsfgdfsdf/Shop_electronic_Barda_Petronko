package com.barda_petrenco.shop_electronic.dao;

import com.barda_petrenco.shop_electronic.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByName(String name);

}
