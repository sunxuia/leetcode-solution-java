package q1600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1566. Detect Pattern of Length M Repeated K or More Times
 * https://leetcode.com/problems/detect-pattern-of-length-m-repeated-k-or-more-times/
 *
 * Given an array of positive integers arr,  find a pattern of length m that is repeated k or more times.
 *
 * A pattern is a subarray (consecutive sub-sequence) that consists of one or more values, repeated multiple times
 * consecutively without overlapping. A pattern is defined by its length and the number of repetitions.
 *
 * Return true if there exists a pattern of length m that is repeated k or more times, otherwise return false.
 *
 * Example 1:
 *
 * Input: arr = [1,2,4,4,4,4], m = 1, k = 3
 * Output: true
 * Explanation: The pattern (4) of length 1 is repeated 4 consecutive times. Notice that pattern can be repeated k or
 * more times but not less.
 *
 * Example 2:
 *
 * Input: arr = [1,2,1,2,1,1,1,3], m = 2, k = 2
 * Output: true
 * Explanation: The pattern (1,2) of length 2 is repeated 2 consecutive times. Another valid pattern (2,1) is also
 * repeated 2 times.
 *
 * Example 3:
 *
 * Input: arr = [1,2,1,2,1,3], m = 2, k = 3
 * Output: false
 * Explanation: The pattern (1,2) is of length 2 but is repeated only 2 times. There is no pattern of length 2 that is
 * repeated 3 or more times.
 *
 * Example 4:
 *
 * Input: arr = [1,2,3,1,2], m = 2, k = 2
 * Output: false
 * Explanation: Notice that the pattern (1,2) exists twice but not consecutively, so it doesn't count.
 *
 * Example 5:
 *
 * Input: arr = [2,2,2,2], m = 2, k = 3
 * Output: false
 * Explanation: The only pattern of length 2 is (2,2) however it's repeated only twice. Notice that we do not count
 * overlapping repetitions.
 *
 * Constraints:
 *
 * 2 <= arr.length <= 100
 * 1 <= arr[i] <= 100
 * 1 <= m <= 100
 * 2 <= k <= 100
 *
 * 题解: 这题要求从arr 中找出长 m 的子数组, 其连续出现次数 >= k.
 */
@RunWith(LeetCodeRunner.class)
public class Q1566_DetectPatternOfLengthMRepeatedKOrMoreTimes {

    @Answer
    public boolean containsPattern(int[] arr, int m, int k) {
        int end = arr.length - m * k;
        for (int i = 0; i <= end; i++) {
            if (match(arr, i, i + m, k)) {
                return true;
            }
        }
        return false;
    }

    private boolean match(int[] arr, int start, int end, int k) {
        int c = end;
        for (int i = 1; i < k; i++) {
            for (int p = start; p < end; p++, c++) {
                if (arr[p] != arr[c]) {
                    return false;
                }
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 4, 4, 4, 4}, 1, 3)
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 2, 1, 2, 1, 1, 1, 3}, 2, 2)
            .expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 2, 1, 2, 1, 3}, 2, 3)
            .expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[]{1, 2, 3, 1, 2}, 2, 2)
            .expect(false);

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(new int[]{2, 2, 2, 2}, 2, 3)
            .expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{2, 2}, 1, 2)
            .expect(true);

}
