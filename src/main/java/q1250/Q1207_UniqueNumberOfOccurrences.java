package q1250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1207. Unique Number of Occurrences
 * https://leetcode.com/problems/unique-number-of-occurrences/
 *
 * Given an array of integers arr, write a function that returns true if and only if the number of occurrences of each
 * value in the array is unique.
 *
 * Example 1:
 *
 * Input: arr = [1,2,2,1,1,3]
 * Output: true
 * Explanation: The value 1 has 3 occurrences, 2 has 2 and 3 has 1. No two values have the same number of occurrences.
 *
 * Example 2:
 *
 * Input: arr = [1,2]
 * Output: false
 *
 * Example 3:
 *
 * Input: arr = [-3,0,1,-3,1,1,1,-3,10,0]
 * Output: true
 *
 * Constraints:
 *
 * 1 <= arr.length <= 1000
 * -1000 <= arr[i] <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1207_UniqueNumberOfOccurrences {

    @Answer
    public boolean uniqueOccurrences(int[] arr) {
        int[] counts = new int[2001];
        boolean[] occurred = new boolean[arr.length + 1];
        for (int i : arr) {
            counts[i + 1000]++;
        }
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > 0) {
                if (occurred[counts[i]]) {
                    return false;
                }
                occurred[counts[i]] = true;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 2, 1, 1, 3}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2}).expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{-3, 0, 1, -3, 1, 1, 1, -3, 10, 0}).expect(true);

}
