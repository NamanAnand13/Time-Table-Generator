package scheduler;

import java.io.*;
import java.util.*;
 
// Gene represents a permutation of slots as a timetable for a single student group
public class Gene implements Serializable {
    int[] slotNo;   // Array to represent slot numbers
    private final int days;
    private final int hours;

    private Random random = new Random();

    // Constructor with index i, generates a random permutation of slot numbers for a group
    public Gene(int i) {
        days = InputData.daysPerWeek;  // Assuming InputData class holds these constants
        hours = InputData.hoursPerDay;

        boolean[] flag = new boolean[days * hours];
        slotNo = new int[days * hours];

        // Generate array of slot numbers corresponding to the gene index
        for (int j = 0; j < days * hours; j++) {
            int rnd;
            // Find a unique random slot not used yet
            while (flag[rnd = random.nextInt(days * hours)]) {
                // Loop until a free random slot is found
            }
            flag[rnd] = true;
            slotNo[j] = i * days * hours + rnd;
        }
    }

    // Getter for slotNo array
    public int[] getSlotNo() {
        return slotNo;
    }

    // Method to deep clone a Gene object using serialization
    public Gene deepClone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Gene) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // For debugging, to print the generated slots
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Gene Slots: ");
        for (int slot : slotNo) {
            sb.append(slot).append(" ");
        }
        return sb.toString();
    }
}
