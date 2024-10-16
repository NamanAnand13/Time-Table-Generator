package scheduler;

import java.util.*;

public class SchedulerMain {

    /*
     * Time Table scheduling is an NP-hard problem which can best be solved
     * using Genetic Algorithms (of Artificial Intelligence).
     * Concepts used here are permutation encoding, elitism, roulette wheel selection,
     * single-point crossover, and swap mutation.
     */

    private List<Chromosome> firstList;
    private List<Chromosome> newList;
    private double firstListFitness;
    private double newListFitness;
    private int populationSize = 1000;
    private int maxGenerations = 100;

    public static Chromosome finalSon;

    public SchedulerMain() {
        // Print input data (for testing)
        InputData inputData = new InputData();
        inputData.takeInput();
        Utility.printInputData();

        // Generating slots
        new TimeTable();

        // Printing slots (for testing purpose only)
        Utility.printSlots();

        // Initializing the first generation of chromosomes
        initializePopulation();

        // Generating newer generations of chromosomes using crossover and mutation
        createNewGenerations();
    }

    // Creating new generations using crossover and mutation
    public void createNewGenerations() {
        Chromosome father;
        Chromosome mother;
        Chromosome son = null;

        int noGenerations = 0;

        // Loop through max number of generations or until a suitable chromosome is found
        while (noGenerations < maxGenerations) {
            newList = new ArrayList<>();
            newListFitness = 0;

            // First 10% of chromosomes added as-is - Elitism
            for (int i = 0; i < populationSize / 10; i++) {
                newList.add(firstList.get(i).deepClone());
                newListFitness += firstList.get(i).getFitness();
            }

            // Adding other members after performing crossover and mutation
            for (int i = populationSize / 10; i < populationSize; i++) {
                father = selectParentRoulette();
                mother = selectParentRoulette();

                // Crossover
                if (new Random().nextDouble() < InputData.crossoverRate) {
                    son = crossover(father, mother);
                } else {
                    son = father;
                }

                // Mutation
                customMutation(son);

                if (son.getFitness() == 1) {
                    System.out.println("Selected Chromosome is:");
                    son.printChromosome();
                    break;
                }

                newList.add(son);
                newListFitness += son.getFitness();
            }

            // If a chromosome with fitness 1 is found
            if (newList.size() < populationSize) {
                System.out.println("****************************************************************************************");
                System.out.println("\n\nSuitable Timetable has been generated in the " + newList.size() + "th Chromosome of " + (noGenerations + 2) + " generation with fitness 1.");
                System.out.println("\nGenerated Timetable is:");
                son.printTimeTable();
                finalSon = son;
                break;
            }

            // If a chromosome with required fitness is not found in this generation
            firstList = newList;
            Collections.sort(newList);
            Collections.sort(firstList);
            System.out.println("**************************     Generation " + (noGenerations + 2) + "     ********************************************\n");
            printGeneration(newList);
            noGenerations++;
        }
    }

    // Selecting using Roulette Wheel Selection only from the best 10% chromosomes
    public Chromosome selectParentRoulette() {
        firstListFitness /= 10;
        double randomDouble = new Random().nextDouble() * firstListFitness;
        double currentSum = 0;
        int i = 0;

        while (currentSum <= randomDouble) {
            currentSum += firstList.get(i++).getFitness();
        }
        return firstList.get(--i).deepClone();
    }

    // Custom mutation
    public void customMutation(Chromosome c) {
        double newFitness = 0;
        double oldFitness = c.getFitness();
        int geneNo = new Random().nextInt(InputData.noStudentGroups);

        int attempts = 0;
        while (newFitness < oldFitness) {
            c.gene[geneNo] = new Gene(geneNo);
            newFitness = c.getFitness();

            attempts++;
            if (attempts >= 500000) break;  // Avoid infinite loop
        }
    }

    // Two-point crossover
    public Chromosome crossover(Chromosome father, Chromosome mother) {
        int randomIndex = new Random().nextInt(InputData.noStudentGroups);
        Gene temp = father.gene[randomIndex].deepClone();
        father.gene[randomIndex] = mother.gene[randomIndex].deepClone();
        mother.gene[randomIndex] = temp;
        return (father.getFitness() > mother.getFitness()) ? father : mother;
    }

    // Initializing the first generation of population
    public void initializePopulation() {
        // Generating the first generation of chromosomes
        firstList = new ArrayList<>();
        firstListFitness = 0;

        for (int i = 0; i < populationSize; i++) {
            Chromosome c = new Chromosome();
            firstList.add(c);
            firstListFitness += c.getFitness();
        }
        Collections.sort(firstList);
        System.out.println("----------Initial Generation-----------\n");
        printGeneration(firstList);
    }

    // Printing important details of a generation
    public void printGeneration(List<Chromosome> list) {
        System.out.println("Fetching details from this generation...\n");

        // Print the first four chromosomes of the sorted list
        for (int i = 0; i < Math.min(4, list.size()); i++) {
            System.out.println("Chromosome no." + i + ": " + list.get(i).getFitness());
            list.get(i).printChromosome();
            System.out.println("");
        }

        if (list.size() > (populationSize / 10)) {
            System.out.println("Chromosome no. " + (populationSize / 10 + 1) + " : " + list.get(populationSize / 10 + 1).getFitness() + "\n");
        }
        if (list.size() > (populationSize / 5)) {
            System.out.println("Chromosome no. " + (populationSize / 5 + 1) + " : " + list.get(populationSize / 5 + 1).getFitness() + "\n");
        }

        System.out.println("Most fit chromosome from this generation has fitness = " + list.get(0).getFitness() + "\n");
    }

    // Selecting from best chromosomes only (alternative to roulette wheel selection)
    public Chromosome selectParentBest(List<Chromosome> list) {
        int randomIndex = new Random().nextInt(100);
        return list.get(randomIndex).deepClone();
    }

    // Simple mutation operation
    public void mutation(Chromosome c) {
        int geneNo = new Random().nextInt(InputData.noStudentGroups);
        int temp = c.gene[geneNo].slotNo[0];

        for (int i = 0; i < InputData.daysPerWeek * InputData.hoursPerDay - 1; i++) {
            c.gene[geneNo].slotNo[i] = c.gene[geneNo].slotNo[i + 1];
        }
        c.gene[geneNo].slotNo[InputData.daysPerWeek * InputData.hoursPerDay - 1] = temp;
    }

    // Swap mutation
    public void swapMutation(Chromosome c) {
        int geneNo = new Random().nextInt(InputData.noStudentGroups);
        int slotNo1 = new Random().nextInt(InputData.hoursPerDay * InputData.daysPerWeek);
        int slotNo2 = new Random().nextInt(InputData.hoursPerDay * InputData.daysPerWeek);

        int temp = c.gene[geneNo].slotNo[slotNo1];
        c.gene[geneNo].slotNo[slotNo1] = c.gene[geneNo].slotNo[slotNo2];
        c.gene[geneNo].slotNo[slotNo2] = temp;
    }

    // Main method to start the scheduler
    public static void main(String[] args) {
        new SchedulerMain();
    }
}