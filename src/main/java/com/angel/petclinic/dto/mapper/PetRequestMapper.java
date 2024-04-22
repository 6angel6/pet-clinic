package com.angel.petclinic.dto.mapper;

import com.angel.petclinic.dto.request.PetRequest;
import com.angel.petclinic.model.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PetRequestMapper {

   // @Mapping(target = "name", expression = "java(org.apache.commons.text.WordUtils.capitalizeFully(dto.getName()))")
    Pet toEntity(PetRequest dto);

    PetRequest toDto(Pet entity);
}