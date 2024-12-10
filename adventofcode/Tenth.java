import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Tenth {
    private static final int[][] DIRECTIONS = {
            {-1, 0}, // up
            {1, 0},  // down
            {0, -1}, // left
            {0, 1}   // right
    };

    public static void main(String[] args) {
        List<int[]> mapList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                int[] row = new int[line.length()];
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (Character.isDigit(c)) {
                        row[i] = c - '0';
                    } else {
                        row[i] = -1; // for impassable
                    }
                }
                mapList.add(row);
            }
        } catch (IOException e) {
            System.err.println("Error");
        }

        int[][] map = new int[mapList.size()][];
        for (int i = 0; i < mapList.size(); i++) {
            map[i] = mapList.get(i);
        }

        List<int[]> trailheads = findTrailheads(map);
        int sumOfScores = 0;

        for (int[] trailhead : trailheads) {
            int score = computeTrailheadScore(map, trailhead[0], trailhead[1]);
            sumOfScores += score;
        }

        System.out.println("scores: " + sumOfScores);
    }
    private static List<int[]> findTrailheads(int[][] map) {
        List<int[]> trailheads = new ArrayList<>();

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] == 0) {
                    trailheads.add(new int[]{row, col});
                }
            }
        }
        return trailheads;
    }

    private static int computeTrailheadScore(int[][] map, int startRow, int startCol) {
        int rows = map.length;
        int cols = map[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int reachedNines = 0;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            int currentHeight = map[row][col];

            if (currentHeight == 9) {
                reachedNines++;
                continue;
            }

            // explore all four directions
            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                // check bounds
                if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols){
                    continue;
                }

                if (visited[newRow][newCol]){
                    continue;
                }

                int nextHeight = map[newRow][newCol];

                if (nextHeight == currentHeight + 1) {
                    visited[newRow][newCol] = true;
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }

        return reachedNines;
    }
}
