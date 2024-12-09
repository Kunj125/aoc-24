import java.io.*;
import java.util.*;

public class Ninth2 {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            String input = br.readLine().trim();
            br.close();

            Disk disk = parseDiskMap(input);
            moveBlocksToFront(disk);
            long checksum = calculateSum(disk);
            System.out.println(checksum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Disk {
        List<Integer> blocks;
        Map<Integer, FileInfo> files; // store mapping from file id to file info

        Disk(List<Integer> blocks, Map<Integer, FileInfo> files) {
            this.blocks = blocks;
            this.files = files;
        }
    }

    static class FileInfo {
        int fileID;
        int start;
        int length; // of the block

        FileInfo(int fileID, int start, int length) {
            this.fileID = fileID;
            this.start = start;
            this.length = length;
        }
    }

    private static Disk parseDiskMap(String input) {
        List<Integer> blocks = new ArrayList<>();
        Map<Integer, FileInfo> files = new HashMap<>();
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
                int start = blocks.size();
                for (int i = 0; i < length; i++) {
                    blocks.add(fileId);
                }
                files.put(fileId, new FileInfo(fileId, start, length));
                fileId++;
            } else {
                for (int i = 0; i < length; i++) {
                    blocks.add(-1);
                }
            }
            isFile = !isFile;
            index++;
        }

        return new Disk(blocks, files);
    }

    private static void moveBlocksToFront(Disk disk) {
        List<Integer> fileIds = new ArrayList<>(disk.files.keySet());
        Collections.sort(fileIds, Collections.reverseOrder());

        for (int fileId : fileIds) {
            FileInfo file = disk.files.get(fileId);
            int targetStart = findLeftmostFit(disk.blocks, file.length, file.start);
            if (targetStart != -1 && targetStart != file.start) {
                moveFile(disk, file, targetStart);
            }
        }
    }

    private static int findLeftmostFit(List<Integer> blocks, int fileLength, int currentStart) {
        for (int i = 0; i <= currentStart - fileLength; i++) {
            boolean canFit = true;
            for (int j = i; j < i + fileLength; j++) {
                if (blocks.get(j) != -1) {
                    canFit = false;
                    break;
                }
            }
            if (canFit) {
                return i;
            }
        }
        return -1;
    }

    private static void moveFile(Disk disk, FileInfo file, int newStart) {
        for (int i = file.start; i < file.start + file.length; i++) {
            disk.blocks.set(i, -1);
        }
        for (int i = newStart; i < newStart + file.length; i++) {
            disk.blocks.set(i, file.fileID);
        }
        file.start = newStart;
        disk.files.put(file.fileID, file);
    }

    private static long calculateSum(Disk disk) {
        long checksum = 0;
        for (int pos = 0; pos < disk.blocks.size(); pos++) {
            int fileId = disk.blocks.get(pos);
            if (fileId != -1) {
                checksum += (long) pos * fileId;
            }
        }
        return checksum;
    }
}