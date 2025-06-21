package com.epam.system.policy_management_core.service;


import com.epam.system.policy_management_core.model.User;

import java.util.Optional;

public interface NotificationStrategy {
    public void sendNotification(User user);

}
