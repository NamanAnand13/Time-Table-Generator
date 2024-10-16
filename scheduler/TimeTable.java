package scheduler;

import java.util.ArrayList;
import java.util.List;

// Through this class, slots are generated to match minimum hours criteria for each subject
// Also, this class holds slots in order
public class TimeTable {

    private static List<Slot> slots;  // Changed from array to List for flexibility

    // Constructor initializes the timetable slots based on student group requirements
    public TimeTable() {
        slots = new ArrayList<>();  // Initialize the list of slots

        int noStudentGroups = InputData.noStudentGroups;
        int hoursPerDay = InputData.hoursPerDay;
        int daysPerWeek = InputData.daysPerWeek;

        // Looping for every student group
        for (int i = 0; i < noStudentGroups; i++) {
            StudentGroup sg = InputData.getStudentGroups().get(i);
            int subjectNo = 0;  // Track current subject
            int hourCount = 1;   // Track the number of hours assigned for the current subject

            // Looping for each slot in a week for the student group
            for (int j = 0; j < hoursPerDay * daysPerWeek; j++) {
                // If all subjects have been assigned required hours, give free periods
                if (subjectNo >= sg.getNoOfSubjects()) {
                    slots.add(null);  // Add a free period
                } else {
                    // Create new slots for the assigned subject
                    slots.add(new Slot(sg, sg.getTeacherIds().get(subjectNo), sg.getSubjects().get(subjectNo)));

                    // Check if the required hours for the current subject have been assigned
                    if (hourCount < sg.getHours().get(subjectNo)) {
                        hourCount++;
                    } else {
                        hourCount = 1;  // Reset hour count
                        subjectNo++;    // Move to the next subject
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
        return null;  // Return null if index is out of bounds
    }
}


// package scheduler;

// //through this class slots are generated to match minimum hours criteria for each subject
// //also this class holds slots in order
// public class TimeTable {

// 	public static Slot[] slot;

// 	TimeTable() {

// 		int k = 0;
// 		int subjectno = 0;
// 		int hourcount = 1;
// 		int days = inputdata.daysperweek;
// 		int hours = inputdata.hoursperday;
// 		int nostgrp = inputdata.nostudentgroup;

// 		// creating as many slots as the no of blocks in overall timetable
// 		slot = new Slot[hours * days * nostgrp];

// 		// looping for every student group
// 		for (int i = 0; i < nostgrp; i++) {

// 			subjectno = 0;
// 			// for every slot in a week for a student group
// 			for (int j = 0; j < hours * days; j++) {

// 				StudentGroup sg = inputdata.studentgroup[i];

// 				// if all subjects have been assigned required hours we give
// 				// free periods
// 				if (subjectno >= sg.nosubject) {
// 					slot[k++] = null;
// 				}

// 				// if not we create new slots
// 				else {

// 					slot[k++] = new Slot(sg, sg.teacherid[subjectno], sg.subject[subjectno]);

// 					// suppose java has to be taught for 5 hours then we make 5
// 					// slots for java, we keep track through hourcount
// 					if (hourcount < sg.hours[subjectno]) {
// 						hourcount++;
// 					} else {
// 						hourcount = 1;
// 						subjectno++;
// 					}

// 				}

// 			} // end inner for

// 		} // end outer for

// 	}// end constructor

// 	public static Slot[] returnSlots() {
// 		return slot;
// 	}
// }