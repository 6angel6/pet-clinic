package com.angel.petclinic.controller;

import com.angel.petclinic.dto.request.PetRequest;
import com.angel.petclinic.dto.request.TypeSetRequest;
import com.angel.petclinic.dto.response.ApiResponse;
import com.angel.petclinic.dto.response.CommandResponse;
import com.angel.petclinic.dto.response.PetResponse;
import com.angel.petclinic.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static com.angel.petclinic.common.Constants.SUCCESS;

@RestController
@RequestMapping("/api/v1/pets")
@RequiredArgsConstructor
public class PetController {

private final PetService petService;
private final Clock clock;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PetResponse>> findById(@PathVariable long id){
        final PetResponse response = petService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<List<PetResponse>>> findAllByUserId(@PathVariable long userId) {
        final List<PetResponse> response = petService.findAllByUserId(userId);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    @PostMapping("/types")
    public ResponseEntity<ApiResponse<Map<String, Long>>> findAllByType(@Valid @RequestBody TypeSetRequest request) {
        final Map<String, Long> response = petService.findAllByType(request);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PetResponse>>> findAll(Pageable pageable){
        final Page<PetResponse> responses = petService.findAll(pageable);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(),SUCCESS, responses));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CommandResponse>> create(@Valid @RequestBody PetRequest request){
        final CommandResponse response = petService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(clock).toEpochMilli(),SUCCESS, response ));

    }

    @PutMapping
    public ResponseEntity<ApiResponse<CommandResponse>> update(@Valid @RequestBody PetRequest request){
        final CommandResponse response = petService.update(request);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable long id ){
        petService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
