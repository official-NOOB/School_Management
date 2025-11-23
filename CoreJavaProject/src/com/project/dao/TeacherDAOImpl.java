package com.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.project.Teacher;

public class TeacherDAOImpl implements TeacherDAO {
    
    @Override
    public void addTeacher(Teacher teacher) {
        String sql = "INSERT INTO teachers (name, subject, phone_number, email) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, teacher.getName());
            stmt.setString(2, teacher.getSubject());
            stmt.setString(3, teacher.getPhoneNumber());
            stmt.setString(4, teacher.getEmail());
            
            stmt.executeUpdate();
            
           
            try (Statement idStmt = conn.createStatement();
                 ResultSet rs = idStmt.executeQuery("SELECT teacher_seq.CURRVAL FROM dual")) {
                if (rs.next()) {
                    teacher.setId(rs.getInt(1));
                    System.out.println("Teacher added successfully with ID: " + teacher.getId());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding teacher: " + e.getMessage());
            e.printStackTrace();
        }
    }

   
    @Override
    public Teacher getTeacherById(int id) {
        String sql = "SELECT * FROM teachers WHERE id = ?";
        Teacher teacher = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                teacher = extractTeacherFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting teacher by ID: " + e.getMessage());
        }
        return teacher;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        String sql = "SELECT * FROM teachers ORDER BY id";
        List<Teacher> teachers = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                teachers.add(extractTeacherFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all teachers: " + e.getMessage());
        }
        return teachers;
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        String sql = "UPDATE teachers SET name = ?, subject = ?, phone_number = ?, email = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, teacher.getName());
            stmt.setString(2, teacher.getSubject());
            stmt.setString(3, teacher.getPhoneNumber());
            stmt.setString(4, teacher.getEmail());
            stmt.setInt(5, teacher.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating teacher: " + e.getMessage());
        }
    }

    @Override
    public void deleteTeacher(int id) {
        String sql = "DELETE FROM teachers WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting teacher: " + e.getMessage());
        }
    }

    @Override
    public boolean teacherExists(int id) {
        return getTeacherById(id) != null;
    }

    @Override
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM teachers WHERE email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking email existence: " + e.getMessage());
        }
        return false;
    }
    
    private Teacher extractTeacherFromResultSet(ResultSet rs) throws SQLException {
        return new Teacher(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("subject"),
            rs.getString("phone_number"),
            rs.getString("email")
        );
    }
}