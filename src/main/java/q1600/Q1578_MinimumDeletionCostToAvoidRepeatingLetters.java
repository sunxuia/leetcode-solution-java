package q1600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1578. Minimum Deletion Cost to Avoid Repeating Letters
 * https://leetcode.com/problems/minimum-deletion-cost-to-avoid-repeating-letters/
 *
 * Given a string s and an array of integers cost where cost[i] is the cost of deleting the ith character in s.
 *
 * Return the minimum cost of deletions such that there are no two identical letters next to each other.
 *
 * Notice that you will delete the chosen characters at the same time, in other words, after deleting a character, the
 * costs of deleting other characters will not change.
 *
 * Example 1:
 *
 * Input: s = "abaac", cost = [1,2,3,4,5]
 * Output: 3
 * Explanation: Delete the letter "a" with cost 3 to get "abac" (String without two identical letters next to each
 * other).
 *
 * Example 2:
 *
 * Input: s = "abc", cost = [1,2,3]
 * Output: 0
 * Explanation: You don't need to delete any character because there are no identical letters next to each other.
 *
 * Example 3:
 *
 * Input: s = "aabaa", cost = [1,2,3,4,1]
 * Output: 2
 * Explanation: Delete the first and the last character, getting the string ("aba").
 *
 * Constraints:
 *
 * s.length == cost.length
 * 1 <= s.length, cost.length <= 10^5
 * 1 <= cost[i] <= 10^4
 * s contains only lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1578_MinimumDeletionCostToAvoidRepeatingLetters {

    @Answer
    public int minCost(String s, int[] cost) {
        int res = 0;
        final int n = s.length();
        int p = -1;
        for (int i = 0; i < n; i++) {
            if (p > -1 && s.charAt(p) == s.charAt(i)) {
                if (cost[p] > cost[i]) {
                    res += cost[i];
                    continue;
                } else {
                    res += cost[p];
                }
            }
            p = i;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith("abaac", new int[]{1, 2, 3, 4, 5})
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith("abc", new int[]{1, 2, 3})
            .expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith("aabaa", new int[]{1, 2, 3, 4, 1})
            .expect(2);

}
