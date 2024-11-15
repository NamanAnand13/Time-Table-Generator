package scheduler;

import java.util.ArrayList;
import java.util.List;

public class StudentGroup {
    private int id;
    private String name;
    private List<String> subjects; // List of subjects
    private int noOfSubjects; // Number of subjects
    private List<Integer> teacherIds; // List of teacher IDs assigned to the group
    private List<Integer> hours; // Hours allocated for each subject
    private List<String> classrooms;

    // Constructor initializes the lists
    public StudentGroup() {
        subjects = new ArrayList<>();
        teacherIds = new ArrayList<>();
        hours = new ArrayList<>();
        classrooms = new ArrayList<>();
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

    // Getter for subjects list
    public List<String> getSubjects() {
        return subjects;
    }

    // Add a subject to the group
    public void addSubject(String subject) {
        this.subjects.add(subject);
    }

    // Getter and Setter for noOfSubjects
    public int getNoOfSubjects() {
        return noOfSubjects;
    }

    public void setNoOfSubjects(int noOfSubjects) {
        this.noOfSubjects = noOfSubjects;
    }

    // Getter for teacherIds
    public List<Integer> getTeacherIds() {
        return teacherIds;
    }

    // Add a teacher ID to the list
    public void addTeacherId(int teacherId) {
        this.teacherIds.add(teacherId);
    }

    // Getter for hours list
    public List<Integer> getHours() {
        return hours;
    }

    // Add hours for a subject
    public void addHours(int hour) {
        this.hours.add(hour);
    }

    public void addClassroom(String classroom) {
        this.classrooms.add(classroom);
    }

    public List<String> getClassrooms() {
        return classrooms;
    }

    @Override
    public String toString() {
        return "StudentGroup [id=" + id + ", name=" + name + ", subjects=" + subjects + ", noOfSubjects=" + noOfSubjects
                + ", teacherIds=" + teacherIds + ", hours=" + hours + "]";
    }
}
