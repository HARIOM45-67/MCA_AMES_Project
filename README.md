# MCA Assessment Management Examination System (MCA AMES)

**GITAM | MCA Department | Java Web Application**

## Included in this starter project
- Role-based login foundation (Admin, Teacher, Student)
- Student registration/listing
- Attendance entry and attendance percentage query
- Assessment entry for sessional tests, assignments and external examination
- Result calculation: internal 40 + external 60 = total 100
- MySQL schema and sample demo accounts
- JSP dashboard and responsive styling

## Requirements
- JDK 17+
- Apache Maven 3.9+
- MySQL 8+
- Apache Tomcat 10.1+

## Setup
1. Create the database by running `database/schema.sql` in MySQL Workbench.
2. Edit `src/main/java/in/gitam/ames/DB.java` and set your MySQL username/password.
3. From this folder run: `mvn clean package`
4. Copy `target/MCA-AMES.war` to Tomcat's `webapps` folder and start Tomcat.
5. Open `http://localhost:8080/MCA-AMES/`

## Demo accounts
The SQL inserts demo users with password `password123` (BCrypt hashes are generated in the seed script).
- admin@gitam.edu / password123
- teacher@gitam.edu / password123
- student@gitam.edu / password123

## Important
This is a runnable academic starter/MVP, not a production-secure deployment. Change demo passwords, configure credentials outside source code, add CSRF protection, audit logging, validation and HTTPS before real institutional use. PDF export and advanced role-specific workflows can be added in the next iteration.
