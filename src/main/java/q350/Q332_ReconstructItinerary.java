package q350;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/reconstruct-itinerary/
 *
 * Given a list of airline tickets represented by pairs of departure and arrival airports [from, to], reconstruct the
 * itinerary in order. All of the tickets belong to a man who departs from JFK. Thus, the itinerary must begin with JFK.
 *
 * Note:
 *
 * If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order
 * when read as a single string. For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than
 * ["JFK", "LGB"].
 * All airports are represented by three capital letters (IATA code).
 * You may assume all tickets form at least one valid itinerary.
 *
 * Example 1:
 *
 * Input: [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
 * Output: ["JFK", "MUC", "LHR", "SFO", "SJC"]
 *
 * Example 2:
 *
 * Input: [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
 * Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
 * Explanation: Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"].
 * But it is larger in lexical order.
 */
@RunWith(LeetCodeRunner.class)
public class Q332_ReconstructItinerary {

    @Answer
    public List<String> findItinerary(List<List<String>> tickets) {
        Map<String, ArrayList<String>> map = new HashMap<>();
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            List<String> list = map.computeIfAbsent(from, k -> new ArrayList<>());
            int i = 0;
            while (i < list.size() && list.get(i).compareTo(to) > 0) {
                i++;
            }
            list.add(i, to);
        }
        List<String> res = new ArrayList<>();
        res.add("JFK");
        dfs(res, map);
        return res;
    }

    private boolean dfs(List<String> res, Map<String, ArrayList<String>> map) {
        String from = res.get(res.size() - 1);
        ArrayList<String> tos = map.get(from);
        if (tos == null) {
            return map.isEmpty();
        }
        for (int i = tos.size() - 1; i >= 0; i--) {
            String to = tos.remove(i);
            if (tos.isEmpty()) {
                map.remove(from);
            }
            res.add(to);
            if (dfs(res, map)) {
                return true;
            }
            res.remove(res.size() - 1);
            tos.add(i, to);
            if (tos.size() == 1) {
                map.put(from, tos);
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(Arrays.asList(
            Arrays.asList("MUC", "LHR"),
            Arrays.asList("JFK", "MUC"),
            Arrays.asList("SFO", "SJC"),
            Arrays.asList("LHR", "SFO")
    )).expect(Arrays.asList("JFK", "MUC", "LHR", "SFO", "SJC"));

    @TestData
    public DataExpectation example2 = DataExpectation.create(Arrays.asList(
            Arrays.asList("JFK", "SFO"),
            Arrays.asList("JFK", "ATL"),
            Arrays.asList("SFO", "ATL"),
            Arrays.asList("ATL", "JFK"),
            Arrays.asList("ATL", "SFO")
    )).expect(Arrays.asList("JFK", "ATL", "JFK", "SFO", "ATL", "SFO"));

    @TestData
    public DataExpectation normal1 = DataExpectation.create(Arrays.asList(
            Arrays.asList("JFK", "KUL"),
            Arrays.asList("JFK", "NRT"),
            Arrays.asList("NRT", "JFK")
    )).expect(Arrays.asList("JFK", "NRT", "JFK", "KUL"));

}
