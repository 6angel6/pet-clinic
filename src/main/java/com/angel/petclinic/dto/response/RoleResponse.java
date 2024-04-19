package com.angel.petclinic.dto.response;

import com.angel.petclinic.model.RoleType;
import lombok.Data;
import lombok.Lombok;

@Data
public class RoleResponse {

    Long id;
    private RoleType type;
}
