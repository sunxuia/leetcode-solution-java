package q1200;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1160. Find Words That Can Be Formed by Characters
 * https://leetcode.com/problems/find-words-that-can-be-formed-by-characters/
 *
 * You are given an array of strings words and a string chars.
 *
 * A string is good if it can be formed by characters from chars (each character can only be used once).
 *
 * Return the sum of lengths of all good strings in words.
 *
 * Example 1:
 *
 * Input: words = ["cat","bt","hat","tree"], chars = "atach"
 * Output: 6
 * Explanation:
 * The strings that can be formed are "cat" and "hat" so the answer is 3 + 3 = 6.
 *
 * Example 2:
 *
 * Input: words = ["hello","world","leetcode"], chars = "welldonehoneyr"
 * Output: 10
 * Explanation:
 * The strings that can be formed are "hello" and "world" so the answer is 5 + 5 = 10.
 *
 * Note:
 *
 * 1 <= words.length <= 1000
 * 1 <= words[i].length, chars.length <= 100
 * All strings contain lowercase English letters only.
 */
@RunWith(LeetCodeRunner.class)
public class Q1160_FindWordsThatCanBeFormedByCharacters {

    @Answer
    public int countCharacters(String[] words, String chars) {
        int[] expects = new int[26];
        for (int i = 0; i < chars.length(); i++) {
            expects[chars.charAt(i) - 'a']++;
        }
        int res = 0;
        int[] counts = new int[26];
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                counts[word.charAt(i) - 'a']++;
            }
            boolean match = true;
            for (int i = 0; i < 26 && match; i++) {
                match = expects[i] >= counts[i];
            }
            if (match) {
                res += word.length();
            }
            Arrays.fill(counts, 0);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{"cat", "bt", "hat", "tree"}, "atach")
            .expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{"hello", "world", "leetcode"}, "welldonehoneyr")
            .expect(10);

}
