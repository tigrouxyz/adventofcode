package xyz.tigrou;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import xyz.tigrou.tools.Tools;

public class DayF {

    private static final String INPUT = "input_f.txt";
     
    public static void main(String[] args) throws Exception {
        partOne();
        partTwo();
    }

    private static void partOne() throws Exception {
        checkMarkers(4);
    }

    private static void partTwo() throws Exception {
        checkMarkers(14);
    }

    private static void checkMarkers(int markerSize) {
        List<String> lines = Tools.getInput(INPUT);

        for (String line : lines) {
            Queue<String> queue = new LinkedList<>();

            int charNumber = 0;
            for (char item : line.toCharArray()) {
                if (queue.size() >= markerSize) {
                    queue.remove();
                }
                queue.add(String.valueOf(item));

                charNumber++;

                boolean isMarkerFound = checkMarker(queue, markerSize);
                if (isMarkerFound) {
                    System.out.println("Marker found at " + charNumber);
                    break;
                }
            }
        }
    }

    private static boolean checkMarker(Queue<String> queue, int markerSize) {
        if (queue.size() < markerSize) {
            return false;
        }

        HashMap<String, Integer> map = new HashMap<>();
        for (String item : queue) {
            if (map.get(item) !=  null) {
                return false;
            }
            map.put(item, 0);
        }
        return true;
    }
}
