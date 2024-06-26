package com.angel.petclinic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private Long timestamp;
    private final String message;
    private final T data;

    public ApiResponse(Long timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
        this.data = null;
    }
}
