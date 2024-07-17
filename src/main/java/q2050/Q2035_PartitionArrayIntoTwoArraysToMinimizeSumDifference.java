package q2050;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 2035. Partition Array Into Two Arrays to Minimize Sum Difference
 * https://leetcode.com/problems/partition-array-into-two-arrays-to-minimize-sum-difference/
 *
 * You are given an integer array nums of 2 * n integers. You need to partition nums into two arrays of length n to
 * minimize the absolute difference of the sums of the arrays. To partition nums, put each element of nums into one of
 * the two arrays.
 *
 * Return the minimum possible absolute difference.
 *
 * Example 1:
 * (图Q2035_PIC1.png)
 * Input: nums = [3,9,7,3]
 * Output: 2
 * Explanation: One optimal partition is: [3,9] and [7,3].
 * The absolute difference between the sums of the arrays is abs((3 + 9) - (7 + 3)) = 2.
 *
 * Example 2:
 *
 * Input: nums = [-36,36]
 * Output: 72
 * Explanation: One optimal partition is: [-36] and [36].
 * The absolute difference between the sums of the arrays is abs((-36) - (36)) = 72.
 *
 * Example 3:
 * (图Q2035_PIC2.png)
 * Input: nums = [2,-1,0,4,-2,-9]
 * Output: 0
 * Explanation: One optimal partition is: [2,4,-9] and [-1,0,-2].
 * The absolute difference between the sums of the arrays is abs((2 + 4 + -9) - (-1 + 0 + -2)) = 0.
 *
 * Constraints:
 *
 * 1 <= n <= 15
 * nums.length == 2 * n
 * -10^7 <= nums[i] <= 10^7
 */
@RunWith(LeetCodeRunner.class)
public class Q2035_PartitionArrayIntoTwoArraysToMinimizeSumDifference {

    /**
     * 根据hint, 可以将nums 分为两个长度为n 的数组, 然后分别计算, 再组合起来.
     */
    @Answer
    public int minimumDifference(int[] nums) {
        final int n = nums.length / 2;
        // 左半边
        int[][] masks1 = getMasks(nums, 0);
        // 右半边
        int[][] masks2 = getMasks(nums, n);
        for (int i = 1; i <= n; i++) {
            Arrays.sort(masks2[i]);
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int diff1 : masks1[i]) {
                //遍历左半边, 找到右半边对应个数(n-i)的组合, 找出最小值.
                int idx = Arrays.binarySearch(masks2[n - i], -diff1);
                if (idx >= 0) {
                    return 0;
                }
                if (-idx - 2 >= 0) {
                    res = Math.min(res, Math.abs(diff1 + masks2[n - i][-idx - 2]));
                }
                if (-idx - 1 < masks2[n - i].length) {
                    res = Math.min(res, Math.abs(diff1 + masks2[n - i][-idx - 1]));
                }
            }
        }
        return res;
    }

    private int[][] getMasks(int[] nums, int o) {
        final int n = nums.length / 2;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i + o];
        }
        // masks[mask] 表示选择一组为 mask 的位值为1 表示的元素(则另一组为0 表示的) 所得到的差值.
        int[] masks = new int[1 << n];
        masks[0] = -sum;
        // 计算不同组合对应的差值, 这种算法比较慢, 下面的算法比较快.
//        for (int m = 1; m < (1 << n); m++) {
//            for (int i = 0; i < n; i++) {
//                if ((m >> i & 1) == 1) {
//                    masks[m] = masks[m ^ (1 << i)] + 2 * nums[i + o];
//                }
//            }
//        }
        // leetcode 中比较快的解法. 遍历每位i 来进行计算.
        for (int i = 0, maxTo = 1; i < n; i++, maxTo <<= 1) {
            // 以 i 为最高位, 用小于 1 << i 的组合(from) 来计算i 位为1 的组合(to).
            for (int from = 0, to = maxTo; from < maxTo; from++, to++) {
                masks[to] = masks[from] + 2 * nums[i + o];
            }
        }

        // res[len] 表示选择的组的长度为len(mask 中1位的数量分组) 的可能结果
        int[][] res = new int[n + 1][];
        int[] resLen = new int[n + 1];
        // 计算长度, 这里面有公式
        for (int i = 0, len = 1; i <= n; i++) {
            res[i] = new int[len];
            len = len * (n - i) / (i + 1);
        }
        // 统计结果
        for (int m = 0; m < (1 << n); m++) {
            int bits = Integer.bitCount(m);
            res[bits][resLen[bits]++] = masks[m];
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 9, 7, 3}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{-36, 36}).expect(72);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{2, -1, 0, 4, -2, -9}).expect(0);

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(new int[]{-50112, 74454, -50094, 84987, -50628, 76156, 97695, 3082, -15036, 3305, 35723, -69768,
                    -55739, 81016, -49474, -30330, 26655, -64831, -36029, -26562, 83990, -94273, -55447, -13238, -12528,
                    -87759, -17300, -40858, 160327, 92841})
            .expect(1);

}
