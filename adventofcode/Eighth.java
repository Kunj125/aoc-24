import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Eighth {
    public static void main(String[] args) {
        HashMap<Character, List<int[]>> map = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader("input.txt"))){
            String line;
            int lineCount = 0;
            int m = 0;
            while((line = br.readLine()) != null){
                lineCount++;
                m = line.length();
                for(int i = 0; i < line.trim().length(); i++){
                    char chr = line.charAt(i);
                    if(Character.isLetterOrDigit(chr)){
                        List<int[]> positions = map.getOrDefault(chr, new ArrayList<>());

                        int[] currentPos = new int[2];

                        currentPos[0] = lineCount;
                        currentPos[1] = i + 1;

                        positions.add(currentPos);
                        map.put(chr, positions);
                    }
                }
            }

            int count = 0;
            HashSet<String> alreadyCounted = new HashSet<>();
            for(Map.Entry<Character, List<int[]>> entry: map.entrySet()){
                List<int[]> pos= entry.getValue();
                for(int i = 0; i < pos.size(); i++){
                    int[] first = pos.get(i);
                    for(int j = i + 1; j < pos.size(); j++){
                        int[] second = pos.get(j);
                        int xDiffSecond = second[0] - first[0];
                        int yDiffSecond = second[1] - first[1];
                        int xDiffFirst = first[0] - second[0];
                        int yDiffFirst = first[1] - second[1];

                        int[] firstAntidote = {first[0] + xDiffFirst, first[1] + yDiffFirst};
                        int[] secondAntidote = {second[0] + xDiffSecond, second[1] + yDiffSecond};

                        if(firstAntidote[0] >= 1 && firstAntidote[0] <= lineCount && firstAntidote[1] <= m && firstAntidote[1] >= 1) {
                            String firstAntidoteStr = firstAntidote[0] + "+" + firstAntidote[1];
                            if (alreadyCounted.add(firstAntidoteStr)) {
                                count++;
                            }
                        }
                        if(secondAntidote[0] >= 1 && secondAntidote[0] <= lineCount && secondAntidote[1] <= m && secondAntidote[1] >= 1) {
                            String secondAntidoteStr = secondAntidote[0] + "+" + secondAntidote[1];
                            if (alreadyCounted.add(secondAntidoteStr)) {
                                count++;
                            }
                        }
                    }
                }
            }
            System.out.println(lineCount);
            System.out.println(m);
            System.out.println(alreadyCounted);
            System.out.println(count);
        }catch(IOException e){
            System.err.println("Error");
        }
    }
}
