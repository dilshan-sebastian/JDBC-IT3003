import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Student operations using MySQL Database
 * Handles all database interactions for Student entity
 */
public class StudentDAO {
    // MySQL connection details - modify these according to your setup
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String DB_USER = "root";  // Change to your MySQL username
    private static final String DB_PASSWORD = "root";  // Change to your MySQL password

    /**
     * Initialize database and create students table if it doesn't exist
     */
    public void initializeDatabase() {
        // First, create database if it doesn't exist
        createDatabaseIfNotExists();

        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS students1 (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                email VARCHAR(150) NOT NULL UNIQUE,
                age INT NOT NULL,
                course VARCHAR(100) NOT NULL
            )
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Database initialized successfully!");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            System.err.println("Please make sure MySQL is running and credentials are correct.");
        }
    }

    /**
     * Create database if it doesn't exist
     */
    private void createDatabaseIfNotExists() {
        String serverURL = "jdbc:mysql://localhost:3306/";
        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS student_db";

        try (Connection conn = DriverManager.getConnection(serverURL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createDatabaseSQL);
        } catch (SQLException e) {
            System.err.println("Error creating database: " + e.getMessage());
        }
    }

    /**
     * Add a new student to the database
     * @param student Student object to add
     * @return true if student was added successfully, false otherwise
     */
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students1(name, email, age, course) VALUES(?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getCourse());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieve all students from the database
     * @return List of all students
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students1 ORDER BY id";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getString("course")
                );
                students.add(student);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
        }

        return students;
    }

    /**
     * Find a student by their ID
     * @param id Student ID to search for
     * @return Student object if found, null otherwise
     */
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students1 WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getString("course")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving student: " + e.getMessage());
        }
        return null;
    }

    /**
     * Find students by email
     * @param email Email to search for
     * @return Student object if found, null otherwise
     */
    public Student getStudentByEmail(String email) {
        String sql = "SELECT * FROM students1 WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getString("course")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving student by email: " + e.getMessage());
        }
        return null;
    }

    /**
     * Update an existing student's information
     * @param student Student object with updated information
     * @return true if student was updated successfully, false otherwise
     */
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students1 SET name = ?, email = ?, age = ?, course = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getCourse());
            pstmt.setInt(5, student.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete a student from the database
     * @param id ID of the student to delete
     * @return true if student was deleted successfully, false otherwise
     */
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students1 WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get the total count of students in the database
     * @return Total number of students
     */
    public int getStudentCount() {
        String sql = "SELECT COUNT(*) as count FROM students1";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (SQLException e) {
            System.err.println("Error counting students: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Search students by name (partial match)
     * @param name Name to search for (case-insensitive)
     * @return List of students matching the search criteria
     */
    public List<Student> searchStudentsByName(String name) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students1 WHERE name LIKE ? ORDER BY name";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getString("course")
                );
                students.add(student);
            }

        } catch (SQLException e) {
            System.err.println("Error searching students1: " + e.getMessage());
        }

        return students;
    }

    /**
     * Test database connection
     * @return true if connection is successful, false otherwise
     */
    public boolean testConnection() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Database connection successful!");
            return true;
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            return false;
        }
    }
}