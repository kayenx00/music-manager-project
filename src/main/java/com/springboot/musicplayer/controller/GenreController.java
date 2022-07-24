package com.springboot.musicplayer.controller;

import com.springboot.musicplayer.model.Genre;
import com.springboot.musicplayer.model.SongPage;
import com.springboot.musicplayer.response.ResponseObject;
import com.springboot.musicplayer.service.serviceImp.GenreServiceImplementation;
import com.springboot.musicplayer.service.serviceImp.SongServiceImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/genre")
public class GenreController {
    @Autowired
    private GenreServiceImplementation genreService;
    @Autowired
    private SongServiceImplementation songService;
    private static final Logger logger = LoggerFactory.getLogger(SongController.class);

    @GetMapping(value = "/all")
    ResponseEntity<ResponseObject> getAllGenres() {
        logger.info("Getting genre list:...");

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Success", genreService.getAll())
        );
    }

    @PostMapping(value = "/add")
    ResponseEntity<ResponseObject> add(@RequestBody Genre g) {
        if (!genreService.checkGenre(g)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    new ResponseObject("NOT_ACCEPTABLE", "Genre name existed", null)
            );
        }

        Genre genre = genreService.save(g);

        if (genre != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Success", genre)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    new ResponseObject("NOT_ACCEPTABLE", "Genre with name '" + g.getName() + "' already exist!", null)
            );
        }

    }

    @DeleteMapping(value = "/delete")
    ResponseEntity<ResponseObject> delete(@RequestParam(name = "id") List<String> list) {
        if (list.size() > 0) {
            for (String s : list) {
                Pageable pageable = PageRequest.of(0 , 1);
                SongPage page = songService.getPage("", "", s, 0, 1, pageable);

                if (page.getSongs().size() > 0) {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                            new ResponseObject("NOT_ACCEPTABLE", "This Genre is match with other songs, delete those songs first", null)
                    );
                }

                String status;
                status = genreService.delete(s);
                if (status.equals("NOT_FOUND")) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            new ResponseObject("NOT_FOUND", "Cannot find song with id: " + s, null)
                    );
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Success", null)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    new ResponseObject("NOT_ACCEPTABLE", "Missing required ID", null)
            );
        }
    }

}
