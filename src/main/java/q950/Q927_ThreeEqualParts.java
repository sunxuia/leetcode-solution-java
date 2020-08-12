package q950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 927. Three Equal Parts
 * https://leetcode.com/problems/three-equal-parts/
 *
 * Given an array A of 0s and 1s, divide the array into 3 non-empty parts such that all of these parts represent the
 * same binary value.
 *
 * If it is possible, return any [i, j] with i+1 < j, such that:
 *
 * A[0], A[1], ..., A[i] is the first part;
 * A[i+1], A[i+2], ..., A[j-1] is the second part, and
 * A[j], A[j+1], ..., A[A.length - 1] is the third part.
 * All three parts have equal binary value.
 *
 * If it is not possible, return [-1, -1].
 *
 * Note that the entire part is used when considering what binary value it represents.  For example, [1,1,0] represents
 * 6 in decimal, not 3.  Also, leading zeros are allowed, so [0,1,1] and [1,1] represent the same value.
 *
 * Example 1:
 *
 * Input: [1,0,1,0,1]
 * Output: [0,3]
 *
 * Example 2:
 *
 * Input: [1,1,0,1,1]
 * Output: [-1,-1]
 *
 * Note:
 *
 * 3 <= A.length <= 30000
 * A[i] == 0 or A[i] == 1
 */
@RunWith(LeetCodeRunner.class)
public class Q927_ThreeEqualParts {

    /**
     * 0 1 ... i  |  i+1 i+2 ... j-1  |  j j+1 ... n-1
     * 参考文档 https://www.cnblogs.com/grandyang/p/12107091.html
     * 通过统计1 的个数, 来去除无效的划分.
     */
    @Answer
    public int[] threeEqualParts(int[] A) {
        final int n = A.length;
        int one = 0;
        for (int i = 0; i < n; i++) {
            one += A[i];
        }
        if (one == 0) {
            return new int[]{0, n - 1};
        }
        if (one % 3 != 0) {
            return new int[]{-1, -1};
        }

        int divide = one / 3;
        one = 0;
        int start = 0, mid = 0, end = 0;
        for (int i = 0; i < n; i++) {
            if (A[i] == 0) {
                continue;
            }
            one++;
            if (one == 1) {
                start = i;
            }
            if (one == divide + 1) {
                mid = i;
            }
            if (one == 2 * divide + 1) {
                end = i;
                break;
            }
        }
        while (end < n && A[start] == A[mid] && A[mid] == A[end]) {
            start++;
            mid++;
            end++;
        }
        if (end == n) {
            return new int[]{start - 1, mid};
        }
        return new int[]{-1, -1};
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 0, 1, 0, 1}).expect(new int[]{0, 3});

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 1, 0, 1, 1}).expect(new int[]{-1, -1});

}
