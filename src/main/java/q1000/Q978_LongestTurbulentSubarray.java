package q1000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 978. Longest Turbulent Subarray
 * https://leetcode.com/problems/longest-turbulent-subarray/
 *
 * A subarray A[i], A[i+1], ..., A[j] of A is said to be turbulent if and only if:
 *
 * For i <= k < j, A[k] > A[k+1] when k is odd, and A[k] < A[k+1] when k is even;
 * OR, for i <= k < j, A[k] > A[k+1] when k is even, and A[k] < A[k+1] when k is odd.
 *
 * That is, the subarray is turbulent if the comparison sign flips between each adjacent pair of elements in the
 * subarray.
 *
 * Return the length of a maximum size turbulent subarray of A.
 *
 * Example 1:
 *
 * Input: [9,4,2,10,7,8,8,1,9]
 * Output: 5
 * Explanation: (A[1] > A[2] < A[3] > A[4] < A[5])
 *
 * Example 2:
 *
 * Input: [4,8,12,16]
 * Output: 2
 *
 * Example 3:
 *
 * Input: [100]
 * Output: 1
 *
 * Note:
 *
 * 1 <= A.length <= 40000
 * 0 <= A[i] <= 10^9
 *
 * 题解: 就是找出最长的 "(高)低高低...高低(高)" 这样的起伏序列.
 */
@RunWith(LeetCodeRunner.class)
public class Q978_LongestTurbulentSubarray {

    @Answer
    public int maxTurbulenceSize(int[] A) {
        // state 状态:
        // -1 : 前面是上升, 这次应该下降 A[i-2] < A[i-1] ? A[i]
        // 0 : 前面是相等的, 这次不知道起伏 A[i-2] == A[i-1] ? A[i]
        // 1 : 前面是下降, 这次应该上升 A[i-2] > A[i-1] ? A[i]
        int state = 0;
        int res = 0, prev = A[0], count = 0;
        for (int num : A) {
            if (prev == num) {
                count = 1;
                state = 0;
            } else if (prev > num) {
                count = state == 1 ? 2 : count + 1;
                state = 1;
            } else {
                count = state == -1 ? 2 : count + 1;
                state = -1;
            }
            res = Math.max(res, count);
            prev = num;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{9, 4, 2, 10, 7, 8, 8, 1, 9}).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{4, 8, 12, 16}).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{100}).expect(1);

}
