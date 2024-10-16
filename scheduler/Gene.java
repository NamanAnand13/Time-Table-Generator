// package scheduler;

// import java.util.Random;
// import java.io.*;

// //gene represents permutation of slots as timetable for a single student group(looks like {5,22,14,1,...} )
// public class Gene implements Serializable{

// 	public int slotno[];
// 	int days=inputdata.daysperweek;
// 	int hours=inputdata.hoursperday;
	
// 	Random r=new Random();
	
// 	Gene(int i){
		
// 		boolean[] flag=new boolean[days*hours];
		
// 		/*  generating an array of slot no corresponding to index of gene eg suppose index
// 		 *	is 2 then slotno will vary from 2*hours*days to 3*hours*days
// 		 */
		
// 		slotno=new int[days*hours];
		
// 		for(int j=0;j<days*hours;j++){
			
// 			int rnd;
// 			while(flag[rnd=r.nextInt(days*hours)]==true){}
// 			flag[rnd]=true;
// 			slotno[j]=i*days*hours + rnd;
			
// 			/*	Slot[] slot=TimeTable.returnSlots();
// 			 *	if(slot[slotno[j]]!=null)System.out.print(slot[slotno[j]].subject+" ");
// 			 *	else System.out.print("break ");
// 			 */
		
// 		}
		
// 	}
	
// 	public Gene deepClone() {
// 		try {
// 			ByteArrayOutputStream baos = new ByteArrayOutputStream();
// 			ObjectOutputStream oos = new ObjectOutputStream(baos);
// 			oos.writeObject(this);

// 			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
// 			ObjectInputStream ois = new ObjectInputStream(bais);
// 			return (Gene) ois.readObject();
// 		} catch (IOException e) {
// 			return null;
// 		} catch (ClassNotFoundException e) {
// 			return null;
// 		}
// 	}
// }
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
