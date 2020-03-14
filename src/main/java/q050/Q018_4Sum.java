package q050;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import org.junit.runner.RunWith;
import q500.Q454_4SumII;
import util.asserthelper.AssertUtils;
import util.asserthelper.ObjectEqualsHelper;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/4sum/
 *
 * Given an array nums of n integers and an integer target, are there elements a, b, c, and d in nums such that a + b
 * + c + d = target? Find all unique quadruplets in the array which gives the sum of target.
 *
 * Note:
 *
 * The solution set must not contain duplicate quadruplets.
 *
 * Example:
 *
 * Given array nums = [1, 0, -1, 0, -2, 2], and target = 0.
 *
 * A solution set is:
 * [
 * [-1,  0, 0, 1],
 * [-2, -1, 1, 2],
 * [-2,  0, 0, 2]
 * ]
 *
 * 下一题 {@link Q454_4SumII}
 */
@RunWith(LeetCodeRunner.class)
public class Q018_4Sum {

    /**
     * 类似 Q015, 3 个数之和变成了4 个数. 时间复杂度 O(n^2logn) ~ O(n^3)
     */
    @Answer
    public List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums.length < 4) {
            return Collections.emptyList();
        }
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i - 1] == nums[i]) {
                continue;
            }
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > i + 1 && nums[j - 1] == nums[j]) {
                    continue;
                }
                int right = nums.length - 1;
                int left = j + 1;
                while (left < right) {
                    int sum = nums[i] + nums[j] + nums[left] + nums[right];
//                    TablePrinter.printArray(nums, i, "i", j, "j", left, "left", right, "right");
                    if (sum == target) {
                        res.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        do {
                            left++;
                            right--;
                        } while (left < right
                                && nums[left - 1] == nums[left]
                                && nums[right] == nums[right + 1]);
                    } else if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        return res;
    }

    private BiConsumer assertMethod = (expected, actual) -> {
        ObjectEqualsHelper helper = new ObjectEqualsHelper();
        helper.unorder("*");
        AssertUtils.assertEquals(expected, actual, helper);
    };

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[]{1, 0, -1, 0, -2, 2})
            .addArgument(0)
            .expect(new int[][]{
                    {-1, 0, 0, 1},
                    {-2, -1, 1, 2},
                    {-2, 0, 0, 2}
            })
            .assertMethod(assertMethod)
            .build();

    @TestData
    public DataExpectation border = DataExpectation.builder()
            .addArgument(new int[]{0, 0, 0, 0})
            .addArgument(0)
            .expect(new int[][]{{0, 0, 0, 0}})
            .assertMethod(assertMethod)
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[]{-3, -2, -1, 0, 0, 1, 2, 3})
            .addArgument(0)
            .expect(new int[][]{{-3, -2, 2, 3}, {-3, -1, 1, 3}, {-3, 0, 0, 3}, {-3, 0, 1, 2}, {-2, -1, 0, 3},
                    {-2, -1, 1, 2}, {-2, 0, 0, 2}, {-1, 0, 0, 1}})
            .assertMethod(assertMethod)
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(new int[]{1, -2, -5, -4, -3, 3, 3, 5})
            .addArgument(-11)
            .expect(new int[][]{{-5, -4, -3, 1}})
            .assertMethod(assertMethod)
            .build();

    @TestData
    public DataExpectation normal3 = DataExpectation.builder()
            .addArgument(new int[]{-493, -470, -464, -453, -451, -446, -445, -407, -406, -393, -328, -312, -307, -303,
                    -259, -253, -252, -243, -221, -193, -126, -126, -122, -117, -106, -105, -101, -71, -20, -12, 3, 4,
                    20, 20, 54, 84, 98, 111, 148, 149, 152, 171, 175, 176, 211, 218, 227, 331, 352, 389, 410, 420, 448,
                    485})
            .addArgument(1057)
            .expect(new int[][]{{-221, 410, 420, 448}, {-12, 211, 410, 448}, {3, 149, 420, 485}, {4, 148, 420, 485},
                    {54, 98, 420, 485}, {84, 211, 352, 410}, {98, 218, 331, 410}, {98, 218, 352, 389},
                    {171, 211, 227, 448}})
            .assertMethod(assertMethod)
            .build();
}
