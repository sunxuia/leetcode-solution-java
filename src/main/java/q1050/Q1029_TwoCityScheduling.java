package q1050;

import java.util.Arrays;
import java.util.Comparator;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1029. Two City Scheduling
 * https://leetcode.com/problems/two-city-scheduling/
 *
 * A company is planning to interview 2n people. Given the array costs where costs[i] = [aCosti, bCosti], the cost of
 * flying the ith person to city a is aCosti, and the cost of flying the ith person to city b is bCosti.
 *
 * Return the minimum cost to fly every person to a city such that exactly n people arrive in each city.
 *
 * Example 1:
 *
 * Input: costs = [[10,20],[30,200],[400,50],[30,20]]
 * Output: 110
 * Explanation:
 * The first person goes to city A for a cost of 10.
 * The second person goes to city A for a cost of 30.
 * The third person goes to city B for a cost of 50.
 * The fourth person goes to city B for a cost of 20.
 *
 * The total minimum cost is 10 + 30 + 50 + 20 = 110 to have half the people interviewing in each city.
 *
 * Example 2:
 *
 * Input: costs = [[259,770],[448,54],[926,667],[184,139],[840,118],[577,469]]
 * Output: 1859
 *
 * Example 3:
 *
 * Input: costs = [[515,563],[451,713],[537,709],[343,819],[855,779],[457,60],[650,359],[631,42]]
 * Output: 3086
 *
 * Constraints:
 *
 * 2n == costs.length
 * 2 <= costs.length <= 100
 * costs.length is even.
 * 1 <= aCosti, bCosti <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1029_TwoCityScheduling {

    @Answer
    public int twoCitySchedCost(int[][] costs) {
        final int n = costs.length / 2;
        Arrays.sort(costs, Comparator.comparingInt(c -> c[0] - c[1]));
        int res = 0;
        for (int i = 0; i < 2 * n; i++) {
            res += costs[i][i / n];
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{10, 20}, {30, 200}, {400, 50}, {30, 20}})
            .expect(110);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{259, 770}, {448, 54}, {926, 667}, {184, 139}, {840, 118}, {577, 469}})
            .expect(1859);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new int[][]{
                    {515, 563}, {451, 713}, {537, 709}, {343, 819}, {855, 779}, {457, 60}, {650, 359}, {631, 42}})
            .expect(3086);

}
