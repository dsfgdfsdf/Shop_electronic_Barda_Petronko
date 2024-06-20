package com.barda_petrenco.shop_electronic.service;

import com.barda_petrenco.shop_electronic.domain.User;
import com.barda_petrenco.shop_electronic.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService extends UserDetailsService {//security
    boolean save(UserDTO userDTO);
    void save(User user);
    List<UserDTO> getAll();
    User findByName(String name);
    void update(UserDTO userDTO);

    @Transactional
    void updateUserProfile(UserDTO dto);


}
