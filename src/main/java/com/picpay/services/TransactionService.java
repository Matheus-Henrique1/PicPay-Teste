package com.picpay.services;

import com.picpay.dtos.ReturnTransactionDTO;
import com.picpay.dtos.TransactionDTO;

public interface TransactionService {

    ReturnTransactionDTO createTransacation(TransactionDTO transactionDTO) throws Exception;

}
