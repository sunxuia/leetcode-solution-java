package q850;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/count-unique-characters-of-all-substrings-of-a-given-string/
 *
 * Let's define a function countUniqueChars(s) that returns the number of unique characters on s, for example if s =
 * "LEETCODE" then "L", "T","C","O","D" are the unique characters since they appear only once in s, therefore
 * countUniqueChars(s) = 5.
 *
 * On this problem given a string s we need to return the sum of countUniqueChars(t) where t is a substring of s.
 * Notice that some substrings can be repeated so on this case you have to count the repeated ones too.
 *
 * Since the answer can be very large, return the answer modulo 10 ^ 9 + 7.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "ABC"
 * Output: 10
 * Explanation: All possible substrings are: "A","B","C","AB","BC" and "ABC".
 * Evey substring is composed with only unique letters.
 * Sum of lengths of all substring is 1 + 1 + 1 + 2 + 2 + 3 = 10
 *
 * Example 2:
 *
 * Input: s = "ABA"
 * Output: 8
 * Explanation: The same as example 1, except countUniqueChars("ABA") = 1.
 *
 * Example 3:
 *
 * Input: s = "LEETCODE"
 * Output: 92
 *
 *
 *
 * Constraints:
 *
 * 0 <= s.length <= 10^4
 * s contain upper-case English letters only.
 */
@RunWith(LeetCodeRunner.class)
public class Q828_CountUniqueCharactersOfAllSubstringsOfAGivenString {

    // O(N^2) 的处理方式, 这种方式会超时
    public int uniqueLetterString(String s) {
        int res = 0;
        int[] counts = new int[26];
        char[] sc = s.toCharArray();
        for (int i = 0; i < sc.length; i++) {
            // single 表示 s[i, j] 范围内有多少符合unique letter
            int single = 0;
            Arrays.fill(counts, 0);
            for (int j = i; j < sc.length; j++) {
                int idx = sc[j] - 'A';
                counts[idx]++;
                single += counts[idx] == 1 ? 1 : counts[idx] == 2 ? -1 : 0;
                res = (res + single) % 10_0000_0007;
            }
        }
        return res;
    }

    // https://www.cnblogs.com/grandyang/p/11616485.html
    @Answer
    public int uniqueLetterString2(String s) {
        int[] first = new int[26], second = new int[26];
        int res = 0, curr = 0;
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'A';
            curr = curr + 1 + i - first[idx] * 2 + second[idx];
            res = (res + curr) % 10_0000_0007;
            second[idx] = first[idx];
            first[idx] = i + 1;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("ABC").expect(10);

    @TestData
    public DataExpectation example2 = DataExpectation.create("ABA").expect(8);

    @TestData
    public DataExpectation example3 = DataExpectation.create("LEETCODE").expect(92);

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.read(String.class))
            .expect(29998);

}
