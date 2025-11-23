package com.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.project.Student;

public class StudentDAOImpl implements StudentDAO {
    
    @Override
    public void addStudent(Student student) {
        // First get the next sequence value
        String getNextIdSql = "SELECT student_seq.NEXTVAL FROM dual";
        String insertSql = "INSERT INTO students (id, name, age, phone_number, email) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement idStmt = conn.createStatement();
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            
            // Get the next ID from sequence
            try (ResultSet rs = idStmt.executeQuery(getNextIdSql)) {
                if (rs.next()) {
                    int nextId = rs.getInt(1);
                    student.setId(nextId);
                    
                    // Now insert with the explicit ID
                    insertStmt.setInt(1, nextId);
                    insertStmt.setString(2, student.getName());
                    insertStmt.setInt(3, student.getAge());
                    insertStmt.setString(4, student.getPhoneNumber());
                    insertStmt.setString(5, student.getEmail());
                    
                    insertStmt.executeUpdate();
                    System.out.println("Student added successfully with ID: " + nextId);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Alternative simpler approach - let the trigger handle the ID
    public void addStudentSimple(Student student) {
        String sql = "INSERT INTO students (name, age, phone_number, email) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, student.getName());
            stmt.setInt(2, student.getAge());
            stmt.setString(3, student.getPhoneNumber());
            stmt.setString(4, student.getEmail());
            
            stmt.executeUpdate();
            
            // Now get the last inserted ID using a separate query
            try (Statement idStmt = conn.createStatement();
                 ResultSet rs = idStmt.executeQuery("SELECT student_seq.CURRVAL FROM dual")) {
                if (rs.next()) {
                    student.setId(rs.getInt(1));
                    System.out.println("Student added successfully with ID: " + student.getId());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        Student student = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                student = extractStudentFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting student by ID: " + e.getMessage());
        }
        return student;
    }

    @Override
    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM students ORDER BY id";
        List<Student> students = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                students.add(extractStudentFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all students: " + e.getMessage());
        }
        return students;
    }

    @Override
    public void updateStudent(Student student) {
        String sql = "UPDATE students SET name = ?, age = ?, phone_number = ?, email = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, student.getName());
            stmt.setInt(2, student.getAge());
            stmt.setString(3, student.getPhoneNumber());
            stmt.setString(4, student.getEmail());
            stmt.setInt(5, student.getId());
            
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Student updated successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
        }
    }

    @Override
    public void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("No student found with ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }
    }

    @Override
    public boolean studentExists(int id) {
        return getStudentById(id) != null;
    }

    @Override
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM students WHERE email = ?";
        
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

    @Override
    public Student getTopper() {
        // FIXED: Oracle uses ROWNUM instead of LIMIT
        String sql = "SELECT * FROM (" +
                     "SELECT * FROM students WHERE marks IS NOT NULL AND marks > 0 ORDER BY marks DESC" +
                     ") WHERE ROWNUM = 1";
        Student student = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                student = extractStudentFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding topper: " + e.getMessage());
        }
        return student;
    }

    @Override
    public void addSubjectToStudent(int studentId, String subject, int marks) {
        String sql = "UPDATE students SET subject = ?, marks = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, subject);
            stmt.setInt(2, marks);
            stmt.setInt(3, studentId);
            
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Subject and marks updated successfully!");
            } else {
                System.out.println("No student found with ID: " + studentId);
            }
        } catch (SQLException e) {
            System.err.println("Error adding subject to student: " + e.getMessage());
        }
    }
    
    private Student extractStudentFromResultSet(ResultSet rs) throws SQLException {
        Student student = new Student(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getInt("age"),
            rs.getString("phone_number"),
            rs.getString("email")
        );
        // Handle possible NULL values
        String subject = rs.getString("subject");
        if (!rs.wasNull()) {
            student.setSubject(subject);
        }
        int marks = rs.getInt("marks");
        if (!rs.wasNull()) {
            student.setMarks(marks);
        }
        return student;
    }
}