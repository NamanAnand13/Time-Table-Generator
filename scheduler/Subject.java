package scheduler;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private int id;
    private String name;
    private List<Teacher> teachers;  // Dynamic list to handle multiple teachers
    private int noOfTeachers;

    // Constructor
    public Subject() {
        teachers = new ArrayList<>();  // Initializes the dynamic list
        noOfTeachers = 0;
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

    // Getter for teacher list
    public List<Teacher> getTeachers() {
        return teachers;
    }

    // Add a teacher to the list
    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
        this.noOfTeachers++;
    }

    // Getter for number of teachers
    public int getNoOfTeachers() {
        return noOfTeachers;
    }
}

