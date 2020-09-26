package q1100;

import java.util.Arrays;
import org.junit.runner.RunWith;
import q350.Q316_RemoveDuplicateLetters;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1081. Smallest Subsequence of Distinct Characters
 * https://leetcode.com/problems/smallest-subsequence-of-distinct-characters/
 *
 * Return the lexicographically smallest subsequence of text that contains all the distinct characters of text exactly
 * once.
 *
 * Example 1:
 *
 * Input: "cdadabcc"
 * Output: "adbc"
 *
 * Example 2:
 *
 * Input: "abcd"
 * Output: "abcd"
 *
 * Example 3:
 *
 * Input: "ecbacba"
 * Output: "eacb"
 *
 * Example 4:
 *
 * Input: "leetcode"
 * Output: "letcod"
 *
 * Constraints:
 *
 * 1 <= text.length <= 1000
 * text consists of lowercase English letters.
 *
 * Note: This question is the same as 316: https://leetcode.com/problems/remove-duplicate-letters/
 *
 * 相同题目 {@link Q316_RemoveDuplicateLetters}
 */
@RunWith(LeetCodeRunner.class)
public class Q1081_SmallestSubsequenceOfDistinctCharacters {

    @Answer
    public String smallestSubsequence(String text) {
        int[] lastPos = new int[26];
        Arrays.fill(lastPos, -1);
        char[] cs = text.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            lastPos[cs[i] - 'a'] = i;
        }

        boolean[] visited = new boolean[26];
        StringBuilder sb = new StringBuilder();
        // 哨兵
        sb.append('0');
        for (int i = 0; i < cs.length; i++) {
            if (visited[cs[i] - 'a']) {
                continue;
            }
            while (true) {
                char prev = sb.charAt(sb.length() - 1);
                if (cs[i] >= prev || lastPos[prev - 'a'] < i) {
                    // 上一字符比当前小或不能替换
                    break;
                }
                sb.setLength(sb.length() - 1);
                visited[prev - 'a'] = false;
            }
            visited[cs[i] - 'a'] = true;
            sb.append(cs[i]);
        }
        return sb.substring(1);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("cdadabcc").expect("adbc");

    @TestData
    public DataExpectation example2 = DataExpectation.create("abcd").expect("abcd");

    @TestData
    public DataExpectation example3 = DataExpectation.create("ecbacba").expect("eacb");

    @TestData
    public DataExpectation example4 = DataExpectation.create("leetcode").expect("letcod");

}
