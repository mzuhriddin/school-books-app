package com.example.books.service;

import com.example.books.dto.ApiResponse;
import com.example.books.dto.BookDto;
import com.example.books.entity.Book;
import com.example.books.entity.User;
import com.example.books.mapper.BookMapper;
import com.example.books.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public ApiResponse add(BookDto dto) {
        if (dto.getFile().isEmpty() || dto.getPicture().isEmpty()) {
            return ApiResponse.builder()
                    .success(false)
                    .message("File or picture of book must not be empty")
                    .build();
        }
        if (!Objects.requireNonNull(dto.getFile().getOriginalFilename()).toLowerCase(Locale.ROOT).matches("^(.+)\\.(pdf|epub|word|fb2)$")) {
            return ApiResponse.builder()
                    .success(false)
                    .message("File type must be pdf, epub, word, fb2, txt")
                    .build();
        }
        if (!Objects.requireNonNull(dto.getPicture().getOriginalFilename()).toLowerCase(Locale.ROOT).matches("^(.+)\\.(png|jpeg|ico|jpg)$")) {
            return ApiResponse.builder()
                    .message("File type must be png, jpeg, ico, jpg")
                    .build();
        }
        Book book = bookMapper.toEntity(dto);
        bookRepository.save(book);
        return ApiResponse.builder()
                .success(true)
                .message("Created!")
                .build();
    }

    @SneakyThrows
    public ApiResponse edit(Integer id, BookDto dto) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return ApiResponse.builder()
                    .success(false)
                    .message("Book not found")
                    .build();
        }
        Book book = optionalBook.get();
        if (dto.getFile().isEmpty() || dto.getPicture().isEmpty()) {
            return ApiResponse.builder()
                    .success(false)
                    .message("File or picture of book must not be empty")
                    .build();
        }
        if (!Objects.requireNonNull(dto.getFile().getOriginalFilename()).matches("^(.+)\\.(pdf|epub|word|fb2)$")) {
            return ApiResponse.builder()
                    .success(false)
                    .message("File type must be pdf, epub, word, fb2, txt")
                    .build();
        }
        if (!Objects.requireNonNull(dto.getPicture().getOriginalFilename()).matches("^(.+)\\.(png|jpeg|ico|jpg)$")) {
            return ApiResponse.builder()
                    .success(false)
                    .message("File type must be png, jpeg, ico, jpg")
                    .build();
        }
        bookMapper.update(dto, book);
        bookRepository.save(book);
        return ApiResponse.builder()
                .success(true)
                .message("Edited!")
                .build();
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
