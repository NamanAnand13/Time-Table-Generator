package scheduler;

public class Utility {
    // Print input data (student groups and teachers)
    public static void printInputData() {
        System.out.println("No. of Student Groups: " + InputData.getStudentGroups().size() + 
                           " No. of Teachers: " + InputData.getTeachers().size() + 
                           " Days per Week: " + InputData.daysPerWeek + 
                           " Hours per Day: " + InputData.hoursPerDay);

        // Print student groups and their subjects
        for (StudentGroup group : InputData.getStudentGroups()) {
            System.out.println("Student Group ID: " + group.getId() + " Name: " + group.getName());

            for (int j = 0; j < group.getNoOfSubjects(); j++) {
                System.out.println("Subject: " + group.getSubjects().get(j) + " " + 
                                   group.getHours().get(j) + " hrs " + 
                                   "Teacher ID: " + group.getTeacherIds().get(j));
            }
            System.out.println("");
        }

        // Print teachers and their assigned subjects
        for (Teacher teacher : InputData.getTeachers()) {
            System.out.println("Teacher ID: " + teacher.getId() + " Name: " + teacher.getName() + 
                               " Subject: " + teacher.getSubjects() + 
                               " No. of Assignments: " + teacher.getAssigned());
        }
    }

    // Print timetable slots
    public static void printSlots() {
        int days = InputData.daysPerWeek;
        int hours = InputData.hoursPerDay;
        int noOfStudentGroups = InputData.getStudentGroups().size();

        System.out.println("----Slots----");

        for (int i = 0; i < days * hours * noOfStudentGroups; i++) {
            Slot slot = TimeTable.getSlot(i);
            if (slot != null) {
                System.out.println(i + " - " + slot.getStudentGroup().getName() + " " + 
                                   slot.getSubject() + " Teacher ID: " + slot.getTeacherId() + " Classroom : " + slot.getClassroom());
            } else {
                System.out.println(i + " - Free Period");
            }

            // Print a separator between days
            if ((i + 1) % (hours * days) == 0) {
                System.out.println("******************************");
            }
        }
    }
}