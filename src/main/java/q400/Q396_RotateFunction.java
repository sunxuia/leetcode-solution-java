package q400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/rotate-function/
 *
 * Given an array of integers A and let n to be its length.
 *
 * Assume Bk to be an array obtained by rotating the array A k positions clock-wise, we define a "rotation function"
 * F on A as follow:
 *
 * F(k) = 0 * Bk[0] + 1 * Bk[1] + ... + (n-1) * Bk[n-1].
 *
 * Calculate the maximum value of F(0), F(1), ..., F(n-1).
 *
 * Note:
 * n is guaranteed to be less than 105.
 *
 * Example:
 *
 * A = [4, 3, 2, 6]
 *
 * F(0) = (0 * 4) + (1 * 3) + (2 * 2) + (3 * 6) = 0 + 3 + 4 + 18 = 25
 * F(1) = (0 * 6) + (1 * 4) + (2 * 3) + (3 * 2) = 0 + 4 + 6 + 6 = 16
 * F(2) = (0 * 2) + (1 * 6) + (2 * 4) + (3 * 3) = 0 + 6 + 8 + 9 = 23
 * F(3) = (0 * 3) + (1 * 2) + (2 * 6) + (3 * 4) = 0 + 2 + 12 + 12 = 26
 *
 * So the maximum value of F(0), F(1), F(2), F(3) is F(3) = 26.
 */
@RunWith(LeetCodeRunner.class)
public class Q396_RotateFunction {

    // 按照题意的方式, 可以通过, 就是很慢
    @Answer
    public int maxRotateFunction(int[] A) {
        if (A.length < 2) {
            return 0;
        }
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < A.length; i++) {
            int sum = 0;
            for (int j = 0; j < A.length; j++) {
                sum += j * A[(i + j) % A.length];
            }
            res = Math.max(res, sum);
        }
        return res;
    }

    // LeetCode 上最快的方式. 找到了 F(k) 到F(k+1) 的变换方程
    @Answer
    public int maxRotateFunction2(int[] A) {
        final int length = A.length;
        int sum = 0; // 数字之和
        int f0 = 0; // F(0) 的结果
        for (int i = 0; i < length; i++) {
            sum += A[i];
            f0 += i * A[i];
        }
        int res = f0;
        for (int k = 1, fPrev = f0; k < length; k++) {
            // F(k) = F(k-1) + sum - length * <F(k-1) 的最后一个数字>
            int fk = fPrev + sum - length * A[length - k];
            res = Math.max(res, fk);
            fPrev = fk;
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{4, 3, 2, 6}).expect(26);

    @TestData
    public DataExpectation border1 = DataExpectation.create(new int[0]).expect(0);

    @TestData
    public DataExpectation border2 = DataExpectation
            .create(new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE})
            .expect(Integer.MIN_VALUE);

}
