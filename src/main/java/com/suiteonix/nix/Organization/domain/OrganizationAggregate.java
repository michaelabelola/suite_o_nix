package com.suiteonix.nix.Organization.domain;

import com.suiteonix.nix.Organization.services.Organization;
import com.suiteonix.nix.Organization.services.OrganizationCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationAggregate {

    private final RegisterOrgUseCase registerOrgUseCase;

    public Organization registerOrganization(OrganizationCreateDto.WithLogos create){
        return OrganizationMapper.INSTANCE.dto(registerOrgUseCase.execute(create));
    }
}
