package com.epam.system.policy_management_core.repository;

import com.epam.system.policy_management_core.model.PolicyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface PolicyUserRepository extends JpaRepository<PolicyUser, Long> {
     @Override
     Page<PolicyUser> findAll(Pageable pageable);
}
