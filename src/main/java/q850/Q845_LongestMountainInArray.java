package q850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/longest-mountain-in-array/
 *
 * Let's call any (contiguous) subarray B (of A) a mountain if the following properties hold:
 *
 * B.length >= 3
 * There exists some 0 < i < B.length - 1 such that B[0] < B[1] < ... B[i-1] < B[i] > B[i+1] > ... > B[B.length - 1]
 *
 * (Note that B could be any subarray of A, including the entire array A.)
 *
 * Given an array A of integers, return the length of the longest mountain.
 *
 * Return 0 if there is no mountain.
 *
 * Example 1:
 *
 * Input: [2,1,4,7,3,2,5]
 * Output: 5
 * Explanation: The largest mountain is [1,4,7,3,2] which has length 5.
 *
 * Example 2:
 *
 * Input: [2,2,2]
 * Output: 0
 * Explanation: There is no mountain.
 *
 * Note:
 *
 * 0 <= A.length <= 10000
 * 0 <= A[i] <= 10000
 *
 * Follow up:
 *
 * Can you solve it using only one pass?
 * Can you solve it in O(1) space?
 */
@RunWith(LeetCodeRunner.class)
public class Q845_LongestMountainInArray {

    @Answer
    public int longestMountain(int[] A) {
        final int n = A.length;
        int res = 0, i = 0;
        while (i < n - 2) {
            // 平地或下坡
            while (i < n - 1 && A[i] >= A[i + 1]) {
                i++;
            }
            // 谷底
            int start = i;
            // 上坡
            while (i < n - 1 && A[i] < A[i + 1]) {
                i++;
            }
            // 山峰
            int peek = i;
            // 下坡
            while (i < n - 1 && A[i] > A[i + 1]) {
                i++;
            }
            // 有下坡才是山
            if (peek < i) {
                res = Math.max(res, i - start + 1);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 1, 4, 7, 3, 2, 5}).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 2, 2}).expect(0);

}
