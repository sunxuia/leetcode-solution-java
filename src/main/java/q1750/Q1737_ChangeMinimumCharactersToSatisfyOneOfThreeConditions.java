package q1750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1737. Change Minimum Characters to Satisfy One of Three Conditions
 * https://leetcode.com/problems/change-minimum-characters-to-satisfy-one-of-three-conditions/
 *
 * You are given two strings a and b that consist of lowercase letters. In one operation, you can change any character
 * in a or b to any lowercase letter.
 *
 * Your goal is to satisfy one of the following three conditions:
 *
 * 1. Every letter in a is strictly less than every letter in b in the alphabet.
 * 2. Every letter in b is strictly less than every letter in a in the alphabet.
 * 3. Both a and b consist of only one distinct letter.
 *
 * Return the minimum number of operations needed to achieve your goal.
 *
 * Example 1:
 *
 * Input: a = "aba", b = "caa"
 * Output: 2
 * Explanation: Consider the best way to make each condition true:
 * 1) Change b to "ccc" in 2 operations, then every letter in a is less than every letter in b.
 * 2) Change a to "bbb" and b to "aaa" in 3 operations, then every letter in b is less than every letter in a.
 * 3) Change a to "aaa" and b to "aaa" in 2 operations, then a and b consist of one distinct letter.
 * The best way was done in 2 operations (either condition 1 or condition 3).
 *
 * Example 2:
 *
 * Input: a = "dabadd", b = "cda"
 * Output: 3
 * Explanation: The best way is to make condition 1 true by changing b to "eee".
 *
 * Constraints:
 *
 * 1 <= a.length, b.length <= 10^5
 * a and b consist only of lowercase letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1737_ChangeMinimumCharactersToSatisfyOneOfThreeConditions {

    @Answer
    public int minCharacters(String a, String b) {
        final int m = a.length(), n = b.length();
        int[] countsA = new int[26];
        for (int i = 0; i < m; i++) {
            countsA[a.charAt(i) - 'a']++;
        }
        int[] countsB = new int[26];
        for (int i = 0; i < n; i++) {
            countsB[b.charAt(i) - 'a']++;
        }

        int res = strictLess(countsA, countsB, m);
        res = Math.min(res, strictLess(countsB, countsA, n));
        res = Math.min(res, allSame(countsA, countsB, m, n));
        return res;
    }

    private int strictLess(int[] countsA, int[] countsB, int m) {
        int res = Integer.MAX_VALUE;
        int a = m, b = 0;
        for (int i = 0; i < 25; i++) {
            // a <= i, b > i
            a -= countsA[i];
            b += countsB[i];
            res = Math.min(res, a + b);
        }
        return res;
    }

    private int allSame(int[] countsA, int[] countsB, int m, int n) {
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < 26; i++) {
            res = Math.min(res, m + n - countsA[i] - countsB[i]);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("aba", "caa").expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("dabadd", "cda").expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("acac", "bd").expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("cecc", "a").expect(0);

    @TestData
    public DataExpectation normal3 = DataExpectation.createWith(
            "otpaynexxlngyrdmand",
            "twtyifiiundfejxfktclktjnqrmycnqmxhxfitnlasyuwotjguerenjjnpkaajsnnraopdnambfccwthppimijghg")
            .expect(19);

}
