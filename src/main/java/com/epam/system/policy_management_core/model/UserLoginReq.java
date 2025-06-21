package com.epam.system.policy_management_core.model;

import lombok.Data;

@Data
public class UserLoginReq {

    private String userName;

    private String password;
}
