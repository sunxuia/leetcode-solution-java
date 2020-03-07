package q450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/arithmetic-slices/
 *
 * A sequence of number is called arithmetic if it consists of at least three elements and if the difference between
 * any two consecutive elements is the same.
 *
 * For example, these are arithmetic sequence:
 *
 * 1, 3, 5, 7, 9
 * 7, 7, 7, 7
 * 3, -1, -5, -9
 *
 * The following sequence is not arithmetic.
 *
 * 1, 1, 2, 5, 7
 *
 *
 * A zero-indexed array A consisting of N numbers is given. A slice of that array is any pair of integers (P, Q) such
 * that 0 <= P < Q < N.
 *
 * A slice (P, Q) of array A is called arithmetic if the sequence:
 * A[P], A[p + 1], ..., A[Q - 1], A[Q] is arithmetic. In particular, this means that P + 1 < Q.
 *
 * The function should return the number of arithmetic slices in the array A.
 *
 * Example:
 *
 * A = [1, 2, 3, 4]
 *
 * return: 3, for 3 arithmetic slices in A: [1, 2, 3], [2, 3, 4] and [1, 2, 3, 4] itself.
 *
 * 下一题 {@link Q446_ArithmeticSlicesIISubsequence}
 */
@RunWith(LeetCodeRunner.class)
public class Q413_ArithmeticSlices {

    @Answer
    public int numberOfArithmeticSlices(int[] A) {
        // count 表示连续序列的数量.
        int res = 0, count = 2;
        for (int i = 2; i < A.length; i++) {
            if (A[i] - A[i - 1] == A[i - 1] - A[i - 2]) {
                // 是一个连续序列
                count++;
            } else {
                // 不是连续序列.
                // 计算可以组合出来的序列组合的数量. (3元素组合, 4元素组合, ..., count 元素组合)
                res += (count - 2) * (count - 1) / 2;
                count = 2;
            }
        }
        res += (count - 2) * (count - 1) / 2;
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{1, 2, 3, 4}).expect(3);

    @TestData
    public DataExpectation border = DataExpectation.create(new int[0]).expect(0);

}
