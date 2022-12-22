package xyz.tigrou;

import java.util.List;

import xyz.tigrou.tools.Tools;

public class DayB {

    private static final String INPUT = "input_b.txt";

    public enum Play {
        ROCK(1, 2),
        PAPER(2, 3),
        SCISSORS(3, 1);

        private int value;

        // Represente the value of the counter play which is the best against the current play
        private int counter;

        Play(int value, int counter) {
            this.value = value;
            this.counter = counter;
        }

        public int getValue() {
            return value;
        }

        public int getCounter() {
            return counter;
        }
    }

    public enum Result {
        WIN(6),
        LOSE(0),
        DRAW(3);

        private int value;

        Result(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
     
    public static void main(String[] args) throws Exception {
        partOne();
        partTwo();
    }

    private static void partOne() throws Exception {
        List<String> lines = Tools.getInput(INPUT);
            
        int totalScore = 0;

        for (String line : lines) {
            String[] plays = line.split(" ");
            Game game = new Game(getPlay(plays[0]), getCounterPlay(null, plays[1], true));
            totalScore += game.getResult();
        }

        System.out.println("Total score: " + totalScore);
    }

    private static void partTwo() throws Exception {
        List<String> lines = Tools.getInput(INPUT);

        int totalScore = 0;

        for (String line : lines) {
            String[] plays = line.split(" ");

            Play firstPlay = getPlay(plays[0]);
            Play counterPlay = getCounterPlay(firstPlay, plays[1], false);

            Game game = new Game(firstPlay, counterPlay);
            totalScore += game.getResult();
        }

        System.out.println("Total score: " + totalScore);
    }

    private static Play getPlay(String value) {
        return value.equalsIgnoreCase("A") ? Play.ROCK : value.equalsIgnoreCase("B") ? Play.PAPER : Play.SCISSORS;
    }

    private static Play getCounterPlay(Play firstPlay, String value, boolean caseOne) throws Exception {
        if (caseOne) {
            return value.equalsIgnoreCase("X") ? Play.ROCK : value.equalsIgnoreCase("Y") ? Play.PAPER : Play.SCISSORS;
        }
        else {
            switch(firstPlay) {
            case ROCK:
                return value.equalsIgnoreCase("X") ? Play.SCISSORS : value.equalsIgnoreCase("Y") ? Play.ROCK : Play.PAPER;
            case PAPER:
                return value.equalsIgnoreCase("X") ? Play.ROCK : value.equalsIgnoreCase("Y") ? Play.PAPER : Play.SCISSORS;
            case SCISSORS:
                return value.equalsIgnoreCase("X") ? Play.PAPER : value.equalsIgnoreCase("Y") ? Play.SCISSORS : Play.ROCK;
            default:
                throw new Exception("Invalid play");
            }
        }
    }

    public static class Game {

        private Play play;
        private Play counterPlay;

        public Game(Play play, Play counterPlay) {
            this.play = play;
            this.counterPlay = counterPlay;
        }

        public Play getPlay() {
            return play;
        }

        public int getResult() {
            if (play == counterPlay) {
                return Result.DRAW.getValue() + counterPlay.getValue();
            }
            else if (play.getCounter() == counterPlay.getValue()) {
                return Result.WIN.getValue() + counterPlay.getValue();
            }
            else {
                return Result.LOSE.getValue() + counterPlay.getValue();
            }
        }
    }
}
