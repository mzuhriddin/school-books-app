package com.example.books.repository;

import com.example.books.entity.Book;
import com.example.books.entity.enums.Group;
import com.example.books.entity.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByGroupNum(Group classname);
    List<Book> findAllByLanguage(Language language);
    List<Book> findAllByLanguageAndGroupNum(Language language, Group group);
    List<Book> findAllByNameContainingIgnoreCase(String name);
}
