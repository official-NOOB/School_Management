package com.project;

import com.project.dao.StudentDAO;
import com.project.dao.StudentDAOImpl;
import java.util.List;
import java.util.Scanner;

public class StudentManagement implements StudentOperations {
    private StudentDAO studentDAO;
    private Scanner sc;

    public StudentManagement() {
        this.studentDAO = new StudentDAOImpl();
        this.sc = new Scanner(System.in);
    }

    @Override
    public void add() {
        System.out.println("\n--- Add New Student ---");
        
        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();
        
        int age = 0;
        while (true) {
            System.out.print("Enter Age (5-60): ");
            try {
                age = Integer.parseInt(sc.nextLine());
                if (age >= 5 && age <= 60) break;
                System.out.println("Age must be between 5 and 60!");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
        
        String phoneNumber;
        while (true) {
            System.out.print("Enter Phone Number (10 digits): ");
            phoneNumber = sc.nextLine();
            if (phoneNumber.matches("\\d{10}")) break;
            System.out.println("Invalid phone number! Must be 10 digits.");
        }
        
        String email;
        while (true) {
            System.out.print("Enter Email: ");
            email = sc.nextLine();
            if (email.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b")) {
                if (!studentDAO.emailExists(email)) break;
                System.out.println("Email already exists! Try another.");
            } else {
                System.out.println("Invalid email format!");
            }
        }
        
        Student student = new Student(0, name, age, phoneNumber, email);
        studentDAO.addStudent(student);
        System.out.println("Student added successfully! ID: " + student.getId());
    }

    @Override
    public void addSubject() {
        System.out.println("\n--- Add Subject to Student ---");
        System.out.print("Enter Student ID: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            
            Student student = studentDAO.getStudentById(id);
            if (student != null) {
                System.out.print("Enter Subject: ");
                String subject = sc.nextLine();
                
                int marks = 0;
                while (true) {
                    System.out.print("Enter Marks (0-100): ");
                    try {
                        marks = Integer.parseInt(sc.nextLine());
                        if (marks >= 0 && marks <= 100) break;
                        System.out.println("Marks must be between 0 and 100!");
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number!");
                    }
                }
                
                studentDAO.addSubjectToStudent(id, subject, marks);
                System.out.println("Subject and marks added successfully!");
            } else {
                System.out.println("Student not found with ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format!");
        }
    }

    @Override
    public void display() {
        System.out.println("\n--- All Students ---");
        List<Student> students = studentDAO.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students available!");
            return;
        }
        students.forEach(System.out::println);
    }

    @Override
    public void search() {
        System.out.println("\n--- Search Student ---");
        System.out.print("Enter Student ID: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            
            Student student = studentDAO.getStudentById(id);
            if (student != null) {
                System.out.println("Student Found:");
                System.out.println(student);
            } else {
                System.out.println("Student not found with ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format!");
        }
    }

    @Override
    public void edit() {
        System.out.println("\n--- Edit Student ---");
        System.out.print("Enter Student ID: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            
            Student student = studentDAO.getStudentById(id);
            if (student != null) {
                System.out.println("Current details: " + student);
                
                System.out.print("Enter new name (press enter to keep current): ");
                String newName = sc.nextLine();
                if (!newName.trim().isEmpty()) {
                    student.setName(newName);
                }
                
                System.out.print("Enter new age (press enter to keep current): ");
                String ageInput = sc.nextLine();
                if (!ageInput.trim().isEmpty()) {
                    try {
                        int newAge = Integer.parseInt(ageInput);
                        if (newAge >= 5 && newAge <= 60) {
                            student.setAge(newAge);
                        } else {
                            System.out.println("Invalid age! Keeping current age.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid age format! Keeping current age.");
                    }
                }
                
                studentDAO.updateStudent(student);
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Student not found with ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format!");
        }
    }

    @Override
    public void delete() {
        System.out.println("\n--- Delete Student ---");
        System.out.print("Enter Student ID: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            
            if (studentDAO.getStudentById(id) != null) {
                System.out.print("Are you sure you want to delete this student? (yes/no): ");
                String confirmation = sc.nextLine();
                if (confirmation.equalsIgnoreCase("yes")) {
                    studentDAO.deleteStudent(id);
                    System.out.println("Student deleted successfully!");
                } else {
                    System.out.println("Deletion cancelled.");
                }
            } else {
                System.out.println("Student not found with ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format!");
        }
    }

    @Override
    public void findTopper() {
        System.out.println("\n--- Find Topper ---");
        Student topper = studentDAO.getTopper();
        if (topper != null) {
            System.out.println("Topper Student:");
            System.out.println(topper);
        } else {
            System.out.println("No students with marks found!");
        }
    }
}