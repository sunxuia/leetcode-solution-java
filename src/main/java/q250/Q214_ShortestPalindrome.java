package q250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/shortest-palindrome/
 *
 * Given a string s, you are allowed to convert it to a palindrome by adding characters in front of it. Find and
 * return the shortest palindrome you can find by performing this transformation.
 *
 * Example 1:
 *
 * Input: "aacecaaa"
 * Output: "aaacecaaa"
 *
 * Example 2:
 *
 * Input: "abcd"
 * Output: "dcbabcd"
 */
@RunWith(LeetCodeRunner.class)
public class Q214_ShortestPalindrome {

    @Answer
    public String shortestPalindrome(String s) {
        char[] sc = s.toCharArray();
        int maxStart = sc.length - 1;
        while (maxStart > 0 && !isPalindrome(sc, 0, maxStart)) {
            maxStart--;
        }

        StringBuilder sb = new StringBuilder(sc.length * 2 - maxStart);
        for (int i = sc.length - 1; i > maxStart; i--) {
            sb.append(sc[i]);
        }
        return sb.append(sc).toString();
    }

    private boolean isPalindrome(char[] sc, int start, int end) {
        while (start < end && sc[start] == sc[end]) {
            start++;
            end--;
        }
        return start >= end;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("aacecaaa").expect("aaacecaaa");

    @TestData
    public DataExpectation example2 = DataExpectation.create("abcd").expect("dcbabcd");

}
