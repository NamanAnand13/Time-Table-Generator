package scheduler;

import java.io.*;
import java.util.*;

// Chromosome represents an array of genes as a complete timetable
public class Chromosome implements Comparable<Chromosome>, Serializable {

    private static double crossoverRate = InputData.crossoverRate;
    private static double mutationRate = InputData.mutationRate;
    private static int hours = InputData.hoursPerDay;
    private static int days = InputData.daysPerWeek;
    private static int noStudentGroups = InputData.noStudentGroups;
    private static int noTeachers = InputData.noTeachers;
    // private static List<Teacher> teachers = InputData.teachers;

    private double fitness;
    private int point;
    public Gene[] gene;

    // Constructor initializes the array of genes (one for each student group)
    public Chromosome() {
        gene = new Gene[noStudentGroups];
        for (int i = 0; i < noStudentGroups; i++) {
            gene[i] = new Gene(i); // Create a gene for each student group
        }
        fitness = getFitness();
    }

    // Deep cloning a chromosome using serialization
    public Chromosome deepClone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Chromosome) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Calculate the fitness of the chromosome
    public double getFitness() {
        point = 0;
        for (int i = 0; i < hours * days; i++) {
            List<Integer> teacherList = new ArrayList<>();
            List<String> classroomList = new ArrayList<>();
            for (int j = 0; j < noStudentGroups; j++) {
                Slot slot = TimeTable.getSlot(gene[j].getSlotNo()[i]); // Access slot from TimeTable

                if (slot != null) {
                    if (teacherList.contains(slot.getTeacherId()) || classroomList.contains(slot.getClassroom())) {
                        point++; // Teacher conflict found
                    } 
                    // if (teacherList.contains(slot.getTeacherId())) {
                    //     point++; // Teacher conflict found
                    // } 
                    else {
                        teacherList.add(slot.getTeacherId());
                        classroomList.add(slot.getClassroom());
                    }
                }
            }
        }

        fitness = 1 - (point / ((noStudentGroups - 1.0) * hours * days));
        point = 0;
        return fitness;
    }

    // Print the final timetable
    public void printTimeTable() {
        for (int i = 0; i < noStudentGroups; i++) {
            boolean status = false;
            int l = 0;

            // Print the name of the batch
            while (!status && l < hours * days) {
                Slot slot = TimeTable.getSlot(gene[i].getSlotNo()[l]);
                if (slot != null) {
                    System.out.println("Batch " + slot.getStudentGroup().getName() + " Timetable: \n");
                    status = true;
                }
                l++;
            }

            // Loop through each day
            for (int j = 0; j < days; j++) {
                // Loop through each hour of the day
                for (int k = 0; k < hours; k++) {
                    Slot slot = TimeTable.getSlot(gene[i].getSlotNo()[k + j * hours]);
                    if (slot != null) {
                        System.out.print("| " + slot.getSubject() + " |");
                    } else {
                        System.out.print("| *FREE* |");
                    }
                }
                System.out.println("");
            }
            System.out.println("\n\n\n");
        }
    }

    // Print the teacher timetable
    public void printTeacherTimeTable() {
        // Iterate over all teachers
        for (Teacher teacher : InputData.teachers) { // Assuming `InputData.teachers` is the list of all teachers
            boolean status = false;
            int l = 0;
            int id = teacher.getId();
            // Print the name of the teacher
            while (!status && l < noStudentGroups * hours * days) {
                Slot slot = TimeTable.getSlot(gene[l / (hours * days)].getSlotNo()[l % (hours * days)]);
                if (slot != null && slot.getTeacherId() == id) {
                    System.out.println("Teacher " + teacher.getName() + " Timetable: \n");
                    status = true;
                }
                l++;
            }

            // Loop through each day
            for (int j = 0; j < days; j++) {
                // Loop through each hour of the day
                for (int k = 0; k < hours; k++) {
                    boolean slotAssigned = false;

                    // Iterate through student groups to check each slot for the teacher
                    for (int i = 0; i < noStudentGroups; i++) {
                        Slot slot = TimeTable.getSlot(gene[i].getSlotNo()[k + j * hours]);
                        if (slot != null && slot.getTeacherId() == id) {
                            System.out.print("| " + slot.getSubject() + " |");
                            slotAssigned = true;
                            break;
                        }
                    }

                    // If no slot is assigned, mark as FREE
                    if (!slotAssigned) {
                        System.out.print("| *FREE* |");
                    }
                }
                System.out.println("");
            }
            System.out.println("\n\n\n");
        }
    }

    public void printClassroomTimeTable() {
        // Iterate over all classrooms
        for (String classroom : InputData.classrooms) { // Assuming `InputData.classrooms` is the list of all classrooms
            boolean status = false;
            int l = 0;
    
            // Print the name of the classroom
            while (!status && l < noStudentGroups * hours * days) {
                Slot slot = TimeTable.getSlot(gene[l / (hours * days)].getSlotNo()[l % (hours * days)]);
                if (slot != null && slot.getClassroom().equals(classroom)) {
                    System.out.println("Classroom " + classroom + " Timetable: \n");
                    status = true;
                }
                l++;
            }
    
            // Loop through each day
            for (int j = 0; j < days; j++) {
                // Loop through each hour of the day
                for (int k = 0; k < hours; k++) {
                    boolean slotAssigned = false;
    
                    // Iterate through student groups to check each slot for the classroom
                    for (int i = 0; i < noStudentGroups; i++) {
                        Slot slot = TimeTable.getSlot(gene[i].getSlotNo()[k + j * hours]);
                        if (slot != null && slot.getClassroom().equals(classroom)) {
                            System.out.print("| " + slot.getSubject() + " (" + slot.getStudentGroup().getName() + ") |");
                            slotAssigned = true;
                            break;
                        }
                    }
    
                    // If no slot is assigned, mark as FREE
                    if (!slotAssigned) {
                        System.out.print("| *FREE* |");
                    }
                }
                System.out.println("");
            }
            System.out.println("\n\n\n");
        }
    }    

    // Print the chromosome
    public void printChromosome() {
        for (int i = 0; i < noStudentGroups; i++) {
            for (int j = 0; j < hours * days; j++) {
                System.out.print(gene[i].getSlotNo()[j] + " ");
            }
            System.out.println("");
        }
    }

    @Override
    public int compareTo(Chromosome c) {
        return Double.compare(c.fitness, this.fitness);
    }
}