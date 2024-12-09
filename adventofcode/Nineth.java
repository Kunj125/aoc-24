import java.io.*;
import java.util.*;

public class Nineth {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            String input = br.readLine().trim();
            br.close();

            List<Integer> disk = parseDiskMap(input);
            System.out.println(disk);
            moveToFront(disk);
            System.out.println(disk);

            long checksum = calculateSum(disk);

            System.out.println(checksum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> parseDiskMap(String input) {
        List<Integer> disk = new ArrayList<>();
        int index = 0;
        int fileId = 0;
        boolean isFile = true;

        while (index < input.length()) {
            char c = input.charAt(index);
            if (!Character.isDigit(c)) {
                break;
            }
            int length = c - '0';
            if (isFile) {
                for (int i = 0; i < length; i++) {
                    disk.add(fileId);
                }
                fileId++;
            } else {
                for (int i = 0; i < length; i++) {
                    disk.add(-1);
                }
            }
            isFile = !isFile;
            index++;
        }
        return disk;
    }

    private static void moveToFront(List<Integer> disk) {
        while (true) {
            int firstFree = -1;
            for (int j = 0; j < disk.size(); j++) {
                if (disk.get(j) == -1) {
                    firstFree = j;
                    break;
                }
            }

            if (firstFree == -1) {
                break;
            }

            int rightmostFile = -1;
            for (int k = disk.size() - 1; k > firstFree; k--) {
                if (disk.get(k) != -1) {
                    rightmostFile = k;
                    break;
                }
            }

            if (rightmostFile == -1) {
                break;
            }

            disk.set(firstFree, disk.get(rightmostFile));
            disk.set(rightmostFile, -1);
        }
    }

    private static long calculateSum(List<Integer> disk) {
        long checksum = 0;
        for (int pos = 0; pos < disk.size(); pos++) {
            int fileId = disk.get(pos);
            if (fileId != -1) {
                checksum += (long) pos * fileId;
            }
        }
        return checksum;
    }
}
