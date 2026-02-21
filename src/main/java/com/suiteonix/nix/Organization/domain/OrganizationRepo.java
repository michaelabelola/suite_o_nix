package com.suiteonix.nix.Organization.domain;

import com.suiteonix.nix.Organization.services.OrgID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepo extends JpaRepository<OrganizationModel, OrgID> {
}