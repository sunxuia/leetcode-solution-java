package q1800;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1799. Maximize Score After N Operations
 * https://leetcode.com/problems/maximize-score-after-n-operations/
 *
 * You are given nums, an array of positive integers of size 2 * n. You must perform n operations on this array.
 *
 * In the ith operation (1-indexed), you will:
 *
 * Choose two elements, x and y.
 * Receive a score of i * gcd(x, y).
 * Remove x and y from nums.
 *
 * Return the maximum score you can receive after performing n operations.
 *
 * The function gcd(x, y) is the greatest common divisor of x and y.
 *
 * Example 1:
 *
 * Input: nums = [1,2]
 * Output: 1
 * Explanation: The optimal choice of operations is:
 * (1 * gcd(1, 2)) = 1
 *
 * Example 2:
 *
 * Input: nums = [3,4,6,8]
 * Output: 11
 * Explanation: The optimal choice of operations is:
 * (1 * gcd(3, 6)) + (2 * gcd(4, 8)) = 3 + 8 = 11
 *
 * Example 3:
 *
 * Input: nums = [1,2,3,4,5,6]
 * Output: 14
 * Explanation: The optimal choice of operations is:
 * (1 * gcd(1, 5)) + (2 * gcd(2, 4)) + (3 * gcd(3, 6)) = 1 + 4 + 9 = 14
 *
 * Constraints:
 *
 * 1 <= n <= 7
 * nums.length == 2 * n
 * 1 <= nums[i] <= 10^6
 */
@RunWith(LeetCodeRunner.class)
public class Q1799_MaximizeScoreAfterNOperations {

    /**
     * 带缓存的dfs
     */
    @Answer
    public int maxScore(int[] nums) {
        final int m = nums.length;
        int[] cache = new int[1 << m];
        Arrays.fill(cache, -1);
        cache[(1 << m) - 1] = 0;
        return dfs(1, nums, 0, cache);
    }

    private int dfs(int time, int[] nums, int mask, int[] cache) {
        final int m = nums.length;
        if (cache[mask] > -1) {
            return cache[mask];
        }
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                if ((mask & ((1 << i) | (1 << j))) == 0) {
                    mask |= (1 << i) | (1 << j);
                    int ope = time * gcd(nums[i], nums[j])
                            + dfs(time + 1, nums, mask, cache);
                    res = Math.max(res, ope);
                    mask -= (1 << i) | (1 << j);
                }
            }
        }
        cache[mask] = res;
        return res;
    }

    private int gcd(int x, int y) {
        return y == 0 ? x : gcd(y, x % y);
    }

    /**
     * dp
     */
    @Answer
    public int maxScore2(int[] nums) {
        final int m = nums.length;
        int[] dp = new int[1 << m];
        for (int mask = 0; mask < (1 << m); mask++) {
            int count = 0;
            for (int i = mask; i != 0; i = i & (i - 1)) {
                count++;
            }
            if (count % 2 == 0) {
                for (int i = 0; i < m; i++) {
                    for (int j = i + 1; j < m; j++) {
                        int choose = (1 << i) | (1 << j);
                        if ((mask & choose) == choose) {
                            int ope = (count / 2) * (gcd(nums[i], nums[j])) + dp[mask - choose];
                            dp[mask] = Math.max(dp[mask], ope);
                        }
                    }
                }
            }
        }
        return dp[(1 << m) - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{3, 4, 6, 8}).expect(11);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 2, 3, 4, 5, 6}).expect(14);

    @TestData
    public DataExpectation overTime = DataExpectation.create(new int[]{
            773274, 313112, 131789, 222437, 918065, 49745, 321270, 74163, 900218, 80160, 325440, 961730
    }).expect(3032);

}
