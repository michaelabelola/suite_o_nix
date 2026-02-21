package com.suiteonix.nix.Organization.domain;

import com.suiteonix.nix.Common.audit.IAuditableOwnableEntity;
import com.suiteonix.nix.Common.audit.NixEntityAuditListener;
import com.suiteonix.nix.Common.audit.OwnerEntityListener;
import com.suiteonix.nix.Organization.services.OrgEvents;
import com.suiteonix.nix.Organization.services.OrgID;
import com.suiteonix.nix.Organization.services.OrganizationCreateDto;
import com.suiteonix.nix.Storage.NixImage;
import com.suiteonix.nix.shared.ids.NixID;
import com.suiteonix.nix.shared.principal.Actor;
import com.suiteonix.nix.spi.location.HomeAddressModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Organization")
@Table(name = "organization")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners({NixEntityAuditListener.class, OwnerEntityListener.class})
@Schema(description = "Organization Model")
public class OrganizationModel extends IAuditableOwnableEntity<OrganizationModel> {

    private static final Log log = LogFactory.getLog(OrganizationModel.class);
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    OrgID id;

    @Column(nullable = false)
    String name;

    String shortName;

    String bio;

    @Embedded
    DetailModel details = new DetailModel();

    @Column(nullable = false)
    String industry;

    @Embedded
    HomeAddressModel address = new HomeAddressModel();

    @Embedded
    ContactModel contact = new ContactModel();

    @Embedded
    SocialModel socials = new SocialModel();

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "registered_by"))
    NixID registeredBy = NixID.EMPTY();

    @Embedded
    LogosModel logos = new LogosModel();

    public static OrganizationModel NEW(OrganizationCreateDto query) {
        OrganizationModel model;
        try {
        model = new OrganizationModel();



        if (query == null) return model;

        model.setId(OrgID.NEW());
        model.setName(query.name());
        model.setShortName(query.shortName());
        model.setBio(query.bio());
        model.setIndustry(query.industry());
        model.setRegisteredBy(Actor.ID());
        model.setDetails(DetailModel.NEW(query.detail()));
        model.setAddress(HomeAddressModel.NEW(query.address()));
        model.setSocials(SocialModel.NEW(query.socials()));
        model.setContact(ContactModel.NEW(query.contact()));
        model.registerEvent(OrgEvents.Created.of(model.id, Actor.ID()));
        return model;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new OrganizationModel();
    }

    @Getter
    @Setter
    @Embeddable
    @FieldDefaults(level = AccessLevel.PRIVATE)
    static class ContactModel {
        String contactEmail;
        String contactPhone;

        public static ContactModel NEW(OrganizationCreateDto.Contacts contact) {
            if (contact == null) return new ContactModel();
            return null;
        }
    }

    @Getter
    @Setter
    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @FieldDefaults(level = AccessLevel.PRIVATE)
    static class DetailModel {
        LocalDate dateEstablished;

        String registrationNumber;

        String registrationCountry;

        @Column(name = "about", columnDefinition = "TEXT")
        String about;

        @Column(columnDefinition = "boolean default false")
        Boolean verified;

        @Column(columnDefinition = "boolean default false")
        Boolean approved;

        @Column(columnDefinition = "boolean default false")
        Boolean suspended;

        public static DetailModel NEW(OrganizationCreateDto.Details detail) {
            if (detail == null) return new DetailModel();
            DetailModel model = new DetailModel();
            model.setAbout(detail.about());
            model.setDateEstablished(detail.dateEstablished());
            model.setRegistrationCountry(detail.registrationCountry());
            model.setRegistrationNumber(detail.registrationNumber());
            model.setVerified(false);
            model.setApproved(false);
            model.setSuspended(false);
            return model;
        }
    }

    @Getter
    @Setter
    @Embeddable
    @FieldDefaults(level = AccessLevel.PRIVATE)
    static class LogosModel {
        @Embedded
        @AttributeOverride(name = "url", column = @Column(name = "logo", columnDefinition = "TEXT"))
        NixImage logo = NixImage.EMPTY();

        @Embedded
        @AttributeOverride(name = "url", column = @Column(name = "logo_dark", columnDefinition = "TEXT"))
        NixImage logoDark = NixImage.EMPTY();

        @Embedded
        @AttributeOverride(name = "url", column = @Column(name = "cover_image", columnDefinition = "TEXT"))
        NixImage coverImage = NixImage.EMPTY();

        @Embedded
        @AttributeOverride(name = "url", column = @Column(name = "cover_image_dark", columnDefinition = "TEXT"))
        NixImage coverImageDark = NixImage.EMPTY();

        public static LogosModel NEW(NixImage logo, NixImage logoDark, NixImage coverImage, NixImage coverImageDark) {
            LogosModel model = new LogosModel();
            model.setLogo(logo);
            model.setLogoDark(logoDark);
            model.setCoverImage(coverImage);
            model.setCoverImageDark(coverImageDark);
            return model;
        }
    }

    @Getter
    @Setter
    @Embeddable
    @FieldDefaults(level = AccessLevel.PRIVATE)
    static class SocialModel {
        String website;
        String facebook;
        String twitter;
        String instagram;
        String linkedin;
        String youtube;
        String snapchat;
        String pinterest;

        public static SocialModel NEW(OrganizationCreateDto.Socials socials) {
            if (socials == null) return new SocialModel();
            SocialModel model = new SocialModel();
            model.setWebsite(socials.website());
            model.setFacebook(socials.facebook());
            model.setTwitter(socials.twitter());
            model.setInstagram(socials.instagram());
            model.setLinkedin(socials.linkedin());
            model.setYoutube(socials.youtube());
            model.setSnapchat(socials.snapchat());
            model.setPinterest(socials.pinterest());
            return model;
        }
    }
}
