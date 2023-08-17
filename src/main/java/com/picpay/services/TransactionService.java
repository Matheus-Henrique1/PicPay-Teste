package com.picpay.services;

import com.picpay.dtos.TransactionDTO;

public interface TransactionService {

    void createTransacation(TransactionDTO transaction) throws Exception;

}
