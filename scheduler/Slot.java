package scheduler;

// Slot represents a single block of the timetable
public class Slot {
    private StudentGroup studentGroup; // Associated student group
    private int teacherId;             // ID of the assigned teacher
    private String subject;            // Subject for this slot

    // Default constructor for allowing free periods
    public Slot() {
    }

    // Parameterized constructor to create a slot with student group, teacher ID, and subject
    public Slot(StudentGroup studentGroup, int teacherId, String subject) {
        this.studentGroup = studentGroup;
        this.teacherId = teacherId;
        this.subject = subject;
    }

    // Getter and Setter for studentGroup
    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    // Getter and Setter for teacherId
    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    // Getter and Setter for subject
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    // Optional: Method to check if the slot is a free period
    public boolean isFreePeriod() {
        return this.subject == null || this.subject.isEmpty();
    }

    @Override
    public String toString() {
        return "Slot [studentGroup=" + studentGroup + ", teacherId=" + teacherId + ", subject=" + subject + "]";
    }
}

