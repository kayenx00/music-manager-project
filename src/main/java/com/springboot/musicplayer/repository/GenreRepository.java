package com.springboot.musicplayer.repository;

import com.springboot.musicplayer.model.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenreRepository extends MongoRepository<Genre, String> {
}
