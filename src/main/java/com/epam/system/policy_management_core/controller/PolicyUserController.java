package com.epam.system.policy_management_core.controller;

import com.epam.system.policy_management_core.DTO.PolicyUserDTO;
import com.epam.system.policy_management_core.mapper.PolicyUserMapper;
import com.epam.system.policy_management_core.model.PolicyUser;
import com.epam.system.policy_management_core.model.User;
import com.epam.system.policy_management_core.repository.PolicyUserRepository;
import com.epam.system.policy_management_core.service.CreatePolicyUserService;
import com.epam.system.policy_management_core.service.GetPolicyUserService;
import com.epam.system.policy_management_core.service.NotificationContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

import java.util.MissingFormatArgumentException;
import java.util.Optional;

@RestController
@RequestMapping("/api/policyUser")
@RequiredArgsConstructor
@Slf4j
public class PolicyUserController {
    private final NotificationContext notificationContext;
    private final CreatePolicyUserService createPolicyUserService;
    private final GetPolicyUserService getPolicyUserService;
    private final PolicyUserRepository policyUserRepository;
    private PolicyUserMapper policyUserMapper;


    @PostMapping(path = "/post/rt-user/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PolicyUser> createPolicyUserByRT(@Valid @RequestBody PolicyUser policyUser, WebRequest webRequest) throws Exception{
        System.out.println("inside createPolicy RT controller..");
        PolicyUser policyUser1 = policyUserRepository.save(policyUser);
        log.info("inside create user....");
        return ResponseEntity.status(HttpStatus.CREATED).body(policyUser1);
    }

    @PutMapping(path = "/put/rt-user/update/{eAppId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPolicyUserByRT(@Valid @RequestBody PolicyUser policyUser, @PathVariable Long eAppId, WebRequest webRequest) throws Exception{
        System.out.println("inside createPolicy RT controller..");
        if(policyUser.getId() == null){
            System.out.println("User ID doesn't exist in application body");
            throw new MissingFormatArgumentException("User ID doesn't exist in application body");
        }
        if(!policyUser.getId().equals(eAppId)){
            System.out.println("User ID doesn't match application ID");
            throw new IllegalArgumentException("User ID doesn't match application ID");
        }
        policyUser.setId(eAppId);
        try{
            System.out.println("proceed with eappid...");
            PolicyUser policyUser1 = policyUserRepository.save(policyUser);
            return ResponseEntity.status(HttpStatus.OK).body(policyUser1);
        }catch (DataAccessException e){
            log.error("Database error while saving policy user", e);
            throw new InternalException("Error processing your request");
        }

    }

    @GetMapping(path = "/get/rt-user/{eUserId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PolicyUser getPolicyUser(@PathVariable Long eUserId){
        System.out.println("inside getPolicyUser::call to DAO using findById() using url: /get/rt-user/{eUserId}");
        Optional<PolicyUser> userOptional = policyUserRepository.findById(eUserId);
        return userOptional.orElseThrow(()->new ResourceAccessException("User Not found"));
    }

    @PatchMapping(path = "/patch/rt-user/partialUpdate/{eUserId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PolicyUser partialUpdatePolicyUser(@PathVariable Long eUserId ,@RequestBody PolicyUserDTO policyUserDTO){
        PolicyUser policyUser = policyUserRepository.findById(eUserId)
                                   .orElseThrow(()->new ResourceAccessException("User Not found"));
        policyUserMapper.updatePolicyUserFromDto(policyUserDTO, policyUser);
        return policyUserRepository.save(policyUser);
    }

}
