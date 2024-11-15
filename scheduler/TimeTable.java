package scheduler;

import java.util.ArrayList;
import java.util.List;

// Through this class, slots are generated to match minimum hours criteria for each subject
// Also, this class holds slots in order
public class TimeTable {

    private static List<Slot> slots; // Changed from array to List for flexibility

    // Constructor initializes the timetable slots based on student group
    // requirements
    public TimeTable() {
        slots = new ArrayList<>(); // Initialize the list of slots

        int noStudentGroups = InputData.noStudentGroups;
        int hoursPerDay = InputData.hoursPerDay;
        int daysPerWeek = InputData.daysPerWeek;

        // Looping for every student group
        for (int i = 0; i < noStudentGroups; i++) {
            StudentGroup sg = InputData.getStudentGroups().get(i);
            int subjectNo = 0; // Track current subject
            int hourCount = 1; // Track the number of hours assigned for the current subject

            // Looping for each slot in a week for the student group
            for (int j = 0; j < hoursPerDay * daysPerWeek; j++) {
                // If all subjects have been assigned required hours, give free periods
                if (subjectNo >= sg.getNoOfSubjects()) {
                    slots.add(null); // Add a free period
                } else {
                    // Create new slots for the assigned subject
                    slots.add(new Slot(sg, sg.getTeacherIds().get(subjectNo), sg.getSubjects().get(subjectNo),
                            sg.getClassrooms().get(subjectNo)));

                    // Check if the required hours for the current subject have been assigned
                    if (hourCount < sg.getHours().get(subjectNo)) {
                        hourCount++;
                    } else {
                        hourCount = 1; // Reset hour count
                        subjectNo++; // Move to the next subject
                    }
                }
            }
        }
    }

    // Return the list of slots
    public static List<Slot> returnSlots() {
        return slots;
    }

    // Optional: Method to print all slots for debugging
    public static void printSlots() {
        for (int i = 0; i < slots.size(); i++) {
            Slot slot = slots.get(i);
            if (slot != null) {
                System.out.println(i + " - " + slot.getStudentGroup().getName() + " " +
                        slot.getSubject() + " Teacher ID: " + slot.getTeacherId());
            } else {
                System.out.println(i + " - Free Period");
            }
        }
    }

    public static Slot getSlot(int index) {
        if (index >= 0 && index < slots.size()) {
            return slots.get(index);
        }
        return null; // Return null if index is out of bounds
    }
}
