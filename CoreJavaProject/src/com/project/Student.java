package com.project;

public class Student {
    private int id;
    private String name;
    private int age;
    private String phoneNumber;
    private String email;
    private String subject;
    private int marks;

    public Student(int id, String name, int age, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public int getMarks() { return marks; }
    public void setMarks(int marks) { this.marks = marks; }

    @Override
    public String toString() {
        return String.format(
            "ID: %d | Name: %s | Age: %d | Phone: %s | Email: %s | Subject: %s | Marks: %d",
            id, name, age, phoneNumber, email, 
            subject != null ? subject : "Not assigned", 
            marks
        );
    }
}