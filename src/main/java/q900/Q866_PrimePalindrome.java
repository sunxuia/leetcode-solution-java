package q900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 866. Prime Palindrome
 * https://leetcode.com/problems/prime-palindrome/
 *
 * Find the smallest prime palindrome greater than or equal to N.
 *
 * Recall that a number is prime if it's only divisors are 1 and itself, and it is greater than 1.
 *
 * For example, 2,3,5,7,11 and 13 are primes.
 *
 * Recall that a number is a palindrome if it reads the same from left to right as it does from right to left.
 *
 * For example, 12321 is a palindrome.
 *
 * Example 1:
 *
 * Input: 6
 * Output: 7
 *
 * Example 2:
 *
 * Input: 8
 * Output: 11
 *
 * Example 3:
 *
 * Input: 13
 * Output: 101
 *
 * Note:
 *
 * 1 <= N <= 10^8
 * The answer is guaranteed to exist and be less than 2 * 10^8.
 */
@RunWith(LeetCodeRunner.class)
public class Q866_PrimePalindrome {

    // 这题挺无聊的, 也没有什么特殊技巧, 直接复制了LeetCode 中一个解答
    @Answer
    public int primePalindrome(int N) {
        while (true) {
            if (isPalindrome(N) && isPrime(N)) {
                return N;
            }
            // 查找下一个N, 是否满足条件
            N++;
            // 跳过不存在回文的情况
            if (1000_0000 < N && N < 1_0000_0000) {
                N = 1_0000_0000;
            }
        }
    }

    // 是否是回文
    public boolean isPalindrome(int n) {
        int divisor = 1;
        while (n / divisor >= 10) {
            divisor *= 10;
        }
        while (n != 0) {
            int leading = n / divisor;
            int trailing = n % 10;
            if (leading != trailing) {
                return false;
            }
            n = (n % divisor) / 10;
            divisor = divisor / 100;
        }
        return true;
    }

    // 是否是质数
    public boolean isPrime(int n) {
        if (n == 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(6).expect(7);

    @TestData
    public DataExpectation example2 = DataExpectation.create(8).expect(11);

    @TestData
    public DataExpectation example3 = DataExpectation.create(13).expect(101);

}
