package q1500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1470. Shuffle the Array
 * https://leetcode.com/problems/shuffle-the-array/
 *
 * Given the array nums consisting of 2n elements in the form [x1,x2,...,xn,y1,y2,...,yn].
 *
 * Return the array in the form [x1,y1,x2,y2,...,xn,yn].
 *
 * Example 1:
 *
 * Input: nums = [2,5,1,3,4,7], n = 3
 * Output: [2,3,5,4,1,7]
 * Explanation: Since x1=2, x2=5, x3=1, y1=3, y2=4, y3=7 then the answer is [2,3,5,4,1,7].
 *
 * Example 2:
 *
 * Input: nums = [1,2,3,4,4,3,2,1], n = 4
 * Output: [1,4,2,3,3,2,4,1]
 *
 * Example 3:
 *
 * Input: nums = [1,1,2,2], n = 2
 * Output: [1,2,1,2]
 *
 * Constraints:
 *
 * 1 <= n <= 500
 * nums.length == 2n
 * 1 <= nums[i] <= 10^3
 */
@RunWith(LeetCodeRunner.class)
public class Q1470_ShuffleTheArray {

    /**
     * 插入的方式
     */
    @Answer
    public int[] shuffle(int[] nums, int n) {
        for (int i = 0, j = n; i < 2 * n; i += 2, j++) {
            int yi = nums[j];
            for (int k = j; k > i + 1; k--) {
                nums[k] = nums[k - 1];
            }
            nums[i + 1] = yi;
        }
        return nums;
    }

    /**
     * 根据1 <= nums[i] <= 10^3 的特性, 把结果缓存在数字的高位.
     */
    @Answer
    public int[] shuffle2(int[] nums, int n) {
        final int level = 10000;
        for (int i = 0; i < n; i++) {
            nums[2 * i] += nums[i] % level * level;
            nums[2 * i + 1] += nums[i + n] % level * level;
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] /= level;
        }
        return nums;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{2, 5, 1, 3, 4, 7}, 3)
            .expect(new int[]{2, 3, 5, 4, 1, 7});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 4, 3, 2, 1}, 4)
            .expect(new int[]{1, 4, 2, 3, 3, 2, 4, 1});

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 1, 2, 2}, 2)
            .expect(new int[]{1, 2, 1, 2});

}
