package com.angel.petclinic.service;

import com.angel.petclinic.model.Role;
import com.angel.petclinic.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public List<Role> findAll(){
        return repository.findAll();
    }
}
