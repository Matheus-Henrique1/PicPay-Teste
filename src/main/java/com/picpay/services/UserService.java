package com.picpay.services;

import com.picpay.domain.user.User;
import com.picpay.dtos.UserDTO;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    void validateTransaction(User sender, BigDecimal amount) throws Exception;

    User findById(Long id) throws Exception;

    void saveUser(User user);

    User createUser(UserDTO userDTO);

    List<User> getAllUser();

}
