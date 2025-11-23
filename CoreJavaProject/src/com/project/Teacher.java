package com.project;

public class Teacher {
    private int id;
    private String name;
    private String subject;
    private String phoneNumber;
    private String email;

    public Teacher(int id, String name, String subject, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return String.format(
            "ID: %d | Name: %s | Subject: %s | Phone: %s | Email: %s",
            id, name, subject, phoneNumber, email
        );
    }
}