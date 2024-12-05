import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Fifth {
    static class OrderRule {
        int before;
        int after;

        public OrderRule(int before, int after) {
            this.before = before;
            this.after = after;
        }
    }
    public static void main(String[] args) {
        boolean readUpdates = false;
//        Map<Integer, List<Integer>> graph = new HashMap<>();
        List<OrderRule> rules = new ArrayList<>();
        int totalSum = 0;

        List<List<Integer>> updates = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    readUpdates = true;
                    continue;
                }
                if(readUpdates) {
                    String[] arr = line.trim().split(",");
                    ArrayList<Integer> intList = new ArrayList<>();
                    for (String str : arr) {
                        intList.add(Integer.parseInt(str.trim()));
                    }
                    updates.add(intList);
                }else{
                    String[] rule = line.split("\\|");
                    int before = Integer.parseInt(rule[0].trim());
                    int after = Integer.parseInt(rule[1].trim());
//                    graph.computeIfAbsent(before, k -> new ArrayList<>()).add(after);
                    rules.add(new OrderRule(before, after));
                }
            }

        }catch (IOException e) {
            System.err.println("Error");
        }

        for(List<Integer> update: updates){
            Map<Integer, Integer> pageIndexMap = new HashMap<>();
            boolean isCorrect = true;
            for (int i = 0; i < update.size(); i++) {
                pageIndexMap.put(update.get(i), i);
            }
            System.out.println(pageIndexMap);
            for (OrderRule rule : rules) {
                if (pageIndexMap.containsKey(rule.before) && pageIndexMap.containsKey(rule.after)) {
                    int beforeIndex = pageIndexMap.get(rule.before);
                    int afterIndex = pageIndexMap.get(rule.after);
                    if (beforeIndex >= afterIndex) {
                        isCorrect = false;
                        break;
                    }
                }
            }
            if(isCorrect){
                int middleIdx = update.size() % 2 == 0 ? (update.size()/2) - 1 : update.size()/2;
                totalSum += update.get(middleIdx);
            }
        }
        System.out.println(totalSum);
    }

}
