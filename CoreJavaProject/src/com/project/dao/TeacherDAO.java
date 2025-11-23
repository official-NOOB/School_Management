package com.project.dao;

import com.project.Teacher;
import java.util.List;

public interface TeacherDAO {
    void addTeacher(Teacher teacher);
    Teacher getTeacherById(int id);
    List<Teacher> getAllTeachers();
    void updateTeacher(Teacher teacher);
    void deleteTeacher(int id);
    boolean teacherExists(int id);
    boolean emailExists(String email);
}