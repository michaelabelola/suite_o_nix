package com.suiteonix.nix.Organization.domain;

import com.suiteonix.nix.Common.ddd.UseCase;
import com.suiteonix.nix.Organization.services.OrgID;
import com.suiteonix.nix.Organization.services.OrganizationCreateDto;
import com.suiteonix.nix.Storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
class RegisterOrgUseCase {

    private final OrganizationRepo organizationRepo;
    private final StorageService storageService;

    @Transactional
    public OrganizationModel execute(OrganizationCreateDto.WithLogos createDto) {
        OrganizationModel model = OrganizationModel.NEW(createDto.data());
        var savedModel = organizationRepo.save(model);
        uploadImages(savedModel.getId(), savedModel.getLogos(), createDto);
        return savedModel;
    }

    private void uploadImages(OrgID id, OrganizationModel.LogosModel logos, OrganizationCreateDto.WithLogos create) {
        Optional.ofNullable(create.logo())
                .ifPresent(multipartFile ->
                        storageService.upload(
                                logos.getLogo(),
                                multipartFile,
                                id.filePath(),
                                "logo"));

        Optional.ofNullable(create.logoDark())
                .ifPresent(multipartFile ->
                        storageService.upload(
                                logos.getLogoDark(),
                                multipartFile,
                                id.filePath(),
                                "logo-dark"));

        Optional.ofNullable(create.coverImage())
                .ifPresent(multipartFile ->
                        storageService.upload(
                                logos.getCoverImage(),
                                multipartFile,
                                id.filePath(),
                                "cover-image"));
        Optional.ofNullable(create.coverImageDark())
                .ifPresent(multipartFile ->
                        storageService.upload(
                                logos.getCoverImageDark(),
                                multipartFile,
                                id.filePath(),
                                "cover-image-dark"));

    }
}
