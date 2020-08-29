package q1050;

import org.junit.runner.RunWith;
import q500.Q485_MaxConsecutiveOnes;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1004. Max Consecutive Ones III
 * https://leetcode.com/problems/max-consecutive-ones-iii/
 *
 * Given an array A of 0s and 1s, we may change up to K values from 0 to 1.
 *
 * Return the length of the longest (contiguous) subarray that contains only 1s.
 *
 * Example 1:
 *
 * Input: A = [1,1,1,0,0,0,1,1,1,1,0], K = 2
 * Output: 6
 * Explanation:
 * [1,1,1,0,0,1,1,1,1,1,1]
 * Bolded numbers were flipped from 0 to 1.  The longest subarray is underlined.
 *
 * Example 2:
 *
 * Input: A = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], K = 3
 * Output: 10
 * Explanation:
 * [0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1]
 * Bolded numbers were flipped from 0 to 1.  The longest subarray is underlined.
 *
 * Note:
 *
 * 1 <= A.length <= 20000
 * 0 <= K <= A.length
 * A[i] is 0 or 1
 *
 * 上一题 {@link Q485_MaxConsecutiveOnes}
 */
@RunWith(LeetCodeRunner.class)
public class Q1004_MaxConsecutiveOnesIII {

    @Answer
    public int longestOnes(int[] A, int K) {
        final int n = A.length;
        int res = 0;
        int left = 0, right = -1, zero = 0;
        while (right < n) {
            while (++right < n) {
                if (A[right] == 0 && ++zero > K) {
                    break;
                }
            }
            res = Math.max(res, right - left);
            while (zero > K) {
                zero -= 1 - A[left++];
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}, 2)
            .expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1}, 3)
            .expect(10);

}
