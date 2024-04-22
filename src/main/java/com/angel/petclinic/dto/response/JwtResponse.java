package com.angel.petclinic.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class JwtResponse {

    private String type;
    private String token;
    private Long id;
    private String username;
    private List<String> roles;
}
