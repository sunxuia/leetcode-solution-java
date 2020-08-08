package q950;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 910. Smallest Range II
 * https://leetcode.com/problems/smallest-range-ii/
 *
 * Given an array A of integers, for each integer A[i] we need to choose either x = -K or x = K, and add x to A[i] (only
 * once).
 *
 * After this process, we have some array B.
 *
 * Return the smallest possible difference between the maximum value of B and the minimum value of B.
 *
 * Example 1:
 *
 * Input: A = [1], K = 0
 * Output: 0
 * Explanation: B = [1]
 *
 * Example 2:
 *
 * Input: A = [0,10], K = 2
 * Output: 6
 * Explanation: B = [2,8]
 *
 * Example 3:
 *
 * Input: A = [1,3,6], K = 3
 * Output: 3
 * Explanation: B = [4,6,3]
 *
 * Note:
 *
 * 1 <= A.length <= 10000
 * 0 <= A[i] <= 10000
 * 0 <= K <= 10000
 *
 * 上一题 {@link Q908_SmallestRangeI} 和上一题相比这题只能且必须选择 +K 或 -K
 */
@RunWith(LeetCodeRunner.class)
public class Q910_SmallestRangeII {

    // https://www.cnblogs.com/grandyang/p/11361245.html
    @Answer
    public int smallestRangeII(int[] A, int K) {
        final int n = A.length;
        Arrays.sort(A);
        int res = A[n - 1] - A[0];
        int left = A[0] + K, right = A[n - 1] - K;
        for (int i = 0; i < n - 1; i++) {
            int high = Math.max(right, A[i] + K);
            int low = Math.min(left, A[i + 1] - K);
            res = Math.min(res, high - low);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1}, 0).expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{0, 10}, 2).expect(6);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{1, 3, 6}, 3).expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{2, 7, 2}, 1).expect(3);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(new int[]{7, 8, 8}, 5).expect(1);

    @TestData
    public DataExpectation normal3 = DataExpectation.createWith(new int[]{9, 10, 0, 7}, 1).expect(8);

    @TestData
    public DataExpectation normal4 = DataExpectation.createWith(new int[]{5, 6, 4}, 5).expect(2);

    @TestData
    public DataExpectation normal5 = DataExpectation.createWith(new int[]{1, 6, 6, 9}, 2).expect(4);

}
