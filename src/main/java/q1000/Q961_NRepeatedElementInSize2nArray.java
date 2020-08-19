package q1000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 961. N-Repeated Element in Size 2N Array
 * https://leetcode.com/problems/n-repeated-element-in-size-2n-array/
 *
 * In a array A of size 2N, there are N+1 unique elements, and exactly one of these elements is repeated N times.
 *
 * Return the element repeated N times.
 *
 * Example 1:
 *
 * Input: [1,2,3,3]
 * Output: 3
 *
 * Example 2:
 *
 * Input: [2,1,2,5,3,2]
 * Output: 2
 *
 * Example 3:
 *
 * Input: [5,1,5,2,5,3,5,4]
 * Output: 5
 *
 * Note:
 *
 * 4 <= A.length <= 10000
 * 0 <= A[i] < 10000
 * A.length is even
 */
@RunWith(LeetCodeRunner.class)
public class Q961_NRepeatedElementInSize2nArray {

    /**
     * 因为所求数字的数量为A 长度的一般, 所以一定会存在连续的3 个数字中存在2 个相同的数字.
     */
    @Answer
    public int repeatedNTimes(int[] A) {
        for (int i = 2; i < A.length; i++) {
            if (A[i - 2] == A[i] || A[i - 1] == A[i]) {
                return A[i];
            }
        }
        return A[0];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 3}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 1, 2, 5, 3, 2}).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{5, 1, 5, 2, 5, 3, 5, 4}).expect(5);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{9, 5, 6, 9}).expect(9);

}
