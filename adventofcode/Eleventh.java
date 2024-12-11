import java.util.*;
import java.io.*;

public class Eleventh {
    private static Map<Long, Map<Integer, Long>> map = new HashMap<>();  // number -> map<blinksLeft, count>

    public static void main(String[] args) {
        List<Long> initialStones = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] numList = line.split("\\s");
                for(String num: numList){
                    initialStones.add(Long.parseLong(num));
                }
            }
        } catch (IOException e) {
            System.err.println("Error");
        }

        int totalBlinks = 3;
        long totalStones = 0;

        for (long stone : initialStones) {
            totalStones += countStones(stone, totalBlinks);
        }
//        System.out.println(map);
        System.out.println(totalStones);
    }

    private static long countStones(long number, int blinksLeft) {
        if (blinksLeft == 0) {
            return 1;
        }

        // check if the number is already memoized
        if (map.containsKey(number)) {
            Map<Integer, Long> innerMap = map.get(number);
            if (innerMap.containsKey(blinksLeft)) {
                System.out.println("=========");
                System.out.println(map);
                System.out.println("Reusing map for " + number + " " + blinksLeft);
                System.out.println("=========");
                return innerMap.get(blinksLeft);
            }
        } else {
            map.put(number, new HashMap<>());
        }

        long count = 0;

        if (number == 0) {
            // replace 0 with 1
            count = countStones(1, blinksLeft - 1);
        } else {
            int numDigits = Long.toString(number).length();
            if (numDigits % 2 == 0) {
                // split into two stones
                String numStr = Long.toString(number);
                int half = numStr.length() / 2;
                String leftStr = numStr.substring(0, half);
                String rightStr = numStr.substring(half);

                long left = leftStr.isEmpty() ? 0 : Long.parseLong(leftStr); // converting to long removed leading zeroes
                long right = rightStr.isEmpty() ? 0 : Long.parseLong(rightStr);

                long leftCount = countStones(left, blinksLeft - 1);
                long rightCount = countStones(right, blinksLeft - 1);

                count = leftCount + rightCount;
            } else {
                // multiply by 2024
                long newNum = number * 2024;
                count = countStones(newNum, blinksLeft - 1);
            }
        }

        // memoize the result
        map.get(number).put(blinksLeft, count);
        return count;
    }
}
