package q1000;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 960. Delete Columns to Make Sorted III
 * https://leetcode.com/problems/delete-columns-to-make-sorted-iii/
 *
 * We are given an array A of N lowercase letter strings, all of the same length.
 *
 * Now, we may choose any set of deletion indices, and for each string, we delete all the characters in those indices.
 *
 * For example, if we have an array A = ["babca","bbazb"] and deletion indices {0, 1, 4}, then the final array after
 * deletions is ["bc","az"].
 *
 * Suppose we chose a set of deletion indices D such that after deletions, the final array has every element (row) in
 * lexicographic order.
 *
 * For clarity, A[0] is in lexicographic order (ie. A[0][0] <= A[0][1] <= ... <= A[0][A[0].length - 1]), A[1] is in
 * lexicographic order (ie. A[1][0] <= A[1][1] <= ... <= A[1][A[1].length - 1]), and so on.
 *
 * Return the minimum possible value of D.length.
 *
 * Example 1:
 *
 * Input: ["babca","bbazb"]
 * Output: 3
 * Explanation: After deleting columns 0, 1, and 4, the final array is A = ["bc", "az"].
 * Both these rows are individually in lexicographic order (ie. A[0][0] <= A[0][1] and A[1][0] <= A[1][1]).
 * Note that A[0] > A[1] - the array A isn't necessarily in lexicographic order.
 *
 * Example 2:
 *
 * Input: ["edcba"]
 * Output: 4
 * Explanation: If we delete less than 4 columns, the only row won't be lexicographically sorted.
 *
 * Example 3:
 *
 * Input: ["ghi","def","abc"]
 * Output: 0
 * Explanation: All rows are already lexicographically sorted.
 *
 * Note:
 *
 * 1 <= A.length <= 100
 * 1 <= A[i].length <= 100
 *
 * 上一题 {@link Q955_DeleteColumnsToMakeSortedII}
 * 相比上一题, 本题要求每行字符串中的字符递增排列.
 */
@RunWith(LeetCodeRunner.class)
public class Q960_DeleteColumnsToMakeSortedIII {

    /**
     * 参考Solution 中的dp 解法.
     */
    @Answer
    public int minDeletionSize(String[] A) {
        final int n = A[0].length();
        // dp[i] 表示对[0, i] 列的字符, 总共需要保留的列数
        int[] dp = new int[n];
        // 初始化为1, 只保留自己所在1 列
        Arrays.fill(dp, 1);

        int res = n - 1;
        for (int i = 0; i < n; i++) {
            // 求在保留第i 列的情况下, [0, i] 能够保留的最大列数
            loop:
            for (int k = 0; k < i; k++) {
                // [0, k] 之间的字符都要 <= i 的字符, 这样才能是递增的.
                for (String a : A) {
                    if (a.charAt(k) > a.charAt(i)) {
                        continue loop;
                    }
                }
                // (k, i) 中间的列不符合条件.
                dp[i] = Math.max(dp[i], dp[k] + 1);
                res = Math.min(res, n - dp[i]);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new String[]{"babca", "bbazb"}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new String[]{"edcba"}).expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new String[]{"ghi", "def", "abc"}).expect(0);

}
