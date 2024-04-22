package com.angel.petclinic.dto.mapper;

import com.angel.petclinic.dto.response.TypeResponse;
import com.angel.petclinic.model.Type;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeResponseMapper {

    Type toEntity(TypeResponse dto);

    TypeResponse toDto(Type entity);
}
