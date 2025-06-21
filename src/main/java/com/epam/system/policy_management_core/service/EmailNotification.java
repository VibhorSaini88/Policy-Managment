package com.epam.system.policy_management_core.service;

import com.epam.system.policy_management_core.model.User;
import org.springframework.stereotype.Component;

@Component("email")
public class EmailNotification implements NotificationStrategy{

    @Override
    public void sendNotification(User user) {
        System.out.println("Email Notification..."+user.getName()+" :: "+user.getEmail()+" :: "+user.getId());
    }
}
