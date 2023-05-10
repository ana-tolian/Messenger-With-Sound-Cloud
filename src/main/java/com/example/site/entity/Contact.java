package com.example.site.entity;


public class Contact {

    private final int id;
    private final User owner;
    private final User referer;
    private final String status;

    public Contact(User owner, User referer, String status) {
        this(0, owner, referer, status);
    }

    public Contact(int id, User owner, User referer, String status) {
        this.id = id;
        this.owner = owner;
        this.referer = referer;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public User getReferer() {
        return referer;
    }

    public String getStatus() {
        return status;
    }
}
