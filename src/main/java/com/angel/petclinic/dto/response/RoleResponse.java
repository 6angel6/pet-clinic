package com.angel.petclinic.dto.response;

import com.angel.petclinic.model.RoleType;
import lombok.Data;

@Data
public class RoleResponse {

    private Long id;
    private RoleType type;
}
