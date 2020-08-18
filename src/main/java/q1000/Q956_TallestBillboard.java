package q1000;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 956. Tallest Billboard
 * https://leetcode.com/problems/tallest-billboard/
 *
 * You are installing a billboard and want it to have the largest height.  The billboard will have two steel supports,
 * one on each side.  Each steel support must be an equal height.
 *
 * You have a collection of rods which can be welded together.  For example, if you have rods of lengths 1, 2, and 3,
 * you can weld them together to make a support of length 6.
 *
 * Return the largest possible height of your billboard installation.  If you cannot support the billboard, return 0.
 *
 * Example 1:
 *
 * Input: [1,2,3,6]
 * Output: 6
 * Explanation: We have two disjoint subsets {1,2,3} and {6}, which have the same sum = 6.
 *
 * Example 2:
 *
 * Input: [1,2,3,4,5,6]
 * Output: 10
 * Explanation: We have two disjoint subsets {2,3,5} and {4,6}, which have the same sum = 10.
 *
 * Example 3:
 *
 * Input: [1,2]
 * Output: 0
 * Explanation: The billboard cannot be supported, so we return 0.
 *
 * Note:
 *
 * 1. 0 <= rods.length <= 20
 * 2. 1 <= rods[i] <= 1000
 * 3. The sum of rods is at most 5000.
 */
@RunWith(LeetCodeRunner.class)
public class Q956_TallestBillboard {

    /**
     * 参考Solution 中的解答.
     * 因为总和不超过5000, 所以使用5000 作为水平线, 杆子放在一边则加上对应数字,
     * 杆子放在另一边则减去对应数字, 最后如果结果是5000, 则说明水平.
     */
    @Answer
    public int tallestBillboard(int[] rods) {
        final int n = rods.length;
        // cache[i][j] 表示使用第 i 个杆子时, 可以得到的最高结果
        int[][] cache = new int[n][10001];
        for (int i = 0; i < n; i++) {
            Arrays.fill(cache[i], Integer.MIN_VALUE);
        }
        return dfs(cache, rods, 0, 5000);
    }

    public int dfs(int[][] cache, int[] rods, int i, int sum) {
        if (i == rods.length) {
            // 如果最后的结果是平衡的, 则返回0,
            // 否则返回1 个足够小的数字, 让结果在调用者的Math.max 方法中被筛选掉.
            return sum == 5000 ? 0 : -10000;
        }
        if (cache[i][sum] > Integer.MIN_VALUE) {
            return cache[i][sum];
        }
        // 跳过
        int res = dfs(cache, rods, i + 1, sum);
        // 杆子加在另一边, 这边长度不变
        res = Math.max(res, dfs(cache, rods, i + 1, sum - rods[i]));
        // 杆子加在这一边, 长度加上 rods[i]
        res = Math.max(res, rods[i] + dfs(cache, rods, i + 1, sum + rods[i]));

        cache[i][sum] = res;
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 6}).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 3, 4, 5, 6}).expect(10);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 2}).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[]{33, 30, 41, 34, 37, 29, 26, 31, 42, 33, 38, 27, 33, 31, 35, 900, 900, 900, 900, 900})
            .expect(2050);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{
            156, 160, 153, 149, 158, 136, 172, 147, 148, 133, 147, 147, 146, 131, 171, 165, 145, 143, 148, 145
    }).expect(1500);

}
