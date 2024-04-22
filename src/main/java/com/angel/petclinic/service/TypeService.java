package com.angel.petclinic.service;

import com.angel.petclinic.dto.mapper.TypeRequestMapper;
import com.angel.petclinic.dto.mapper.TypeResponseMapper;
import com.angel.petclinic.dto.request.TypeRequest;
import com.angel.petclinic.dto.response.CommandResponse;
import com.angel.petclinic.dto.response.TypeResponse;
import com.angel.petclinic.exception.ElementAlreadyExistsException;
import com.angel.petclinic.model.Type;
import com.angel.petclinic.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.events.Event;

import java.util.NoSuchElementException;

import static com.angel.petclinic.common.Constants.*;

@Slf4j(topic = "TypeService")
@Service
@RequiredArgsConstructor
public class TypeService {

    private final TypeRepository typeRepository;
    private final TypeRequestMapper typeRequestMapper;
    private final TypeResponseMapper typeResponseMapper;

    public TypeResponse findById(long id){
        return typeRepository.findById(id)
                .map(typeResponseMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_TYPE));
    }

    public Type getById(long id){
        return typeRepository.findById(id).orElseThrow(()-> new NoSuchElementException(NOT_FOUND_TYPE));
    }

    @Transactional(readOnly = true)
    public Page<TypeResponse> findAll(Pageable pageable){
        Page<TypeResponse> typeResponsePage = typeRepository.findAll(pageable)
                .map(typeResponseMapper::toDto);
        if(typeResponsePage.isEmpty())
             throw new NoSuchElementException(NOT_FOUND_RECORD);
        return typeResponsePage;

    }

    public CommandResponse create(TypeRequest typeRequest){
        if (typeRepository.existsByNameIgnoreCase(typeRequest.getName()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_TYPE);
        final Type type = typeRequestMapper.toEntity(typeRequest);
        typeRepository.save(type);
        log.info(CREATED_TYPE);
        return CommandResponse.builder().id(type.getId()).build();
    }

    public CommandResponse update(TypeRequest request){
        final Type type = typeRepository.findById(request.getId())
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_TYPE));

        if(!request.getName().equalsIgnoreCase(type.getName()) && typeRepository.existsByNameIgnoreCase(request.getName()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_TYPE);

        typeRepository.save(typeRequestMapper.toEntity(request));
        log.info(UPDATED_TYPE);
        return CommandResponse.builder().id(type.getId()).build();

    }

    public void delete(long id){
        final Type type = typeRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException(NOT_FOUND_TYPE));
        typeRepository.delete(type);
        log.info(DELETED_TYPE);
    }
}
