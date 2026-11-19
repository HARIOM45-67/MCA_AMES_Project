CREATE DATABASE IF NOT EXISTS mca_ames CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE mca_ames;

CREATE TABLE IF NOT EXISTS users (
 id INT AUTO_INCREMENT PRIMARY KEY,
 full_name VARCHAR(120) NOT NULL,
 email VARCHAR(160) NOT NULL UNIQUE,
 password_hash VARCHAR(100) NOT NULL,
 role ENUM('ADMIN','TEACHER','STUDENT') NOT NULL,
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS students (
 id INT AUTO_INCREMENT PRIMARY KEY,
 roll_no VARCHAR(40) NOT NULL UNIQUE,
 full_name VARCHAR(120) NOT NULL,
 email VARCHAR(160),
 course VARCHAR(80) DEFAULT 'MCA',
 semester INT NOT NULL DEFAULT 1,
 phone VARCHAR(20),
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS attendance (
 id INT AUTO_INCREMENT PRIMARY KEY,
 student_id INT NOT NULL,
 attendance_date DATE NOT NULL,
 status ENUM('PRESENT','ABSENT') NOT NULL,
 marked_by INT,
 UNIQUE KEY uq_attendance(student_id, attendance_date),
 FOREIGN KEY(student_id) REFERENCES students(id) ON DELETE CASCADE,
 FOREIGN KEY(marked_by) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS assessments (
 id INT AUTO_INCREMENT PRIMARY KEY,
 student_id INT NOT NULL,
 assessment_type ENUM('SESSIONAL','ASSIGNMENT','CLASSWORK','EXTERNAL') NOT NULL,
 title VARCHAR(160) NOT NULL,
 marks_obtained DECIMAL(6,2) NOT NULL,
 max_marks DECIMAL(6,2) NOT NULL,
 assessment_date DATE NOT NULL,
 entered_by INT,
 FOREIGN KEY(student_id) REFERENCES students(id) ON DELETE CASCADE,
 FOREIGN KEY(entered_by) REFERENCES users(id) ON DELETE SET NULL
);

-- Demo password: password123. BCrypt hash generated for the demo password.
INSERT IGNORE INTO users(full_name,email,password_hash,role) VALUES
('AMES Administrator','admin@gitam.edu','$2a$10$L5unbpZpoVJ.OKSpxXHjl.9FujvBEC.oLhotJVnl5EzzyEw8Y99um','ADMIN'),
('AMES Teacher','teacher@gitam.edu','$2a$10$L5unbpZpoVJ.OKSpxXHjl.9FujvBEC.oLhotJVnl5EzzyEw8Y99um','TEACHER'),
('Demo Student','student@gitam.edu','$2a$10$L5unbpZpoVJ.OKSpxXHjl.9FujvBEC.oLhotJVnl5EzzyEw8Y99um','STUDENT');

INSERT IGNORE INTO students(roll_no,full_name,email,semester) VALUES
('MCA2026001','Demo Student','student@gitam.edu',1);
