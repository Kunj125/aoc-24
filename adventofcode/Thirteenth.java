import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Thirteenth {

    static class Machine {
        int ax, ay;
        int bx, by;
        long px, py;

        public Machine(int ax, int ay, int bx, int by, long px, long py) {
            this.ax = ax;
            this.ay = ay;
            this.bx = bx;
            this.by = by;
            this.px = px;
            this.py = py;
        }
    }

    public static void main(String[] args) {
        ArrayList<Machine> machines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            int ax = 0, ay = 0, bx = 0, by = 0, px = 0, py = 0;
            int lineCount = 0;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] split = line.substring(line.indexOf(':') + 1).split(",");
                if (line.startsWith("Button A:")) {
                    String[] parts = split;
                    ax = parseCoordinate(parts[0].trim(), 'X');
                    ay = parseCoordinate(parts[1].trim(), 'Y');
                    lineCount++;
                } else if (line.startsWith("Button B:")) {
                    String[] parts = split;
                    bx = parseCoordinate(parts[0].trim(), 'X');
                    by = parseCoordinate(parts[1].trim(), 'Y');
                    lineCount++;
                } else if (line.startsWith("Prize:")) {
                    String[] parts = split;
                    px = parseCoordinate(parts[0].trim(), 'X');
                    py = parseCoordinate(parts[1].trim(), 'Y');
                    lineCount++;
                }
                if (lineCount == 3) {
                    machines.add(new Machine(ax, ay, bx, by, px + 10000000000000L, py + 10000000000000L));
                    lineCount = 0;
                }
            }

        } catch (IOException e) {
            System.err.println("Error");
            return;
        }

        long totalTokens = 0;
        for (Machine machine : machines) {
            //        AX = B
            // | ax ay |  | px |
            // | bx by |  | py |
//            https://www.youtube.com/watch?v=jBsC34PxzoM
            double detA = machine.ax * machine.by - machine.ay * machine.bx;
            double a = (double)(machine.px * machine.by - machine.py * machine.bx) / detA; // replace first column of A by p
            double b = (double)(machine.ax * machine.py - machine.ay * machine.px) / detA; // replace second column of A by p
            if (isInteger(a) && isInteger(b)) {
                long a_rounded = Math.round(a);
                long b_rounded = Math.round(b);
                totalTokens += a_rounded * 3 + b_rounded;
            }
        }

        System.out.println(totalTokens);
    }

    private static int parseCoordinate(String coord, char axis) {
        coord = coord.replace("X+", "").replace("Y+", "")
                .replace("X=", "").replace("Y=", "");
        return Integer.parseInt(coord);
    }
    public static boolean isInteger(double num){
        return Math.abs(Math.round(num) - num) < 0.00001;
    }
}
