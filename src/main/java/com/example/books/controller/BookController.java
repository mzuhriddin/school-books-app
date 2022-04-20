package com.example.books.controller;

import com.example.books.dto.ApiResponse;
import com.example.books.dto.BookDto;
import com.example.books.entity.Book;
import com.example.books.entity.User;
import com.example.books.entity.enums.Class;
import com.example.books.entity.enums.Language;
import com.example.books.repository.BookRepository;
import com.example.books.service.BookService;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@PreAuthorize("hasAuthority('BOOK_CRUD')")
@RequestMapping("/book")
public record BookController(BookRepository bookRepository, BookService bookService) {

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok().body(bookRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Integer id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(optionalBook.get());
    }

    @PreAuthorize("hasAuthority('BOOK_ADD')")
    @PostMapping
    public ResponseEntity add(@Valid @RequestBody BookDto bookDto) {
        ApiResponse apiResponse = bookService.add(bookDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @PreAuthorize("hasAuthority('BOOK_EDIT')")
    @PutMapping("/{id}")
    public ResponseEntity edit(@PathVariable Integer id, @RequestBody BookDto bookDto) {
        ApiResponse apiResponse = bookService.edit(id, bookDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @PreAuthorize("hasAuthority('BOOK_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        ApiResponse apiResponse = bookService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 404).body(apiResponse);
    }

    @SneakyThrows
    @GetMapping("/allByClass")
    public ResponseEntity findByClass(@RequestParam int classNumber) {
        return ResponseEntity.ok(bookRepository.findAllByAClass(Class.findByKey(classNumber)));
    }

    @SneakyThrows
    @GetMapping("/allByLanguage")
    public ResponseEntity findByLanguage(@RequestParam String language) {
        return ResponseEntity.ok(bookRepository.findAllByLanguage(Language.findByKey(language)));
    }

    @SneakyThrows
    @GetMapping("/allByLanguageAndClass")
    public ResponseEntity find(@RequestParam String language, @RequestParam int classNumber) {
        return ResponseEntity.ok(bookRepository.findAllByLanguageAndAClass(Language.findByKey(language), Class.findByKey(classNumber)));
    }

    @GetMapping("/search")
    public ResponseEntity searchByName(@RequestParam String name) {
        return ResponseEntity.ok(bookRepository.findAllByNameContainingIgnoreCase(name));
    }

    @PreAuthorize("hasAuthority('BOOKMARK')")
    @PostMapping("/bookmark/{id}")
    public ResponseEntity bookmark(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        ApiResponse apiResponse = bookService.bookmark(id, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @PreAuthorize("hasAuthority('BOOKMARK')")
    @PostMapping("/unBookmark/{id}")
    public ResponseEntity unBookmark(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        ApiResponse apiResponse = bookService.unBookmark(id, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }


    @GetMapping("/bookmarks")
    public ResponseEntity bookmarks(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user.getBooks());
    }
}
