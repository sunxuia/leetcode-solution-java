package q950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 908. Smallest Range I
 * https://leetcode.com/problems/smallest-range-i/
 *
 * Given an array A of integers, for each integer A[i] we may choose any x with -K <= x <= K, and add x to A[i].
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
 * Output: 0
 * Explanation: B = [3,3,3] or B = [4,4,4]
 *
 * Note:
 *
 * 1 <= A.length <= 10000
 * 0 <= A[i] <= 10000
 * 0 <= K <= 10000
 *
 * 下一题 {@link q950.Q910_SmallestRangeII}
 */
@RunWith(LeetCodeRunner.class)
public class Q908_SmallestRangeI {

    @Answer
    public int smallestRangeI(int[] A, int K) {
        int max = A[0], min = A[0];
        for (int a : A) {
            max = Math.max(max, a);
            min = Math.min(min, a);
        }
        return max - min < 2 * K ? 0 : max - min - 2 * K;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1}, 0).expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{0, 10}, 2).expect(6);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{1, 3, 6}, 3).expect(0);

}
