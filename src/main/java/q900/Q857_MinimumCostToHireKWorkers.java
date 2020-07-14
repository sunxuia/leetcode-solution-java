package q900;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] Minimum Cost to Hire K Workers
 * https://leetcode.com/problems/minimum-cost-to-hire-k-workers/
 *
 * There are N workers.  The i-th worker has a quality[i] and a minimum wage expectation wage[i].
 *
 * Now we want to hire exactly K workers to form a paid group.  When hiring a group of K workers, we must pay them
 * according to the following rules:
 *
 * Every worker in the paid group should be paid in the ratio of their quality compared to other workers in the paid
 * group.
 * Every worker in the paid group must be paid at least their minimum wage expectation.
 *
 * Return the least amount of money needed to form a paid group satisfying the above conditions.
 *
 * Example 1:
 *
 * Input: quality = [10,20,5], wage = [70,50,30], K = 2
 * Output: 105.00000
 * Explanation: We pay 70 to 0-th worker and 35 to 2-th worker.
 *
 * Example 2:
 *
 * Input: quality = [3,1,10,10,1], wage = [4,8,2,2,7], K = 3
 * Output: 30.66667
 * Explanation: We pay 4 to 0-th worker, 13.33333 to 2-th and 3-th workers seperately.
 *
 * Note:
 *
 * 1 <= K <= N <= 10000, where N = quality.length = wage.length
 * 1 <= quality[i] <= 10000
 * 1 <= wage[i] <= 10000
 * Answers within 10^-5 of the correct answer will be considered correct.
 */
@RunWith(LeetCodeRunner.class)
public class Q857_MinimumCostToHireKWorkers {

    // 贪婪算法
    // 参考文档 https://www.cnblogs.com/grandyang/p/11329482.html
    // https://leetcode.com/problems/minimum-cost-to-hire-k-workers/discuss/185085/75ms-Java-with-Explanations
    @Answer
    public double mincostToHireWorkers(int[] quality, int[] wage, int K) {
        final int n = quality.length;
        Integer[] workers = new Integer[n];
        for (int i = 0; i < n; i++) {
            workers[i] = i;
        }
        Arrays.sort(workers, Comparator.comparingDouble(i -> (double) wage[i] / quality[i]));

        double res = Double.MAX_VALUE, totalQuality = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>(K + 1, Comparator.reverseOrder());
        for (int i = 0; i < n; i++) {
            final int w = workers[i];
            totalQuality += quality[w];
            pq.add(quality[w]);
            if (pq.size() > K) {
                totalQuality -= pq.poll();
            }
            if (pq.size() == K) {
                res = Math.min(res, totalQuality * wage[w] / quality[w]);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[]{10, 20, 5})
            .addArgument(new int[]{70, 50, 30})
            .addArgument(2)
            .expectDouble(105.00000, 5)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[]{3, 1, 10, 10, 1})
            .addArgument(new int[]{4, 8, 2, 2, 7})
            .addArgument(3)
            .expectDouble(30.66667, 5)
            .build();

}
