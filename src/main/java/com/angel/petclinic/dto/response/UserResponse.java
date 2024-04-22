package com.angel.petclinic.dto.response;

import lombok.Data;

import java.util.Set;

@Data
public class UserResponse {

    private Long Id;
    private String username;
    private String firstName;
    private String lastName;
    private String fullName;
    private Set<RoleResponse> roles;
}
