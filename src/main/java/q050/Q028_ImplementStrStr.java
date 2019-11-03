package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/implement-strstr/
 *
 * Implement strStr().
 *
 * Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
 *
 * Example 1:
 *
 * Input: haystack = "hello", needle = "ll"
 * Output: 2
 * Example 2:
 *
 * Input: haystack = "aaaaa", needle = "bba"
 * Output: -1
 * Clarification:
 *
 * What should we return when needle is an empty string? This is a great question to ask during an interview.
 *
 * For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's strstr
 * () and Java's indexOf().
 */
@RunWith(LeetCodeRunner.class)
public class Q028_ImplementStrStr {

    @Answer
    public int javaImplements(String haystack, String needle) {
        return haystack.indexOf(needle);
    }

    /**
     * KMP 方式要比java 自带时间慢挺多的, 空间消耗也比较大. LeetCode 中反而
     * 比其他采用字符比较的要慢.
     */
    @Answer
    public int strStr(String haystack, String needle) {
        if (needle == null || needle.isEmpty()) {
            return 0;
        }
        int[][] kmp = kmp(needle);
        for (int i = 0, status = 0; i < haystack.length(); i++) {
            status = kmp[status][haystack.charAt(i)];
            if (status == kmp.length) {
                return i - needle.length() + 1;
            }
        }
        return -1;
    }

    private int[][] kmp(String pattern) {
        int[][] kmp = new int[pattern.length()][128];
        int[] reset = kmp[0];
        reset[pattern.charAt(0)] = 1;
        for (int i = 1; i < pattern.length(); i++) {
            for (int j = 0; j < 128; j++) {
                kmp[i][j] = reset[j];
            }
            char c = pattern.charAt(i);
            kmp[i][c] = i + 1;
            reset = kmp[reset[c]];
        }
        return kmp;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("hello")
            .addArgument("ll")
            .expect(2)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("aaaaa")
            .addArgument("bba")
            .expect(-1)
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument("mississippi")
            .addArgument("issip")
            .expect(4)
            .build();
}
