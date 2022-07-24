package com.springboot.musicplayer.repository;

import com.springboot.musicplayer.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepositiry extends MongoRepository<Author, String> {
}
