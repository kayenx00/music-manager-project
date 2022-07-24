package com.springboot.musicplayer.repository;

import com.springboot.musicplayer.model.Song;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SongRepository extends MongoRepository<Song, String> {
}
