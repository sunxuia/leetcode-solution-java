package q1100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1053. Previous Permutation With One Swap
 * https://leetcode.com/problems/previous-permutation-with-one-swap/
 *
 * Given an array A of positive integers (not necessarily distinct), return the lexicographically largest permutation
 * that is smaller than A, that can be made with one swap (A swap exchanges the positions of two numbers A[i] and A[j]).
 * If it cannot be done, then return the same array.
 *
 * Example 1:
 *
 * Input: [3,2,1]
 * Output: [3,1,2]
 * Explanation: Swapping 2 and 1.
 *
 * Example 2:
 *
 * Input: [1,1,5]
 * Output: [1,1,5]
 * Explanation: This is already the smallest permutation.
 *
 * Example 3:
 *
 * Input: [1,9,4,6,7]
 * Output: [1,7,4,6,9]
 * Explanation: Swapping 9 and 7.
 *
 * Example 4:
 *
 * Input: [3,1,1,3]
 * Output: [1,3,1,3]
 * Explanation: Swapping 1 and 3.
 *
 * Note:
 *
 * 1 <= A.length <= 10000
 * 1 <= A[i] <= 10000
 */
@RunWith(LeetCodeRunner.class)
public class Q1053_PreviousPermutationWithOneSwap {

    /**
     * 找规律的题.
     */
    @Answer
    public int[] prevPermOpt1(int[] A) {
        for (int i = A.length - 2; i >= 0; i--) {
            if (A[i] > A[i + 1]) {
                for (int j = A.length - 1; j > i; j--) {
                    if (A[i] > A[j] && A[j - 1] != A[j]) {
                        int t = A[i];
                        A[i] = A[j];
                        A[j] = t;
                        return A;
                    }
                }
            }
        }
        return A;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 2, 1}).expect(new int[]{3, 1, 2});

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 1, 5}).expect(new int[]{1, 1, 5});

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 9, 4, 6, 7}).expect(new int[]{1, 7, 4, 6, 9});

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{3, 1, 1, 3}).expect(new int[]{1, 3, 1, 3});

}
