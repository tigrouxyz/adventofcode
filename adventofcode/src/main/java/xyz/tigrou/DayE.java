package xyz.tigrou;

import java.util.ArrayList;
import java.util.List;

import xyz.tigrou.tools.Tools;

public class DayE {

    private static final String INPUT = "input_e.txt";
     
    public static void main(String[] args) throws Exception {
        partOne();
        partTwo();
    }

    private static void partOne() throws Exception {
        moveCratesAndPrintResult(true);
    }

    private static void partTwo() throws Exception {
        moveCratesAndPrintResult(false);
    }

    private static void moveCratesAndPrintResult(boolean moveOneByOne) {
        List<String> lines = Tools.getInput(INPUT);

        boolean isLineStack = true;

        List<String> linesStack = new ArrayList<>();
        List<String> linesMove = new ArrayList<>();
        for (String line : lines) {
            if (isLineStack) {
                if (line.isEmpty()) {
                    isLineStack = false;
                    continue;
                }
                linesStack.add(line);
            }
            else {
                linesMove.add(line);
            }
        }

        List<List<String>> stacks = buildStacks(linesStack);
        System.out.println("Initial state");
        displayCrates(stacks);

        List<Move> moves = buildMoves(linesMove);
        makeMoves(stacks, moves, moveOneByOne);

        System.out.println("Final state");
        displayCrates(stacks);

        String message = "";
        for (List<String> stack : stacks) {
            message += stack.get(stack.size() - 1);
        }

        System.out.println("Message: " + message);
    }

    private static void makeMoves(List<List<String>> stacks, List<Move> moves, boolean moveOneByOne) {
        for (Move move : moves) {
            List<String> fromStack = stacks.get(move.getFrom() - 1);
            List<String> toStack = stacks.get(move.getTo() - 1);

            if (moveOneByOne) {
                for (int i = 0; i < move.getNbOfCrates(); i++) {
                        // Move one crate at a time
                        String crate = fromStack.remove(fromStack.size() - 1);
                        toStack.add(crate);
                }
            }
            else {
                List<String> crates = new ArrayList<String>(fromStack.subList(fromStack.size() - move.getNbOfCrates(), fromStack.size()));
                for (int i = 0; i < move.getNbOfCrates(); i++) {
                    // Remove last crate
                    fromStack.remove(fromStack.size() - 1);
                }

                toStack.addAll(crates);
                // System.out.println("Move " + crates.size() + " from " + move.getFrom() + " to " + move.getTo() + " (" + crates.toString() + ")" + " - To stack: " + toStack.toString());
            }

            // displayCrates(stacks);
        }
    }

    private static List<List<String>> buildStacks(List<String> linesStack) {
        List<List<String>> stacks = new ArrayList<>();

        for (int i = 0; i < linesStack.size(); i++) {
            if (i == linesStack.size() - 1) {
                //We don't care about the last line
                continue;
            }

            String line = linesStack.get(i);
            // Split the line every three characters which corresponds to a crate
            String[] results = line.split("(?<=\\G.{4})");

            for (int j = 0; j < results.length; j++) {
                // If the stack doesn't exist, create it
                if (stacks.size() <= j) {
                    stacks.add(new ArrayList<String>());
                }

                String result = results[j].trim();
                if (result.isEmpty()) {
                    continue;
                }

                // Get the crate ID
                String crateId = result.substring(1, 2);
                // Add the crate to the stack
                stacks.get(j).add(0, crateId);
            }
        }

        return stacks;
    }

    private static void displayCrates(List<List<String>> stacks) {
        for (int i = 0; i < stacks.size(); i++) {
            System.out.println("Stack " + (i + 1) + ": " + stacks.get(i).toString() + " (" + stacks.get(i).size() + " crates)");
        }
    }

    private static List<Move> buildMoves(List<String> linesMove) {
        List<Move> moves = new ArrayList<>();
        for (String line : linesMove) {
            line = line.replace("move", "");

            String[] value = line.split("from");
            int nbMoves = Integer.parseInt(value[0].trim());

            value = value[1].split("to");
            int from = Integer.parseInt(value[0].trim());
            int to = Integer.parseInt(value[1].trim());

            moves.add(new Move(nbMoves, from, to));
            // System.out.println("Move " + nbMoves + " from " + from + " to " + to);
        }
        return moves;
    }

    public static class Move {
        private int nbOfCrates;
        private int from;
        private int to;

        public Move(int nbOfCrates, int from, int to) {
            this.nbOfCrates = nbOfCrates;
            this.from = from;
            this.to = to;
        }

        public int getNbOfCrates() {
            return nbOfCrates;
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }
    }
}
