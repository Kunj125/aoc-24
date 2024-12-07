import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Seventh {
    public static void main(String[] args) {
        List<Long> testValues = new ArrayList<>();
        List<List<Integer>> nums = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] arr = line.trim().split(":");
                Long testValue = Long.parseLong(arr[0].trim());
                testValues.add(testValue);

                List<Integer> numList = new ArrayList<>();
                String[] strArr = arr[1].trim().split("\\s");
                for (String str : strArr) {
                    numList.add(Integer.parseInt(str));
                }
                nums.add(numList);
            }

            long ans = 0L;
            for (int i = 0; i < nums.size(); i++) {
                Long testVal = testValues.get(i);
                if (checkIfPossible(testVal, nums.get(i))) {
                    ans += testVal;
                }
            }
            System.out.println(ans);
        } catch (IOException e) {
            System.err.println("Error");
        }
    }

    public static boolean checkIfPossible(Long testVal, List<Integer> nums) {
        if (nums.isEmpty()) {
            return false;
        }
        return helper(testVal, nums, nums.get(0).longValue(), 1);
    }

    public static boolean helper(long testVal, List<Integer> nums, long current, int index) {
        if (index == nums.size()) {
            return current == testVal;
        }
        if(current > testVal){
            return false; // if all nums >= 1, can only increase with  +, *, || operations
        }
        long nextNum = nums.get(index);

        if (helper(testVal, nums, current + nextNum, index + 1)) {
            return true;
        }
        if (helper(testVal, nums, current * nextNum, index + 1)) {
            return true;
        }
        return helper(testVal, nums, Long.parseLong(Long.toString(current) + Long.toString(nextNum)), index + 1); // concat operator
    }
}
