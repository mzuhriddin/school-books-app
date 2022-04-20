package com.example.books.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;


@Getter
@AllArgsConstructor
public enum Role {
    ADMIN(List.of(new SimpleGrantedAuthority("BOOK_CRUD"))), USER(List.of(new SimpleGrantedAuthority("BOOKMARK")));

    private final List<SimpleGrantedAuthority> permissions;

}
