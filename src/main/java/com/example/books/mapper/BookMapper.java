package com.example.books.mapper;

import com.example.books.dto.BookDto;
import com.example.books.entity.Attachment;
import com.example.books.entity.Book;
import com.example.books.entity.enums.Group;
import com.example.books.entity.enums.Language;
import lombok.SneakyThrows;
import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "file", expression = "java(attachment(dto.getFile()))")
    @Mapping(target = "picture", expression = "java(attachment(dto.getPicture()))")
    @Mapping(target = "groupNum", expression = "java(aClass(dto.getGroup()))")
    @Mapping(target = "language", expression = "java(lang(dto.getLanguage()))")
    @Mapping(target = "id", ignore = true)
    Book toEntity(BookDto dto);


    @Named("aClass")
    default Group aClass(int aClass) {
        return Group.findByKey(aClass);
    }

    @Named("lang")
    default Language lang(String lang) {
        return Language.findByKey(lang);
    }

    @SneakyThrows
    @Named("attachment")
    default Attachment attachment(MultipartFile file) {
        return Attachment.builder()
                .name(file.getOriginalFilename())
                .bytes(file.getBytes())
                .size(file.getSize())
                .type(file.getContentType())
                .build();
    }

    @SneakyThrows
    @Named("attachmentEdit")
    default Attachment attachmentEdit(MultipartFile file, Attachment attachment) {
        attachment.setBytes(file.getBytes());
        attachment.setName(file.getOriginalFilename());
        attachment.setSize(file.getSize());
        attachment.setType(file.getContentType());
        return attachment;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "groupNum", ignore = true)
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "file", expression = "java(attachmentEdit(dto.getFile(), book.getFile()))")
    @Mapping(target = "picture", expression = "java(attachmentEdit(dto.getPicture(), book.getPicture()))")
    void update(BookDto dto, @MappingTarget Book book);

    @AfterMapping
    default void handleClassLanguage(BookDto dto, @MappingTarget Book book) {
        book.setLanguage(Language.findByKey(dto.getLanguage()));
        book.setGroupNum(Group.findByKey(dto.getGroup()));
    }
}
