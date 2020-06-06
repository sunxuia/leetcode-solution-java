package q850;

import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/split-array-with-same-average/
 *
 * In a given integer array A, we must move every element of A to either list B or list C. (B and C initially start
 * empty.)
 *
 * Return true if and only if after such a move, it is possible that the average value of B is equal to the average
 * value of C, and B and C are both non-empty.
 *
 * Example :
 * Input:
 * [1,2,3,4,5,6,7,8]
 * Output: true
 * Explanation: We can split the array into [1,4,5,8] and [2,3,6,7], and both of them have the average of 4.5.
 *
 * Note:
 *
 * The length of A will be in the range [1, 30].
 * A[i] will be in the range of [0, 10000].
 */
@RunWith(LeetCodeRunner.class)
public class Q805_SplitArrayWithSameAverage {

    // https://www.cnblogs.com/grandyang/p/10285531.html
    @Answer
    public boolean splitArraySameAverage(int[] A) {
        final int n = A.length;
        int sum = 0;
        for (int i : A) {
            sum += i;
        }

        boolean possible = false;
        for (int i = 1; i <= n / 2 && !possible; ++i) {
            possible = sum * i % n == 0;
        }
        if (!possible) {
            return false;
        }

        Set<Integer>[] dp = new Set[n / 2 + 1];
        for (int i = 0; i < dp.length; i++) {
            dp[i] = new HashSet<>();
        }

        dp[0].add(0);
        for (int num : A) {
            for (int i = n / 2; i >= 1; --i) {
                for (int a : dp[i - 1]) {
                    dp[i].add(a + num);
                }
            }
        }

        for (int i = 1; i <= n / 2; ++i) {
            if (sum * i % n == 0 && dp[i].contains(sum * i / n)) {
                return true;
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{1, 2, 3, 4, 5, 6, 7, 8}).expect(true);

    @TestData
    public DataExpectation border = DataExpectation.create(new int[]{0}).expect(false);

    @TestData
    public DataExpectation overTime = DataExpectation.create(new int[]{
            60, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
            30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30
    }).expect(false);

}
