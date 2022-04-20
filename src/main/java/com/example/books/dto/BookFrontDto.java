package com.example.books.dto;

import com.example.books.entity.enums.Class;
import com.example.books.entity.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookFrontDto {
    private String name;
    private Language language;
    private Class classNumber;
    private AttachmentDto file, picture;
    private int year;
    private List<String> authors;
}
