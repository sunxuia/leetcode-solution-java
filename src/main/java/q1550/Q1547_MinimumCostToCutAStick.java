package q1550;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1547. Minimum Cost to Cut a Stick
 * https://leetcode.com/problems/minimum-cost-to-cut-a-stick/
 *
 * Given a wooden stick of length n units. The stick is labelled from 0 to n. For example, a stick of length 6 is
 * labelled as follows:
 * <img src="./Q1547_PIC1.png">
 * Given an integer array cuts where cuts[i] denotes a position you should perform a cut at.
 *
 * You should perform the cuts in order, you can change the order of the cuts as you wish.
 *
 * The cost of one cut is the length of the stick to be cut, the total cost is the sum of costs of all cuts. When you
 * cut a stick, it will be split into two smaller sticks (i.e. the sum of their lengths is the length of the stick
 * before the cut). Please refer to the first example for a better explanation.
 *
 * Return the minimum total cost of the cuts.
 *
 * Example 1:
 * <img src="./Q1547_PIC2.png">
 * Input: n = 7, cuts = [1,3,4,5]
 * Output: 16
 * Explanation: Using cuts order = [1, 3, 4, 5] as in the input leads to the following scenario:
 * <img src="./Q1547_PIC3.png">
 * The first cut is done to a rod of length 7 so the cost is 7. The second cut is done to a rod of length 6 (i.e. the
 * second part of the first cut), the third is done to a rod of length 4 and the last cut is to a rod of length 3. The
 * total cost is 7 + 6 + 4 + 3 = 20.
 * Rearranging the cuts to be [3, 5, 1, 4] for example will lead to a scenario with total cost = 16 (as shown in the
 * example photo 7 + 4 + 3 + 2 = 16).
 *
 * Example 2:
 *
 * Input: n = 9, cuts = [5,6,1,4,2]
 * Output: 22
 * Explanation: If you try the given cuts ordering the cost will be 25.
 * There are much ordering with total cost <= 25, for example, the order [4, 6, 5, 2, 1] has total cost = 22 which is
 * the minimum possible.
 *
 * Constraints:
 *
 * 2 <= n <= 10^6
 * 1 <= cuts.length <= min(n - 1, 100)
 * 1 <= cuts[i] <= n - 1
 * All the integers in cuts array are distinct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1547_MinimumCostToCutAStick {

    /**
     * 带缓存的dfs
     */
    @Answer
    public int minCost(int n, int[] cuts) {
        final int len = cuts.length;
        Arrays.sort(cuts);
        int[][] cache = new int[len][len];
        return dfs(cache, n, cuts, 0, len - 1);
    }

    /**
     * 切割木棍
     *
     * @param cuts 切割数组
     * @param start 切割数组开始 (包含)
     * @param end 切割数组结束 (包含)
     */
    private int dfs(int[][] cache, int n, int[] cuts, int start, int end) {
        if (start > end) {
            return 0;
        }
        if (cache[start][end] != 0) {
            return cache[start][end];
        }
        int res = Integer.MAX_VALUE;
        for (int i = start; i <= end; i++) {
            int left = dfs(cache, n, cuts, start, i - 1);
            int right = dfs(cache, n, cuts, i + 1, end);
            res = Math.min(res, left + right);
        }
        int ns = start == 0 ? 0 : cuts[start - 1];
        int ne = end == cuts.length - 1 ? n : cuts[end + 1];
        res += ne - ns;
        cache[start][end] = res;
        return res;
    }

    /**
     * 上面解法的 dp 改写
     */
    @Answer
    public int minCost2(int n, int[] cuts) {
        Arrays.sort(cuts);
        final int len = cuts.length;
        int[][] dp = new int[len][len];
        for (int i = 0; i < len; i++) {
            int ns = i == 0 ? 0 : cuts[i - 1];
            int ne = i == len - 1 ? n : cuts[i + 1];
            dp[i][i] = ne - ns;
        }
        for (int step = 1; step < len; step++) {
            for (int start = 0, end = start + step; end < len; start++, end++) {
                int cost = Math.min(dp[start + 1][end], dp[start][end - 1]);
                for (int i = start + 1; i < end; i++) {
                    cost = Math.min(cost, dp[start][i - 1] + dp[i + 1][end]);
                }
                int ns = start == 0 ? 0 : cuts[start - 1];
                int ne = end == len - 1 ? n : cuts[end + 1];
                dp[start][end] = cost + ne - ns;
            }
        }
        return dp[0][len - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(7, new int[]{1, 3, 4, 5}).expect(16);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(9, new int[]{5, 6, 1, 4, 2}).expect(22);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(30, new int[]{13, 25, 16, 20, 26, 5, 27, 8, 23, 14, 6, 15, 21, 24, 29, 1, 19, 9, 3})
            .expect(127);

}
