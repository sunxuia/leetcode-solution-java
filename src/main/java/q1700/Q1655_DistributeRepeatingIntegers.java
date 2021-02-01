package q1700;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1655. Distribute Repeating Integers
 * https://leetcode.com/problems/distribute-repeating-integers/
 *
 * You are given an array of n integers, nums, where there are at most 50 unique values in the array. You are also given
 * an array of m customer order quantities, quantity, where quantity[i] is the amount of integers the ith customer
 * ordered. Determine if it is possible to distribute nums such that:
 *
 * The ith customer gets exactly quantity[i] integers,
 * The integers the ith customer gets are all equal, and
 * Every customer is satisfied.
 *
 * Return true if it is possible to distribute nums according to the above conditions.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,4], quantity = [2]
 * Output: false
 * Explanation: The 0th customer cannot be given two different integers.
 *
 * Example 2:
 *
 * Input: nums = [1,2,3,3], quantity = [2]
 * Output: true
 * Explanation: The 0th customer is given [3,3]. The integers [1,2] are not used.
 *
 * Example 3:
 *
 * Input: nums = [1,1,2,2], quantity = [2,2]
 * Output: true
 * Explanation: The 0th customer is given [1,1], and the 1st customer is given [2,2].
 *
 * Example 4:
 *
 * Input: nums = [1,1,2,3], quantity = [2,2]
 * Output: false
 * Explanation: Although the 0th customer could be given [1,1], the 1st customer cannot be satisfied.
 *
 * Example 5:
 *
 * Input: nums = [1,1,1,1,1], quantity = [2,3]
 * Output: true
 * Explanation: The 0th customer is given [1,1], and the 1st customer is given [1,1,1].
 *
 * Constraints:
 *
 * n == nums.length
 * 1 <= n <= 10^5
 * 1 <= nums[i] <= 1000
 * m == quantity.length
 * 1 <= m <= 10
 * 1 <= quantity[i] <= 10^5
 * There are at most 50 unique values in nums.
 */
@RunWith(LeetCodeRunner.class)
public class Q1655_DistributeRepeatingIntegers {

    /**
     * 状态压缩的题.
     *
     * @formatter:off
     * 参考文档
     * https://leetcode-cn.com/problems/distribute-repeating-integers/solution/zi-ji-mei-ju-jing-dian-tao-lu-zhuang-ya-dp-by-arse/
     * @formatter:on
     */
    @Answer
    public boolean canDistribute(int[] nums, int[] quantity) {
        Map<Integer, Integer> buckets = new HashMap<>();
        for (int num : nums) {
            buckets.put(num, buckets.getOrDefault(num, 0) + 1);
        }
        List<Integer> counts = new ArrayList<>(buckets.values());
        Collections.sort(counts);
        Arrays.sort(quantity);

        final int n = counts.size();
        final int m = quantity.length;
        final int len = 1 << m;

        // 计算不同 mask 的需要的数字的总数
        int[] sums = new int[len];
        for (int mask = 1; mask < len; mask++) {
            for (int i = 0; i < m; i++) {
                if ((mask >> i & 1) == 1) {
                    // 由之前的加上最低位的1 对应位
                    int prev = mask - (1 << i);
                    sums[mask] = sums[prev] + quantity[i];
                    break;
                }
            }
        }

        // dp[i][mask] 表示遍历到counts 第i 个时, 是否能为 mask 表示的消费者成功赋值.
        boolean[][] dp = new boolean[n][len];
        for (int i = 0; i < n; i++) {
            dp[i][0] = true;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < len; j++) {
                if (i > 0 && dp[i - 1][j]) {
                    // 之前第j 个客户已经给赋值了, 所以直接跳过
                    dp[i][j] = true;
                    continue;
                }
                // 遍历集合 j 的非空子集.
                // 参考 https://oi-wiki.org/math/bit/#_14
                for (int k = j; k != 0; k = (k - 1) & j) {
                    int prev = j - k;
                    boolean last = i == 0 ? prev == 0 : dp[i - 1][prev];
                    boolean need = sums[k] <= counts.get(i);
                    if (last && need) {
                        dp[i][j] = true;
                        break;
                    }
                }
            }
        }
        return dp[n - 1][len - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4}, new int[]{2})
            .expect(false);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 2, 3, 3}, new int[]{2})
            .expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 1, 2, 2}, new int[]{2, 2})
            .expect(true);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[]{1, 1, 2, 3}, new int[]{2, 2})
            .expect(false);

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(new int[]{1, 1, 1, 1, 1}, new int[]{2, 3})
            .expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(
            new int[]{1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14, 15,
                    15, 16, 16, 17, 17, 18, 18, 19, 19, 20, 20, 21, 21, 22, 22, 23, 23, 24, 24, 25, 25, 26, 26, 27, 27,
                    28, 28, 29, 29, 30, 30, 31, 31, 32, 32, 33, 33, 34, 34, 35, 35, 36, 36, 37, 37, 38, 38, 39, 39, 40,
                    40, 41, 41, 42, 42, 43, 43, 44, 44, 45, 45, 46, 46, 47, 47, 48, 48, 49, 49, 50, 50},
            new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 3})
            .expect(false);

}
