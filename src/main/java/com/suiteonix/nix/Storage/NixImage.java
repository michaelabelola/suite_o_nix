package com.suiteonix.nix.Storage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suiteonix.nix.shared.interfaces.EmptyChecker;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NixImage implements EmptyChecker, IFile {

    //    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
//    @JdbcType(TextJdbcType.class)
    @Column(columnDefinition = "TEXT")
    private String url;

    private NixImage(String url) {
        this.url = url;
    }

    @Override
    @Transient
    @JsonIgnore
    public boolean isEmpty() {
        return url == null || url.isEmpty();
    }

    public static NixImage of(NixImage image) {
        return new NixImage(image.url);
    }

    public static NixImage EMPTY() {
        return new NixImage(null);
    }

}
