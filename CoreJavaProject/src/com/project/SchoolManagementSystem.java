package com.project;

import com.project.dao.DatabaseInitializer;
import java.util.Scanner;

public class SchoolManagementSystem {
    
    static {
        DatabaseInitializer.initializeDatabase();
        
        DatabaseInitializer.showDatabaseStatus();
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManagement sm = new StudentManagement();
        TeacherManagement tm = new TeacherManagement();

        System.out.println("\n School Management System Started!");
        System.out.println(" Database is ready!");

        while (true) {
            System.out.println("\n===== School Management System =====");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Teachers");
            System.out.println("3. Check Database Status");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = sc.nextInt();
                sc.nextLine();
                
                switch (choice) {
                    case 1:
                        studentMenu(sc, sm);
                        break;
                    case 2:
                        teacherMenu(sc, tm);
                        break;
                    case 3:
                        DatabaseInitializer.showDatabaseStatus();
                        break;
                    case 4:
                        System.out.println("Exiting... Goodbye! ");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
            }
        }
    }
    
    // Rest of your menu methods remain the same...
    private static void studentMenu(Scanner sc, StudentManagement sm) {
        while (true) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. Add Subject to Student");
            System.out.println("3. Display All Students");
            System.out.println("4. Search Student");
            System.out.println("5. Find Topper");
            System.out.println("6. Edit Student");
            System.out.println("7. Delete Student");
            System.out.println("8. Back to Main Menu");
            System.out.print("Enter choice: ");

            try {
                int choice = sc.nextInt();
                sc.nextLine();
                
                switch (choice) {
                    case 1: sm.add(); break;
                    case 2: sm.addSubject(); break;
                    case 3: sm.display(); break;
                    case 4: sm.search(); break;
                    case 5: sm.findTopper(); break;
                    case 6: sm.edit(); break;
                    case 7: sm.delete(); break;
                    case 8: return;
                    default: System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
            }
        }
    }

    private static void teacherMenu(Scanner sc, TeacherManagement tm) {
        while (true) {
            System.out.println("\n--- Teacher Management ---");
            System.out.println("1. Add Teacher");
            System.out.println("2. Display All Teachers");
            System.out.println("3. Search Teacher");
            System.out.println("4. Edit Teacher");
            System.out.println("5. Delete Teacher");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter choice: ");

            try {
                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1: tm.add(); break;
                    case 2: tm.display(); break;
                    case 3: tm.search(); break;
                    case 4: tm.edit(); break;
                    case 5: tm.delete(); break;
                    case 6: return;
                    default: System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
            }
        }
    }
}