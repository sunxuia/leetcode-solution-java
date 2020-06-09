package q850;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/bus-routes/
 *
 * We have a list of bus routes. Each routes[i] is a bus route that the i-th bus repeats forever. For example if
 * routes[0] = [1, 5, 7], this means that the first bus (0-th indexed) travels in the sequence 1->5->7->1->5->7->1->.
 * .. forever.
 *
 * We start at bus stop S (initially not on a bus), and we want to go to bus stop T. Travelling by buses only, what
 * is the least number of buses we must take to reach our destination? Return -1 if it is not possible.
 *
 * Example:
 * Input:
 * routes = [[1, 2, 7], [3, 6, 7]]
 * S = 1
 * T = 6
 * Output: 2
 * Explanation:
 * The best strategy is take the first bus to the bus stop 7, then take the second bus to the bus stop 6.
 *
 *
 *
 * Constraints:
 *
 * 1 <= routes.length <= 500.
 * 1 <= routes[i].length <= 10^5.
 * 0 <= routes[i][j] < 10 ^ 6.
 */
@RunWith(LeetCodeRunner.class)
public class Q815_BusRoutes {

    // 转换为连通图的方式, 公交站点就是连通图, 相同公交线路之间的站点之间的距离为0.
    @Answer
    public int numBusesToDestination(int[][] routes, int S, int T) {
        Map<Integer, List<Integer>> busStops = new HashMap<>();
        for (int i = 0; i < routes.length; i++) {
            for (int busStop : routes[i]) {
                busStops.computeIfAbsent(busStop, k -> new ArrayList<>()).add(i);
            }
        }

        // 下一站公交车站
        Queue<Integer> queue = new LinkedList<>();
        queue.add(S);
        // 已经上过的公交车
        boolean[] visited = new boolean[routes.length];
        int res = 0;
        while (!queue.isEmpty()) {
            for (int len = queue.size(); len > 0; len--) {
                int busStop = queue.poll();
                if (busStop == T) {
                    return res;
                }
                for (int bus : busStops.get(busStop)) {
                    if (!visited[bus]) {
                        visited[bus] = true;
                        for (int nextStop : routes[bus]) {
                            queue.offer(nextStop);
                        }
                    }
                }
            }
            res++;
        }
        return -1;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(new int[][]{{1, 2, 7}, {3, 6, 7}}, 1, 6).expect(2);

    @TestData
    public DataExpectation border = DataExpectation.createWith(new int[][]{{1, 2, 7}, {3, 6, 7}}, 1, 1).expect(0);

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(TestDataFileHelper.read2DArray("Q815_TestData"), 0, 90000)
            .expect(300);

}
