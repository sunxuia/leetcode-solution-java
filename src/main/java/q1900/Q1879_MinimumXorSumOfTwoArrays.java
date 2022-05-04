package q1900;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1879. Minimum XOR Sum of Two Arrays
 * https://leetcode.com/problems/minimum-xor-sum-of-two-arrays/
 *
 * You are given two integer arrays nums1 and nums2 of length n.
 *
 * The XOR sum of the two integer arrays is (nums1[0] XOR nums2[0]) + (nums1[1] XOR nums2[1]) + ... + (nums1[n - 1] XOR
 * nums2[n - 1]) (0-indexed).
 *
 * For example, the XOR sum of [1,2,3] and [3,2,1] is equal to (1 XOR 3) + (2 XOR 2) + (3 XOR 1) = 2 + 0 + 2 = 4.
 *
 * Rearrange the elements of nums2 such that the resulting XOR sum is minimized.
 *
 * Return the XOR sum after the rearrangement.
 *
 * Example 1:
 *
 * Input: nums1 = [1,2], nums2 = [2,3]
 * Output: 2
 * Explanation: Rearrange nums2 so that it becomes [3,2].
 * The XOR sum is (1 XOR 3) + (2 XOR 2) = 2 + 0 = 2.
 *
 * Example 2:
 *
 * Input: nums1 = [1,0,3], nums2 = [5,3,4]
 * Output: 8
 * Explanation: Rearrange nums2 so that it becomes [5,4,3].
 * The XOR sum is (1 XOR 5) + (0 XOR 4) + (3 XOR 3) = 4 + 4 + 0 = 8.
 *
 * Constraints:
 *
 * n == nums1.length
 * n == nums2.length
 * 1 <= n <= 14
 * 0 <= nums1[i], nums2[i] <= 10^7
 */
@RunWith(LeetCodeRunner.class)
public class Q1879_MinimumXorSumOfTwoArrays {

    /**
     * 带缓存的dfs
     */
    @Answer
    public int minimumXORSum(int[] nums1, int[] nums2) {
        final int maskSize = 1 << nums2.length;
        // cache[mask2] 表示 nums2 的占用情况为mask2,
        // 此时对于 nums1[x] (x = mask2中1 的个数) 和之后的元素能得到的最小结果.
        int[] cache = new int[maskSize];
        Arrays.fill(cache, -1);
        cache[maskSize - 1] = 0;

        return dfs(cache, nums1, 0, nums2, 0);
    }

    private int dfs(int[] cache, int[] nums1, int index1, int[] nums2, int mask2) {
        if (cache[mask2] != -1) {
            return cache[mask2];
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < nums2.length; i++) {
            if ((mask2 >> i & 1) == 0) {
                int sum = (nums1[index1] ^ nums2[i])
                        + dfs(cache, nums1, index1 + 1, nums2, mask2 | (1 << i));
                res = Math.min(res, sum);
            }
        }
        cache[mask2] = res;
        return res;
    }

    /**
     * 上面解法的dp改写, 有优化.
     */
    @Answer
    public int minimumXORSum_dp(int[] nums1, int[] nums2) {
        final int n = nums1.length;
        final int maskSize = 1 << n;
        // dp[mask] 表示 nums2 已匹配的元素分布为 mask (nums1 中的元素为0 到mask 中1 的数量) 时的最小结果
        int[] dp = new int[maskSize];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        // 从 nums1[0] 开始逐个遍历匹配
        for (int i = 0; i < n; i++) {
            // nums[i] 之前匹配i 个元素的nums2 的模式有 MASKS[i] 种
            for (int j = 0; MASKS[i][j] < maskSize; j++) {
                // 遍历 nums2 中的元素, 找到可与 nums[i] 匹配的元素, 找到最小值
                for (int k = 0; k < n; k++) {
                    // nums2[k] 之前没被匹配过, 这次尝试与 nums1[i] 匹配, 得出新的mask
                    if ((MASKS[i][j] >> k & 1) == 0) {
                        int sum = (nums1[i] ^ nums2[k]) + dp[MASKS[i][j]];
                        int newMask = MASKS[i][j] | (1 << k);
                        dp[newMask] = Math.min(dp[newMask], sum);
                    }
                }
            }
        }
        return dp[maskSize - 1];
    }

    private static final int MAX_SIZE = 14;

    private static final int[][] MASKS = new int[MAX_SIZE + 1][];

    static {
        List<Integer>[] maskList = new List[MAX_SIZE + 1];
        for (int i = 0; i <= MAX_SIZE; i++) {
            maskList[i] = new ArrayList<>();
        }
        for (int mask = 0; mask < (1 << MAX_SIZE); mask++) {
            int one = Integer.bitCount(mask);
            maskList[one].add(mask);
        }
        for (int i = 0; i <= MAX_SIZE; i++) {
            int size = maskList[i].size();
            MASKS[i] = new int[size + 1];
            for (int j = 0; j < size; j++) {
                MASKS[i][j] = maskList[i].get(j);
            }
            MASKS[i][size] = Integer.MAX_VALUE;
        }
    }

    /**
     * leetcode 中的dp, 是上面dp 不同优化方式, 思路类似
     */
    @Answer
    public int minimumXORSum_leetcode(int[] nums1, int[] nums2) {
        final int n = nums1.length;
        final int maskSize = 1 << n;
        int[] dp = new int[maskSize];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int mask = 1; mask < maskSize; mask++) {
            // 找出mask 中1 的个数, 从而找到要进行匹配的 nums[i] (i从0开始)
            int i = Integer.bitCount(mask) - 1;
            // 遍历nums2, 找出可与 nums[i] 匹配的位
            for (int j = 0; j < n; j++) {
                if ((mask >> j & 1) == 1) {
                    // mask ^ (1 << j) 表示的就是 mask 去掉与 nums[i] 匹配的第j 位,
                    // 这个 prevMask 算上匹配的j 就得到了mask
                    int sum = (nums1[i] ^ nums2[j]) + dp[mask ^ (1 << j)];
                    dp[mask] = Math.min(dp[mask], sum);
                }
            }
        }
        return dp[maskSize - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2}, new int[]{2, 3})
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 0, 3}, new int[]{5, 3, 4})
            .expect(8);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{72, 97, 8, 32, 15}, new int[]{63, 97, 57, 60, 83})
            .expect(152);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(
                    new int[]{65022, 4657711, 8572489, 3336640, 7744043, 8672352, 6861299, 5122697, 2857375, 7539481,
                            8907966, 3311170},
                    new int[]{6030101, 8828015, 59043, 6529065, 9719816, 7144904, 6799001, 5637315, 9805075, 1136584,
                            8266168, 4154565})
            .expect(15088819);

}
