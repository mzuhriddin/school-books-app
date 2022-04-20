package com.example.books.service;

import com.example.books.dto.ApiResponse;
import com.example.books.dto.BookDto;
import com.example.books.entity.Book;
import com.example.books.entity.User;
import com.example.books.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record BookService(BookRepository bookRepository) {
    public ApiResponse add(BookDto bookDto) {
        bookRepository.save(Book.builder()
                .data(bookDto.getData())
                .name(bookDto.getName())
                .aClass(bookDto.getAClass())
                .language(bookDto.getLanguage())
                .build());
        return ApiResponse.builder().message("ADDED").success(true).build();
    }

    public ApiResponse edit(Integer id, BookDto bookDto) {
        Optional<Book> byId = bookRepository.findById(id);
        if (byId.isPresent()) {
            Book book = byId.get();
            book.setAClass(bookDto.getAClass());
            book.setData(bookDto.getData());
            book.setLanguage(bookDto.getLanguage());
            book.setName(bookDto.getName());
            bookRepository.save(book);
            return ApiResponse.builder().success(true).message("EDITED").build();
        }
        return ApiResponse.builder().message("NOT FOUND").success(false).build();
    }

    public ApiResponse delete(Integer id) {
        Optional<Book> byId = bookRepository.findById(id);
        if (byId.isPresent()) {
            Book book = byId.get();
            bookRepository.delete(book);
            return ApiResponse.builder().message("DELETED").success(true).build();
        }
        return ApiResponse.builder().success(false).message("NOT FOUND").build();
    }

    public ApiResponse bookmark(Integer id, User user) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return ApiResponse.builder().message("BOOK NOT FOUND").success(false).build();
        }
        Book book = optionalBook.get();
        List<Book> books = user.getBooks();
        if (books.contains(book)) {
            return ApiResponse.builder().success(false).message("BOOK IS ALREADY BOOKMARKED").build();
        }
        books.add(book);
        user.setBooks(books);
        return ApiResponse.builder().message("SAVED").success(true).build();
    }

    public ApiResponse unBookmark(Integer id, User user) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return ApiResponse.builder().message("BOOK NOT FOUND").success(false).build();
        }
        Book book = optionalBook.get();
        List<Book> books = user.getBooks();
        if (!books.contains(book)) {
            return ApiResponse.builder().success(false).message("BOOK IS NOT IN THE SAVED LIST").build();
        }
        books.remove(book);
        user.setBooks(books);
        return ApiResponse.builder().success(true).message("REMOVED").build();
    }
}
