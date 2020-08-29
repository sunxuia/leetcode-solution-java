package q1050;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1005. Maximize Sum Of Array After K Negations
 * https://leetcode.com/problems/maximize-sum-of-array-after-k-negations/
 *
 * Given an array A of integers, we must modify the array in the following way: we choose an i and replace A[i] with
 * -A[i], and we repeat this process K times in total.  (We may choose the same index i multiple times.)
 *
 * Return the largest possible sum of the array after modifying it in this way.
 *
 * Example 1:
 *
 * Input: A = [4,2,3], K = 1
 * Output: 5
 * Explanation: Choose indices (1,) and A becomes [4,-2,3].
 *
 * Example 2:
 *
 * Input: A = [3,-1,0,2], K = 3
 * Output: 6
 * Explanation: Choose indices (1, 2, 2) and A becomes [3,1,0,2].
 *
 * Example 3:
 *
 * Input: A = [2,-3,-1,5,-4], K = 2
 * Output: 13
 * Explanation: Choose indices (1, 4) and A becomes [2,3,-1,5,4].
 *
 * Note:
 *
 * 1 <= A.length <= 10000
 * 1 <= K <= 10000
 * -100 <= A[i] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1005_MaximizeSumOfArrayAfterKNegations {

    @Answer
    public int largestSumAfterKNegations(int[] A, int K) {
        final int n = A.length;
        Arrays.sort(A);
        int i = 0;
        while (i < n && A[i] < 0 && K > 0) {
            A[i] = -A[i];
            K--;
            i++;
        }
        int min = 0;
        if (K % 2 > 0) {
            min = A[i == n ? n - 1 : i];
            if (i > 0) {
                min = Math.min(A[i - 1], min);
            }
            if (i < n - 1) {
                min = Math.min(A[i + 1], min);
            }
        }
        return Arrays.stream(A).sum() - 2 * min;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{4, 2, 3}, 1).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{3, -1, 0, 2}, 3).expect(6);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{2, -3, -1, 5, -4}, 2).expect(13);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{-8, 3, -5, -3, -5, -2}, 6).expect(22);

}
