package com.springboot.musicplayer.model;

import java.util.Objects;

public class Genre {
    private String id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Genre genre = (Genre) o;
        if (this.getId().equals(genre.getId()))
            return true;
        return this.getName().equalsIgnoreCase(genre.getName());
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode() + 32;
    }

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

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }
}
