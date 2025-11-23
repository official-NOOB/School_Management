package com.project.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseInitializer {
    
    public static void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            System.out.println("Checking database setup...");
            
            // Check if tables already exist
            boolean studentsTableExists = checkTableExists(conn, "STUDENTS");
            boolean teachersTableExists = checkTableExists(conn, "TEACHERS");
            boolean studentSeqExists = checkSequenceExists(conn, "STUDENT_SEQ");
            boolean teacherSeqExists = checkSequenceExists(conn, "TEACHER_SEQ");
            
            // Create sequences if they don't exist
            if (!studentSeqExists) {
                try {
                    stmt.execute("CREATE SEQUENCE student_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE");
                    System.out.println(" student_seq sequence created");
                } catch (Exception e) {
                    System.err.println("Error creating student_seq: " + e.getMessage());
                }
            } else {
                System.out.println(" student_seq sequence already exists");
            }
            
            if (!teacherSeqExists) {
                try {
                    stmt.execute("CREATE SEQUENCE teacher_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE");
                    System.out.println(" teacher_seq sequence created");
                } catch (Exception e) {
                    System.err.println("Error creating teacher_seq: " + e.getMessage());
                }
            } else {
                System.out.println(" teacher_seq sequence already exists");
            }
            
            // Create students table if it doesn't exist
            if (!studentsTableExists) {
                String createStudentsTable = 
                    "CREATE TABLE students (" +
                    "    id NUMBER PRIMARY KEY, " +
                    "    name VARCHAR2(50) NOT NULL, " +
                    "    age NUMBER NOT NULL, " +
                    "    phone_number VARCHAR2(15) NOT NULL, " +
                    "    email VARCHAR2(50) UNIQUE NOT NULL, " +
                    "    subject VARCHAR2(50), " +
                    "    marks NUMBER DEFAULT 0, " +
                    "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
                
                stmt.execute(createStudentsTable);
                System.out.println(" students table created");
                
                // Create trigger for students table
                createStudentTrigger(stmt);
            } else {
                System.out.println(" students table already exists");
            }
            
            // Create teachers table if it doesn't exist
            if (!teachersTableExists) {
                String createTeachersTable = 
                    "CREATE TABLE teachers (" +
                    "    id NUMBER PRIMARY KEY, " +
                    "    name VARCHAR2(50) NOT NULL, " +
                    "    subject VARCHAR2(50) NOT NULL, " +
                    "    phone_number VARCHAR2(15) NOT NULL, " +
                    "    email VARCHAR2(50) UNIQUE NOT NULL, " +
                    "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
                
                stmt.execute(createTeachersTable);
                System.out.println(" teachers table created");
                
                // Create trigger for teachers table
                createTeacherTrigger(stmt);
            } else {
                System.out.println(" teachers table already exists");
            }
            
            System.out.println("Database setup completed successfully! ");
            
        } catch (Exception e) {
            System.out.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Check if table exists
    private static boolean checkTableExists(Connection conn, String tableName) {
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, null, tableName, new String[]{"TABLE"});
            return rs.next(); // Returns true if table exists
        } catch (Exception e) {
            System.out.println("Error checking table existence: " + e.getMessage());
            return false;
        }
    }
    
    // Check if sequence exists
    private static boolean checkSequenceExists(Connection conn, String sequenceName) {
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, null, sequenceName, new String[]{"SEQUENCE"});
            return rs.next();
        } catch (Exception e) {
            // Alternative method for older Oracle versions
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                     "SELECT sequence_name FROM user_sequences WHERE sequence_name = '" + sequenceName + "'")) {
                return rs.next();
            } catch (Exception ex) {
                return false;
            }
        }
    }
    
    // Create student trigger
    private static void createStudentTrigger(Statement stmt) {
        try {
            String triggerSql = 
                "CREATE OR REPLACE TRIGGER student_bir " +
                "BEFORE INSERT ON students " +
                "FOR EACH ROW " +
                "BEGIN " +
                "    IF :new.id IS NULL THEN " +
                "        SELECT student_seq.NEXTVAL INTO :new.id FROM dual; " +
                "    END IF; " +
                "END;";
            stmt.execute(triggerSql);
            System.out.println("✓ student_bir trigger created");
        } catch (Exception e) {
            System.err.println("Error creating student trigger: " + e.getMessage());
        }
    }
    
    // Create teacher trigger
    private static void createTeacherTrigger(Statement stmt) {
        try {
            String triggerSql = 
                "CREATE OR REPLACE TRIGGER teacher_bir " +
                "BEFORE INSERT ON teachers " +
                "FOR EACH ROW " +
                "BEGIN " +
                "    IF :new.id IS NULL THEN " +
                "        SELECT teacher_seq.NEXTVAL INTO :new.id FROM dual; " +
                "    END IF; " +
                "END;";
            stmt.execute(triggerSql);
            System.out.println("✓ teacher_bir trigger created");
        } catch (Exception e) {
            System.err.println("Error creating teacher trigger: " + e.getMessage());
        }
    }
    
    // Method to verify current database state
    public static void showDatabaseStatus() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            System.out.println("\n--- Current Database Status ---");
            
            // Check table counts
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM students")) {
                if (rs.next()) {
                    System.out.println("Students count: " + rs.getInt(1));
                }
            } catch (Exception e) {
                System.out.println("Students table: Not found");
            }
            
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM teachers")) {
                if (rs.next()) {
                    System.out.println("Teachers count: " + rs.getInt(1));
                }
            } catch (Exception e) {
                System.out.println("Teachers table: Not found");
            }
            
            System.out.println("Database status check completed!");
            
        } catch (Exception e) {
            System.err.println("Error checking database status: " + e.getMessage());
        }
    }
}