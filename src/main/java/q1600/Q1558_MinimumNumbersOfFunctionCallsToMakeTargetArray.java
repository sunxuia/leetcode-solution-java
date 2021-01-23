package q1600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1558. Minimum Numbers of Function Calls to Make Target Array
 * https://leetcode.com/problems/minimum-numbers-of-function-calls-to-make-target-array/
 *
 * <img src="./Q1558_PIC.png">
 * Your task is to form an integer array nums from an initial array of zeros arr that is the same size as nums.
 *
 * Return the minimum number of function calls to make nums from arr.
 *
 * The answer is guaranteed to fit in a 32-bit signed integer.
 *
 * Example 1:
 *
 * Input: nums = [1,5]
 * Output: 5
 * Explanation: Increment by 1 (second element): [0, 0] to get [0, 1] (1 operation).
 * Double all the elements: [0, 1] -> [0, 2] -> [0, 4] (2 operations).
 * Increment by 1 (both elements)  [0, 4] -> [1, 4] -> [1, 5] (2 operations).
 * Total of operations: 1 + 2 + 2 = 5.
 *
 * Example 2:
 *
 * Input: nums = [2,2]
 * Output: 3
 * Explanation: Increment by 1 (both elements) [0, 0] -> [0, 1] -> [1, 1] (2 operations).
 * Double all the elements: [1, 1] -> [2, 2] (1 operation).
 * Total of operations: 2 + 1 = 3.
 *
 * Example 3:
 *
 * Input: nums = [4,2,5]
 * Output: 6
 * Explanation: (initial)[0,0,0] -> [1,0,0] -> [1,0,1] -> [2,0,2] -> [2,1,2] -> [4,2,4] -> [4,2,5](nums).
 *
 * Example 4:
 *
 * Input: nums = [3,2,2,4]
 * Output: 7
 *
 * Example 5:
 *
 * Input: nums = [2,4,8,16]
 * Output: 8
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^9
 *
 * 题解 :
 * modify 方法就 2 个功能: 1) 将 arr 中的一个元素+1; 2) 将 arr 中所有元素 *2.
 * 题目要求最少可以通过多少次modify 调用, 让初始全为0 的 arr 变成 num.
 */
@RunWith(LeetCodeRunner.class)
public class Q1558_MinimumNumbersOfFunctionCallsToMakeTargetArray {

    /**
     * 从nums 还原回去即可
     */
    @Answer
    public int minOperations(int[] nums) {
        final int n = nums.length;
        int res = 0;
        while (true) {
            for (int i = 0; i < n; i++) {
                if (nums[i] % 2 == 1) {
                    nums[i]--;
                    res++;
                }
            }
            boolean allZero = true;
            for (int i = 0; i < n; i++) {
                allZero &= nums[i] == 0;
                nums[i] /= 2;
            }
            if (allZero) {
                return res;
            }
            res++;
        }
    }

    /**
     * 参考 LeetCode 上比较块的解法.
     */
    @Answer
    public int minOperations2(int[] nums) {
        int res = 0, maxDivide = 0;
        for (int num : nums) {
            // 计算 num -> 0 至少需要进行多少次转换
            int divide = 0;
            while (num > 0) {
                if ((num & 1) == 1) {
                    // 如果是 -1, 则需要单独计次
                    // (这里使用位运算可以加快速度)
                    res += num & 1;
                    num--;
                } else {
                    // 如果是/2, 则计算最大次数即可
                    num >>>= 1;
                    divide++;
                }
            }
            maxDivide = Math.max(maxDivide, divide);
        }
        return res + maxDivide;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 5}).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 2}).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{4, 2, 5}).expect(6);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{3, 2, 2, 4}).expect(7);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new int[]{2, 4, 8, 16}).expect(8);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1}).expect(1);

}
