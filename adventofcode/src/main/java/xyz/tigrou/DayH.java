package xyz.tigrou;

import java.util.List;

import xyz.tigrou.tools.Tools;

public class DayH {

    // private static final String INPUT = "input_h_test.txt";
    private static final String INPUT = "input_h.txt";
     
    public static void main(String[] args) throws Exception {
        List<String> lines = Tools.getInput(INPUT);
        partOne(lines);
        partTwo(lines);
    }

    private static void partOne(List<String> lines) throws Exception {
        Integer[][] grid = buildGrid(lines);

        int nbTreesVisible = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (isTreeVisible(grid, i, j)) {
                    nbTreesVisible++;
                }
            }
        }
        // printGrid(grid);
        
        System.out.println("Tree visible: " + nbTreesVisible);
    }

    private static void partTwo(List<String> lines) throws Exception {
        Integer[][] grid = buildGrid(lines);

        int maxScenicScore = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int scenicScore = calculateScenicScore(grid, i, j);
                if (scenicScore > maxScenicScore) {
                    maxScenicScore = scenicScore;
                }
            }
        }
        
        System.out.println("Max scenic score: " + maxScenicScore);
    }

    private static int calculateScenicScore(Integer[][] grid, int i, int j) {
        int tree = grid[i][j];

        int scoreLeft = 0;
        int scoreRight = 0;
        int scoreTop = 0;
        int scoreDown = 0;

        // Checking if tree is on the border
        if (i == 0 || i == grid.length - 1 || j == 0 || j == grid[i].length - 1) {
            return 0;
        }
        
        // Checking right
        for (int k = j + 1; k < grid[i].length; k++) {
            scoreRight++;
            if (grid[i][k] >= tree) {
                break;
            }
        }

        // Checking left
        for (int k = j - 1; k >= 0; k--) {
            scoreLeft++;
            if (grid[i][k] >= tree) {
                break;
            }
        }

        // Checking up
        for (int k = i - 1; k >= 0; k--) {
            scoreTop++;
            if (grid[k][j] >= tree) {
                break;
            }
        }

        // Checking down
        for (int k = i + 1; k < grid.length; k++) {
            scoreDown++;
            if (grid[k][j] >= tree) {
                break;
            }
        }

        return scoreLeft * scoreRight * scoreTop * scoreDown;
    }

    private static void printGrid(Integer[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (isTreeVisible(grid, i, j)) {
                    System.out.print("\033[1m" + grid[i][j] + "\033[0m");
                } else {
                    System.out.print(grid[i][j]);
                }
            }
            System.out.println();
        }
    }

    private static boolean isTreeVisible(Integer[][] grid, int i, int j) {
        int tree = grid[i][j];

        // Checking if tree is on the border
        if (i == 0 || i == grid.length - 1 || j == 0 || j == grid[i].length - 1) {
            return true;
        }

        // Checking right
        boolean isVisible = true;
        for (int k = j + 1; k < grid[i].length; k++) {
            if (grid[i][k] >= tree) {
                isVisible = false;
                break;
            }
        }

        if (isVisible) {
            return true;
        }

        // Checking left
        isVisible = true;
        for (int k = j - 1; k >= 0; k--) {
            if (grid[i][k] >= tree) {
                isVisible = false;
                break;
            }
        }

        if (isVisible) {
            return true;
        }

        // Checking up
        isVisible = true;
        for (int k = i - 1; k >= 0; k--) {
            if (grid[k][j] >= tree) {
                isVisible = false;
                break;
            }
        }

        if (isVisible) {
            return true;
        }

        // Checking down
        isVisible = true;
        for (int k = i + 1; k < grid.length; k++) {
            if (grid[k][j] >= tree) {
                isVisible = false;
                break;
            }
        }

        if (isVisible) {
            return true;
        }

        return false;
    }

    private static Integer[][] buildGrid(List<String> lines) {
        Integer[][] grid = new Integer[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                grid[i][j] = Integer.parseInt(line.substring(j, j+1));
            }
        }
        return grid;
    }
}
