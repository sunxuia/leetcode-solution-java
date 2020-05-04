package q050;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import util.asserthelper.AssertUtils;
import util.asserthelper.ObjectEqualsHelper;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/3sum/
 *
 * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique
 * triplets in the array which gives the sum of zero.
 *
 * Note:
 *
 * The solution set must not contain duplicate triplets.
 *
 * Example:
 *
 * Given array nums = [-1, 0, 1, 2, -1, -4],
 *
 * A solution set is:
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 */
@RunWith(LeetCodeRunner.class)
public class Q015_3Sum {

    /**
     * 排序后, 选择一个数字, 然后从两边查找匹配的数字. 时间复杂度在 O(nlogn) ~ O(n^2).
     */
    @Answer
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums.length < 3) {
            return Collections.emptyList();
        }
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
//        TablePrinter printer = new TablePrinter();
//        printer.useNo(true).cellWidth(5).textAlignCenter();
//        printer.print(new int[][]{nums});
        for (int i = 0; i < nums.length - 2 && nums[i] <= 0; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                // 避免相同结果的数字
                continue;
            }
            int right = findNumber(nums, i, nums.length, -nums[i] - nums[i + 1]);
            if (right <= i) {
                continue;
            }
            int left = findNumber(nums, i, right, -nums[i] - nums[right]);
            left = Math.max(left, i + 1);
            while (left < right) {
                if (nums[right] + nums[left] < -nums[i]) {
                    left++;
                } else if (nums[right] + nums[left] > -nums[i]) {
                    right--;
                } else {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    // 去掉重复值 (normal3), 避免left 和right 的下一个数字都相同的情况
                    do {
                        left++;
                        right--;
                    } while (left < right
                            && nums[left - 1] == nums[left]
                            && nums[right] == nums[right + 1]);
                }
            }
        }
        return res;
    }

    /**
     * 二分查找, 找出匹配expect 的值,
     * 如果有多个重复的数字, 则是最右边的一个数, 如果没有匹配则是小于expect 的最右边的数.
     */
    private int findNumber(int[] nums, int start, int end, int expect) {
        while (start < end) {
            int middle = (start + end) / 2;
            if (nums[middle] < expect + 1) {
                start = middle + 1;
            } else {
                end = middle;
            }
        }
        return start - 1;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[]{-1, 0, 1, 2, -1, -4})
            .expect(new int[][]{{-1, 0, 1}, {-1, -1, 2}})
            .assertMethod((expected, actual) -> {
                ObjectEqualsHelper helper = new ObjectEqualsHelper();
                helper.unorder("*");
                AssertUtils.assertEquals(expected, actual, helper);
            })
            .build();

    @TestData
    public DataExpectation border1 = DataExpectation.builder()
            .addArgument(new int[]{0, 0, 0, 0})
            .expect(new int[][]{{0, 0, 0}})
            .build();

    @TestData
    public DataExpectation border2 = DataExpectation.builder()
            .addArgument(new int[]{1, 1, 1, 1})
            .expect(Collections.emptyList())
            .build();

    @TestData
    public DataExpectation border3 = DataExpectation.builder()
            .addArgument(new int[]{-1, -1, -1, -1})
            .expect(Collections.emptyList())
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[]{-1, -1, 0, 0, 1, 1})
            .expect(new int[][]{{-1, 0, 1}})
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(new int[]{0, 1, 1})
            .expect(Collections.emptyList())
            .build();

    @TestData
    public DataExpectation normal3 = DataExpectation.builder()
            .addArgument(new int[]{-1, -2, -3, 4, 1, 3, 0, 3, -2, 1, -2, 2, -1, 1, -5, 4, -3})
            .expect(new int[][]{{-5, 1, 4}, {-5, 2, 3}, {-3, -1, 4}, {-3, 0, 3}, {-3, 1, 2}, {-2, -2, 4}, {-2, -1, 3},
                    {-2, 0, 2}, {-2, 1, 1}, {-1, -1, 2}, {-1, 0, 1}})
            .assertMethod((expected, actual) -> {
                ObjectEqualsHelper helper = new ObjectEqualsHelper();
                helper.unorder("*");
                AssertUtils.assertEquals(expected, actual, helper);
            })
            .build();
}
