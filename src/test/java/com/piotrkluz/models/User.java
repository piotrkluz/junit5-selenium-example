package com.piotrkluz.models;

import org.apache.commons.lang3.RandomStringUtils;

public class User {
    public String name;
    public String email;
    public String password;

    public User() {
        String name = RandomStringUtils.randomAlphabetic(10);
        this.name = name;
        this.email = name + "@mail.xx";
        this.password = "aaaaaaaa";
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
