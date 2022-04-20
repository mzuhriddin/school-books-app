package com.example.books.repository;

import com.example.books.entity.Book;
import com.example.books.entity.enums.Class;
import com.example.books.entity.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByAClass(Class classname);
    List<Book> findAllByLanguage(Language language);
    List<Book> findAllByLanguageAndAClass(Language language, Class AClass);
    List<Book> findAllByNameContainingIgnoreCase(String name);
}
