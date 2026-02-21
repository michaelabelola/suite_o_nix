package com.suiteonix.nix.Organization.domain;

import com.suiteonix.nix.Organization.services.Organization;
import com.suiteonix.nix.Organization.services.OrganizationCreateDto;
import com.suiteonix.nix.Organization.services.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final RegisterOrgUseCase registerOrgUseCase;

    @Override
    public Organization registerOrganization(OrganizationCreateDto.WithLogos onboarding) {
        return OrganizationMapper.INSTANCE.dto(registerOrgUseCase.execute(onboarding));
    }
}
