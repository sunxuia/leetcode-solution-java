package q1300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1299. Replace Elements with Greatest Element on Right Side
 * https://leetcode.com/problems/replace-elements-with-greatest-element-on-right-side/
 *
 * Given an array arr, replace every element in that array with the greatest element among the elements to its right,
 * and replace the last element with -1.
 *
 * After doing so, return the array.
 *
 * Example 1:
 * Input: arr = [17,18,5,4,6,1]
 * Output: [18,6,6,6,1,-1]
 *
 * Constraints:
 *
 * 1 <= arr.length <= 10^4
 * 1 <= arr[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1299_ReplaceElementsWithGreatestElementOnRightSide {

    @Answer
    public int[] replaceElements(int[] arr) {
        int max = -1;
        for (int i = arr.length - 1; i >= 0; i--) {
            int t = arr[i];
            arr[i] = max;
            max = Math.max(max, t);
        }
        return arr;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(new int[]{17, 18, 5, 4, 6, 1}).expect(new int[]{18, 6, 6, 6, 1, -1});

}
