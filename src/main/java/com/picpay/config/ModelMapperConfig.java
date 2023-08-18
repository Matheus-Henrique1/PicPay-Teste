package com.picpay.config;

import com.picpay.domain.transaction.Transaction;
import com.picpay.domain.user.User;
import com.picpay.dtos.ReturnTransactionDTO;
import com.picpay.dtos.TransactionDTO;
import com.picpay.dtos.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class ModelMapperConfig {

    @Bean
    public static ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static UserDTO convertUserToDto(User user) {
        UserDTO userDTO = modelMapper().map(user, UserDTO.class);
        return userDTO;
    }

    public static User convertUserDTOToEntity(UserDTO userDTO) {
        User user = modelMapper().map(userDTO, User.class);
        return user;
    }

    public static ReturnTransactionDTO convertTransactionToDto(Transaction transaction) {
        ReturnTransactionDTO ReturnTransactionDTO = modelMapper().map(transaction, ReturnTransactionDTO.class);
        return ReturnTransactionDTO;
    }

    public static Transaction convertTransactionDTOToEntity(ReturnTransactionDTO ReturnTransactionDTO) {
        Transaction transaction = modelMapper().map(ReturnTransactionDTO, Transaction.class);
        return transaction;
    }
}
