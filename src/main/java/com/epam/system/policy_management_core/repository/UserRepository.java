package com.epam.system.policy_management_core.repository;

import com.epam.system.policy_management_core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
