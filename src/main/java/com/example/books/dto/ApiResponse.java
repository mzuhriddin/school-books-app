package com.example.books.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse<T> {
    private String message;
    private boolean success;
    private T object;

    public ApiResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }
}
