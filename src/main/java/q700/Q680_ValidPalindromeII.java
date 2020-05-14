package q700;

import org.junit.runner.RunWith;
import q150.Q125_ValidPalindrome;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/valid-palindrome-ii/
 *
 * Given a non-empty string s, you may delete at most one character. Judge whether you can make it a palindrome.
 *
 * Example 1:
 *
 * Input: "aba"
 * Output: True
 *
 * Example 2:
 *
 * Input: "abca"
 * Output: True
 * Explanation: You could delete the character 'c'.
 *
 * Note:
 *
 * The string will only contain lowercase characters a-z. The maximum length of the string is 50000.
 *
 * 上一题 {@link Q125_ValidPalindrome}
 */
@RunWith(LeetCodeRunner.class)
public class Q680_ValidPalindromeII {

    @Answer
    public boolean validPalindrome(String s) {
        char[] sc = s.toCharArray();
        // 上一次的回溯地址
        int backLeft = -1, backRight = -1;
        int left = 0, right = sc.length - 1;
        while (left < right) {
            if (sc[left] == sc[right]) {
                left++;
                right--;
            } else {
                if (backLeft == -1) {
                    backLeft = left;
                    backRight = right;
                    left++;
                } else if (backLeft == -2) {
                    return false;
                } else {
                    left = backLeft;
                    right = backRight - 1;
                    backLeft = -2;
                }
            }
        }
        return true;
    }

    // LeetCode 中另一种的解法, 大同小异, 速度比上面稍微慢一点
    @Answer
    public boolean validPalindrome2(String s) {
        char[] sc = s.toCharArray();
        int left = 0, right = sc.length - 1;
        while (left <= right) {
            if (sc[left] == sc[right]) {
                left++;
                right--;
            } else {
                // 分别跳过左边或右边的一个字符
                return isPalindrome(sc, left + 1, right)
                        || isPalindrome(sc, left, right - 1);
            }
        }
        return true;
    }

    // 普通的判断是否是回文的方法
    private boolean isPalindrome(char[] sc, int left, int right) {
        while (left <= right) {
            if (sc[left] != sc[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("aba").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create("abca").expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("cbbcc").expect(true);

}
