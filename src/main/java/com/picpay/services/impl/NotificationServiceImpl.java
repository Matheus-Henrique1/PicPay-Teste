package com.picpay.services.impl;

import com.picpay.domain.user.User;
import com.picpay.dtos.NotificationDTO;
import com.picpay.services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationServiceImpl implements NotificationService {

    public NotificationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private RestTemplate restTemplate;

    @Value("S{url.notification}")
    private String urlNotification;

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);


    @Override
    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

//        ResponseEntity<String> notificationResponse = restTemplate.postForEntity(urlNotification, notificationRequest, String.class);
//
//        if(!(notificationResponse.getStatusCode() == HttpStatus.OK)){
//            logger.info("Serviço de mensagem indisponível no momento.");
//            throw new Exception("O serviço de notificação está fora do ar.");
//        }

        logger.info("Notificação enviada para o usuário.");

    }
}
