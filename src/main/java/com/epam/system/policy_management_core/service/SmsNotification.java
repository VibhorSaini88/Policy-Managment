package com.epam.system.policy_management_core.service;

import com.epam.system.policy_management_core.model.User;
import org.springframework.stereotype.Component;

@Component("sms")
public class SmsNotification implements NotificationStrategy{

    @Override
    public void sendNotification(User user) {
        System.out.println("Sms notification.."+user.getName()+" :: "+user.getEmail()+" :: "+user.getId());
    }
}
