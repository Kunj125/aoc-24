import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


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
        int incorrectTotalSum = 0;
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
            int middleIdx = update.size() % 2 == 0 ? (update.size()/2) - 1 : update.size()/2;
            if(isCorrect){
                totalSum += update.get(middleIdx);
            }else{
                System.out.println("================");
                System.out.println(update);
                List<Integer> reordered = reorderUpdate(update, rules);
                incorrectTotalSum += reordered.get(middleIdx);
                System.out.println(reordered);
                System.out.println("================");
            }
        }
        System.out.println(totalSum);
        System.out.println(incorrectTotalSum);
    }

    private static List<Integer> reorderUpdate(List<Integer> update, List<OrderRule> wrongRules) {
//        https://www.geeksforgeeks.org/topological-sorting-indegree-based-solution/
//        https://www.youtube.com/watch?v=cIBFEhD77b4

        Map<Integer, List<Integer>> graph = new HashMap<>(); // page -> all the pages it must come before
        Map<Integer, Integer> inDegree = new HashMap<>();

        for (Integer page : update) {
            graph.put(page, new ArrayList<>());
            inDegree.put(page, 0);
        }

        for (OrderRule rule : wrongRules) {
            if (graph.containsKey(rule.before) && graph.containsKey(rule.after)) {
                graph.get(rule.before).add(rule.after);
                inDegree.put(rule.after, inDegree.get(rule.after) + 1);
            }
        }

        // Topological Sort using Kahn's Algorithm
        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }

        List<Integer> sortedList = new ArrayList<>();
        while (!queue.isEmpty()) {
            Integer current = queue.poll();
            sortedList.add(current);

            for (Integer adjacent : graph.get(current)) {
                inDegree.put(adjacent, inDegree.get(adjacent) - 1);
                if (inDegree.get(adjacent) == 0) {
                    queue.offer(adjacent);
                }
            }
        }

        return sortedList;
    }

}
