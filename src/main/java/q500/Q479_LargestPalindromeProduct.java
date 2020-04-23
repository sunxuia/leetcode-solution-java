package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/largest-palindrome-product/
 *
 * Find the largest palindrome made from the product of two n-digit numbers.
 *
 * Since the result could be very large, you should return the largest palindrome mod 1337.
 *
 *
 *
 * Example:
 *
 * Input: 2
 *
 * Output: 987
 *
 * Explanation: 99 x 91 = 9009, 9009 % 1337 = 987
 */
@RunWith(LeetCodeRunner.class)
public class Q479_LargestPalindromeProduct {

    /**
     * https://www.cnblogs.com/grandyang/p/7644725.html
     * 就是一个一个找, 跳过一些边界.
     */
    @Answer
    public int largestPalindrome(int n) {
        int upper = (int) Math.pow(10, n) - 1, lower = upper / 10;
        for (int i = upper; i > lower; i--) {
            long p = Long.parseLong(new StringBuilder()
                    .append(i).reverse().insert(0, i).toString());
            for (long j = upper; j * j > p; j--) {
                if (p % j == 0) {
                    return (int) (p % 1337);
                }
            }
        }
        return 9;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(2).expect(987);

}
