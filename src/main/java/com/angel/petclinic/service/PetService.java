package com.angel.petclinic.service;

import com.angel.petclinic.dto.mapper.PetRequestMapper;
import com.angel.petclinic.dto.mapper.PetResponseMapper;
import com.angel.petclinic.dto.request.PetRequest;
import com.angel.petclinic.dto.request.TypeSetRequest;
import com.angel.petclinic.dto.response.CommandResponse;
import com.angel.petclinic.dto.response.PetResponse;
import com.angel.petclinic.repository.PetRepositry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.angel.petclinic.model.Pet;


import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.angel.petclinic.common.Constants.*;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "PetService")
public class PetService {

    private final UserService userService;
    private final TypeService typeService;
    private final PetRepositry petRepository;
    private final PetRequestMapper petRequestMapper;
    private final PetResponseMapper petResponseMapper;

    public PetResponse findById(long id){
        return petRepository.findById(id)
                .map(petResponseMapper::toDto)
                .orElseThrow(()-> new NoSuchElementException(NOT_FOUND_PET));

    }

    @Transactional(readOnly = true)
    public Page<PetResponse> findAll(Pageable pageable){
        final Page<PetResponse> pets = petRepository.findAll(pageable)
                .map(petResponseMapper::toDto);

        if (pets.isEmpty()){
            throw new NoSuchElementException(NOT_FOUND_RECORD);
        }
        return pets;

    }

    @Transactional(readOnly = true)
    public List<PetResponse> findAllByUserId(long userId){
        final List<PetResponse> pets = petRepository.findAllByUserId(userId).stream()
                .map(petResponseMapper::toDto).toList();

        if (pets.isEmpty())
            throw new NoSuchElementException(NOT_FOUND_RECORD);
        return pets;
    }

    @Transactional(readOnly = true)
    public Map<String, Long> findAllByType(TypeSetRequest types) {
        return petRepository.findAll().stream()
                .filter(pet -> types.getIds().isEmpty() || types.getIds().contains(pet.getType().getId()))
                .collect(Collectors.groupingBy(x -> x.getType().getName(), Collectors.counting()));
        // if we need to return TypeResponse instead of String (type names), we can use this:
        // .collect(Collectors.groupingBy(x -> typeResponseMapper.toDto(x.getType()), Collectors.counting()));
    }

    public CommandResponse create(PetRequest request){
        final Pet pet = petRequestMapper.toEntity(request);
        pet.setType(typeService.getById(request.getTypeId()));
        pet.setUser(userService.getById(request.getUserId()));
        petRepository.save(pet);
        log.info(CREATED_PET);

        return CommandResponse.builder().id(pet.getId()).build();

    }

    public CommandResponse update(PetRequest request){
        if (!petRepository.existsById(request.getId()))
            throw new NoSuchElementException(NOT_FOUND_PET);

        final Pet pet = petRequestMapper.toEntity(request);
        pet.setType(typeService.getById(request.getTypeId()));
        pet.setUser(userService.getById(request.getUserId()));
        petRepository.save(pet);
        log.info(UPDATED_PET);
        return CommandResponse.builder().id(pet.getId()).build();
    }

    public void deleteById(Long id){
        final Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_PET));
                petRepository.delete(pet);
                log.info(DELETED_PET);
    }
}
