import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Eighth {
//    using 1-based indexing
    static int n = 0; // rows
    static int m = 0; // cols

    public static void main(String[] args) {
        HashMap<Character, List<int[]>> map = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader("input.txt"))){
            String line;
            while((line = br.readLine()) != null){
                n++;
                m = line.length();
                for(int i = 0; i < line.trim().length(); i++){
                    char chr = line.charAt(i);
                    if(Character.isLetterOrDigit(chr)){
                        List<int[]> positions = map.getOrDefault(chr, new ArrayList<>());

                        int[] currentPos = new int[2];

                        currentPos[0] = n;
                        currentPos[1] = i + 1;

                        positions.add(currentPos);
                        map.put(chr, positions);
                    }
                }
            }
            int partOne = partOne(map);
            int partTwo = partTwo(map);
            System.out.println(partOne);
            System.out.println(partTwo);
        }catch(IOException e){
            System.err.println("Error");
        }
    }

    private static int partOne(HashMap<Character, List<int[]>> map){
        HashSet<String> alreadyCounted = new HashSet<>();
        for(Map.Entry<Character, List<int[]>> entry: map.entrySet()){
            List<int[]> pos= entry.getValue();
            for(int i = 0; i < pos.size(); i++){
                int[] first = pos.get(i);
                for(int j = i + 1; j < pos.size(); j++){
                    int[] second = pos.get(j);
                    int xDiff = second[0] - first[0];
                    int yDiff = second[1] - first[1];

                    int[] firstAntidote = {first[0] - xDiff, first[1] - yDiff};
                    int[] secondAntidote = {second[0] + xDiff, second[1] + yDiff};

                    if(checkBounds(firstAntidote)) {
                        String firstAntidoteStr = firstAntidote[0] + "+" + firstAntidote[1];
                        alreadyCounted.add(firstAntidoteStr);
                    }
                    if(checkBounds(secondAntidote)) {
                        String secondAntidoteStr = secondAntidote[0] + "+" + secondAntidote[1];
                        alreadyCounted.add(secondAntidoteStr);
                    }
                }
            }
        }
        return alreadyCounted.size();
    }

    private static int partTwo(HashMap<Character, List<int[]>> map){
        HashSet<String> alreadyCounted = new HashSet<>();
        for(Map.Entry<Character, List<int[]>> entry: map.entrySet()){
            List<int[]> pos= entry.getValue();

            for(int[] a: pos){
                String str = a[0] + "+" + a[1];
                alreadyCounted.add(str);
            }

            for(int i = 0; i < pos.size(); i++){
                for(int j = i + 1; j < pos.size(); j++){
                    int[] first = Arrays.copyOf(pos.get(i), pos.get(i).length);
                    int[] second = Arrays.copyOf(pos.get(j), pos.get(j).length);

                    int xDiff = second[0] - first[0];
                    int yDiff = second[1] - first[1];

                    while(true) {
                        first[0] -= xDiff;
                        first[1] -= yDiff;
                        int[] firstAntidote = {first[0], first[1]};

                        if (checkBounds(firstAntidote)) {
                            String firstAntidoteStr = firstAntidote[0] + "+" + firstAntidote[1];
                            alreadyCounted.add(firstAntidoteStr);
                        }else{
                            break;
                        }
                    }

                    while(true) {
                        second[0] += xDiff;
                        second[1] += yDiff;
                        int[] secondAntidote = {second[0], second[1]};
                        if (checkBounds(secondAntidote)) {
                            String secondAntidoteStr = secondAntidote[0] + "+" + secondAntidote[1];
                            alreadyCounted.add(secondAntidoteStr);
                        }else{
                            break;
                        }
                    }
                }
            }
        }
        return alreadyCounted.size();
    }

    private static boolean checkBounds(int[] antidotePos){
        return antidotePos[0] >= 1 && antidotePos[0] <= n && antidotePos[1] <= m && antidotePos[1] >= 1;
    }
}
