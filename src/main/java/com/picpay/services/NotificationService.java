package com.picpay.services;

import com.picpay.domain.user.User;

public interface NotificationService {

    void sendNotification(User user, String message) throws Exception;

}
