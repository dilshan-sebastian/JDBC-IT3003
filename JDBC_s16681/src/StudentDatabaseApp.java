import java.util.Scanner;

/**
 * Main application class for Student Database Management System
 * Provides console-based user interface for managing student records
 */
public class StudentDatabaseApp {
    private StudentService studentService;
    private Scanner scanner;
    private boolean isRunning;

    public StudentDatabaseApp() {
        this.studentService = new StudentService();
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
    }

    /**
     * Main method - entry point of the application
     */
    public static void main(String[] args) {
        StudentDatabaseApp app = new StudentDatabaseApp();
        app.run();
    }

    /**
     * Main application loop
     */
    public void run() {
        displayWelcomeMessage();
        initializeApplication();

        while (isRunning) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            handleMenuChoice(choice);
        }

        shutdown();
    }

    /**
     * Display welcome message
     */
    private void displayWelcomeMessage() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║    Student Database Management System    ║");
        System.out.println("║              Version 1.0                 ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    /**
     * Initialize the application
     */
    private void initializeApplication() {
        System.out.println("\nInitializing database...");
        studentService.initializeDatabase();
        System.out.println("Application ready!");
    }

    /**
     * Display the main menu
     */
    private void displayMainMenu() {
        System.out.println("\n╔════════════ MAIN MENU ═════════════╗");
        System.out.println("║ 1. Add New Student                 ║");
        System.out.println("║ 2. View All Students               ║");
        System.out.println("║ 3. View Student by ID              ║");
        System.out.println("║ 4. Update Student                  ║");
        System.out.println("║ 5. Delete Student                  ║");
        System.out.println("║ 6. Search Students                 ║");
        System.out.println("║ 7. Show Statistics                 ║");
        System.out.println("║ 8. Exit Application                ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    /**
     * Handle user menu choices
     */
    private void handleMenuChoice(int choice) {
        try {
            switch (choice) {
                case 1:
                    studentService.addNewStudent();
                    pauseForUser();
                    break;
                case 2:
                    studentService.displayAllStudents();
                    pauseForUser();
                    break;
                case 3:
                    studentService.viewStudentById();
                    pauseForUser();
                    break;
                case 4:
                    studentService.updateStudent();
                    pauseForUser();
                    break;
                case 5:
                    studentService.deleteStudent();
                    pauseForUser();
                    break;
                case 6:
                    studentService.searchStudents();
                    pauseForUser();
                    break;
                case 7:
                    studentService.showStatistics();
                    pauseForUser();
                    break;
                case 8:
                    confirmExit();
                    break;
                default:
                    System.out.println("✗ Invalid choice! Please select a number between 1-8.");
                    pauseForUser();
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            pauseForUser();
        }
    }

    /**
     * Confirm exit with user
     */
    private void confirmExit() {
        System.out.print("\nAre you sure you want to exit? (y/N): ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes")) {
            isRunning = false;
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║   Thank you for using Student        ║");
            System.out.println("║   Database Management System!        ║");
            System.out.println("║              Goodbye!                 ║");
            System.out.println("╚═══════════════════════════════════════╝");
        } else {
            System.out.println("Returning to main menu...");
        }
    }

    /**
     * Get integer input with validation
     */
    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Please enter a number.");
                    continue;
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Pause for user to read output
     */
    private void pauseForUser() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
        clearScreen();
    }

    /**
     * Clear screen (works on most terminals)
     */
    private void clearScreen() {
        try {
            // For Windows
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For Unix/Linux/Mac
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // If clearing doesn't work, just print some newlines
            for (int i = 0; i < 3; i++) {
                System.out.println();
            }
        }
    }

    /**
     * Shutdown the application gracefully
     */
    private void shutdown() {
        try {
            if (scanner != null) {
                scanner.close();
            }
            studentService.closeScanner();
        } catch (Exception e) {
            System.err.println("Error during shutdown: " + e.getMessage());
        }
    }
}
