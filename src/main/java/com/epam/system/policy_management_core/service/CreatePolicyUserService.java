package com.epam.system.policy_management_core.service;

import com.epam.system.policy_management_core.model.PolicyUser;
import com.epam.system.policy_management_core.repository.PolicyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

@Service
@RequiredArgsConstructor
public class CreatePolicyUserService {

    @Autowired
    private final PolicyUserRepository policyUserRepository;
    private final RestTemplateBuilder restTemplateBuilder;
    private static final String EXTNAL_URL = "http://localhost:8080/api/policyUser/post/rt-user";


    public PolicyUser createPolicyUser(PolicyUser policyUser, WebRequest request){
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = EXTNAL_URL + "/create";

        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", bearerToken);

        HttpEntity<Object> entity = new HttpEntity<>(policyUser, httpHeaders);

        return restTemplate.postForObject(url,entity, PolicyUser.class);
    }

}
