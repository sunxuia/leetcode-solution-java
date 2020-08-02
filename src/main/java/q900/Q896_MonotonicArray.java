package q900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 896. Monotonic Array
 * https://leetcode.com/problems/monotonic-array/
 *
 * An array is monotonic if it is either monotone increasing or monotone decreasing.
 *
 * An array A is monotone increasing if for all i <= j, A[i] <= A[j].  An array A is monotone decreasing if for all i <=
 * j, A[i] >= A[j].
 *
 * Return true if and only if the given array A is monotonic.
 *
 * Example 1:
 *
 * Input: [1,2,2,3]
 * Output: true
 *
 * Example 2:
 *
 * Input: [6,5,4,4]
 * Output: true
 *
 * Example 3:
 *
 * Input: [1,3,2]
 * Output: false
 *
 * Example 4:
 *
 * Input: [1,2,4,5]
 * Output: true
 *
 * Example 5:
 *
 * Input: [1,1,1]
 * Output: true
 *
 * Note:
 *
 * 1 <= A.length <= 50000
 * -100000 <= A[i] <= 100000
 */
@RunWith(LeetCodeRunner.class)
public class Q896_MonotonicArray {

    @Answer
    public boolean isMonotonic(int[] A) {
        int offset = 0;
        for (int i = 1; i < A.length; i++) {
            int newOffset = A[i] - A[i - 1];
            if (newOffset != 0) {
                if (offset * newOffset < 0) {
                    return false;
                }
                offset = newOffset;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 2, 3}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{6, 5, 4, 4}).expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 3, 2}).expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{1, 2, 4, 5}).expect(true);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new int[]{1, 1, 1}).expect(true);

}
