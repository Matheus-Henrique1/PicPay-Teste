package com.picpay.services.impl;

import com.picpay.config.ModelMapperConfig;
import com.picpay.domain.transaction.Transaction;
import com.picpay.domain.user.User;
import com.picpay.dtos.ReturnTransactionDTO;
import com.picpay.dtos.TransactionDTO;
import com.picpay.repositories.TransactionRepository;
import com.picpay.services.NotificationService;
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
                                  RestTemplate restTemplate, NotificationService notificationService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.restTemplate = restTemplate;
        this.notificationService = notificationService;
    }

    private TransactionRepository transactionRepository;
    private UserService userService;
    private RestTemplate restTemplate;
    private NotificationService notificationService;

    @Value("${url.authorize.transaction}")
    private String urlAuthorizeTransaction;

    @Override
    public ReturnTransactionDTO createTransacation(TransactionDTO transactionDTO) throws Exception {
        User sender = userService.findById(transactionDTO.getSenderId());
        User receiver = userService.findById(transactionDTO.getReceiveId());

        userService.validateTransaction(sender, transactionDTO.getValue());

        if (!authorizeTransaction(sender, transactionDTO.getValue())) {
            throw new Exception("Transação não autorizada.");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transactionDTO.getValue());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimesTamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDTO.getValue()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.getValue()));

        Transaction transaction = transactionRepository.save(newTransaction);
        ReturnTransactionDTO dto = ModelMapperConfig.convertTransactionToDto(transaction);
        userService.saveUser(sender);
        userService.saveUser(receiver);

        notificationService.sendNotification(sender, "Transação enviada com sucesso!");
        notificationService.sendNotification(receiver, "Transação recebida com sucesso!");

        return dto;
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
