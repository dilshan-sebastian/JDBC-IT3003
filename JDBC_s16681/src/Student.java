/**
 * Student model class representing a student entity
 */
public class Student {
    private int id;
    private String name;
    private String email;
    private int age;
    private String course;

    // Default constructor
    public Student() {}

    // Constructor without ID (for new students)
    public Student(String name, String email, int age, String course) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.course = course;
    }

    // Constructor with ID (for existing students)
    public Student(int id, String name, String email, int age, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.course = course;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getCourse() {
        return course;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    // toString method for easy display
    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Email: %s | Age: %d | Course: %s",
                id, name, email, age, course);
    }

    // equals method for comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Student student = (Student) obj;
        return id == student.id;
    }

    // hashCode method
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
