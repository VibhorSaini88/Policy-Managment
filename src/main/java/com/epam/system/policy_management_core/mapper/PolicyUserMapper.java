package com.epam.system.policy_management_core.mapper;

import com.epam.system.policy_management_core.DTO.PolicyUserDTO;
import com.epam.system.policy_management_core.model.PolicyUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PolicyUserMapper {

    void updatePolicyUserFromDto(PolicyUserDTO policyUserDTO, @MappingTarget PolicyUser policyUser);

}
