package q950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 941. Valid Mountain Array
 * https://leetcode.com/problems/valid-mountain-array/
 *
 * Given an array A of integers, return true if and only if it is a valid mountain array.
 *
 * Recall that A is a mountain array if and only if:
 *
 * 1. A.length >= 3
 * 2. There exists some i with 0 < i < A.length - 1 such that:
 * 2.1. A[0] < A[1] < ... A[i-1] < A[i]
 * 2.2. A[i] > A[i+1] > ... > A[A.length - 1]
 *
 * (图Q941_PIC.png)
 *
 * Example 1:
 *
 * Input: [2,1]
 * Output: false
 *
 * Example 2:
 *
 * Input: [3,5,5]
 * Output: false
 *
 * Example 3:
 *
 * Input: [0,3,2,1]
 * Output: true
 *
 * Note:
 *
 * 0 <= A.length <= 10000
 * 0 <= A[i] <= 10000
 */
@RunWith(LeetCodeRunner.class)
public class Q941_ValidMountainArray {

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 1}).expect(false);
    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{3, 5, 5}).expect(false);
    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{0, 3, 2, 1}).expect(true);
    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}).expect(false);

    @Answer
    public boolean validMountainArray(int[] A) {
        final int n = A.length;
        int i = 1;
        while (i < n && A[i - 1] < A[i]) {
            i++;
        }
        if (i == 1 || i == n) {
            return false;
        }
        while (i < n && A[i - 1] > A[i]) {
            i++;
        }
        return i == n;
    }

}
