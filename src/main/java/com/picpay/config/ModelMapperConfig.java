package com.picpay.config;

import com.picpay.domain.user.User;
import com.picpay.dtos.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class ModelMapperConfig {

    @Bean
    public static ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static UserDTO convertToDto(User user) {
        UserDTO userDTO = modelMapper().map(user, UserDTO.class);
        return userDTO;
    }


    public static User convertToEntity(UserDTO userDTO) {
        User user = modelMapper().map(userDTO, User.class);
        return user;
    }
}
