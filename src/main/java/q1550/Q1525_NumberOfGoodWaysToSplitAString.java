package q1550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1525. Number of Good Ways to Split a String
 * https://leetcode.com/problems/number-of-good-ways-to-split-a-string/
 *
 * You are given a string s, a split is called good if you can split s into 2 non-empty strings p and q where its
 * concatenation is equal to s and the number of distinct letters in p and q are the same.
 *
 * Return the number of good splits you can make in s.
 *
 * Example 1:
 *
 * Input: s = "aacaba"
 * Output: 2
 * Explanation: There are 5 ways to split "aacaba" and 2 of them are good.
 * ("a", "acaba") Left string and right string contains 1 and 3 different letters respectively.
 * ("aa", "caba") Left string and right string contains 1 and 3 different letters respectively.
 * ("aac", "aba") Left string and right string contains 2 and 2 different letters respectively (good split).
 * ("aaca", "ba") Left string and right string contains 2 and 2 different letters respectively (good split).
 * ("aacab", "a") Left string and right string contains 3 and 1 different letters respectively.
 *
 * Example 2:
 *
 * Input: s = "abcd"
 * Output: 1
 * Explanation: Split the string as follows ("ab", "cd").
 *
 * Example 3:
 *
 * Input: s = "aaaaa"
 * Output: 4
 * Explanation: All possible splits are good.
 *
 * Example 4:
 *
 * Input: s = "acbadbaada"
 * Output: 2
 *
 * Constraints:
 *
 * s contains only lowercase English letters.
 * 1 <= s.length <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1525_NumberOfGoodWaysToSplitAString {

    @Answer
    public int numSplits(String s) {
        int[] right = new int[26];
        int countRight = 0;
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            right[idx]++;
            if (right[idx] == 1) {
                countRight++;
            }
        }

        int res = 0;
        boolean[] left = new boolean[26];
        int countLeft = 0;
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            if (!left[idx]) {
                countLeft++;
            }
            left[idx] = true;
            if (--right[idx] == 0) {
                countRight--;
            }
            if (countLeft == countRight) {
                res++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("aacaba").expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create("abcd").expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.create("aaaaa").expect(4);

    @TestData
    public DataExpectation example4 = DataExpectation.create("acbadbaada").expect(2);

}
