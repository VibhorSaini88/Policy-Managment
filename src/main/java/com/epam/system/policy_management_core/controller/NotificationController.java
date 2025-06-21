package com.epam.system.policy_management_core.controller;

import com.epam.system.policy_management_core.DTO.PolicyUserDTO;
import com.epam.system.policy_management_core.component.ApiResponse;
import com.epam.system.policy_management_core.model.PolicyUser;
import com.epam.system.policy_management_core.model.User;
import com.epam.system.policy_management_core.repository.PolicyUserRepository;
import com.epam.system.policy_management_core.service.CreatePolicyUserService;
import com.epam.system.policy_management_core.service.GetPolicyUserService;
import com.epam.system.policy_management_core.service.NotificationContext;
import com.epam.system.policy_management_core.service.UpdatePolicyUserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/policy")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationContext notificationContext;
    private final CreatePolicyUserService createPolicyUserService;
    private final UpdatePolicyUserService updatePolicyUserService;
    private final GetPolicyUserService getPolicyUserService;
    private final PolicyUserRepository policyUserRepository;

    @PostMapping(path = "/create/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PolicyUser> createPolicyUser(@Valid @RequestBody PolicyUser policyUser, WebRequest webRequest) throws Exception{
        System.out.println("inside createPolicy controller..");
        PolicyUser policyUser1 = createPolicyUserService.createPolicyUser(policyUser, webRequest);
        //webRequest.getDescription(false).replace("uri=", "");
        log.info("inside create user....");
        return ResponseEntity.status(HttpStatus.CREATED).body(policyUser1);
    }

    @PostMapping(path = "/create/rt-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PolicyUser> createPolicyUserByRT(@Valid @RequestBody PolicyUser policyUser, WebRequest webRequest) throws Exception{
        System.out.println("inside createPolicy RT controller..");
        PolicyUser policyUser1 = createPolicyUserService.createPolicyUser(policyUser, webRequest);
        //webRequest.getDescription(false).replace("uri=", "");
        log.info("inside create user....");
        return ResponseEntity.status(HttpStatus.CREATED).body(policyUser1);
    }

    @PutMapping(path = "/update/rt-user/{eAppId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PolicyUser> updatePolicyUserByRT(@Valid @RequestBody PolicyUser policyUser, @PathVariable Long eAppId, WebRequest webRequest) throws Exception{
        System.out.println("inside createPolicy RT controller..");
        updatePolicyUserService.AllUpdatePolicyUser(policyUser, eAppId);
        //webRequest.getDescription(false).replace("uri=", "");
        log.info("inside create user....");
        return ResponseEntity.status(HttpStatus.CREATED).body(policyUser);
    }

    @PostMapping({"/notify" ,"/notify/{type}"})
    public String notify(@PathVariable(required = false) String type, @RequestParam(defaultValue = "guest") List<Integer> ids, @RequestParam(defaultValue = "guest") String name, @RequestBody User user){
        notificationContext.notifyUser(type, user);
        return "Notification send using: type: "+type+" :: Email: "+user.getEmail()+" :: sms: "+user.getSms()+" :: name: "+user.getName()+" "+" :: "+user.getId();
    }

    @GetMapping(path = "/get/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')"    )
    public ResponseEntity<PolicyUser> getPolicyUser(@PathVariable("id") String eUserId, @RequestParam(required = false) String name, WebRequest request){
        System.out.println("getPolicyUser method call:: /get/user/{id}");
        PolicyUser user = getPolicyUserService.getPolicyUser(eUserId, request);
        return ResponseEntity.status(HttpStatus.FOUND).body(user);
    }

    @GetMapping(path = "/get/userTest/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PolicyUser> getPolicyUserTest(@PathVariable("id") List<String> eUserId, @RequestParam(required = false) String name, WebRequest webRequest){
        PolicyUser user = getPolicyUserService.getPolicyUser(eUserId.get(0), webRequest);
        return ResponseEntity.status(HttpStatus.FOUND).body(user);
    }

    @PatchMapping(path = "/partialUpdate/rt-user/{eAppId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PolicyUserDTO> partialUpdatePolicyUserByRT(@Valid @RequestBody PolicyUserDTO policyUserDTO, @PathVariable Long eAppId, WebRequest webRequest) throws Exception{
        System.out.println("inside createPolicy RT controller..");
        policyUserDTO = updatePolicyUserService.partialUpdatePolicyUser(policyUserDTO, eAppId);
        //webRequest.getDescription(false).replace("uri=", "");
        log.info("inside create user....");
        return ResponseEntity.status(HttpStatus.CREATED).body(policyUserDTO);
    }




}
