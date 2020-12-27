package q1450;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1436. Destination City
 * https://leetcode.com/problems/destination-city/
 *
 * You are given the array paths, where paths[i] = [cityAi, cityBi] means there exists a direct path going from cityAi
 * to cityBi. Return the destination city, that is, the city without any path outgoing to another city.
 *
 * It is guaranteed that the graph of paths forms a line without any loop, therefore, there will be exactly one
 * destination city.
 *
 * Example 1:
 *
 * Input: paths = [["London","New York"],["New York","Lima"],["Lima","Sao Paulo"]]
 * Output: "Sao Paulo"
 * Explanation: Starting at "London" city you will reach "Sao Paulo" city which is the destination city. Your trip
 * consist of: "London" -> "New York" -> "Lima" -> "Sao Paulo".
 *
 * Example 2:
 *
 * Input: paths = [["B","C"],["D","B"],["C","A"]]
 * Output: "A"
 * Explanation: All possible trips are:
 * "D" -> "B" -> "C" -> "A".
 * "B" -> "C" -> "A".
 * "C" -> "A".
 * "A".
 * Clearly the destination city is "A".
 *
 * Example 3:
 *
 * Input: paths = [["A","Z"]]
 * Output: "Z"
 *
 * Constraints:
 *
 * 1 <= paths.length <= 100
 * paths[i].length == 2
 * 1 <= cityAi.length, cityBi.length <= 10
 * cityAi != cityBi
 * All strings consist of lowercase and uppercase English letters and the space character.
 */
@RunWith(LeetCodeRunner.class)
public class Q1436_DestinationCity {

    @Answer
    public String destCity(List<List<String>> paths) {
        Map<String, Integer> outs = new HashMap<>();
        for (List<String> path : paths) {
            String from = path.get(0);
            String to = path.get(1);
            outs.put(from, outs.getOrDefault(from, 0) + 1);
            outs.putIfAbsent(to, 0);
        }
        for (String city : outs.keySet()) {
            if (outs.get(city) == 0) {
                return city;
            }
        }
        return "";
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(List.of(List.of("London", "New York"), List.of("New York", "Lima"), List.of("Lima", "Sao Paulo")))
            .expect("Sao Paulo");

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(List.of(List.of("B", "C"), List.of("D", "B"), List.of("C", "A")))
            .expect("A");

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(List.of(List.of("A", "Z")))
            .expect("Z");

}
