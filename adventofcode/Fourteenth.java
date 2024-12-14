import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Fourteenth {
    final static int WIDTH = 101;
    final static int HEIGHT = 103;

    static class Robot {
        int x;
        int y;
        int vx;
        int vy;

        Robot(int x, int y, int vx, int vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }

        void updatePosition() {
            x = (x + vx) % WIDTH;
            y = (y + vy) % HEIGHT;

            if (x < 0) x += WIDTH;
            if (y < 0) y += HEIGHT;
        }
    }

    public static void main(String[] args) {
        ArrayList<Robot> robots = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                String[] parts = line.split("\\s");
                String posPart = parts[0].replace("p=", "").replace(",", " ");
                String velPart = parts[1].replace("v=", "").replace(",", " ");

                String[] posTokens = posPart.split("\\s");
                String[] velTokens = velPart.split("\\s");

                int x = Integer.parseInt(posTokens[0]);
                int y = Integer.parseInt(posTokens[1]);
                int vx = Integer.parseInt(velTokens[0]);
                int vy = Integer.parseInt(velTokens[1]);

                robots.add(new Robot(x, y, vx, vy));
            }
        } catch (IOException e) {
            System.out.println("Error");
            return;
        }


        for (int t = 0; t < 100; t++) {
            for (Robot robot : robots) {
                robot.updatePosition();
            }
        }

        int mid_x = WIDTH / 2;
        int mid_y = HEIGHT / 2;

        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;

        for (Robot robot : robots) {
            double rx = robot.x;
            double ry = robot.y;
            boolean onMiddleX = rx == mid_x;
            boolean onMiddleY = ry == mid_y;

            if (onMiddleX || onMiddleY) {
                continue;
            }
            if (rx < mid_x && ry < mid_y) {
                System.out.println("1");
                System.out.println(rx + " " + ry);
                q1++;
            } else if (rx > mid_x && ry < mid_y) {
                System.out.println("2");
                System.out.println(rx + " " + ry);
                q2++;
            } else if (rx > mid_x && ry > mid_y) {
                System.out.println("3");
                System.out.println(rx + " " + ry);
                q3++;
            } else if (rx < mid_x && ry > mid_y) {
                System.out.println("4");
                System.out.println(rx + " " + ry);
                q4++;
            }
        }

        long safetyFactor = (long) q1 * q2 * q3 * q4;

        System.out.println("Safety Factor" + safetyFactor);
    }
}
