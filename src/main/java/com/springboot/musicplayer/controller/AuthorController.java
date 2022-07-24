package com.springboot.musicplayer.controller;

import com.springboot.musicplayer.model.Author;
import com.springboot.musicplayer.model.SongPage;
import com.springboot.musicplayer.response.ResponseObject;
import com.springboot.musicplayer.service.serviceImp.AuthorServiceImplementation;
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
@RequestMapping(path = "/api/author")
public class AuthorController {

        @Autowired
        private AuthorServiceImplementation authorService;
        @Autowired
        private SongServiceImplementation songService;
        private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);


        @GetMapping(value = "/all")
        ResponseEntity<ResponseObject> getAll() {
            logger.info("Getting author list:...");

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Success", authorService.getAll())
            );
        }

        @PostMapping(value = "/add")
        ResponseEntity<ResponseObject> add(@RequestBody Author a) {
            if (!authorService.checkAuthor(a)) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                        new ResponseObject("NOT_ACCEPTABLE", "Author name existed", null)
                );
            }

            Author author = authorService.save(a);

            if (author != null) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("OK", "Success", author)
                );
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                        new ResponseObject("NOT_ACCEPTABLE", "Author with name '" + a.getName() + "' already exist!", null)
                );
            }

        }

        @DeleteMapping(value = "/delete")
        ResponseEntity<ResponseObject> delete(@RequestParam(name = "id") List<String> list) {
            if (list.size() > 0) {
                Author author = new Author();

                for (String s : list) {

                    Pageable pageable = PageRequest.of(0, 1);
                    SongPage page = songService.getPage("", s, "", 0, 1, pageable);

                    if (page.getSongs().size() > 0) {
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                                new ResponseObject("NOT_ACCEPTABLE", "This Author is match with other songs, delete those songs first", null)
                        );
                    }

                    String status;
                    status = authorService.delete(s);
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
