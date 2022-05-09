package q1900;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1894. Find the Student that Will Replace the Chalk
 * https://leetcode.com/problems/find-the-student-that-will-replace-the-chalk/
 *
 * There are n students in a class numbered from 0 to n - 1. The teacher will give each student a problem starting with
 * the student number 0, then the student number 1, and so on until the teacher reaches the student number n - 1. After
 * that, the teacher will restart the process, starting with the student number 0 again.
 *
 * You are given a 0-indexed integer array chalk and an integer k. There are initially k pieces of chalk. When the
 * student number i is given a problem to solve, they will use chalk[i] pieces of chalk to solve that problem. However,
 * if the current number of chalk pieces is strictly less than chalk[i], then the student number i will be asked to
 * replace the chalk.
 *
 * Return the index of the student that will replace the chalk.
 *
 * Example 1:
 *
 * Input: chalk = [5,1,5], k = 22
 * Output: 0
 * Explanation: The students go in turns as follows:
 * - Student number 0 uses 5 chalk, so k = 17.
 * - Student number 1 uses 1 chalk, so k = 16.
 * - Student number 2 uses 5 chalk, so k = 11.
 * - Student number 0 uses 5 chalk, so k = 6.
 * - Student number 1 uses 1 chalk, so k = 5.
 * - Student number 2 uses 5 chalk, so k = 0.
 * Student number 0 does not have enough chalk, so they will have to replace it.
 *
 * Example 2:
 *
 * Input: chalk = [3,4,1,2], k = 25
 * Output: 1
 * Explanation: The students go in turns as follows:
 * - Student number 0 uses 3 chalk so k = 22.
 * - Student number 1 uses 4 chalk so k = 18.
 * - Student number 2 uses 1 chalk so k = 17.
 * - Student number 3 uses 2 chalk so k = 15.
 * - Student number 0 uses 3 chalk so k = 12.
 * - Student number 1 uses 4 chalk so k = 8.
 * - Student number 2 uses 1 chalk so k = 7.
 * - Student number 3 uses 2 chalk so k = 5.
 * - Student number 0 uses 3 chalk so k = 2.
 * Student number 1 does not have enough chalk, so they will have to replace it.
 *
 * Constraints:
 *
 * chalk.length == n
 * 1 <= n <= 10^5
 * 1 <= chalk[i] <= 10^5
 * 1 <= k <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1894_FindTheStudentThatWillReplaceTheChalk {

    @Answer
    public int chalkReplacer(int[] chalk, int k) {
        final int n = chalk.length;
        long[] sums = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + chalk[i];
        }
        k %= sums[n];
        int idx = Arrays.binarySearch(sums, k);
        return idx >= 0 ? idx : -idx - 2;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{5, 1, 5}, 22)
            .expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{3, 4, 1, 2}, 25)
            .expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(TestDataFileHelper.read(int[].class), 539095482)
            .expect(10737);

}
