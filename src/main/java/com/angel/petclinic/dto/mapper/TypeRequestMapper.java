package com.angel.petclinic.dto.mapper;

import com.angel.petclinic.dto.request.TypeRequest;
import com.angel.petclinic.model.Type;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TypeRequestMapper {

    @Mapping(target = "name", expression = "java(org.apache.commons.text.WordUtils.capitalizeFully(dto.getName()))")
    Type toEntity(TypeRequest dto);

    TypeRequest toDto(Type entity);


}
