package com.libt.intern.model;

public class Item {

    private Long id;
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public Item(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Item() {}
}
