package com.epam.system.policy_management_core.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserLoginRes {

    private final String token;

}
