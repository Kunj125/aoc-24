import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Twelvth {
    public static void main(String[] args) {
        List<char[]> mapChr = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("input.txt"))){
            String line;
            while((line = br.readLine()) != null){
               char[] lineChr = line.trim().toCharArray();
                mapChr.add(lineChr);
            }
        }catch(IOException e){
            System.err.println("Error");
        }

        char[][] map = new char[mapChr.size()][mapChr.get(0).length];
        for (int i = 0; i < mapChr.size(); i++) {
            map[i] = mapChr.get(i);
        }

        boolean[][] visited = new boolean[map.length][map[0].length];
        int totalCost = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (!visited[i][j]) {
                    // DFS to find plots of the same type
                    Region region = dfs(map, visited, i, j, map.length, map[0].length);
                    long cost = (long) region.area * region.perimeter;
                    totalCost += cost;
                }
            }
        }

        System.out.println("Total fencing cost: " + totalCost);
    }

    private static final int[][] DIRECTIONS = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
    };

    private static class Region {
        char type;
        int area;
        int perimeter;

        int sides;

        Region(char type, int area, int perimeter) {
            this.type = type;
            this.area = area;
            this.perimeter = perimeter;
            this.sides = sides;
        }
    }

    private static Region dfs(char[][] grid, boolean[][] visited, int startRow, int startCol, int rows, int cols) {
        char plantType = grid[startRow][startCol];
        int area = 0;
        int perimeter = 0;

        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int r = current[0];
            int c = current[1];
            area++;

            for (int[] dir : DIRECTIONS) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) {
                    // edge of map
                    perimeter++;
                } else if (grid[nr][nc] != plantType) {
                    // adjacent to other plant type
                    perimeter++;
                } else {
                    if (!visited[nr][nc]) {
                        visited[nr][nc] = true;
                        stack.push(new int[]{nr, nc});
                    }
                }
            }
        }

        return new Region(plantType, area, perimeter);
    }

}
