package q1550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1550. Three Consecutive Odds
 * https://leetcode.com/problems/three-consecutive-odds/
 *
 * Given an integer array arr, return true if there are three consecutive odd numbers in the array. Otherwise, return
 * false.
 *
 * Example 1:
 *
 * Input: arr = [2,6,4,1]
 * Output: false
 * Explanation: There are no three consecutive odds.
 *
 * Example 2:
 *
 * Input: arr = [1,2,34,3,4,5,7,23,12]
 * Output: true
 * Explanation: [5,7,23] are three consecutive odds.
 *
 * Constraints:
 *
 * 1 <= arr.length <= 1000
 * 1 <= arr[i] <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1550_ThreeConsecutiveOdds {

    @Answer
    public boolean threeConsecutiveOdds(int[] arr) {
        for (int i = 2; i < arr.length; i++) {
            if (arr[i - 2] % 2 == 1
                    && arr[i - 1] % 2 == 1
                    && arr[i] % 2 == 1) {
                return true;
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 6, 4, 1}).expect(false);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 34, 3, 4, 5, 7, 23, 12}).expect(true);

}
