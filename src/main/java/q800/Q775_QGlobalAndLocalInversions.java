package q800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/global-and-local-inversions/
 *
 * We have some permutation A of [0, 1, ..., N - 1], where N is the length of A.
 *
 * The number of (global) inversions is the number of i < j with 0 <= i < j < N and A[i] > A[j].
 *
 * The number of local inversions is the number of i with 0 <= i < N and A[i] > A[i+1].
 *
 * Return true if and only if the number of global inversions is equal to the number of local inversions.
 *
 * Example 1:
 *
 * Input: A = [1,0,2]
 * Output: true
 * Explanation: There is 1 global inversion, and 1 local inversion.
 *
 * Example 2:
 *
 * Input: A = [1,2,0]
 * Output: false
 * Explanation: There are 2 global inversions, and 1 local inversion.
 *
 * Note:
 *
 * A will be a permutation of [0, 1, ..., A.length - 1].
 * A will have length in range [1, 5000].
 * The time limit for this problem has been reduced.
 */
@RunWith(LeetCodeRunner.class)
public class Q775_QGlobalAndLocalInversions {

    /**
     * https://www.cnblogs.com/grandyang/p/8983098.html
     *
     * 局部倒置是全局倒置的一种特殊情况, 局部倒置一定是全局倒置, 而全局倒置不一定是局部倒置.
     * 如果所有的倒置都是局部倒置, 那么由于局部倒置一定是全局倒置, 则二者个数一定相等.
     * 所以如果出现某个全局倒置不是局部倒置的情况, 则数量就不会相等了.
     */
    @Answer
    public boolean isIdealPermutation(int[] A) {
        int max = -1;
        for (int i = 0; i < A.length - 2; ++i) {
            max = Math.max(max, A[i]);
            // 非局部倒置的全局倒置情况
            if (max > A[i + 2]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 上面链接中的一种更快的做法.
     * 如果乱序数组中某个数字和其坐标差的绝对值大于1的话, 那么一定是有非局部倒置的全局倒置的存在.
     * 详情参见链接.
     */
    @Answer
    public boolean isIdealPermutation2(int[] A) {
        for (int i = 0; i < A.length; i++) {
            if (Math.abs(A[i] - i) > 1) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 0, 2}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 0}).expect(false);

}
