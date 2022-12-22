package xyz.tigrou;

import java.io.IOException;
import java.util.List;

import xyz.tigrou.tools.Tools;

public class DayA {

    private static final String INPUT = "input_a.txt";
    
    public static void main(String[] args) throws IOException {
        partOne();
        partTwo();
    }

    private static void partOne() {
        List<String> lines = Tools.getInput(INPUT);
        int max = -1;
        int currentNumber = 0;

        for (String line : lines) {
            // If the line is a breakline we test the max
            if (line.equals("")) {
                // If the current number is greater than the max, we update the max
                if (currentNumber > max) {
                    max = currentNumber;
                }
                currentNumber = 0;
            } else {
                // We get the number of the line
                int number = Integer.parseInt(line);
                // We add the number to the current number
                currentNumber += number;
            }
        }

        System.out.println("Max found: " + max);
    }

    private static void partTwo() {
        List<String> lines = Tools.getInput(INPUT);

        int topOne = -1;
        int topTwo = -1;
        int topThree = -1;
        
        int currentNumber = 0;

        for (String line : lines) {
            // If the line is a breakline we test the max
            if (line.equals("")) {
                // If the current number is greater than the max, we update the max
                if (currentNumber > topOne) {
                    topThree = topTwo;
                    topTwo = topOne;
                    topOne = currentNumber;
                } else if (currentNumber > topTwo) {
                    topThree = topTwo;
                    topTwo = currentNumber;
                } else if (currentNumber > topThree) {
                    topThree = currentNumber;
                }
                currentNumber = 0;
            } else {
                // We get the number of the line
                int number = Integer.parseInt(line);
                // We add the number to the current number
                currentNumber += number;
            }
        }

        System.out.println("Max found for top three: " + (topOne + topTwo + topThree));
    }
}
