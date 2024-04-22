package com.angel.petclinic.dto.mapper;


import com.angel.petclinic.dto.response.PetResponse;
import com.angel.petclinic.model.Pet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetResponseMapper {

    Pet toEntity(PetResponse dto);

    PetResponse toDto(Pet entity);
}
