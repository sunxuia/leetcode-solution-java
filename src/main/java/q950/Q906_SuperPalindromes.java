package q950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 906. Super Palindromes
 * https://leetcode.com/problems/super-palindromes/
 *
 * Let's say a positive integer is a superpalindrome if it is a palindrome, and it is also the square of a palindrome.
 *
 * Now, given two positive integers L and R (represented as strings), return the number of superpalindromes in the
 * inclusive range [L, R].
 *
 * Example 1:
 *
 * Input: L = "4", R = "1000"
 * Output: 4
 * Explanation: 4, 9, 121, and 484 are superpalindromes.
 * Note that 676 is not a superpalindrome: 26 * 26 = 676, but 26 is not a palindrome.
 *
 * Note:
 *
 * 1 <= len(L) <= 18
 * 1 <= len(R) <= 18
 * L and R are strings representing integers in the range [1, 10^18).
 * int(L) <= int(R)
 */
@RunWith(LeetCodeRunner.class)
public class Q906_SuperPalindromes {

    // 符合题意的解法, 遍历数字, 寻找符合条件的数字
//    @Answer
    public int superpalindromesInRange_overTime(String L, String R) {
        long start = (long) Math.ceil(Math.sqrt(Double.parseDouble(L)));
        long end = (long) Math.floor(Math.sqrt(Double.parseDouble(R)));
        int res = 0;
        for (long i = start; i <= end; i++) {
            if (isPalidrome(i) && isPalidrome(i * i)) {
                res++;
            }
        }
        return res;
    }

    private boolean isPalidrome(long val) {
        long palidrome = 0;
        for (long i = val; i > 0; i /= 10) {
            palidrome = palidrome * 10 + i % 10;
        }
        return palidrome == val;
    }

    /**
     * LeetCode 的一种做法: 直接寻找回文, 然后计算其平方是否也是回文.
     * 相比上面减少了很多无效的遍历.
     */
    @Answer
    public int superpalindromesInRange(String L, String R) {
        long low = (long) Math.ceil(Math.sqrt(Long.parseLong(L)));
        long high = (long) Math.floor(Math.sqrt(Long.parseLong(R)));

        int ret = (3 >= low && 3 <= high) ? 1 : 0;
        ret += dfs(low, high, "");
        ret += dfs(low, high, "0");
        ret += dfs(low, high, "1");
        ret += dfs(low, high, "2");

        return ret;
    }

    private int dfs(long low, long high, String s) {
        if (s.length() > String.valueOf(high).length()) {
            return 0;
        }

        int count = 0;

        if (!s.isEmpty() && s.charAt(0) != '0') {
            long num = Long.parseLong(s);
            if (num > high) {
                return 0;
            }
            if (num >= low && isPalidrome(num * num)) {
                count++;
            }
        }

        for (char c = '0'; c <= '2'; c++) {
            count += dfs(low, high, c + s + c);
        }
        return count;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith("4", "1000").expect(4);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("1", "5").expect(2);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("92904622", "232747148").expect(6);

    @TestData
    public DataExpectation normal3 = DataExpectation.createWith("398904669", "13479046850").expect(6);

    @TestData
    public DataExpectation normal4 = DataExpectation.createWith("103966815", "201619847391450").expect(32);

}
