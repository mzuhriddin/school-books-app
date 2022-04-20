package com.example.books.dto;

import com.example.books.entity.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {
    @NotBlank
    private String name;
    @NotNull
    private Integer groupId;
    @Size(min = 1)
    private List<String> authors;
    @NotNull
    private Language language;
    @NotNull
    private MultipartFile file, picture;
}
