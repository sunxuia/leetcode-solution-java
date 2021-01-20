package q1550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1523. Count Odd Numbers in an Interval Range
 * https://leetcode.com/problems/count-odd-numbers-in-an-interval-range/
 *
 * Given two non-negative integers low and high. Return the count of odd numbers between low and high (inclusive).
 *
 * Example 1:
 *
 * Input: low = 3, high = 7
 * Output: 3
 * Explanation: The odd numbers between 3 and 7 are [3,5,7].
 *
 * Example 2:
 *
 * Input: low = 8, high = 10
 * Output: 1
 * Explanation: The odd numbers between 8 and 10 are [9].
 *
 * Constraints:
 *
 * 0 <= low <= high <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1523_CountOddNumbersInAnIntervalRange {

    @Answer
    public int countOdds(int low, int high) {
        if (low % 2 == 0 && high % 2 == 0) {
            return (high - low) / 2;
        } else {
            return (high - low) / 2 + 1;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(3, 7).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(8, 10).expect(1);

}
