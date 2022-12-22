package xyz.tigrou;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xyz.tigrou.tools.Tools;

public class DayC {

    private static final String INPUT = "input_c.txt";

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
     
    public static void main(String[] args) throws Exception {
        partOne();
        partTwo();
    }

    private static void partOne() throws Exception {
        List<String> lines = Tools.getInput(INPUT);

        int sum = 0;
        for (String line : lines) {
            String firstCompartment  = line.substring(0, line.length() / 2);
            String secondCompartment  = line.substring(line.length() / 2, line.length());

            sum += checkItemsInCompartment(firstCompartment, secondCompartment);
        }

        System.out.println("Sum: " + sum);
    }

    private static void partTwo() throws Exception {
        List<String> lines = Tools.getInput(INPUT);

        List<String> groupRucksack = new ArrayList<>();

        int sum = 0;
        for (String line : lines) {
            groupRucksack.add(line);
            if (groupRucksack.size() == 3) {
                sum += checkItemsInCompartment(groupRucksack.toArray(new String[groupRucksack.size()]));
                groupRucksack = new ArrayList<>();
            }
        }

        System.out.println("Sum: " + sum);
    }

    private static int checkItemsInCompartment(String... compartments) {
        for (int i = 0; i < compartments[0].length(); i++) {
            char firstChar = compartments[0].charAt(i);
            
            boolean isPresentInAllCompartment = true;
            for (int j = 1; j < compartments.length; j++) {
                String followingCompartment = compartments[j];
                if (followingCompartment.indexOf(firstChar) == -1) {
                    isPresentInAllCompartment = false;
                    break;
                }
            }
            
            if (isPresentInAllCompartment) {
                int number = ALPHABET.indexOf(firstChar) + 1;
                System.out.println("Found: " + firstChar + " (" + number + ") in " + Arrays.toString(compartments));
                return number;
            }
        }
        return 0;
    }
}
