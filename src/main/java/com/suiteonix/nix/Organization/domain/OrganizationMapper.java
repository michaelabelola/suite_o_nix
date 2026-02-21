package com.suiteonix.nix.Organization.domain;

import com.suiteonix.nix.Location.LocationMapper;
import com.suiteonix.nix.Organization.services.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {LocationMapper.class})
interface OrganizationMapper {

    OrganizationMapper INSTANCE = Mappers.getMapper(OrganizationMapper.class);

    @Mapping(target = "isSuspended", source = "details.suspended")
    @Mapping(target = "isApproved", source = "details.approved")
    @Mapping(target = "logo", source = "logos.logo")
    @Mapping(target = "logoDark", source = "logos.logoDark")
    @Mapping(target = "coverImage", source = "logos.coverImage")
    @Mapping(target = "coverImageDark", source = "logos.coverImageDark")
    Organization dto(OrganizationModel organizationModel);

    @Mapping(target = "address", source = "address", qualifiedByName = "toDTO")
    Organization.Detailed dtoDetailed(OrganizationModel organizationModel);

    @Mapping(target = "phone", source = "contactPhone")
    @Mapping(target = "email", source = "contactEmail")
    Organization.Contact contactDto(OrganizationModel.ContactModel contactModel);

    Organization.Detail detailDto(OrganizationModel.DetailModel detailModel);

    Organization.Logos logosDto(OrganizationModel.LogosModel logosModel);

    Organization.Socials socialsDto(OrganizationModel.SocialModel socialModel);
}
