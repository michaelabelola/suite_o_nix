package com.suiteonix.nix.Entity.domain;

import com.suiteonix.nix.Entity.NixEntity;
import com.suiteonix.nix.shared.utils.EntityMapper;
import org.mapstruct.Mapper;

@Mapper
public interface NixEntityMapper extends EntityMapper<NixEntityModel, NixEntity> {
    //    @BeanMapping(ignoreByDefault = true)
    NixEntity map(NixEntityModel model);

}
