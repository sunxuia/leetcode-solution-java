package q300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/missing-number/
 *
 * Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.
 *
 * Example 1:
 *
 * Input: [3,0,1]
 * Output: 2
 * Example 2:
 *
 * Input: [9,6,4,2,3,5,7,0,1]
 * Output: 8
 * Note:
 * Your algorithm should run in linear runtime complexity. Could you implement it using only constant extra space
 * complexity?
 */
@RunWith(LeetCodeRunner.class)
public class Q268_MissingNumber {

    // 题目要求 O(n) 的时间复杂度和 O(1) 的空间复杂度
    @Answer
    public int missingNumber(int[] nums) {
        final int n = nums.length;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        return n * (n + 1) / 2 - sum;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 0, 1}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{9, 6, 4, 2, 3, 5, 7, 0, 1}).expect(8);

}

