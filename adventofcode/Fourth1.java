import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fourth1 {
    public static void main(String[] args) {
        String filename = "input.txt";
        char[][] grid = readGridFromFile(filename);
        for (char[] chars : grid) System.out.println(Arrays.toString(chars));

        String target = "XMAS";
        int count = countOccurrences(grid, target);
        System.out.println("Total number of times " + target + " appears: " + count);
    }

    private static char[][] readGridFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }

            int numRows = lines.size();
            int numCols = lines.get(0).length();

            char[][] grid = new char[numRows][numCols];
            for (int i = 0; i < numRows; i++) {
                String currentLine = lines.get(i);
                grid[i] = currentLine.toUpperCase().toCharArray(); // converting to uppercase for consistency
            }

            return grid;
        } catch (IOException e) {
            System.out.println("Error reading the file");
            return null;
        }
    }

    private static int countOccurrences(char[][] grid, String target) {
        int count = 0;
        int numRows = grid.length;
        int numCols = grid[0].length;
        int wordLength = target.length();

        int[][] directions = {
                {-1,  0}, // north
                {-1,  1}, // northeast
                { 0,  1}, // east
                { 1,  1}, // southeast
                { 1,  0}, // south
                { 1, -1}, // southwest
                { 0, -1}, // west
                {-1, -1}  // northwest
        };

        char[] targetChars = target.toCharArray();

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (grid[row][col] == targetChars[0]) {
                    for (int[] dir : directions) {
                        int currentRow = row;
                        int currentCol = col;
                        int i;

                        for (i = 1; i < wordLength; i++) {
                            currentRow += dir[0];
                            currentCol += dir[1];

                            if (currentRow < 0 || currentRow >= numRows ||
                                    currentCol < 0 || currentCol >= numCols) {
                                break;
                            }

                            if (grid[currentRow][currentCol] != targetChars[i]) {
                                break;
                            }
                        }

                        if (i == wordLength) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }
}
