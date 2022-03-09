package q1800;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1787. Make the XOR of All Segments Equal to Zero
 * https://leetcode.com/problems/make-the-xor-of-all-segments-equal-to-zero/
 *
 * You are given an array nums and an integer k. The XOR of a segment [left, right] where left <= right is the XOR of
 * all the elements with indices between left and right, inclusive: nums[left] XOR nums[left+1] XOR ... XOR nums[right].
 *
 * Return the minimum number of elements to change in the array such that the XOR of all segments of size k is equal to
 * zero.
 *
 * Example 1:
 *
 * Input: nums = [1,2,0,3,0], k = 1
 * Output: 3
 * Explanation: Modify the array from [1,2,0,3,0] to from [0,0,0,0,0].
 *
 * Example 2:
 *
 * Input: nums = [3,4,5,2,1,7,3,4,7], k = 3
 * Output: 3
 * Explanation: Modify the array from [3,4,5,2,1,7,3,4,7] to [3,4,7,3,4,7,3,4,7].
 *
 * Example 3:
 *
 * Input: nums = [1,2,4,1,2,5,1,2,6], k = 3
 * Output: 3
 * Explanation: Modify the array from [1,2,4,1,2,5,1,2,6] to [1,2,3,1,2,3,1,2,3].
 *
 * Constraints:
 *
 * 1 <= k <= nums.length <= 2000
 * 0 <= nums[i] < 2^10
 */
@RunWith(LeetCodeRunner.class)
public class Q1787_MakeTheXorOfAllSegmentsEqualToZero {

    /**
     * 参考文档 https://blog.csdn.net/zml66666/article/details/114679147
     * 由题意可知 nums[i] = nums[i+k],
     * 则可以nums[i], nums[i+k], nums[i+2k]... 为一组将nums 分为k 组.
     */
    @Answer
    public int minChanges(int[] nums, int k) {
        // 题目中限定 nums[i] < 2^10
        final int limit = 1024;
        final int n = nums.length;

        // 每组中不同元素的数量 (这里需要是Map, 使用数组会出现超时).
        Map<Integer, Integer>[] group = new Map[k];
        // 每组元素的总数
        int[] groupSize = new int[k];
        for (int i = 0; i < k; i++) {
            group[i] = new HashMap<>();
            for (int j = i; j < n; j += k) {
                group[i].merge(nums[j], 1, Integer::sum);
                groupSize[i]++;
            }
        }

        // dp[i+1][v] 表示第i 组异或到数字v 需要的更改次数.
        int[][] dp = new int[k + 1][limit];
        // 初始化次数为最大值(变更为0 次的次数是0 次)
        Arrays.fill(dp[0], Integer.MAX_VALUE);
        dp[0][0] = 0;
        for (int i = 0; i < k; i++) {
            // 找出之前一次最小的改变次数.
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < limit; j++) {
                min = Math.min(min, dp[i][j]);
            }
            // 当前组中不存在数字的改变次数: 之前的最小改变次数+当前组大小 (也就是所有数字全改)
            Arrays.fill(dp[i + 1], min + groupSize[i]);

            for (int j = 0; j < limit; j++) {
                // 对第0组, 保证dp[i][j] 是异或到0 的最小改变数
                if (dp[i][j] < Integer.MAX_VALUE) {
                    for (int num : group[i].keySet()) {
                        // 将全组改成数字num 需要的次数
                        dp[i + 1][num ^ j] = Math.min(dp[i + 1][num ^ j],
                                dp[i][j] + groupSize[i] - group[i].get(num));
                    }
                }
            }
        }
        return dp[k][0];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 0, 3, 0}, 1)
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{3, 4, 5, 2, 1, 7, 3, 4, 7}, 3)
            .expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 2, 4, 1, 2, 5, 1, 2, 6}, 3)
            .expect(3);

}
