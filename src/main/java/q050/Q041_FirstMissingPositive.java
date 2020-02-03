package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/first-missing-positive/
 *
 * Given an unsorted integer array, find the smallest missing positive integer.
 *
 * Example 1:
 *
 * Input: [1,2,0]
 * Output: 3
 * Example 2:
 *
 * Input: [3,4,-1,1]
 * Output: 2
 * Example 3:
 *
 * Input: [7,8,9,11,12]
 * Output: 1
 *
 * Note:
 *
 * Your algorithm should run in O(n) time and uses constant extra space.
 *
 * 题解:
 * 找出(无序)数组中没有的最小的正数, 要求时间复杂度为 O(n), 空间复杂度为 O(1).
 */
@RunWith(LeetCodeRunner.class)
public class Q041_FirstMissingPositive {

    /**
     * 思路是缺少的数字肯定小于等于数组长度, 那么就把正数放到 -1 值对应的下标中,
     * 最后遍历一遍数组, 找出第1 个和下标+1不符的, 就是需需要的数了.
     */
    @Answer
    public int firstMissingPositive(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                // 当前数移动到指定下标, 指定下标的数继续移动
                int v = nums[i];
                while (v > 0 && v <= nums.length && nums[v - 1] != v) {
                    int next = nums[v - 1];
                    nums[v - 1] = v;
                    v = next;
                }
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return nums.length + 1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[]{1, 2, 0})
            .expect(3)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[]{3, 4, -1, 1})
            .expect(2)
            .build();

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument(new int[]{7, 8, 9, 11, 12})
            .expect(1)
            .build();

    @TestData
    public DataExpectation border = DataExpectation.builder()
            .addArgument(new int[0])
            .expect(1)
            .build();
}
