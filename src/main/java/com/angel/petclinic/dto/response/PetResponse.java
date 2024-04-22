package com.angel.petclinic.dto.response;

import lombok.Data;

@Data
public class PetResponse {

    private Long id;
    private String name;
    private TypeResponse type;
    private UserResponse user;
}
