import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fourth2 {
    public static void main(String[] args) {
        String filename = "input.txt";
        char[][] grid = readGridFromFile(filename);
        for (char[] chars : grid) System.out.println(Arrays.toString(chars));

        int count = countXMASPatterns(grid);
        System.out.println("Total number of X-MAS patterns: " + count);
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

    private static int countXMASPatterns(char[][] grid) {
        int count = 0;
        int numRows = grid.length;
        int numCols = grid[0].length;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (grid[row][col] == 'A') {
                    // Check NW-SE diagonal
                    boolean nwToSe = checkMASSequence(grid, row, col, -1, -1, 1, 1);
                    // Check NE-SW diagonal
                    boolean neToSw = checkMASSequence(grid, row, col, -1, 1, 1, -1);

                    if (nwToSe && neToSw) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private static boolean checkMASSequence(char[][] grid, int row, int col,
                                            int dr1, int dc1, int dr2, int dc2) {
        int numRows = grid.length;
        int numCols = grid[0].length;

        int row1 = row + dr1;
        int col1 = col + dc1;
        int row2 = row + dr2;
        int col2 = col + dc2;

        // boundaries
        if (row1 < 0 || row1 >= numRows || col1 < 0 || col1 >= numCols ||
                row2 < 0 || row2 >= numRows || col2 < 0 || col2 >= numCols) {
            return false;
        }

        char firstChar = grid[row1][col1];
        char secondChar = grid[row2][col2];

        if (!((firstChar == 'M' && secondChar == 'S') ||
                (firstChar == 'S' && secondChar == 'M'))) {
            return false;
        }

        return true;
    }
}
