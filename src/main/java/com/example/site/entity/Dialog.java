package com.example.site.entity;

public class Dialog {

    private final int id;
    private final String title;
    private final User user1;
    private final User user2;

    public Dialog(String title, User user1, User user2) {
        this(0, title, user1, user2);
    }

    public Dialog(int id, String title, User user1, User user2) {
        this.id = id;
        this.title = title;
        this.user1 = user1;
        this.user2 = user2;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }


}
