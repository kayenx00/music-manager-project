package com.springboot.musicplayer.service.serviceImp;

import com.springboot.musicplayer.model.Author;
import com.springboot.musicplayer.repository.AuthorRepositiry;
import com.springboot.musicplayer.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import org.springframework.data.mongodb.core.query.Query;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImplementation implements AuthorService {
    @Autowired
    private AuthorRepositiry authorRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public Optional<Author> getById(String id){
        return authorRepository.findById(id);
    }
    @Override
    public List<Author> getAll(){
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.ASC, "name"));
        return mongoTemplate.find(query, Author.class, "author");
    }
    @Override
    public List<Author> findByName(String name){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(name, "i"));
        return mongoTemplate.find(query, Author.class, "author");
    }
    @Override
    public Boolean checkAuthor(Author author){
        List<Author> authors = findByName(author.getName());
        for(Author a : authors){
            if(a.equals(author))
                return false;
        }
        return true;
    }
    @Override
    public Author save(Author author){
        authorRepository.save(author);
        return author;
    }
    @Override
    public String delete(String id) {
        if (getById(id).isPresent()) {
            authorRepository.deleteById(id);
            return "OK";
        } else {
            return "NOT_FOUND";
        }
    }
    @Override
    public Author update(Author author) {
        if (findByName(author.getName()).size() > 0) {
            return null;
        } else {
            authorRepository.save(author);
            return author;
        }
    }
}

