package com.suiteonix.db.nix.spi;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.suiteonix.db.nix.shared.audit.AuditSection;
import com.suiteonix.db.nix.shared.ids.NixID;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.locationtech.jts.geom.Point;

import java.util.HashSet;
import java.util.Set;

public record Location(
        String id,
        String name,
        String description,
        @Schema(example = "234 Main Street")
        @Column(name = "street") String street,
        @Schema(example = "Kitchener") String city,
        @Schema(example = "ON") String state,
        @Schema(example = "CA") String country,
        @Schema(example = "Q4E E2A") String zipcode,
        @Schema(example = "23.4531") Double latitude,
        @Schema(example = "12.4354") Double longitude,
        @JsonUnwrapped(prefix = "owner_") NixID ownerId,
        AuditSection audit
) {
    public static Location of(String street, String city, String state, String country, String zipcode, Double latitude, Double longitude, String id) {
        return new Location(id, null, null, street, city, state, country, zipcode, latitude, longitude, null, null);
    }

    public static Location of(String id, String name, String description, String street, String city, String state, String country, String zipcode, Double latitude, Double longitude, NixID ownerId, AuditSection audit) {
        return new Location(id, name, description, street, city, state, country, zipcode, latitude, longitude, ownerId, audit);
    }

    @Schema(name = "Location.Create")
    public record Create(
            @Schema(example = "Col Location") String name,
            @Schema(example = "This is the location of College View Residence") String description,
            @Schema(example = "234 Main Street")
            @Column(name = "street") String street,
            @Schema(example = "Kitchener") String city,
            @Schema(example = "ON") String state,
            @Schema(example = "CA") String country,
            @Schema(example = "Q4E E2A") String zipcode,
            @Schema(example = "23.4531") Double latitude,
            @Schema(example = "12.4354") Double longitude
    ) {
        public Create {
//            validateCountry();
//            validateState();
        }

        public static Create of(String name, String description, String street, String city, String state, String country, String zipcode, Double latitude, Double longitude) {
            return new Create(name, description, street, city, state, country, zipcode, latitude, longitude);
        }

//        public void validateCountry() {
//            if (country == null) return;
//            LocationService.INSTANCE().getCountryByIso2(country).orElseThrow(() -> EX.badRequest("INVALID_LOCATION_COUNTRY", "Invalid Country"));
//        }
//
//        public void validateState() {
//            if (state == null) return;
//            LocationService.INSTANCE().getStateByCountryIso2AndIso2(country, state).orElseThrow(() -> EX.badRequest("INVALID_LOCATION_STATE", "Invalid State"));
//        }
    }

    @Schema(name = "Location.Update")
    public record Update(
            @Schema(example = "Col Location") String name,
            @Schema(example = "This is the location of College View Residence") String description,
            @Schema(example = "234 Main Street")
            @Column(name = "street") String street,
            @Schema(example = "Kitchener") String city,
            @Schema(example = "ON") String state,
            @Schema(example = "CA") String country,
            @Schema(example = "Q4E E2A") String zipcode,
            @Schema(example = "23.4531") Double latitude,
            @Schema(example = "12.4354") Double longitude
    ) {
        public static Update of(String name, String description, String street, String city, String state, String country, String zipcode, Double latitude, Double longitude) {
            return new Update(name, description, street, city, state, country, zipcode, latitude, longitude);
        }
    }

    @Schema(name = "Location.query")
    public record Query(
            String id,
            String name, String description,
            String street, String city, String state, String country,
            String zipcode, Double latitude, Double longitude
    ) {

    }

    /**
     * @param latitude  the points latitude
     * @param longitude the points longitude
     * @param meters    the radius in meters to search within
     */
    public record PointQuery(
            Double latitude,
            Double longitude,
            Double meters
    ) {
        public Set<Predicate> generatePredicates(Path<Object> root, CriteriaBuilder cb) {

            Set<Predicate> predicates = new HashSet<>();
            if (latitude == null || longitude == null || meters == null)
                return predicates;


            Expression<Boolean> within =
                    cb.function(
                            "ST_DWithin",
                            Boolean.class,
                            root.get("location"),
                            cb.function(
                                    "ST_SetSRID",
                                    Point.class,
                                    cb.function("ST_MakePoint", Point.class,
                                            cb.literal(longitude()), cb.literal(latitude())),
                                    cb.literal(4326)
                            ),
                            cb.literal(meters)
                    );
            return predicates;
        }
    }
}
