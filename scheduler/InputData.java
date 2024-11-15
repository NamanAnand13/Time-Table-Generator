package scheduler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class InputData {

    private static List<StudentGroup> studentGroups; // List of student groups
    public static List<Teacher> teachers; // List of teachers
    public static Set<String> classrooms = new HashSet<>();
    public static double crossoverRate = 1.0, mutationRate = 0.1;
    public static int noStudentGroups, noTeachers;
    public static int hoursPerDay = 7, daysPerWeek = 5; // Hardcoded for development

    // Constructor initializes lists
    public InputData() {
        studentGroups = new ArrayList<>();
        teachers = new ArrayList<>();
    }

    // Takes input from a file (input.txt)
    public void takeInput() {
        // Hardcoded file path for development purposes
        File file = new File("/Users/namananand/Desktop/Naman/TimeTable-Generator/Time-Table-Generator/test/input.txt");

        // Try-with-resources for better resource management
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Process student groups
                if (line.equalsIgnoreCase("studentgroups")) {
                    processStudentGroups(scanner);
                }
                // Process teachers
                processTeachers(scanner);
                // }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Assign teachers to subjects for each student group
        assignTeachers();
    }

    // Processes the input for student groups
    private void processStudentGroups(Scanner scanner) {
        int i = 0;
        String line;
        while (!(line = scanner.nextLine()).equalsIgnoreCase("teachers")) {
            StudentGroup group = new StudentGroup();
            StringTokenizer st = new StringTokenizer(line, " ");
            group.setId(i);
            group.setName(st.nextToken());
            group.setNoOfSubjects(0);

            while (st.hasMoreTokens()) {
                group.addSubject(st.nextToken());
                group.addHours(Integer.parseInt(st.nextToken()));
                group.setNoOfSubjects(group.getNoOfSubjects() + 1);
                String classroom = st.nextToken();
                group.addClassroom(classroom);
                classrooms.add(classroom);
            }

            studentGroups.add(group);
            i++;
        }
        noStudentGroups = studentGroups.size();
    }

    // Processes the input for teachers
    private void processTeachers(Scanner scanner) {
        int i = 0;
        String line;
        while (!(line = scanner.nextLine()).equalsIgnoreCase("end")) {
            // Teacher teacher = new Teacher();
            StringTokenizer st = new StringTokenizer(line, " ");
            String teacher = st.nextToken();
            String subject = st.nextToken();
            Teacher existingTeacher = null;
            for (Teacher t : teachers) {
                if (t.getName().equalsIgnoreCase(teacher)) {
                    existingTeacher = t;
                    break;
                }
            }
            // teacher.setId(i);
            // teacher.setName(st.nextToken());
            // teacher.setSubject(st.nextToken());

            // teachers.add(teacher);
            if (existingTeacher != null) {
                existingTeacher.getSubjects().add(subject);
            } else {
                Teacher newTeacher = new Teacher();
                newTeacher.setId(i++);
                newTeacher.setName(teacher);
                // newTeacher.setSubject(subject);
                newTeacher.addSubject(subject);

                teachers.add(newTeacher);
            }
            // i++;
        }
        noTeachers = teachers.size();
        // for (Teacher teacher : teachers) {
        //     System.out.println(teacher.getName());
        //     for (String sub : teacher.getSubjects()) {
        //         System.out.print(sub + ' ');
        //     }
        //     System.out.println('\n');
        // }
    }

    // Assigns teachers to each subject for every student group
    public void assignTeachers() {
        // Loop through every student group
        for (StudentGroup group : studentGroups) {
            // Loop through every subject of a student group
            for (int j = 0; j < group.getNoOfSubjects(); j++) {
                String subject = group.getSubjects().get(j);
                int teacherId = findTeacherForSubject(subject);
                System.out.println(subject + ' ' + teacherId);
                // Increment assigned count for the selected teacher
                if (teacherId == -1) {
                    System.err.println(
                            "No teacher found for subject: " + subject + " in student group: " + group.getName());
                    continue; // Skip assigning if no teacher is found
                }
                teachers.get(teacherId).setAssigned(teachers.get(teacherId).getAssigned() + 1);

                // Assign teacher to the student group's subject
                group.addTeacherId(teacherId);
            }
        }
    }

    // Finds the best teacher for a given subject based on least prior assignments
    private int findTeacherForSubject(String subject) {
        int teacherId = -1;
        int minAssignments = Integer.MAX_VALUE;

        // Loop through all teachers and find the one with the least assignments for the
        // subject
        for (Teacher teacher : teachers) {
            // System.out.println(teacher.getName());
            for (String teacherSubject : teacher.getSubjects()) {
                // System.out.println("Subjects : " + teacherSubject);
                if (teacherSubject.equalsIgnoreCase(subject)) {
                    if (teacherId == -1 || teacher.getAssigned() < minAssignments) {
                        System.err.println("here : " + teacherSubject);
                        minAssignments = teacher.getAssigned();
                        teacherId = teacher.getId();
                    }
                }

            }
        }

        return teacherId;
    }

    // Getters for accessing the data
    public static List<StudentGroup> getStudentGroups() {
        return studentGroups;
    }

    public static List<Teacher> getTeachers() {
        return teachers;
    }
}