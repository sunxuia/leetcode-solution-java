package q900;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 891. Sum of Subsequence Widths
 * https://leetcode.com/problems/sum-of-subsequence-widths/
 *
 * Given an array of integers A, consider all non-empty subsequences of A.
 *
 * For any sequence S, let the width of S be the difference between the maximum and minimum element of S.
 *
 * Return the sum of the widths of all subsequences of A.
 *
 * As the answer may be very large, return the answer modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: [2,1,3]
 * Output: 6
 * Explanation:
 * Subsequences are [1], [2], [3], [2,1], [2,3], [1,3], [2,1,3].
 * The corresponding widths are 0, 0, 0, 1, 1, 2, 2.
 * The sum of these widths is 6.
 *
 * Note:
 *
 * 1 <= A.length <= 20000
 * 1 <= A[i] <= 20000
 */
@RunWith(LeetCodeRunner.class)
public class Q891_SumOfSubsequenceWidths {

    /**
     * 参考文档 https://www.cnblogs.com/grandyang/p/11020261.html
     */
    @Answer
    public int sumSubseqWidths(int[] A) {
        final int n = A.length;
        long res = 0, c = 1;
        Arrays.sort(A);
        for (int i = 0; i < n; i++) {
            res = (res + A[i] * c - A[n - i - 1] * c) % 10_0000_0007;
            c = (c << 1) % 10_0000_0007;
        }
        return (int) res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{2, 1, 3}).expect(6);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{7, 8, 8, 10, 4}).expect(96);

}
