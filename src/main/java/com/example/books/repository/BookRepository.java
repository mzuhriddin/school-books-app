package com.example.books.repository;

import com.example.books.entity.Book;
import com.example.books.entity.enums.Group;
import com.example.books.entity.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByGroupNum(Group classname);

    List<Book> findAllByLanguage(Language language);

    List<Book> findAllByLanguageAndGroupNum(Language language, Group group);

    @Query(value = "select * from book b where b.name like %:name%",nativeQuery = true)
    List<Book> searchBookByName(@Param("name") String n);
}
