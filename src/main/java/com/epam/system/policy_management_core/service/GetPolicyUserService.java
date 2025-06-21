package com.epam.system.policy_management_core.service;

import com.epam.system.policy_management_core.model.PolicyUser;
import com.epam.system.policy_management_core.repository.PolicyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

//@Configuration
//class RestTemplateConfig {
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//}


@Service
@RequiredArgsConstructor
public class GetPolicyUserService {

    @Autowired
    private PolicyUserRepository policyUserRepository;
    private final RestTemplateBuilder restTemplate;
    private static final String EXTNAL_URL = "http://localhost:8080/api/policyUser/get/rt-user";

    public PolicyUser getPolicyUser(String eUserId, WebRequest request){
        System.out.println("inside getPolicyUser using id:: "+eUserId);
        String url = EXTNAL_URL + "/"+eUserId;

        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", bearerToken);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        RestTemplate restTemplate1 = restTemplate.build();
        System.out.println("RestTemplate call for another api :: using url: "+url);

        ResponseEntity<PolicyUser> response = null;
        try {
            response = restTemplate1.exchange(url, HttpMethod.GET, entity, PolicyUser.class);
        } catch (ResourceAccessException e) {
            throw e;
        }
        return response.getBody();
    }

}
