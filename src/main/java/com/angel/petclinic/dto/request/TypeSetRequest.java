package com.angel.petclinic.dto.request;

import lombok.Data;

import java.util.Set;

@Data
public class TypeSetRequest {

    private Set<Long> ids;
}
