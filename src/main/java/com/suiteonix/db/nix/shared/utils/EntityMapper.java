package com.suiteonix.db.nix.shared.utils;

import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.stream.Stream;

public interface EntityMapper<M, DTO> {


    DTO map(M model);

    default Page<DTO> mapPage(Page<M> page) {
        return page.map(this::map);
    }

    default Stream<DTO> mapPage(Collection<M> page) {
        return page.stream().map(this::map);
    }

    default Stream<DTO> mapStream(Stream<M> stream) {
        return stream.map(this::map);
    }

}
