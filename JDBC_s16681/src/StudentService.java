import java.util.List;
import java.util.Scanner;

/**
 * Service class for handling business logic and user interactions
 * Acts as a bridge between the console interface and data access layer
 */
public class StudentService {
    private StudentDAO studentDAO;
    private Scanner scanner;

    public StudentService() {
        this.studentDAO = new StudentDAO();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Initialize the database
     */
    public void initializeDatabase() {
        studentDAO.initializeDatabase();
    }

    /**
     * Display all students in a formatted way
     */
    public void displayAllStudents() {
        List<Student> students = studentDAO.getAllStudents();

        System.out.println("\n=== ALL STUDENTS ===");
        System.out.println("------------------------------------------------------------");

        if (students.isEmpty()) {
            System.out.println("No students found in database.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
            System.out.println("\nTotal Students: " + students.size());
        }
        System.out.println("------------------------------------------------------------");
    }

    /**
     * Add a new student with user input validation
     */
    public void addNewStudent() {
        System.out.println("\n=== ADD NEW STUDENT ===");

        try {
            System.out.print("Enter student name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty!");
                return;
            }

            System.out.print("Enter student email: ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty() || !isValidEmail(email)) {
                System.out.println("Please enter a valid email address!");
                return;
            }

            // Check if email already exists
            if (studentDAO.getStudentByEmail(email) != null) {
                System.out.println("Email already exists! Please use a different email.");
                return;
            }

            int age = getIntInput("Enter student age: ");
            if (age <= 0 || age > 150) {
                System.out.println("Please enter a valid age (1-150)!");
                return;
            }

            System.out.print("Enter course: ");
            String course = scanner.nextLine().trim();
            if (course.isEmpty()) {
                System.out.println("Course cannot be empty!");
                return;
            }

            Student student = new Student(name, email, age, course);

            if (studentDAO.addStudent(student)) {
                System.out.println("✓ Student added successfully!");
            } else {
                System.out.println("✗ Failed to add student. Please try again.");
            }

        } catch (Exception e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    /**
     * View a specific student by ID
     */
    public void viewStudentById() {
        int id = getIntInput("\nEnter student ID: ");
        Student student = studentDAO.getStudentById(id);

        if (student != null) {
            System.out.println("\n=== STUDENT DETAILS ===");
            System.out.println(student);
        } else {
            System.out.println("✗ Student not found with ID: " + id);
        }
    }

    /**
     * Update an existing student's information
     */
    public void updateStudent() {
        int id = getIntInput("\nEnter student ID to update: ");
        Student student = studentDAO.getStudentById(id);

        if (student == null) {
            System.out.println("✗ Student not found with ID: " + id);
            return;
        }

        System.out.println("Current details: " + student);
        System.out.println("\nEnter new details (press Enter to keep current value):");

        // Update name
        System.out.print("Name [" + student.getName() + "]: ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            student.setName(name);
        }

        // Update email
        System.out.print("Email [" + student.getEmail() + "]: ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) {
            if (!isValidEmail(email)) {
                System.out.println("Invalid email format. Keeping current value.");
            } else {
                Student existingStudent = studentDAO.getStudentByEmail(email);
                if (existingStudent != null && existingStudent.getId() != student.getId()) {
                    System.out.println("Email already exists. Keeping current value.");
                } else {
                    student.setEmail(email);
                }
            }
        }

        // Update age
        System.out.print("Age [" + student.getAge() + "]: ");
        String ageStr = scanner.nextLine().trim();
        if (!ageStr.isEmpty()) {
            try {
                int age = Integer.parseInt(ageStr);
                if (age > 0 && age <= 150) {
                    student.setAge(age);
                } else {
                    System.out.println("Invalid age. Keeping current value.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid age format. Keeping current value.");
            }
        }

        // Update course
        System.out.print("Course [" + student.getCourse() + "]: ");
        String course = scanner.nextLine().trim();
        if (!course.isEmpty()) {
            student.setCourse(course);
        }

        if (studentDAO.updateStudent(student)) {
            System.out.println("✓ Student updated successfully!");
        } else {
            System.out.println("✗ Failed to update student.");
        }
    }

    /**
     * Delete a student with confirmation
     */
    public void deleteStudent() {
        int id = getIntInput("\nEnter student ID to delete: ");
        Student student = studentDAO.getStudentById(id);

        if (student == null) {
            System.out.println("✗ Student not found with ID: " + id);
            return;
        }

        System.out.println("Student to delete: " + student);
        System.out.print("Are you sure you want to delete this student? (y/N): ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes")) {
            if (studentDAO.deleteStudent(id)) {
                System.out.println("✓ Student deleted successfully!");
            } else {
                System.out.println("✗ Failed to delete student.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    /**
     * Search students by name
     */
    public void searchStudents() {
        System.out.print("\nEnter student name to search: ");
        String searchName = scanner.nextLine().trim();

        if (searchName.isEmpty()) {
            System.out.println("Search term cannot be empty!");
            return;
        }

        List<Student> students = studentDAO.searchStudentsByName(searchName);

        System.out.println("\n=== SEARCH RESULTS ===");
        System.out.println("------------------------------------------------------------");

        if (students.isEmpty()) {
            System.out.println("No students found matching '" + searchName + "'");
        } else {
            System.out.println("Found " + students.size() + " student(s) matching '" + searchName + "':");
            for (Student student : students) {
                System.out.println(student);
            }
        }
        System.out.println("------------------------------------------------------------");
    }

    /**
     * Display database statistics
     */
    public void showStatistics() {
        int totalStudents = studentDAO.getStudentCount();
        System.out.println("\n=== DATABASE STATISTICS ===");
        System.out.println("Total Students: " + totalStudents);
        System.out.println("===========================");
    }

    /**
     * Helper method to get integer input with validation
     */
    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Basic email validation
     */
    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".")
                && email.length() > 5 && !email.startsWith("@") && !email.endsWith("@");
    }

    /**
     * Get the scanner instance
     */
    public Scanner getScanner() {
        return scanner;
    }

    /**
     * Close the scanner
     */
    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
