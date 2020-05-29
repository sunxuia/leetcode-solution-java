package q750;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/network-delay-time/
 *
 * There are N network nodes, labelled 1 to N.
 *
 * Given times, a list of travel times as directed edges times[i] = (u, v, w), where u is the source node, v is the
 * target node, and w is the time it takes for a signal to travel from source to target.
 *
 * Now, we send a signal from a certain node K. How long will it take for all nodes to receive the signal? If it is
 * impossible, return -1.
 *
 *
 *
 * Example 1:
 *
 * (图 Q743_PIC.png)
 *
 * Input: times = [[2,1,1],[2,3,1],[3,4,1]], N = 4, K = 2
 * Output: 2
 *
 *
 *
 * Note:
 *
 * N will be in the range [1, 100].
 * K will be in the range [1, N].
 * The length of times will be in the range [1, 6000].
 * All edges times[i] = (u, v, w) will have 1 <= u, v <= N and 0 <= w <= 100.
 */
@RunWith(LeetCodeRunner.class)
public class Q743_NetworkDelayTime {

    @Answer
    public int networkDelayTime(int[][] times, int N, int K) {
        int[][] links = new int[N + 1][N + 1];
        for (int[] link : links) {
            Arrays.fill(link, -1);
        }
        for (int[] time : times) {
            links[time[0]][time[1]] = time[2];
        }
        int[] arriveTime = new int[N + 1];
        Arrays.fill(arriveTime, Integer.MAX_VALUE);
        Deque<Integer> queue = new ArrayDeque<>();
        arriveTime[K] = 0;
        queue.add(K);
        while (!queue.isEmpty()) {
            int from = queue.poll();
            for (int to = 1; to <= N; to++) {
                if (links[from][to] > -1
                        && links[from][to] + arriveTime[from] < arriveTime[to]) {
                    arriveTime[to] = links[from][to] + arriveTime[from];
                    queue.add(to);
                }
            }
        }

        int res = 0;
        for (int i = 1; i <= N; i++) {
            if (arriveTime[i] == Integer.MAX_VALUE) {
                return -1;
            }
            res = Math.max(res, arriveTime[i]);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{2, 1, 1}, {2, 3, 1}, {3, 4, 1}}, 4, 2)
            .expect(2);

    // 这里有个权重为0 的边
    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[][]{
                            {3, 5, 78}, {2, 1, 1}, {1, 3, 0}, {4, 3, 59}, {5, 3, 85}, {5, 2, 22}, {2, 4, 23},
                            {1, 4, 43}, {4, 5, 75}, {5, 1, 15}, {1, 5, 91}, {4, 1, 16}, {3, 2, 98}, {3, 4, 22},
                            {5, 4, 31}, {1, 2, 0}, {2, 5, 4}, {4, 2, 51}, {3, 1, 36}, {2, 3, 59}}
                    , 5, 5)
            .expect(31);

}
