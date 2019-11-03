package q100;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/scramble-string/
 *
 * Given a string s1, we may represent it as a binary tree by partitioning it to two non-empty substrings recursively.
 *
 * Below is one possible representation of s1 = "great":
 *
 * >     great
 * >    /    \
 * >   gr    eat
 * >  / \    /  \
 * > g   r  e   at
 * >            / \
 * >           a   t
 * To scramble the string, we may choose any non-leaf node and swap its two children.
 *
 * For example, if we choose the node "gr" and swap its two children, it produces a scrambled string "rgeat".
 *
 * >    rgeat
 * >    /    \
 * >   rg    eat
 * >  / \    /  \
 * > r   g  e   at
 * >            / \
 * >           a   t
 * We say that "rgeat" is a scrambled string of "great".
 *
 * Similarly, if we continue to swap the children of nodes "eat" and "at", it produces a scrambled string "rgtae".
 *
 * >    rgtae
 * >    /    \
 * >   rg    tae
 * >  / \    /  \
 * > r   g  ta  e
 * >        / \
 * >       t   a
 * We say that "rgtae" is a scrambled string of "great".
 *
 * Given two strings s1 and s2 of the same length, determine if s2 is a scrambled string of s1.
 */
@RunWith(LeetCodeRunner.class)
public class Q087_ScrambleString {

    /**
     * dfs 遍历s1 所有可能的生成树, 判断是否有与 s2 相等的.
     */
    @Answer
    public boolean isScramble(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        if (s1.isEmpty()) {
            return true;
        }
        temp = new char[Math.max(s1.length(), 26)];
        return dfs(s1.toCharArray(), s2.toCharArray(), 0, s1.length());
    }

    private boolean dfs(char[] s1, char[] s2, int start, int end) {
        if (start == end - 1) {
            return s1[start] == s2[start];
        }

        // 比较字符数是否相等, 减少无效的对比次数, 避免超时情况.
        // 这里因为输入只有小写的26个字母, 所以可以使用数组来判断是否相等.
        Arrays.fill(temp, 0, 26, (char) 0);
        for (int i = start; i < end; i++) {
            temp[s1[i] - 'a']++;
            temp[s2[i] - 'a']--;
        }
        for (int i = 0; i < 26; i++) {
            if (temp[i] != 0) {
                return false;
            }
        }

        for (int i = start + 1; i < end; i++) {
            // 左右不交换比较
            if (dfs(s1, s2, start, i) && dfs(s1, s2, i, end)) {
                return true;
            }

            // 左右交换比较
            swapRange(s1, i, start, end);
            final int ni = start + end - i;
            if (dfs(s1, s2, start, ni) && dfs(s1, s2, ni, end)) {
                return true;
            }
            swapRange(s1, ni, start, end);
        }

        return false;
    }

    private char[] temp;

    private void swapRange(char[] cs, int i, int start, int end) {
        System.arraycopy(cs, start, temp, end - i, i - start);
        System.arraycopy(cs, i, temp, 0, end - i);
        System.arraycopy(temp, 0, cs, start, end - start);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("great", "rgeat").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("abcde", "caebd").expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("abc", "acb").expect(true);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("abc", "cab").expect(true);

    @TestData
    public DataExpectation normal3 = DataExpectation.createWith("abcd", "badc").expect(true);

    @TestData
    public DataExpectation border = DataExpectation.createWith("", "").expect(true);

    @TestData
    public DataExpectation overtime = DataExpectation
            .createWith("xstjzkfpkggnhjzkpfjoguxvkbuopi", "xbouipkvxugojfpkzjhnggkpfkzjts").expect(true);


}
