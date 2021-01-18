package q1550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Easy] 1502. Can Make Arithmetic Progression From Sequence
 * https://leetcode.com/problems/can-make-arithmetic-progression-from-sequence/
 *
 * Given an array of numbers arr. A sequence of numbers is called an arithmetic progression if the difference between
 * any two consecutive elements is the same.
 *
 * Return true if the array can be rearranged to form an arithmetic progression, otherwise, return false.
 *
 * Example 1:
 *
 * Input: arr = [3,5,1]
 * Output: true
 * Explanation: We can reorder the elements as [1,3,5] or [5,3,1] with differences 2 and -2 respectively, between each
 * consecutive elements.
 *
 * Example 2:
 *
 * Input: arr = [1,2,4]
 * Output: false
 * Explanation: There is no way to reorder the elements to obtain an arithmetic progression.
 *
 * Constraints:
 *
 * 2 <= arr.length <= 1000
 * -10^6 <= arr[i] <= 10^6
 */
@RunWith(LeetCodeRunner.class)
public class Q1502_CanMakeArithmeticProgressionFromSequence {

    @Answer
    public boolean canMakeArithmeticProgression(int[] arr) {
        Arrays.sort(arr);
        for (int i = 1; i < arr.length - 1; i++) {
            if (arr[i] - arr[i - 1] != arr[i + 1] - arr[i]) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 5, 1}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 4}).expect(false);

}
