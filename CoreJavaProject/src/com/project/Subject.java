package com.project;

public class Subject {
    private String subjectName;
    private Double marks;

    public Subject(String subjectName, Double marks) {
        this.subjectName = subjectName;
        this.marks = marks;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Double getMarks() {
        return marks;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
    }
}

