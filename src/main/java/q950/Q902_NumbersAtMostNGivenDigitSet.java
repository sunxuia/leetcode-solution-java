package q950;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 902. Numbers At Most N Given Digit Set
 * https://leetcode.com/problems/numbers-at-most-n-given-digit-set/
 *
 * We have a sorted set of digits D, a non-empty subset of {'1','2','3','4','5','6','7','8','9'}.  (Note that '0' is not
 * included.)
 *
 * Now, we write numbers using these digits, using each digit as many times as we want.  For example, if D =
 * {'1','3','5'}, we may write numbers such as '13', '551', '1351315'.
 *
 * Return the number of positive integers that can be written (using the digits of D) that are less than or equal to N.
 *
 * Example 1:
 *
 * Input: D = ["1","3","5","7"], N = 100
 * Output: 20
 * Explanation:
 * The 20 numbers that can be written are:
 * 1, 3, 5, 7, 11, 13, 15, 17, 31, 33, 35, 37, 51, 53, 55, 57, 71, 73, 75, 77.
 *
 * Example 2:
 *
 * Input: D = ["1","4","9"], N = 1000000000
 * Output: 29523
 * Explanation:
 * We can write 3 one digit numbers, 9 two digit numbers, 27 three digit numbers,
 * 81 four digit numbers, 243 five digit numbers, 729 six digit numbers,
 * 2187 seven digit numbers, 6561 eight digit numbers, and 19683 nine digit numbers.
 * In total, this is 29523 integers that can be written using the digits of D.
 *
 * Note:
 *
 * D is a subset of digits '1'-'9' in sorted order.
 * 1 <= N <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q902_NumbersAtMostNGivenDigitSet {

    @Answer
    public int atMostNGivenDigitSet(String[] D, int N) {
        int[] nums = new int[D.length];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = D[i].charAt(0) - '0';
        }
        char[] nc = String.valueOf(N).toCharArray();

        int res = 0, prev = 0;
        for (int i = 0; i < nc.length; i++) {
            res += dfs(nums, nc, prev, i);
            prev = prev * 10 + nc[i] - '0';
        }
        return res;
    }

    private int dfs(int[] nums, char[] nc, int prev, int i) {
        int val = prev * 10 + nc[i] - '0';
        if (val > nums[nums.length - 1]) {
            return (int) Math.pow(nums.length, nc.length - i);
        }

        int idx = Arrays.binarySearch(nums, val);
        int limit = idx >= 0 ? idx + 1 : -idx - 1;
        if (i == nc.length - 1) {
            return limit;
        }

        int res = 0;
        for (int j = 0; j < limit; j++) {
            res += dfs(nums, nc, val - nums[j], i + 1);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{"1", "3", "5", "7"}, 100)
            .expect(20);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{"1", "4", "9"}, 1000000000)
            .expect(29523);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new String[]{"3", "5"}, 4)
            .expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new String[]{"3", "4", "5", "6"}, 64)
            .expect(18);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(new String[]{"1", "7"}, 231)
            .expect(10);

}
