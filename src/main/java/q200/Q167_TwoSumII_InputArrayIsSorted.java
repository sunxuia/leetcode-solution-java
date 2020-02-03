package q200;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
 *
 * Given an array of integers that is already sorted in ascending order, find two numbers such that they add up to a
 * specific target number.
 *
 * The function twoSum should return indices of the two numbers such that they add up to the target, where index1
 * must be less than index2.
 *
 * Note:
 *
 * Your returned answers (both index1 and index2) are not zero-based.
 * You may assume that each input would have exactly one solution and you may not use the same element twice.
 *
 * Example:
 *
 * Input: numbers = [2,7,11,15], target = 9
 * Output: [1,2]
 * Explanation: The sum of 2 and 7 is 9. Therefore index1 = 1, index2 = 2.
 */
@RunWith(LeetCodeRunner.class)
public class Q167_TwoSumII_InputArrayIsSorted {

    // O(n) 的时间复杂度
    @Answer
    public int[] twoSum(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            int right = Arrays.binarySearch(numbers, i + 1, numbers.length, target - numbers[i]);
            if (right > 0) {
                return new int[]{i + 1, right + 1};
            }
        }
        throw new RuntimeException("should not run to here");
    }

    // LeetCode 上的优化
    @Answer
    public int[] twoSum2(int[] numbers, int target) {
        int start = 0, end = numbers.length - 1;
        while (start < end) {
            int sum = numbers[start] + numbers[end];
            if (sum == target) {
                break;
            } else if (sum < target) {
                start++;
            } else {
                end--;
            }
        }
        return new int[]{start + 1, end + 1};
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(new int[]{2, 7, 11, 15}, 9).expect(new int[]{1, 2});

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{0, 1, 2}, 3).expect(new int[]{2, 3});

}
