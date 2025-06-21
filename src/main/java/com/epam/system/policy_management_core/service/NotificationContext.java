package com.epam.system.policy_management_core.service;

import com.epam.system.policy_management_core.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationContext {

    private final Map<String, NotificationStrategy> strategyMap;

//    @Autowired
//    public NotificationContext(Map<String, NotificationStrategy> strategyMap) {
//        this.strategyMap = strategyMap;
//    }


    public void notifyUser(String type, User user){
        NotificationStrategy notificationStrategy = strategyMap.get(type.toLowerCase());
        if(notificationStrategy != null){
            notificationStrategy.sendNotification(user);
        }else{
            System.out.println("not user..");
            throw new IllegalArgumentException("Unknown strategy: " + type);

        }
    }
}
