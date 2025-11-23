package com.project.dao;

import com.project.Student;
import java.util.List;

public interface StudentDAO {
    void addStudent(Student student);
    Student getStudentById(int id);
    List<Student> getAllStudents();
    void updateStudent(Student student);
    void deleteStudent(int id);
    boolean studentExists(int id);
    boolean emailExists(String email);
    Student getTopper();
    void addSubjectToStudent(int studentId, String subject, int marks);
}