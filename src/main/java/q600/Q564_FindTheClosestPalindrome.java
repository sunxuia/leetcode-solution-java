package q600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/find-the-closest-palindrome/
 *
 * Given an integer n, find the closest integer (not including itself), which is a palindrome.
 *
 * The 'closest' is defined as absolute difference minimized between two integers.
 *
 * Example 1:
 *
 * Input: "123"
 * Output: "121"
 *
 * Note:
 *
 * The input n is a positive integer represented by string, whose length will not exceed 18.
 * If there is a tie, return the smaller one as answer.
 */
@RunWith(LeetCodeRunner.class)
public class Q564_FindTheClosestPalindrome {

    // LeetCode 的Solution 中给出的答案
    @Answer
    public String nearestPalindromic(String n) {
        if ("1".equals(n)) {
            return "0";
        }

        // 直接镜像的结果
        String candidate1 = mirrorHalf(new StringBuilder(n)).toString();
        long diff1 = Math.abs(Long.parseLong(n) - Long.parseLong(candidate1));
        // 回文数字不能和自己相等
        if (diff1 == 0) {
            diff1 = Long.MAX_VALUE;
        }

        // 中间 -1 的镜像的结果 (中间-1 是因为更小位数的会被高位镜像覆盖)
        StringBuilder sb2 = new StringBuilder(n);
        minus1(sb2, (sb2.length() - 1) / 2);
        String candidate2 = mirrorHalf(sb2).toString();
        long diff2 = Math.abs(Long.parseLong(n) - Long.parseLong(candidate2));

        // 中间 +1 的镜像结果
        StringBuilder sb3 = new StringBuilder(n);
        plus1(sb3, (sb3.length() - 1) / 2);
        String candidate3 = mirrorHalf(sb3).toString();
        long diff3 = Math.abs(Long.parseLong(n) - Long.parseLong(candidate3));

        // 优先返回小的数字
        if (diff2 <= diff1 && diff2 <= diff3) {
            return candidate2;
        }
        return diff1 <= diff3 ? candidate1 : candidate3;
    }

    // 将字符串s 的前半部分镜像到后半部分(这样返回值就是对称的了)
    private StringBuilder mirrorHalf(StringBuilder sb) {
        int left = sb.length() / 2 - 1;
        int right = sb.length() % 2 == 0 ? left + 1 : left + 2;
        while (left >= 0) {
            sb.setCharAt(right, sb.charAt(left));
            left--;
            right++;
        }
        return sb;
    }

    // 字符串的数字 -1
    private void minus1(StringBuilder sb, int idx) {
        while (idx >= 0 && sb.charAt(idx) == '0') {
            // 如果是类似 10000 这样, -1 的时候就需要把0 改成9
            sb.setCharAt(idx, '9');
            idx--;
        }
        if (idx == 0 && sb.charAt(idx) == '1') {
            sb.deleteCharAt(0);
            int mid = (sb.length() - 1) / 2;
            sb.setCharAt(mid, '9');
        } else {
            sb.setCharAt(idx, (char) (sb.charAt(idx) - 1));
        }
    }

    // 字符串的数字 +1
    private void plus1(StringBuilder sb, int idx) {
        while (idx >= 0 && sb.charAt(idx) == '9') {
            sb.setCharAt(idx, '0');
            idx--;
        }
        if (idx < 0) {
            sb.insert(0, '1');
        } else {
            sb.setCharAt(idx, (char) (sb.charAt(idx) + 1));
        }
    }

    @TestData
    public DataExpectation example = DataExpectation.create("123").expect("121");

    @TestData
    public DataExpectation normal1 = DataExpectation.create("1").expect("0");

    @TestData
    public DataExpectation normal2 = DataExpectation.create("230").expect("232");

}
