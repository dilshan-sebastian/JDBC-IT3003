1. MySQL Setup
Option A: Using XAMPP (Recommended for beginners)

Download and install XAMPP
Start Apache and MySQL services from XAMPP Control Panel
Default credentials:

Username: root
Password: `` (empty)
Port: 3306



Option B: MySQL Server Installation

Install MySQL Server from official website
Note your username, password, and port during installation
Ensure MySQL service is running

2. Database Configuration
Open StudentDAO.java and update the database connection details:
javaprivate static final String DB_URL = "jdbc:mysql://localhost:3306/student_db";
private static final String DB_USER = "your_username";      // Update this
private static final String DB_PASSWORD = "your_password";  // Update this
Common Configurations:

XAMPP Users: username = root, password = `` (empty)
Default MySQL: username = root, password = your_root_password
Custom Setup: Use your specific credentials

3. Add MySQL Connector
Ensure mysql-connector-j-x.x.x.jar is in your project's External Libraries or classpath.
