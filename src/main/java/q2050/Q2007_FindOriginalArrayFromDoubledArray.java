package q2050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 2007. Find Original Array From Doubled Array
 * https://leetcode.com/problems/find-original-array-from-doubled-array/
 *
 * An integer array original is transformed into a doubled array changed by appending twice the value of every element
 * in original, and then randomly shuffling the resulting array.
 *
 * Given an array changed, return original if changed is a doubled array. If changed is not a doubled array, return an
 * empty array. The elements in original may be returned in any order.
 *
 * Example 1:
 *
 * Input: changed = [1,3,4,2,6,8]
 * Output: [1,3,4]
 * Explanation: One possible original array could be [1,3,4]:
 * - Twice the value of 1 is 1 * 2 = 2.
 * - Twice the value of 3 is 3 * 2 = 6.
 * - Twice the value of 4 is 4 * 2 = 8.
 * Other original arrays could be [4,3,1] or [3,1,4].
 *
 * Example 2:
 *
 * Input: changed = [6,3,0,1]
 * Output: []
 * Explanation: changed is not a doubled array.
 *
 * Example 3:
 *
 * Input: changed = [1]
 * Output: []
 * Explanation: changed is not a doubled array.
 *
 * Constraints:
 *
 * 1 <= changed.length <= 10^5
 * 0 <= changed[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q2007_FindOriginalArrayFromDoubledArray {

    @Answer
    public int[] findOriginalArray(int[] changed) {
        final int n = changed.length;
        if ((n & 1) == 1) {
            return new int[0];
        }
        final int len = n / 2;

        int max = 0;
        for (int val : changed) {
            max = Math.max(max, val);
        }
        int[] buckets = new int[max + 1];
        for (int val : changed) {
            buckets[val]++;
        }

        int[] res = new int[len];
        int size = 0;
        for (int val = max; val >= 0; ) {
            if (buckets[val] == 0) {
                val--;
            } else if ((val & 1) == 1 || buckets[val] < 0
                    || size == len && buckets[val] != 0) {
                return new int[0];
            } else {
                res[size++] = val / 2;
                buckets[val]--;
                buckets[val / 2]--;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[]{1, 3, 4, 2, 6, 8})
            .expect(new int[]{1, 3, 4})
            .unOrder();

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[]{6, 3, 0, 1})
            .expect(new int[]{})
            .unOrder();

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new int[]{1})
            .expect(new int[]{})
            .unOrder();

}
