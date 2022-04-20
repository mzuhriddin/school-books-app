package com.example.books.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto {
    @NotBlank(message = "username kiritilishi shart")
    private String username;
    @NotBlank(message = "password kiritilishi shart")
    private String password;
}
