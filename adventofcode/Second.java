import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Second {

    public static boolean isSafeReport(List<Integer> levels) {
        boolean isIncreasing = false;
        boolean isDecreasing = false;

        if(levels.size() <= 1){
            return true;
        }
        int diff = levels.get(1) - levels.get(0);

        if (diff < 0) {
            isDecreasing = true;
        } else if (diff > 0) {
            isIncreasing = true;
        }
        diff = Math.abs(diff);
        if (diff < 1 || diff > 3) {
            return false;
        }

        for (int i = 2; i < levels.size(); i++) {
            int dif = levels.get(i) - levels.get(i - 1);
            if(isIncreasing && dif < 0){
                return false;
            }if(isDecreasing && dif > 0){
                return false;
            }
            dif = Math.abs(dif);
            if (dif < 1 || dif > 3) {
                return false;
            }
        }
        return true;
    }

    public static boolean canBeSafeWithDampener(List<Integer> levels) {
        for (int i = 0; i < levels.size(); i++) {
            List<Integer> modifiedLevels = new ArrayList<>(levels);
            modifiedLevels.remove(i);
            if (isSafeReport(modifiedLevels)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String filename = "input.txt";
        int safeReportsCountWithoutDampener = 0;
        int safeReportsCount = 0;
        int totalReports = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                totalReports++;
                String[] tokens = line.trim().split("\\s+");
                List<Integer> levels = new ArrayList<>();

                boolean validLine = true;
                for (String token : tokens) {
                    try {
                        levels.add(Integer.parseInt(token));
                    } catch (NumberFormatException e) {
                        validLine = false;
                        break;
                    }
                }

                if (!validLine) {
                    continue;
                }

                if (isSafeReport(levels)) {
                    safeReportsCount++;
                    safeReportsCountWithoutDampener++;
                } else {
                    if (canBeSafeWithDampener(levels)) {
                        safeReportsCount++;
                    }
                }
            }

            System.out.println("Total Reports: " + totalReports);
            System.out.println("Safe Reports (with Dampener): " + safeReportsCount);
            System.out.println("Safe Reports (without Dampener): " + safeReportsCountWithoutDampener);

        } catch (IOException e) {
            System.err.println("Error");
        }
    }
}
