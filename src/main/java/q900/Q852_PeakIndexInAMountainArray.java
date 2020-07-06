package q900;

import org.junit.runner.RunWith;
import q850.Q845_LongestMountainInArray;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/peak-index-in-a-mountain-array/
 *
 * Let's call an array A a mountain if the following properties hold:
 *
 * A.length >= 3
 * There exists some 0 < i < A.length - 1 such that A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1]
 *
 * Given an array that is definitely a mountain, return any i such that A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ..
 * . > A[A.length - 1].
 *
 * Example 1:
 *
 * Input: [0,1,0]
 * Output: 1
 *
 * Example 2:
 *
 * Input: [0,2,1,0]
 * Output: 1
 *
 * Note:
 *
 * 3 <= A.length <= 10000
 * 0 <= A[i] <= 10^6
 * A is a mountain, as defined above.
 *
 * 类似题目 {@link Q845_LongestMountainInArray}
 */
@RunWith(LeetCodeRunner.class)
public class Q852_PeakIndexInAMountainArray {

    // 题目中的条件限定了元素不会重复
    @Answer
    public int peakIndexInMountainArray(int[] A) {
        int start = 0, end = A.length - 1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (A[mid] < A[mid + 1]) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return end;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{0, 1, 0}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{0, 2, 1, 0}).expect(1);

}
