package com.springboot.musicplayer.model;

import javax.persistence.Id;
import java.util.Objects;

public class Author {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Author author = (Author) o;
        if(this.getId().equals(author.getId()))
            return true;
        return this.getName().equalsIgnoreCase(author.getName());

    }

    @Override
    public int hashCode() {
        return this.getName().hashCode() + 32;
    }

    public Author(String name) {
        this.name = name;
    }

    public Author() {
    }
}
