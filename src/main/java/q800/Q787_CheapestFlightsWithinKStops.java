package q800;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/cheapest-flights-within-k-stops/
 *
 * There are n cities connected by m flights. Each flight starts from city u and arrives at v with a price w.
 *
 * Now given all the cities and flights, together with starting city src and the destination dst, your task is to
 * find the cheapest price from src to dst with up to k stops. If there is no such route, output -1.
 *
 * Example 1:
 * Input:
 * n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
 * src = 0, dst = 2, k = 1
 * Output: 200
 * Explanation:
 * The graph looks like this:
 * (图 Q787_PIC1.png)
 *
 * The cheapest price from city 0 to city 2 with at most 1 stop costs 200, as marked red in the picture.
 *
 * Example 2:
 * Input:
 * n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
 * src = 0, dst = 2, k = 0
 * Output: 500
 * Explanation:
 * The graph looks like this:
 * (图 Q787_PIC2.png)
 *
 * The cheapest price from city 0 to city 2 with at most 0 stop costs 500, as marked blue in the picture.
 *
 *
 *
 * Constraints:
 *
 * The number of nodes n will be in range [1, 100], with nodes labeled from 0 to n - 1.
 * The size of flights will be in range [0, n * (n - 1) / 2].
 * The format of each flight will be (src, dst, price).
 * The price of each flight will be in the range [1, 10000].
 * k is in the range of [0, n - 1].
 * There will not be any duplicated flights or self cycles.
 */
@RunWith(LeetCodeRunner.class)
public class Q787_CheapestFlightsWithinKStops {

    // bfs
    @Answer
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        List<int[]>[] paths = new List[n];
        for (int i = 0; i < n; i++) {
            paths[i] = new ArrayList<>();
        }
        for (int[] flight : flights) {
            paths[flight[0]].add(flight);
        }
        int[] costs = new int[n];
        Arrays.fill(costs, Integer.MAX_VALUE);

        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(src);
        queue.add(0);
        for (int stop = K + 1; stop >= 0; stop--) {
            for (int i = queue.size() / 2; i > 0; i--) {
                int pos = queue.poll();
                int cost = queue.poll();
                if (cost >= costs[pos]) {
                    continue;
                }
                costs[pos] = cost;
                for (int[] flight : paths[pos]) {
                    queue.add(flight[1]);
                    queue.add(cost + flight[2]);
                }
            }
        }
        return costs[dst] == Integer.MAX_VALUE ? -1 : costs[dst];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(3, new int[][]{{0, 1, 100}, {1, 2, 100}, {0, 2, 500}}, 0, 2, 1)
            .expect(200);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(3, new int[][]{{0, 1, 100}, {1, 2, 100}, {0, 2, 500}}, 0, 2, 0)
            .expect(500);

}
