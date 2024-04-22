package com.angel.petclinic.controller;

import com.angel.petclinic.dto.request.ProfileRequest;
import com.angel.petclinic.dto.request.UserRequest;
import com.angel.petclinic.dto.response.ApiResponse;
import com.angel.petclinic.dto.response.CommandResponse;
import com.angel.petclinic.dto.response.UserResponse;
import com.angel.petclinic.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.Instant;

import static com.angel.petclinic.common.Constants.SUCCESS;
@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final Clock clock;
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> findById(@PathVariable long id) {
        final UserResponse response = userService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }


    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserResponse>>> findAll(Pageable pageable){
        final Page<UserResponse> responses = userService.findAll(pageable);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS , responses));
    }


    @PostMapping
    public ResponseEntity<ApiResponse<CommandResponse>> create(@Valid @RequestBody UserRequest userRequest){
        final CommandResponse response = userService.create(userRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CommandResponse>> update(@Valid @RequestBody ProfileRequest profileRequest){
        final  CommandResponse response = userService.update(profileRequest);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(),SUCCESS, response ));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<CommandResponse>> updateFullName(@Valid @RequestBody ProfileRequest profileRequest) {
        final CommandResponse response = userService.updateFullName(profileRequest);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable long id){
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
