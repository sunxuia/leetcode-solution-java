package q1350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1346. Check If N and Its Double Exist
 * https://leetcode.com/problems/check-if-n-and-its-double-exist/
 *
 * Given an array arr of integers, check if there exists two integers N and M such that N is the double of M ( i.e. N =
 * 2 * M).
 *
 * More formally check if there exists two indices i and j such that :
 *
 * i != j
 * 0 <= i, j < arr.length
 * arr[i] == 2 * arr[j]
 *
 * Example 1:
 *
 * Input: arr = [10,2,5,3]
 * Output: true
 * Explanation: N = 10 is the double of M = 5,that is, 10 = 2 * 5.
 *
 * Example 2:
 *
 * Input: arr = [7,1,14,11]
 * Output: true
 * Explanation: N = 14 is the double of M = 7,that is, 14 = 2 * 7.
 *
 * Example 3:
 *
 * Input: arr = [3,1,7,11]
 * Output: false
 * Explanation: In this case does not exist N and M, such that N = 2 * M.
 *
 * Constraints:
 *
 * 2 <= arr.length <= 500
 * -10^3 <= arr[i] <= 10^3
 */
@RunWith(LeetCodeRunner.class)
public class Q1346_CheckIfNAndItsDoubleExist {

    @Answer
    public boolean checkIfExist(int[] arr) {
        final int n = 1000;
        boolean[] exists = new boolean[2 * n + 1];
        for (int num : arr) {
            if (num % 2 == 0 && exists[num / 2 + n]
                    || -n <= num * 2 && num * 2 <= n && exists[num * 2 + n]) {
                return true;
            }
            exists[num + n] = true;
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{10, 2, 5, 3}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{7, 1, 14, 11}).expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{3, 1, 7, 11}).expect(false);

}
