package q1150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1122. Relative Sort Array
 * https://leetcode.com/problems/relative-sort-array/
 *
 * Given two arrays arr1 and arr2, the elements of arr2 are distinct, and all elements in arr2 are also in arr1.
 *
 * Sort the elements of arr1 such that the relative ordering of items in arr1 are the same as in arr2.  Elements that
 * don't appear in arr2 should be placed at the end of arr1 in ascending order.
 *
 * Example 1:
 * Input: arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
 * Output: [2,2,2,1,4,3,3,9,6,7,19]
 *
 * Constraints:
 *
 * arr1.length, arr2.length <= 1000
 * 0 <= arr1[i], arr2[i] <= 1000
 * Each arr2[i] is distinct.
 * Each arr2[i] is in arr1.
 */
@RunWith(LeetCodeRunner.class)
public class Q1122_RelativeSortArray {

    @Answer
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int[] counts = new int[1001];
        for (int val : arr1) {
            counts[val]++;
        }

        int i = 0;
        for (int j = 0; j < arr2.length; j++) {
            while (counts[arr2[j]]-- > 0) {
                arr1[i++] = arr2[j];
            }
        }
        for (int val = 0; val < 1001; val++) {
            while (counts[val]-- > 0) {
                arr1[i++] = val;
            }
        }
        return arr1;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(new int[]{2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19}, new int[]{2, 1, 4, 3, 9, 6})
            .expect(new int[]{2, 2, 2, 1, 4, 3, 3, 9, 6, 7, 19});

}
