package com.nsma.popularmovies.Models;

public class Trailer {


        String id;
        String key;
        String name;

    public Trailer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Trailer(String id, String key, String name) {
        this.id = id;
        this.key = key;
        this.name = name;
    }
}
