import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Third {
    public static void main(String[] args) {
        String filePath = "input.txt";
        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        String regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        int totalSum = 0;
        boolean mulEnabled = true;
        int instructionCount = 0;

        while (matcher.find()) {
            System.out.println("--------------------");
            System.out.println(matcher.group());
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println("--------------------");

            if (matcher.group(1) != null && matcher.group(2) != null) {
                if (mulEnabled) {
                    String xStr = matcher.group(1);
                    String yStr = matcher.group(2);
                    int x = Integer.parseInt(xStr);
                    int y = Integer.parseInt(yStr);

                    int product = x * y;
                    totalSum += product;
                    instructionCount++;
                }
            } else {
                String match = matcher.group();
                if (match.equals("do()")) {
                    mulEnabled = true;
                } else if (match.equals("don't()")) {
                    mulEnabled = false;
                }
            }
        }
        System.out.println("Total sum of enabled multiplications: " + totalSum);
        System.out.println("Number of valid mul: " + instructionCount);
    }
}
