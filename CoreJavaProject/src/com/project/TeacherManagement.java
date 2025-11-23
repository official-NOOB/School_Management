package com.project;

import com.project.dao.TeacherDAO;
import com.project.dao.TeacherDAOImpl;
import java.util.List;
import java.util.Scanner;

public class TeacherManagement implements SchoolOperations {
    private TeacherDAO teacherDAO;
    private Scanner sc;

    public TeacherManagement() {
        this.teacherDAO = new TeacherDAOImpl();
        this.sc = new Scanner(System.in);
    }

    @Override
    public void add() {
        System.out.println("\n--- Add New Teacher ---");
        
        System.out.print("Enter Teacher Name: ");
        String name = sc.nextLine();
        
        System.out.print("Enter Subject: ");
        String subject = sc.nextLine();
        
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
                if (!teacherDAO.emailExists(email)) break;
                System.out.println("Email already exists! Try another.");
            } else {
                System.out.println("Invalid email format!");
            }
        }
        
        Teacher teacher = new Teacher(0, name, subject, phoneNumber, email);
        teacherDAO.addTeacher(teacher);
        System.out.println("Teacher added successfully! ID: " + teacher.getId());
    }

    @Override
    public void display() {
        System.out.println("\n--- All Teachers ---");
        List<Teacher> teachers = teacherDAO.getAllTeachers();
        if (teachers.isEmpty()) {
            System.out.println("No teachers available!");
            return;
        }
        teachers.forEach(System.out::println);
    }

    @Override
    public void search() {
        System.out.println("\n--- Search Teacher ---");
        System.out.print("Enter Teacher ID: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            
            Teacher teacher = teacherDAO.getTeacherById(id);
            if (teacher != null) {
                System.out.println("Teacher Found:");
                System.out.println(teacher);
            } else {
                System.out.println("Teacher not found with ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format!");
        }
    }

    @Override
    public void edit() {
        System.out.println("\n--- Edit Teacher ---");
        System.out.print("Enter Teacher ID: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            
            Teacher teacher = teacherDAO.getTeacherById(id);
            if (teacher != null) {
                System.out.println("Current details: " + teacher);
                
                System.out.print("Enter new name (press enter to keep current): ");
                String newName = sc.nextLine();
                if (!newName.trim().isEmpty()) {
                    teacher.setName(newName);
                }
                
                System.out.print("Enter new subject (press enter to keep current): ");
                String newSubject = sc.nextLine();
                if (!newSubject.trim().isEmpty()) {
                    teacher.setSubject(newSubject);
                }
                
                teacherDAO.updateTeacher(teacher);
                System.out.println("Teacher updated successfully!");
            } else {
                System.out.println("Teacher not found with ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format!");
        }
    }

    @Override
    public void delete() {
        System.out.println("\n--- Delete Teacher ---");
        System.out.print("Enter Teacher ID: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            
            if (teacherDAO.getTeacherById(id) != null) {
                System.out.print("Are you sure you want to delete this teacher? (yes/no): ");
                String confirmation = sc.nextLine();
                if (confirmation.equalsIgnoreCase("yes")) {
                    teacherDAO.deleteTeacher(id);
                    System.out.println("Teacher deleted successfully!");
                } else {
                    System.out.println("Deletion cancelled.");
                }
            } else {
                System.out.println("Teacher not found with ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format!");
        }
    }
}