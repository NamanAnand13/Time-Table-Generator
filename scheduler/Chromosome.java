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

            for (int j = 0; j < noStudentGroups; j++) {
                Slot slot = TimeTable.getSlot(gene[j].getSlotNo()[i]); // Access slot from TimeTable

                if (slot != null) {
                    if (teacherList.contains(slot.getTeacherId())) {
                        point++; // Teacher conflict found
                    } else {
                        teacherList.add(slot.getTeacherId());
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

// package scheduler;

// import java.io.*;
// import java.util.*;

// //Chromosome represents array of genes as complete timetable (looks like
// gene[0]gene[1]gene[2]...)
// public class Chromosome implements Comparable<Chromosome>,Serializable{

// static double crossoverrate=inputdata.crossoverrate;
// static double mutationrate=inputdata.mutationrate;
// static int hours=inputdata.hoursperday,days=inputdata.daysperweek;
// static int nostgrp=inputdata.nostudentgroup;
// double fitness;
// int point;

// public Gene[] gene;

// Chromosome(){

// gene=new Gene[nostgrp];

// for(int i=0;i<nostgrp;i++){

// gene[i]=new Gene(i);

// //System.out.println("");
// }
// fitness=getFitness();

// }

// public Chromosome deepClone() {
// try {
// ByteArrayOutputStream baos = new ByteArrayOutputStream();
// ObjectOutputStream oos = new ObjectOutputStream(baos);
// oos.writeObject(this);

// ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
// ObjectInputStream ois = new ObjectInputStream(bais);
// return (Chromosome) ois.readObject();
// } catch (IOException e) {
// return null;
// } catch (ClassNotFoundException e) {
// return null;
// }
// }

// public double getFitness(){
// point=0;
// for(int i=0;i<hours*days;i++){

// List<Integer> teacherlist=new ArrayList<Integer>();

// for(int j=0;j<nostgrp;j++){

// Slot slot;
// //System.out.println("i="+i+" j="+j);
// if(TimeTable.slot[gene[j].slotno[i]]!=null)
// slot=TimeTable.slot[gene[j].slotno[i]];
// else slot=null;

// if(slot!=null){

// if(teacherlist.contains(slot.teacherid)){
// point++;
// }
// else teacherlist.add(slot.teacherid);
// }
// }

// }
// //System.out.println(point);
// fitness=1-(point/((nostgrp-1.0)*hours*days));
// point=0;
// return fitness;
// }

// //printing final timetable
// public void printTimeTable(){

// //for each student group separate time table
// for(int i=0;i<nostgrp;i++){

// //status used to get name of student grp because in case first class is free
// it will throw error
// boolean status=false;
// int l=0;
// while(!status){

// //printing name of batch
// if(TimeTable.slot[gene[i].slotno[l]]!=null){
// System.out.println("Batch
// "+TimeTable.slot[gene[i].slotno[l]].studentgroup.name+" Timetable-");

// status=true;
// }
// l++;

// }

// //looping for each day
// for(int j=0;j<days;j++){

// //looping for each hour of the day
// for(int k=0;k<hours;k++){

// //checking if this slot is free otherwise printing it
// if(TimeTable.slot[gene[i].slotno[k+j*hours]]!=null)

// System.out.print(TimeTable.slot[gene[i].slotno[k+j*hours]].subject+" ");

// else System.out.print("*FREE* ");

// }

// System.out.println("");
// }

// System.out.println("\n\n\n");

// }

// }

// public void printChromosome(){

// for(int i=0;i<nostgrp;i++){
// for(int j=0;j<hours*days;j++){
// System.out.print(gene[i].slotno[j]+" ");
// }
// System.out.println("");
// }

// }

// public int compareTo(Chromosome c) {

// if(fitness==c.fitness) return 0;
// else if(fitness>c.fitness) return -1;
// else return 1;

// }

// }