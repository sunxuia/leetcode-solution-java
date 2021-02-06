package q1750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1704. Determine if String Halves Are Alike
 * https://leetcode.com/problems/determine-if-string-halves-are-alike/
 *
 * You are given a string s of even length. Split this string into two halves of equal lengths, and let a be the first
 * half and b be the second half.
 *
 * Two strings are alike if they have the same number of vowels ('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U').
 * Notice that s contains uppercase and lowercase letters.
 *
 * Return true if a and b are alike. Otherwise, return false.
 *
 * Example 1:
 *
 * Input: s = "book"
 * Output: true
 * Explanation: a = "bo" and b = "ok". a has 1 vowel and b has 1 vowel. Therefore, they are alike.
 *
 * Example 2:
 *
 * Input: s = "textbook"
 * Output: false
 * Explanation: a = "text" and b = "book". a has 1 vowel whereas b has 2. Therefore, they are not alike.
 * Notice that the vowel o is counted twice.
 *
 * Example 3:
 *
 * Input: s = "MerryChristmas"
 * Output: false
 *
 * Example 4:
 *
 * Input: s = "AbCdEfGh"
 * Output: true
 *
 * Constraints:
 *
 * 2 <= s.length <= 1000
 * s.length is even.
 * s consists of uppercase and lowercase letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1704_DetermineIfStringHalvesAreAlike {

    @Answer
    public boolean halvesAreAlike(String s) {
        int count = 0;
        for (int i = 0; i < s.length() / 2; i++) {
            count += MAP[s.charAt(i)];
        }
        for (int i = s.length() / 2; i < s.length(); i++) {
            count -= MAP[s.charAt(i)];
        }
        return count == 0;
    }

    private static final int[] MAP = new int[127];

    static {
        MAP['a'] = 1;
        MAP['A'] = 1;
        MAP['e'] = 1;
        MAP['E'] = 1;
        MAP['i'] = 1;
        MAP['I'] = 1;
        MAP['o'] = 1;
        MAP['O'] = 1;
        MAP['u'] = 1;
        MAP['U'] = 1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("book").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create("textbook").expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create("MerryChristmas").expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation.create("AbCdEfGh").expect(true);

}
