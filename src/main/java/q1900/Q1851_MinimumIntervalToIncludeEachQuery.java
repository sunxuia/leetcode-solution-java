package q1900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Hard] 1851. Minimum Interval to Include Each Query
 * https://leetcode.com/problems/minimum-interval-to-include-each-query/
 *
 * You are given a 2D integer array intervals, where intervals[i] = [lefti, righti] describes the ith interval starting
 * at lefti and ending at righti (inclusive). The size of an interval is defined as the number of integers it contains,
 * or more formally righti - lefti + 1.
 *
 * You are also given an integer array queries. The answer to the jth query is the size of the smallest interval i such
 * that lefti <= queries[j] <= righti. If no such interval exists, the answer is -1.
 *
 * Return an array containing the answers to the queries.
 *
 * Example 1:
 *
 * Input: intervals = [[1,4],[2,4],[3,6],[4,4]], queries = [2,3,4,5]
 * Output: [3,3,1,4]
 * Explanation: The queries are processed as follows:
 * - Query = 2: The interval [2,4] is the smallest interval containing 2. The answer is 4 - 2 + 1 = 3.
 * - Query = 3: The interval [2,4] is the smallest interval containing 3. The answer is 4 - 2 + 1 = 3.
 * - Query = 4: The interval [4,4] is the smallest interval containing 4. The answer is 4 - 4 + 1 = 1.
 * - Query = 5: The interval [3,6] is the smallest interval containing 5. The answer is 6 - 3 + 1 = 4.
 *
 * Example 2:
 *
 * Input: intervals = [[2,3],[2,5],[1,8],[20,25]], queries = [2,19,5,22]
 * Output: [2,-1,4,6]
 * Explanation: The queries are processed as follows:
 * - Query = 2: The interval [2,3] is the smallest interval containing 2. The answer is 3 - 2 + 1 = 2.
 * - Query = 19: None of the intervals contain 19. The answer is -1.
 * - Query = 5: The interval [2,5] is the smallest interval containing 5. The answer is 5 - 2 + 1 = 4.
 * - Query = 22: The interval [20,25] is the smallest interval containing 22. The answer is 25 - 20 + 1 = 6.
 *
 * Constraints:
 *
 * 1 <= intervals.length <= 10^5
 * 1 <= queries.length <= 10^5
 * intervals[i].length == 2
 * 1 <= lefti <= righti <= 10^7
 * 1 <= queries[j] <= 10^7
 */
@RunWith(LeetCodeRunner.class)
public class Q1851_MinimumIntervalToIncludeEachQuery {

    @Answer
    public int[] minInterval(int[][] intervals, int[] queries) {
        final int m = queries.length;
        // 按照起始位置为区间排序
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        // 按照起始位置为查询排序
        Integer[] querySort = new Integer[m];
        for (int i = 0; i < m; i++) {
            querySort[i] = i;
        }
        Arrays.sort(querySort, Comparator.comparingInt(a -> queries[a]));

        // 保存长度最小的区间
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) ->
                a[1] - a[0] == b[1] - b[0] ? a[0] - b[0] : (a[1] - a[0]) - (b[1] - b[0]));
        int[] res = new int[m];
        for (int i = 0, ii = 0; i < m; i++) {
            int curr = queries[querySort[i]];
            // 将符合条件的区间加入
            while (ii < intervals.length && intervals[ii][0] <= curr) {
                pq.offer(intervals[ii++]);
            }
            // 将超过范围的最小区间删除
            while (!pq.isEmpty() && pq.peek()[1] < curr) {
                pq.poll();
            }
            if (pq.isEmpty()) {
                res[querySort[i]] = -1;
            } else {
                res[querySort[i]] = pq.peek()[1] - pq.peek()[0] + 1;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{1, 4}, {2, 4}, {3, 6}, {4, 4}}, new int[]{2, 3, 4, 5})
            .expect(new int[]{3, 3, 1, 4});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{2, 3}, {2, 5}, {1, 8}, {20, 25}}, new int[]{2, 19, 5, 22})
            .expect(new int[]{2, -1, 4, 6});

}
