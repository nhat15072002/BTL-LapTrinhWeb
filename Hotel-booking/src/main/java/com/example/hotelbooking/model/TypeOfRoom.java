package com.example.hotelbooking.model;

public enum TypeOfRoom {
    vip1("VIP room with one bed"),
    vip2("VIP room with two bed"),
    normal1("Standard room with one bed"),
    normal2("Standard room with two bed");

    private final String name;

    TypeOfRoom(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
