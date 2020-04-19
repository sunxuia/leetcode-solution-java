package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/count-the-repetitions/
 *
 * Define S = [s,n] as the string S which consists of n connected strings s. For example, ["abc", 3] ="abcabcabc".
 *
 * On the other hand, we define that string s1 can be obtained from string s2 if we can remove some characters from
 * s2 such that it becomes s1. For example, “abc” can be obtained from “abdbec” based on our definition, but it can
 * not be obtained from “acbbe”.
 *
 * You are given two non-empty strings s1 and s2 (each at most 100 characters long) and two integers 0 ≤ n1 ≤ 10^6 and
 * 1 ≤ n2 ≤ 10^6. Now consider the strings S1 and S2, where S1=[s1,n1] and S2=[s2,n2]. Find the maximum integer M such
 * that [S2,M] can be obtained from S1.
 *
 * Example:
 *
 * Input:
 * s1="acb", n1=4
 * s2="ab", n2=2
 *
 * Return:
 * 2
 */
@RunWith(LeetCodeRunner.class)
public class Q466_CountTheRepetitions {

    // https://www.cnblogs.com/grandyang/p/6149294.html
    // LeetCode 上的Solution 中也有解答
    @Answer
    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        int[] repeatCnt = new int[n1 + 1];
        int[] nextIndex = new int[n1 + 1];
        char[] sc1 = s1.toCharArray();
        char[] sc2 = s2.toCharArray();
        int j = 0, cnt = 0;
        for (int k = 1; k <= n1; ++k) {
            for (int i = 0; i < sc1.length; i++) {
                if (sc1[i] == sc2[j]) {
                    ++j;
                    if (j == sc2.length) {
                        j = 0;
                        ++cnt;
                    }
                }
            }
            repeatCnt[k] = cnt;
            nextIndex[k] = j;
            for (int start = 0; start < k; start++) {
                if (nextIndex[start] == j) {
                    int interval = k - start;
                    int repeat = (n1 - start) / interval;
                    int patternCnt = (repeatCnt[k] - repeatCnt[start]) * repeat;
                    int remainCnt = repeatCnt[start + (n1 - start) % interval];
                    return (patternCnt + remainCnt) / n2;
                }
            }
        }
        return repeatCnt[n1] / n2;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith("acb", 4, "ab", 2).expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("acb", 1, "acb", 1).expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("aaa", 3, "aa", 1).expect(4);

    @TestData
    public DataExpectation overTime = DataExpectation.createWith(
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            1000000,
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            1000000)
            .expect(1);

}
