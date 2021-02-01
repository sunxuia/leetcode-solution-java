package q1700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1652. Defuse the Bomb
 * https://leetcode.com/problems/defuse-the-bomb/
 *
 * You have a bomb to defuse, and your time is running out! Your informer will provide you with a circular array code of
 * length of n and a key k.
 *
 * To decrypt the code, you must replace every number. All the numbers are replaced simultaneously.
 *
 * If k > 0, replace the ith number with the sum of the next k numbers.
 * If k < 0, replace the ith number with the sum of the previous k numbers.
 * If k == 0, replace the ith number with 0.
 *
 * As code is circular, the next element of code[n-1] is code[0], and the previous element of code[0] is code[n-1].
 *
 * Given the circular array code and an integer key k, return the decrypted code to defuse the bomb!
 *
 * Example 1:
 *
 * Input: code = [5,7,1,4], k = 3
 * Output: [12,10,16,13]
 * Explanation: Each number is replaced by the sum of the next 3 numbers. The decrypted code is [7+1+4, 1+4+5, 4+5+7,
 * 5+7+1]. Notice that the numbers wrap around.
 *
 * Example 2:
 *
 * Input: code = [1,2,3,4], k = 0
 * Output: [0,0,0,0]
 * Explanation: When k is zero, the numbers are replaced by 0.
 *
 * Example 3:
 *
 * Input: code = [2,4,9,3], k = -2
 * Output: [12,5,6,13]
 * Explanation: The decrypted code is [3+9, 2+3, 4+2, 9+4]. Notice that the numbers wrap around again. If k is negative,
 * the sum is of the previous numbers.
 *
 * Constraints:
 *
 * n == code.length
 * 1 <= n <= 100
 * 1 <= code[i] <= 100
 * -(n - 1) <= k <= n - 1
 */
@RunWith(LeetCodeRunner.class)
public class Q1652_DefuseTheBomb {

    @Answer
    public int[] decrypt(int[] code, int k) {
        final int n = code.length;
        int[] res = new int[n];
        if (k == 0) {
            return res;
        }
        int[] sums = new int[2 * n + 1];
        for (int i = 0; i < 2 * n; i++) {
            sums[i + 1] = sums[i] + code[i % n];
        }
        if (k > 0) {
            for (int i = 0; i < n; i++) {
                res[i] = sums[i + 1 + k] - sums[i + 1];
            }
        } else {
            for (int i = 0; i < n; i++) {
                res[i] = sums[n + i] - sums[n + i + k];
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{5, 7, 1, 4}, 3)
            .expect(new int[]{12, 10, 16, 13});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4}, 0)
            .expect(new int[]{0, 0, 0, 0});

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{2, 4, 9, 3}, -2)
            .expect(new int[]{12, 5, 6, 13});

}
