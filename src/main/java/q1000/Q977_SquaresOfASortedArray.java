package q1000;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 977. Squares of a Sorted Array
 * https://leetcode.com/problems/squares-of-a-sorted-array/
 *
 * Given an array of integers A sorted in non-decreasing order, return an array of the squares of each number, also in
 * sorted non-decreasing order.
 *
 * Example 1:
 *
 * Input: [-4,-1,0,3,10]
 * Output: [0,1,9,16,100]
 *
 * Example 2:
 *
 * Input: [-7,-3,2,3,11]
 * Output: [4,9,9,49,121]
 *
 * Note:
 *
 * 1 <= A.length <= 10000
 * -10000 <= A[i] <= 10000
 * A is sorted in non-decreasing order.
 */
@RunWith(LeetCodeRunner.class)
public class Q977_SquaresOfASortedArray {

    @Answer
    public int[] sortedSquares(int[] A) {
        int[] res = new int[A.length];
        int next = 0;
        int zeroIndex = Arrays.binarySearch(A, 0);
        int left = zeroIndex >= 0 ? zeroIndex : -zeroIndex - 2;
        int right = zeroIndex >= 0 ? zeroIndex + 1 : -zeroIndex - 1;
        while (0 <= left || right < A.length) {
            int leftSquare = 0 <= left ? A[left] * A[left] : Integer.MAX_VALUE;
            int rightSquare = right < A.length ? A[right] * A[right] : Integer.MAX_VALUE;
            if (leftSquare < rightSquare) {
                res[next++] = leftSquare;
                left--;
            } else {
                res[next++] = rightSquare;
                right++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[]{-4, -1, 0, 3, 10})
            .expect(new int[]{0, 1, 9, 16, 100});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[]{-7, -3, 2, 3, 11})
            .expect(new int[]{4, 9, 9, 49, 121});

}
