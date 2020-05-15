package q700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/repeated-string-match/
 *
 * Given two strings A and B, find the minimum number of times A has to be repeated such that B is a substring of it.
 * If no such solution, return -1.
 *
 * For example, with A = "abcd" and B = "cdabcdab".
 *
 * Return 3, because by repeating A three times (“abcdabcdabcd”), B is a substring of it; and B is not a substring of
 * A repeated two times ("abcdabcd").
 *
 * Note:
 * The length of A and B will be between 1 and 10000.
 */
@RunWith(LeetCodeRunner.class)
public class Q686_RepeatedStringMatch {

    // 这题有很多的边界条件
    @Answer
    public int repeatedStringMatch(String A, String B) {
        final int aLen = A.length(), bLen = B.length();
        char[] ac = A.toCharArray();
        int[][] kmp = kmp(B);
        int i = 0;
        for (int j = 0; j < bLen; i++) {
            int next = kmp[j][ac[i % aLen] - 'a'];
            // 开始的时候可能会不匹配, 造成状态回退
            if (next <= j && i >= 2 * aLen) {
                return -1;
            }
            j = next;
        }
        return (i % aLen == 0 ? 0 : 1) + i / aLen;
    }

    private int[][] kmp(String pattern) {
        int[][] dfa = new int[pattern.length()][26];
        int[] reset = dfa[0];
        reset[pattern.charAt(0) - 'a'] = 1;
        for (int i = 1; i < pattern.length(); i++) {
            System.arraycopy(reset, 0, dfa[i], 0, 26);
            int c = pattern.charAt(i) - 'a';
            dfa[i][c] = i + 1;
            reset = dfa[reset[c]];
        }
        return dfa;
    }

    // LeetCode 将该题标记为easy 的意图应该是这种解法.
    // 不过这种解法要比上面kmp 的解法慢不少, 但是基于此思路优化后的hash 解法则要更快.
    @Answer
    public int repeatedStringMatch2(String A, String B) {
        StringBuilder sb = new StringBuilder();
        int res = 0;
        while (sb.length() < B.length()) {
            sb.append(A);
            res++;
        }

        if (sb.indexOf(B) >= 0) {
            return res;
        }
        if (sb.append(A).indexOf(B) >= 0) {
            return ++res;
        }
        return -1;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith("abcd", "cdabcdab").expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("abcabcabcabc", "abac").expect(-1);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("aa", "a").expect(1);

    @TestData
    public DataExpectation normal3 = DataExpectation.createWith("abc", "cabcabca").expect(4);

    @TestData
    public DataExpectation normal4 = DataExpectation.createWith("a", "a").expect(1);

    @TestData
    public DataExpectation normal5 = DataExpectation.createWith("aaaaaaaaaaaaaaaaaaaaaab", "ba").expect(2);

    @TestData
    public DataExpectation normal6 = DataExpectation.createWith("abcd", "bcdab").expect(2);

    @TestData
    public DataExpectation normal7 = DataExpectation.createWith("abaabaa", "abaababaab").expect(-1);

    @TestData
    public DataExpectation normal8 = DataExpectation.createWith("aabaa", "aaab").expect(2);

}
