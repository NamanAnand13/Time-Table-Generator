package scheduler;

import java.util.ArrayList;

public class Teacher {
    private int id;
    private String name;
    private String subject;
    private ArrayList<String> subjects;
    private int assigned; // Indicates if the teacher is assigned to a subject

    // Constructor
    public Teacher() {
        // Default constructor
        subjects = new ArrayList<>();
    }

    public Teacher(int id, String name, String subject) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.assigned = 0; // Default value for not assigned
        subjects = new ArrayList<>();
    }

    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for subject
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    // Getter and Setter for assigned status
    public int getAssigned() {
        return assigned;
    }

    public void setAssigned(int assigned) {
        this.assigned = assigned;
    }

    public void addSubject(String subject) {
        subjects.add(subject);
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    @Override
    public String toString() {
        return "Teacher [id=" + id + ", name=" + name + ", subject=" + subject + ", assigned=" + assigned + "]";
    }
}
