package q1000;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 989. Add to Array-Form of Integer
 * https://leetcode.com/problems/add-to-array-form-of-integer/
 *
 * For a non-negative integer X, the array-form of X is an array of its digits in left to right order.  For example, if
 * X = 1231, then the array form is [1,2,3,1].
 *
 * Given the array-form A of a non-negative integer X, return the array-form of the integer X+K.
 *
 * Example 1:
 *
 * Input: A = [1,2,0,0], K = 34
 * Output: [1,2,3,4]
 * Explanation: 1200 + 34 = 1234
 *
 * Example 2:
 *
 * Input: A = [2,7,4], K = 181
 * Output: [4,5,5]
 * Explanation: 274 + 181 = 455
 *
 * Example 3:
 *
 * Input: A = [2,1,5], K = 806
 * Output: [1,0,2,1]
 * Explanation: 215 + 806 = 1021
 *
 * Example 4:
 *
 * Input: A = [9,9,9,9,9,9,9,9,9,9], K = 1
 * Output: [1,0,0,0,0,0,0,0,0,0,0]
 * Explanation: 9999999999 + 1 = 10000000000
 *
 * Note:
 *
 * 1 <= A.length <= 10000
 * 0 <= A[i] <= 9
 * 0 <= K <= 10000
 * If A.length > 1, then A[0] != 0
 */
@RunWith(LeetCodeRunner.class)
public class Q989_AddToArrayFormOfInteger {

    @Answer
    public List<Integer> addToArrayForm(int[] A, int K) {
        LinkedList<Integer> res = new LinkedList<>();
        for (int i = A.length - 1; i >= 0; i--) {
            res.addFirst((A[i] + K) % 10);
            K = (A[i] + K) / 10;
        }
        while (K > 0) {
            res.addFirst(K % 10);
            K /= 10;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 0, 0}, 34)
            .expect(Arrays.asList(1, 2, 3, 4));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{2, 7, 4}, 181)
            .expect(Arrays.asList(4, 5, 5));

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{2, 1, 5}, 806)
            .expect(Arrays.asList(1, 0, 2, 1));

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 9}, 1)
            .expect(Arrays.asList(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));


    @TestData
    public DataExpectation border = DataExpectation.createWith(new int[]{0}, 23).expect(Arrays.asList(2, 3));

}
