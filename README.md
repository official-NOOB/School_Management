# 🏫 School Management System (Core Java & Advanced Java)

A **console-based School Management System** developed using **Core Java and Advanced Java (JDBC)**.  
The application manages **Students and Teachers** with complete **CRUD operations**, clean **OOP-based architecture**, and robust **exception handling**.

---

## ✨ Features

### Student Management
- Add new students
- Update student details
- View student records
- Delete student records

### Teacher Management
- Add new teachers
- Update teacher information
- View teacher records
- Delete teacher records

### Core System Features
- Menu-driven console interface
- JDBC-based database connectivity
- Input validation & exception handling
- Modular OOP-based design
- Clean separation of layers

---

## 🛠️ Tech Stack

- **Language:** Java (Core & Advanced)
- **Database:** Oracle / MySQL
- **Database Access:** JDBC
- **Architecture:** Layered (DAO, Service, Model)
- **Tools:** Eclipse / STS
- **Version Control:** Git & GitHub

---

## 🏗️ Project Architecture

```text
com.school.management
│
├── model        # Entity classes (Student, Teacher)
├── dao          # Database access logic (JDBC)
├── service      # Business logic
├── util         # DB connection & utilities
├── exception    # Custom exception handling
└── MainApp.java # Entry point (Menu-driven UI)
