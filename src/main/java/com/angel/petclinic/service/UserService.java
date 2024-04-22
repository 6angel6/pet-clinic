package com.angel.petclinic.service;

import com.angel.petclinic.dto.mapper.UserRequestMapper;
import com.angel.petclinic.dto.mapper.UserResponseMapper;
import com.angel.petclinic.dto.request.ProfileRequest;
import com.angel.petclinic.dto.request.UserRequest;
import com.angel.petclinic.dto.response.CommandResponse;
import com.angel.petclinic.dto.response.UserResponse;
import com.angel.petclinic.exception.ElementAlreadyExistsException;
import com.angel.petclinic.model.Role;
import com.angel.petclinic.model.RoleType;
import com.angel.petclinic.model.User;
import com.angel.petclinic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.text.WordUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.angel.petclinic.common.Constants.*;

@Slf4j(topic = "UserService")
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserResponseMapper userResponseMapper;
    private final UserRequestMapper userRequestMapper;

    public UserResponse findById(long id){
        return userRepository.findById(id).map(userResponseMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_USER));
    }

    public User getById(long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException(NOT_FOUND_USER));
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> findAll(Pageable pageable){
        final Page<UserResponse> users = userRepository.findAll(pageable)
                .map(userResponseMapper::toDto);
        if (users.isEmpty()){
            throw new NoSuchElementException(NOT_FOUND_RECORD);
        }
        return users;
    }

    public CommandResponse create(UserRequest request){
        if(userRepository.existsByUsernameIgnoreCase(request.getUsername()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_USER);

        final Set<Role> roles = new HashSet<>(Arrays.asList(new Role(1L, RoleType.ROLE_USER)));

        if (request.getRoles() != null && request.getRoles().contains(RoleType.ROLE_ADMIN.name()))
            roles.add(new Role(2L, RoleType.ROLE_ADMIN));


        final User user = userRequestMapper.toEntity(request);
        user.setRoles(roles);
        userRepository.save(user);
        log.info(CREATED_USER);

        return CommandResponse.builder().id(user.getId()).build();
    }

    public CommandResponse update(ProfileRequest request){
        final User user = userRepository.findById(request.getId()).orElseThrow(() -> new NoSuchElementException(NOT_FOUND_USER));

        if (request.getRoles() != null && request.getRoles().contains(RoleType.ROLE_ADMIN.name()))
            user.addRole(new Role(2L, RoleType.ROLE_ADMIN));
        else
            user.removeRole(new Role(2L, RoleType.ROLE_ADMIN));

        user.setFirstName(WordUtils.capitalizeFully(request.getFirstName()));
        user.setLastName(WordUtils.capitalizeFully(request.getLastName()));
        userRepository.save(user);
        log.info(UPDATED_USER);
        return CommandResponse.builder().id(user.getId()).build();
    }

    public CommandResponse updateFullName(ProfileRequest request){
        final User user = userRepository.findById(request.getId()).orElseThrow(() -> new NoSuchElementException(NOT_FOUND_USER));

        user.setFirstName(WordUtils.capitalizeFully(request.getFirstName()));
        user.setLastName(WordUtils.capitalizeFully(request.getLastName()));
        userRepository.save(user);
        log.info(UPDATED_USER);
        return CommandResponse.builder().id(user.getId()).build();
    }

    public void deleteById(long id){
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_USER));
        userRepository.delete(user);
        log.info(DELETED_USER);
    }
}
