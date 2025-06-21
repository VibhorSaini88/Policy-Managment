package com.epam.system.policy_management_core.service;

import com.epam.system.policy_management_core.DTO.PolicyUserDTO;
import com.epam.system.policy_management_core.model.PolicyUser;
import com.epam.system.policy_management_core.repository.PolicyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UpdatePolicyUserService {

    @Autowired
    private PolicyUserRepository policyUserRepository;
    private final RestTemplateBuilder restTemplateBuilder;
    private static final String EXTNAL_PUT_URL = "http://localhost:8080/api/policyUser/put/rt-user";
    private static final String EXTNAL_PATCH_URL = "http://localhost:8080/api/policyUser/patch/rt-user";

    public void AllUpdatePolicyUser(PolicyUser policyUser, Long eAppId){
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = EXTNAL_PUT_URL + "/update"+"/"+eAppId;
        HttpEntity<PolicyUser> policyUserHttpEntity = new HttpEntity<>(policyUser);
        restTemplate.put(url,policyUserHttpEntity, PolicyUser.class);
    }

    public PolicyUserDTO partialUpdatePolicyUser(PolicyUserDTO policyUserDTO, Long eAppId){
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = EXTNAL_PATCH_URL + "/partialUpdate"+"/"+eAppId;
        HttpEntity<PolicyUserDTO> policyUserHttpEntity = new HttpEntity<>(policyUserDTO);
        return restTemplate.patchForObject(url,policyUserHttpEntity, PolicyUserDTO.class);
    }


}
