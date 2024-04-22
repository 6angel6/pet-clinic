package com.angel.petclinic.controller;

import com.angel.petclinic.dto.request.ProfileRequest;
import com.angel.petclinic.dto.request.TypeRequest;
import com.angel.petclinic.dto.response.ApiResponse;
import com.angel.petclinic.dto.response.CommandResponse;
import com.angel.petclinic.dto.response.TypeResponse;
import com.angel.petclinic.service.TypeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.Instant;

import static com.angel.petclinic.common.Constants.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/type")
public class TypeController {

    private final TypeService typeService;
    private final Clock clock;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TypeResponse>>> findAll(Pageable pageable){
        final Page<TypeResponse> typeResponsePage = typeService.findAll(pageable);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, typeResponsePage));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TypeResponse>> findById(@PathVariable Long id){
        final TypeResponse typeResponse = typeService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, typeResponse));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CommandResponse>> create(@Valid @RequestBody TypeRequest typeRequest){
        final  CommandResponse commandResponse = typeService.create(typeRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, commandResponse));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CommandResponse>> update(@Valid @RequestBody TypeRequest typeRequest){
        final CommandResponse commandResponse =  typeService.update(typeRequest);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS , commandResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable long id){
        typeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}

