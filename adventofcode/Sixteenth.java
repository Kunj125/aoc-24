import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Sixteenth {

    // Directions: 0 = North, 1 = East, 2 = South, 3 = West
    static final int[] DX = {0, 1, 0, -1};
    static final int[] DY = {-1, 0, 1, 0};

    static class State implements Comparable<State> {
        int x, y, direction;
        long score;

        State(int x, int y, int direction, long score) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.score = score;
        }

        @Override
        public int compareTo(State other) {
            return Long.compare(this.score, other.score);
        }
    }

    public static void main(String[] args) {
        char[][] maze = readMazeFromFile();
        int startX = -1, startY = -1, endX = -1, endY = -1;
        int height = maze.length;
        int width = maze[0].length;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (maze[y][x] == 'S') {
                    startX = x;
                    startY = y;
                } else if (maze[y][x] == 'E') {
                    endX = x;
                    endY = y;
                }
            }
        }

        long lowestScore = findLowestScore(maze, startX, startY, endX, endY);
        System.out.println("lowest possible score is: " + lowestScore);
    }

    private static char[][] readMazeFromFile() {
        List<char[]> mazeList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                mazeList.add(line.toCharArray());
            }
        } catch (IOException e) {
            System.out.println("Error");
            return null;
        }

        int height = mazeList.size();
        int width = mazeList.get(0).length;
        char[][] maze = new char[height][width];
        for (int y = 0; y < height; y++) {
            maze[y] = mazeList.get(y);
        }
        return maze;
    }

    private static long findLowestScore(char[][] maze, int startX, int startY, int endX, int endY) {
        int height = maze.length;
        int width = maze[0].length;
        // visited[y][x][direction] = minimum score to reach this state
        long[][][] visited = new long[height][width][4];
        for (long[][] layer : visited) {
            for (long[] row : layer) {
                Arrays.fill(row, Long.MAX_VALUE);
            }
        }

        PriorityQueue<State> pq = new PriorityQueue<>();
        // initial state: at start, facing East (1), score 0
        State startState = new State(startX, startY, 1, 0);
        pq.add(startState);
        visited[startY][startX][1] = 0;

        while (!pq.isEmpty()) {
            State current = pq.poll();

            if (current.x == endX && current.y == endY) {
                return current.score;
            }

            if (current.score > visited[current.y][current.x][current.direction]) {
                continue;
            }

            int newX = current.x + DX[current.direction];
            int newY = current.y + DY[current.direction];
            if (isInBounds(newX, newY, width, height) && maze[newY][newX] != '#') {
                long newScore = current.score + 1;
                if (newScore < visited[newY][newX][current.direction]) {
                    visited[newY][newX][current.direction] = newScore;
                    pq.add(new State(newX, newY, current.direction, newScore));
                }
            }

            // rotate clockwise
            int newDirCW = (current.direction + 1) % 4;
            long scoreCW = current.score + 1000;
            if (scoreCW < visited[current.y][current.x][newDirCW]) {
                visited[current.y][current.x][newDirCW] = scoreCW;
                pq.add(new State(current.x, current.y, newDirCW, scoreCW));
            }

            // rotate counterclockwise
            int newDirCCW = (current.direction + 3) % 4;
            long scoreCCW = current.score + 1000;
            if (scoreCCW < visited[current.y][current.x][newDirCCW]) {
                visited[current.y][current.x][newDirCCW] = scoreCCW;
                pq.add(new State(current.x, current.y, newDirCCW, scoreCCW));
            }
        }

        return Long.MAX_VALUE;
    }

    private static boolean isInBounds(int x, int y, int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
