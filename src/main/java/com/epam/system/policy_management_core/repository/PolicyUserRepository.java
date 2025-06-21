package com.epam.system.policy_management_core.repository;

import com.epam.system.policy_management_core.model.PolicyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyUserRepository extends JpaRepository<PolicyUser, Long> {

}
