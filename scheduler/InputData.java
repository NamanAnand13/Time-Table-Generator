package scheduler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class InputData {

    private static List<StudentGroup> studentGroups; // List of student groups
    private static List<Teacher> teachers; // List of teachers
    public static double crossoverRate = 1.0, mutationRate = 0.1;
    public static int noStudentGroups, noTeachers;
    public static int hoursPerDay = 7, daysPerWeek = 5; // Hardcoded for development

    // Constructor initializes lists
    public InputData() {
        studentGroups = new ArrayList<>();
        teachers = new ArrayList<>();
    }

    // Validates the format of a class line
    private boolean classFormat(String line) {
        StringTokenizer st = new StringTokenizer(line, " ");
        return st.countTokens() == 3;
    }

    // Takes input from a file (input.txt)
    public void takeInput() {
        // Hardcoded file path for development purposes
        File file = new File("/Users/namananand/Desktop/Naman/TimeTable-Generator/test/input.txt");

        // Try-with-resources for better resource management
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Process student groups
                if (line.equalsIgnoreCase("studentgroups")) {
                    processStudentGroups(scanner);
                }
                // Process teachers
                // if (line.equalsIgnoreCase("teachers")) {
                //     System.out.println("processing teachers");
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

            int j = 0;
            while (st.hasMoreTokens()) {
                group.addSubject(st.nextToken());
                group.addHours(Integer.parseInt(st.nextToken()));
                group.setNoOfSubjects(group.getNoOfSubjects() + 1);
                j++;
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
            Teacher teacher = new Teacher();
            StringTokenizer st = new StringTokenizer(line, " ");
            teacher.setId(i);
            teacher.setName(st.nextToken());
            teacher.setSubject(st.nextToken());

            teachers.add(teacher);
            i++;
        }
        noTeachers = teachers.size();
    }

    // Assigns teachers to each subject for every student group
    public void assignTeachers() {
        // Loop through every student group
        for (StudentGroup group : studentGroups) {
            // Loop through every subject of a student group
            for (int j = 0; j < group.getNoOfSubjects(); j++) {
                String subject = group.getSubjects().get(j);
                int teacherId = findTeacherForSubject(subject);

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
            if (teacher.getSubject().equalsIgnoreCase(subject)) {
                if (teacherId == -1 || teacher.getAssigned() < minAssignments) {
                    minAssignments = teacher.getAssigned();
                    teacherId = teacher.getId();
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

// package scheduler;

// import java.io.File;
// import java.util.*;

// public class inputdata {

// public static StudentGroup[] studentgroup;
// public static Teacher[] teacher;
// public static double crossoverrate = 1.0, mutationrate = 0.1;
// public static int nostudentgroup, noteacher;
// public static int hoursperday, daysperweek;

// public inputdata() {
// studentgroup = new StudentGroup[100];
// teacher = new Teacher[100];
// }

// boolean classformat(String l) {
// StringTokenizer st = new StringTokenizer(l, " ");
// if (st.countTokens() == 3)
// return (true);
// else
// return (false);
// }

// public void takeinput()// takes input from file input.txt
// {
// //this method of taking input through file is only for development purpose so
// hours and days are hard coded
// hoursperday = 7;
// daysperweek = 5;
// try {
// File file = new File("c:\\test\\input.txt");
// // File file = new File(System.getProperty("user.dir") +
// // "/input.txt");

// Scanner scanner = new Scanner(file);

// while (scanner.hasNextLine()) {
// String line = scanner.nextLine();

// // input student groups
// if (line.equalsIgnoreCase("studentgroups")) {
// int i = 0, j;
// while (!(line = scanner.nextLine()).equalsIgnoreCase("teachers")) {
// studentgroup[i] = new StudentGroup();
// StringTokenizer st = new StringTokenizer(line, " ");
// studentgroup[i].id = i;
// studentgroup[i].name = st.nextToken();
// studentgroup[i].nosubject = 0;
// j = 0;
// while (st.hasMoreTokens()) {
// studentgroup[i].subject[j] = st.nextToken();
// studentgroup[i].hours[j++] = Integer.parseInt(st.nextToken());
// studentgroup[i].nosubject++;
// }
// i++;
// }
// nostudentgroup = i;
// }

// //input teachers
// if (line.equalsIgnoreCase("teachers")) {
// int i = 0;
// while (!(line = scanner.nextLine()).equalsIgnoreCase("end")) {
// teacher[i] = new Teacher();
// StringTokenizer st = new StringTokenizer(line, " ");
// teacher[i].id = i;
// teacher[i].name = st.nextToken();
// teacher[i].subject = st.nextToken();

// i++;
// }
// noteacher = i;
// }

// }
// scanner.close();
// } catch (Exception e) {
// e.printStackTrace();
// }

// assignTeacher();

// }

// // assigning a teacher for each subject for every studentgroup
// public void assignTeacher() {

// // looping through every studentgroup
// for (int i = 0; i < nostudentgroup; i++) {

// // looping through every subject of a student group
// for (int j = 0; j < studentgroup[i].nosubject; j++) {

// int teacherid = -1;
// int assignedmin = -1;

// String subject = studentgroup[i].subject[j];

// // looping through every teacher to find which teacher teaches the current
// subject
// for (int k = 0; k < noteacher; k++) {

// // if such teacher found,checking if he should be assigned the subject or
// some other teacher based on prior assignments
// if (teacher[k].subject.equalsIgnoreCase(subject)) {

// // if first teacher found for this subject
// if (assignedmin == -1) {
// assignedmin = teacher[k].assigned;
// teacherid = k;
// }

// // if teacher found has less no of pre assignments than the teacher assigned
// for this subject
// else if (assignedmin > teacher[k].assigned) {
// assignedmin = teacher[k].assigned;
// teacherid = k;
// }
// }
// }

// // 'assigned' variable for selected teacher incremented
// teacher[teacherid].assigned++;

// studentgroup[i].teacherid[j]= teacherid;
// }
// }
// }
// }