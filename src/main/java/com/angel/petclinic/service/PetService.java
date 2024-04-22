package com.angel.petclinic.service;

import com.angel.petclinic.dto.mapper.PetRequestMapper;
import com.angel.petclinic.dto.mapper.PetResponseMapper;
import com.angel.petclinic.dto.response.PetResponse;
import com.angel.petclinic.repository.PetRepositry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static com.angel.petclinic.common.Constants.NOT_FOUND_PET;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "PetService")
public class PetService {

    private final UserService userService;
    private final TypeService typeService;
    private final PetRepositry petRepositry;
    private final PetRequestMapper petRequestMapper;
    private final PetResponseMapper petResponseMapper;

    public PetResponse findById(long id){
        return petRepositry.findById(id)
                .map(petResponseMapper::toDto)
                .orElseThrow(()-> new NoSuchElementException(NOT_FOUND_PET));

    }

    public

}
