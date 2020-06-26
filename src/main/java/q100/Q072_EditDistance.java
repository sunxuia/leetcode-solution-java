package q100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/edit-distance/
 *
 * Given two words word1 and word2, find the minimum number of operations required to convert word1 to word2.
 *
 * You have the following 3 operations permitted on a word:
 *
 * Insert a character
 * Delete a character
 * Replace a character
 * Example 1:
 *
 * Input: word1 = "horse", word2 = "ros"
 * Output: 3
 * Explanation:
 * horse -> rorse (replace 'h' with 'r')
 * rorse -> rose (remove 'r')
 * rose -> ros (remove 'e')
 * Example 2:
 *
 * Input: word1 = "intention", word2 = "execution"
 * Output: 5
 * Explanation:
 * intention -> inention (remove 't')
 * inention -> enention (replace 'i' with 'e')
 * enention -> exention (replace 'n' with 'x')
 * exention -> exection (replace 'n' with 'c')
 * exection -> execution (insert 'u')
 */
@RunWith(LeetCodeRunner.class)
public class Q072_EditDistance {

    /**
     * dp 的方式, distance[i][j] 表示从 word1[i] 字符变化到 word2[j] 的字符需要的最小变化次数.
     * 如果 word1[i] 和 word2[j] 相同, 则变化次数不增加, 和 distance[i-1][j-1] 相同, 因为只有在 i 与 j 对应的时候才不增加,
     * 所以变化次数与 i-1 ↔ j-1 的次数一样;
     * 如果 word1[i] 和 word2[j] 不同, 则 i ↔ j 的变化次数是 i-1 ↔ j-1 (替换word[i]为word[j]) 或 i ↔ j-1 (新增 word1[i])
     * 或 i-1 ↔ j (新增 word2[j]) 三种情况匹配的变化次数 +1; 因为需要找到最小值, 所以只需要取这 3 个值的最小值 +1.
     */
    @Answer
    public int minDistance(String word1, String word2) {
        final int len1 = word1.length(), len2 = word2.length();
        int[][] distance = new int[len1 + 1][len2 + 1];
        for (int i = 1; i <= len1; i++) {
            distance[i][0] = i;
        }
        for (int j = 1; j <= len2; j++) {
            distance[0][j] = j;
        }
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    distance[i][j] = distance[i - 1][j - 1];
                } else {
                    distance[i][j] = Math.min(Math.min(
                            distance[i - 1][j - 1],
                            distance[i][j - 1]),
                            distance[i - 1][j]) + 1;
                }
            }
        }
        return distance[len1][len2];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("horse", "ros").expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("intention", "execution").expect(5);

}
