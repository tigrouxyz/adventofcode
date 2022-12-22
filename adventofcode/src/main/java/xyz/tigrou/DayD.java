package xyz.tigrou;

import java.util.ArrayList;
import java.util.List;

import xyz.tigrou.tools.Tools;

public class DayD {

    private static final String INPUT = "input_d.txt";
     
    public static void main(String[] args) throws Exception {
        partOne();
        partTwo();
    }

    private static void partOne() throws Exception {
        List<String> lines = Tools.getInput(INPUT);

        int errors = 0;
        for (String line : lines) {
            String[] parts = line.split(",");

            List<Integer> firstRange = parseRange(parts[0]);
            List<Integer> secondRange = parseRange(parts[1]);

            errors += checkFullOverlapError(firstRange, secondRange);
        }

        System.out.println("Errors: " + errors);
    }

    private static void partTwo() throws Exception {
        List<String> lines = Tools.getInput(INPUT);

        int errors = 0;
        for (String line : lines) {
            String[] parts = line.split(",");

            List<Integer> firstRange = parseRange(parts[0]);
            List<Integer> secondRange = parseRange(parts[1]);

            errors += checkOverlapError(firstRange, secondRange);
        }

        System.out.println("Errors: " + errors);
    }

    private static int checkFullOverlapError(List<Integer> firstRange, List<Integer> secondRange) {
        // Check if full range is contains in the other
        if (firstRange.containsAll(secondRange) || secondRange.containsAll(firstRange)) {
            return 1;
        }
        return 0;
    }

    private static int checkOverlapError(List<Integer> firstRange, List<Integer> secondRange) {
        // Check if one value is in the other range
        for (Integer value : firstRange) {
            if (secondRange.contains(value)) {
                return 1;
            }
        }
        return 0;
    }

    private static List<Integer> parseRange(String value) {
        int firstPart = Integer.parseInt(value.split("-")[0]);
        int secondPart = Integer.parseInt(value.split("-")[1]);

        List<Integer> result = new ArrayList<>();
        for (int i = firstPart; i <= secondPart; i++) {
            result.add(i);
        }
        return result;
    }
}
