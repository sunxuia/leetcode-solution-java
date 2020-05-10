package q650;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/palindromic-substrings/
 *
 * Given a string, your task is to count how many palindromic substrings in this string.
 *
 * The substrings with different start indexes or end indexes are counted as different substrings even they consist
 * of same characters.
 *
 * Example 1:
 *
 * Input: "abc"
 * Output: 3
 * Explanation: Three palindromic strings: "a", "b", "c".
 *
 *
 *
 * Example 2:
 *
 * Input: "aaa"
 * Output: 6
 * Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".
 *
 *
 *
 * Note:
 *
 * The input string length won't exceed 1000.
 */
@RunWith(LeetCodeRunner.class)
public class Q647_PalindromicSubstrings {

    @Answer
    public int countSubstrings(String s) {
        int res = 0;
        char[] sc = s.toCharArray();
        for (int i = 0; i < sc.length; i++) {
            int left = i, right = i;
            while (0 <= left && right < sc.length
                    && sc[left] == sc[right]) {
                res++;
                left--;
                right++;
            }
            left = i - 1;
            right = i;
            while (0 <= left && right < sc.length
                    && sc[left] == sc[right]) {
                res++;
                left--;
                right++;
            }
        }
        return res;
    }

    // LeetCode 上最快的解法
    // 这个解法利用了LeetCode 测试用例中相同字符回文比较多的特性, 跳过了重复的计算.
    @Answer
    public int countSubstrings2(String s) {
        char[] sc = s.toCharArray();
        int res = 0;
        for (int i = 0, same; i < sc.length; i += same) {
            int right = i + 1;
            // 计算相同字符的数量
            while (right < sc.length && sc[i] == sc[right]) {
                right++;
            }
            same = right - i;
            // + 相同元素对应的回文数量
            res += (1 + same) * same / 2;
            // 以i 为中心, 往外找回文字符
            int left = i - 1;
            while (left >= 0 && right < sc.length
                    && sc[left] == sc[right]) {
                res++;
                left--;
                right++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("abc").expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create("aaa").expect(6);

}
