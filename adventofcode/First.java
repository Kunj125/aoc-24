import java.io.*;
import java.util.*;

public class First {
    public static void main(String[] args) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        System.out.println("HELLO");
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()){
                    continue;
                }

                String[] tokens = line.trim().split("\\s+");
                try {
                    int leftNum = Integer.parseInt(tokens[0]);
                    int rightNum = Integer.parseInt(tokens[1]);

                    left.add(leftNum);
                    right.add(rightNum);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid" + line);
                }
            }

            if (left.size() != right.size()) {
                System.err.println("Different sizes");
                return;
            }

            Collections.sort(left);
            Collections.sort(right);

            int totalDistance = 0;
            for (int i = 0; i < left.size(); i++) {
                int distance = Math.abs(left.get(i) - right.get(i));
                totalDistance += distance;
            }

            int similarity = 0;

            for(int i = 0; i < left.size(); i++){
                int freq = Collections.frequency(right,left.get(i));
                similarity += (left.get(i) * freq);
            }
            System.out.println("Total distance: " + totalDistance);
            System.out.println("Similarity Score: " + similarity);

        } catch (IOException e) {
            System.err.println("Error" + e.getMessage());
        }
    }
}
