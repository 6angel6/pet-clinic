package com.angel.petclinic.dto.mapper;

import com.angel.petclinic.dto.response.UserResponse;
import com.angel.petclinic.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.text.MessageFormat;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    User toEntity(UserResponse dto);

    UserResponse toDto(User entity);

    @AfterMapping
    default void setFullName(@MappingTarget UserResponse dto, User entity) {
        dto.setFullName(MessageFormat.format("{0} {1}", entity.getFirstName(), entity.getLastName()));

    }
}
