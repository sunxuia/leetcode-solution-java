package q050;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * Given an array nums of n integers and an integer target, find three integers in nums such that the sum is closest
 * to target. Return the sum of the three integers. You may assume that each input would have exactly one solution.
 *
 * Example:
 *
 * Given array nums = [-1, 2, 1, -4], and target = 1.
 *
 * The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
 */
@RunWith(LeetCodeRunner.class)
public class Q016_3SumClosest {

    /**
     * 通过排序后左右逼近, 避免了3 层嵌套, 将时间复杂度限制在 O(n^2)
     */
    @Answer
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        // 因为有且仅有一个匹配, 所以nums 的长度肯定>=3
        int res = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (Math.abs(sum - target) < Math.abs(res - target)) {
                    res = sum;
                }
                if (sum > target) {
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    return res;
                }
            }
        }
        return res;
    }

    /**
     * leetcode 中运行快的程序中加入了一些边界值判断, 能够减少一些运算.
     */
    @Answer
    public int optimized(int[] nums, int target) {
        Arrays.sort(nums);
        int offset = nums[0] + nums[1] + nums[2] - target;
        for (int i = 0; i < nums.length - 2; i++) {
            int cur = nums[i] + nums[nums.length - 2] + nums[nums.length - 1] - target;
            if (cur < 0) {
                offset = Math.abs(cur) < Math.abs(offset) ? cur : offset;
                continue;
            }
            cur = nums[i] + nums[i + 1] + nums[i + 2] - target;
            if (cur > 0) {
                offset = Math.abs(cur) < Math.abs(offset) ? cur : offset;
                continue;
            }
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                cur = nums[i] + nums[left] + nums[right] - target;
//                TablePrinter.printArray(nums, i, "i", left, "left", right, "right");
//                System.out.println(String.format("%d : %d : %d", sum, target, Math.abs(sum - target)));
                offset = Math.abs(cur) < Math.abs(offset) ? cur : offset;
                if (cur > 0) {
                    right--;
                } else if (cur < 0) {
                    left++;
                } else {
                    return target;
                }
            }
        }
        return offset + target;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[]{-1, 2, 1, -4})
            .addArgument(1)
            .expect(2)
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[]{0, 2, 1, -3})
            .addArgument(1)
            .expect(0)
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(new int[]{1, 2, 4, 8, 16, 32, 64, 128})
            .addArgument(82)
            .expect(82)
            .build();

    @TestData
    public DataExpectation normal3 = DataExpectation.builder()
            .addArgument(
                    new int[]{6, -18, -20, -7, -15, 9, 18, 10, 1, -20, -17, -19, -3, -5, -19, 10, 6, -11, 1, -17, -15,
                            6, 17, -18, -3, 16, 19, -20, -3, -17, -15, -3, 12, 1, -9, 4, 1, 12, -2, 14, 4, -4, 19, -20,
                            6, 0, -19, 18, 14, 1, -15, -5, 14, 12, -4, 0, -10, 6, 6, -6, 20, -8, -6, 5, 0, 3, 10, 7, -2,
                            17, 20, 12, 19, -13, -1, 10, -1, 14, 0, 7, -3, 10, 14, 14, 11, 0, -4, -15, -8, 3, 2, -5, 9,
                            10, 16, -4, -3, -9, -8, -14, 10, 6, 2, -12, -7, -16, -6, 10})
            .addArgument(-52)
            .expect(-52)
            .build();

    @TestData
    public DataExpectation normal4 = DataExpectation.builder()
            .addArgument(new int[]{1, 1, -1, -1, 3})
            .addArgument(3)
            .expect(3)
            .build();
}
