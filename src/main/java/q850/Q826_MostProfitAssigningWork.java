package q850;

import java.util.Arrays;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/most-profit-assigning-work/
 *
 * We have jobs: difficulty[i] is the difficulty of the ith job, and profit[i] is the profit of the ith job.
 *
 * Now we have some workers. worker[i] is the ability of the ith worker, which means that this worker can only
 * complete a job with difficulty at most worker[i].
 *
 * Every worker can be assigned at most one job, but one job can be completed multiple times.
 *
 * For example, if 3 people attempt the same job that pays $1, then the total profit will be $3.  If a worker cannot
 * complete any job, his profit is $0.
 *
 * What is the most profit we can make?
 *
 * Example 1:
 *
 * Input: difficulty = [2,4,6,8,10], profit = [10,20,30,40,50], worker = [4,5,6,7]
 * Output: 100
 * Explanation: Workers are assigned jobs of difficulty [4,4,6,6] and they get profit of [20,20,30,30] seperately.
 *
 * Notes:
 *
 * 1 <= difficulty.length = profit.length <= 10000
 * 1 <= worker.length <= 10000
 * difficulty[i], profit[i], worker[i]  are in range [1, 10^5]
 */
@RunWith(LeetCodeRunner.class)
public class Q826_MostProfitAssigningWork {

    // LeetCode 中最快的方式用的不是优先队列, 而是直接排序.
    @Answer
    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        final int n = difficulty.length;
        PriorityQueue<Integer> pq = new PriorityQueue<>(n, (a, b) -> profit[b] - profit[a]);
        for (int i = 0; i < n; i++) {
            pq.add(i);
        }
        Arrays.sort(worker);

        int res = 0;
        for (int i = worker.length - 1; i >= 0; i--) {
            while (!pq.isEmpty() && worker[i] < difficulty[pq.peek()]) {
                pq.poll();
            }
            if (pq.isEmpty()) {
                return res;
            }
            res += profit[pq.peek()];
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(
            new int[]{2, 4, 6, 8, 10},
            new int[]{10, 20, 30, 40, 50},
            new int[]{4, 5, 6, 7}
    ).expect(100);

}
