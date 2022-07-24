package com.springboot.musicplayer.service;

import com.springboot.musicplayer.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Optional<Author> getById(String id);

    List<Author> getAll();

    List<Author> findByName(String name);

    Boolean checkAuthor(Author author);

    Author save(Author author);

    String delete(String id);

    Author update(Author author);
}
