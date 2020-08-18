package q1000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 953. Verifying an Alien Dictionary
 * https://leetcode.com/problems/verifying-an-alien-dictionary/
 *
 * In an alien language, surprisingly they also use english lowercase letters, but possibly in a different order. The
 * order of the alphabet is some permutation of lowercase letters.
 *
 * Given a sequence of words written in the alien language, and the order of the alphabet, return true if and only if
 * the given words are sorted lexicographicaly in this alien language.
 *
 * Example 1:
 *
 * Input: words = ["hello","leetcode"], order = "hlabcdefgijkmnopqrstuvwxyz"
 * Output: true
 * Explanation: As 'h' comes before 'l' in this language, then the sequence is sorted.
 *
 * Example 2:
 *
 * Input: words = ["word","world","row"], order = "worldabcefghijkmnpqstuvxyz"
 * Output: false
 * Explanation: As 'd' comes after 'l' in this language, then words[0] > words[1], hence the sequence is unsorted.
 *
 * Example 3:
 *
 * Input: words = ["apple","app"], order = "abcdefghijklmnopqrstuvwxyz"
 * Output: false
 * Explanation: The first three characters "app" match, and the second string is shorter (in size.) According to
 * lexicographical rules "apple" > "app", because 'l' > '∅', where '∅' is defined as the blank character
 * which is less than any other character (More info).
 *
 * Constraints:
 *
 * 1 <= words.length <= 100
 * 1 <= words[i].length <= 20
 * order.length == 26
 * All characters in words[i] and order are English lowercase letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q953_VerifyingAnAlienDictionary {

    @Answer
    public boolean isAlienSorted(String[] words, String order) {
        int[] sort = new int[26];
        for (int i = 0; i < 26; i++) {
            sort[order.charAt(i) - 'a'] = i;
        }
        for (int i = 1; i < words.length; i++) {
            int j = 0;
            while (j < words[i - 1].length() && j < words[i].length()) {
                int prevSort = sort[words[i - 1].charAt(j) - 'a'];
                int currSort = sort[words[i].charAt(j) - 'a'];
                if (prevSort > currSort) {
                    return false;
                }
                if (prevSort < currSort) {
                    j = words[i - 1].length();
                }
                j++;
            }
            if (j < words[i - 1].length()) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{"hello", "leetcode"}, "hlabcdefgijkmnopqrstuvwxyz")
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{"word", "world", "row"}, "worldabcefghijkmnpqstuvxyz")
            .expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new String[]{"apple", "app"}, "abcdefghijklmnopqrstuvwxyz")
            .expect(false);

}
