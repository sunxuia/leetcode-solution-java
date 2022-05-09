package q1900;

import java.util.Arrays;
import java.util.Comparator;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1893. Check if All the Integers in a Range Are Covered
 * https://leetcode.com/problems/check-if-all-the-integers-in-a-range-are-covered/
 *
 * You are given a 2D integer array ranges and two integers left and right. Each ranges[i] = [starti, endi] represents
 * an inclusive interval between starti and endi.
 *
 * Return true if each integer in the inclusive range [left, right] is covered by at least one interval in ranges.
 * Return false otherwise.
 *
 * An integer x is covered by an interval ranges[i] = [starti, endi] if starti <= x <= endi.
 *
 * Example 1:
 *
 * Input: ranges = [[1,2],[3,4],[5,6]], left = 2, right = 5
 * Output: true
 * Explanation: Every integer between 2 and 5 is covered:
 * - 2 is covered by the first range.
 * - 3 and 4 are covered by the second range.
 * - 5 is covered by the third range.
 *
 * Example 2:
 *
 * Input: ranges = [[1,10],[10,20]], left = 21, right = 21
 * Output: false
 * Explanation: 21 is not covered by any range.
 *
 * Constraints:
 *
 * 1 <= ranges.length <= 50
 * 1 <= starti <= endi <= 50
 * 1 <= left <= right <= 50
 */
@RunWith(LeetCodeRunner.class)
public class Q1893_CheckIfAllTheIntegersInARangeAreCovered {

    @Answer
    public boolean isCovered(int[][] ranges, int left, int right) {
        Arrays.sort(ranges, Comparator.comparingInt(arr -> arr[0]));
        for (int[] range : ranges) {
            if (left < range[0]) {
                return false;
            }
            left = Math.max(left, range[1] + 1);
            if (left > right) {
                return true;
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{1, 2}, {3, 4}, {5, 6}}, 2, 5)
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{1, 10}, {10, 20}}, 21, 21)
            .expect(false);

}
