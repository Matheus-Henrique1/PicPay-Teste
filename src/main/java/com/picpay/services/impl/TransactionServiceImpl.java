package com.picpay.services.impl;

import com.picpay.domain.transaction.Transaction;
import com.picpay.domain.user.User;
import com.picpay.dtos.TransactionDTO;
import com.picpay.repositories.TransactionRepository;
import com.picpay.services.TransactionService;
import com.picpay.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {

    public TransactionServiceImpl(TransactionRepository transactionRepository, UserService userService,
                                  RestTemplate restTemplate) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    private TransactionRepository transactionRepository;
    private UserService userService;
    private RestTemplate restTemplate;

    @Value("${url.authorize.transaction}")
    private String urlAuthorizeTransaction;

    @Override
    public void createTransacation(TransactionDTO transaction) throws Exception {
        User sender = userService.findById(transaction.senderId());
        User receiver = userService.findById(transaction.receiveId());

        userService.validateTransaction(sender, transaction.value());

        if (!authorizeTransaction(sender, transaction.value())) {
            throw new Exception("Transação não autorizada.");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimesTamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        transactionRepository.save(newTransaction);
        userService.saveUser(sender);
        userService.saveUser(receiver);
    }

    private boolean authorizeTransaction(User sender, BigDecimal value) {
        ResponseEntity<Map> authorizeResponse = restTemplate.getForEntity(urlAuthorizeTransaction, Map.class);

        if (authorizeResponse.getStatusCode() == HttpStatus.OK) {
            String message = (String) authorizeResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        } else {
            return false;
        }
    }

}
