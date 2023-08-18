package com.picpay.controllers;

import com.picpay.domain.transaction.Transaction;
import com.picpay.dtos.ReturnTransactionDTO;
import com.picpay.dtos.TransactionDTO;
import com.picpay.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<ReturnTransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception {
        ReturnTransactionDTO dto = transactionService.createTransacation(transactionDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


}
