package com.entities;

public class Student {
    public int id;
    public String name;
    public String email;
    public String address;

    public Student() {
    }

    public Student(int id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public Student setId(int id) {
        this.id = id;
        return this;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public Student setEmail(String email) {
        this.email = email;
        return this;
    }

    public Student setAddress(String address) {
        this.address = address;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }
}
