import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Sixth {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            String line;
            StringBuilder sb = new StringBuilder();
            int width = -1;
            int height = 0;
            while ((line = br.readLine()) != null) {
                if (width == -1) {
                    width = line.length();
                }
                sb.append(line).append("\n");
                height++;
            }
            br.close();

            char[][] map = new char[height][width];
            String[] rows = sb.toString().split("\n");
            int startX = -1;
            int startY = -1;
            Direction startDir = null;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    char c = rows[y].charAt(x);
                    map[y][x] = c;
                    if (c == '^') {
                        startX = x;
                        startY = y;
                        startDir = Direction.UP;
                        map[y][x] = '.';
                    } else if (c == 'v') {
                        startX = x;
                        startY = y;
                        startDir = Direction.DOWN;
                        map[y][x] = '.';
                    } else if (c == '<') {
                        startX = x;
                        startY = y;
                        startDir = Direction.LEFT;
                        map[y][x] = '.';
                    } else if (c == '>') {
                        startX = x;
                        startY = y;
                        startDir = Direction.RIGHT;
                        map[y][x] = '.';
                    }
                }
            }

            Direction dir = startDir;
            int guardX = startX;
            int guardY = startY;

            Set<String> visited = new HashSet<>();
            visited.add(guardX + "," + guardY);

            while (true) {
                int frontX = guardX + dir.dx;
                int frontY = guardY + dir.dy;

                boolean blocked = false;
                if (frontX < 0 || frontX >= width || frontY < 0 || frontY >= height) {
                    // if the next step is outside the map
                    break;
                } else {
                    if (map[frontY][frontX] == '#') {
                        blocked = true;
                    }
                }

                if (blocked) {
                    dir = turnRight(dir);
                } else {
                    guardX = frontX;
                    guardY = frontY;
                    // if the guard moves outside the map now
                    if (guardX < 0 || guardX >= width || guardY < 0 || guardY >= height) {
                        break;
                    }
                    visited.add(guardX + "," + guardY);
                }
            }

            int loopCount = 0;
            long startTime = System.nanoTime();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (x == startX && y == startY) {
                        continue;
                    }
                    if (map[y][x] == '.') {
                        map[y][x] = '#';
                        if (simulate(map, startX, startY, startDir)) {
                            loopCount++;
                        }
                        // revert the change
                        map[y][x] = '.';
                    }
                }
            }
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            double elapsedMillis = elapsedTime / 1_000_000.0;

            System.out.println("Runtime: " + elapsedMillis + " ms");

            System.out.println(loopCount);
            System.out.println(visited.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static boolean simulate(char[][] map, int startX, int startY, Direction startDir) {
        int width = map[0].length;
        int height = map.length;

        int guardX = startX;
        int guardY = startY;
        Direction dir = startDir;

        Set<String> visitedStates = new HashSet<>();
        visitedStates.add(state(guardX, guardY, dir));

        while (true) {
            int frontX = guardX + dir.dx;
            int frontY = guardY + dir.dy;

            boolean blocked = false;
            if (frontX < 0 || frontX >= width || frontY < 0 || frontY >= height) {
                return false;
            }
            if (map[frontY][frontX] == '#') {
                blocked = true;
            }

            if (blocked) {
                dir = turnRight(dir);
            } else {
                guardX = frontX;
                guardY = frontY;

                if (guardX < 0 || guardX >= width || guardY < 0 || guardY >= height) {
                    return false;
                }

                String currentState = state(guardX, guardY, dir);
                if (visitedStates.contains(currentState)) {
                    // ie visited before
                    return true;
                }
                visitedStates.add(currentState);
            }
        }
    }

    private static String state(int x, int y, Direction d) {
        return x + "," + y + "," + d.name();
    }

    private static Direction turnRight(Direction d) {
        switch (d) {
            case UP:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.DOWN;
            case DOWN:
                return Direction.LEFT;
            case LEFT:
                return Direction.UP;
        }
        return d;
    }

    enum Direction {
        UP(0, -1),
        RIGHT(1, 0),
        DOWN(0, 1),
        LEFT(-1, 0);

        int dx;
        int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }
}
