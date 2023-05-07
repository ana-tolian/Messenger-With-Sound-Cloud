package com.example.site.entity;

public class Dialog {

    private final int id;
    private final User user1;
    private final User user2;

    public Dialog(User user1, User user2) {
        this(0, user1, user2);
    }

    public Dialog(int id, User user1, User user2) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
    }

    public int getId() {
        return id;
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }


}
