package q750;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/shortest-completing-word/
 *
 * Find the minimum length word from a given dictionary words, which has all the letters from the string
 * licensePlate. Such a word is said to complete the given string licensePlate
 *
 * Here, for letters we ignore case. For example, "P" on the licensePlate still matches "p" on the word.
 *
 * It is guaranteed an answer exists. If there are multiple answers, return the one that occurs first in the array.
 *
 * The license plate might have the same letter occurring multiple times. For example, given a licensePlate of "PP",
 * the word "pair" does not complete the licensePlate, but the word "supper" does.
 *
 * Example 1:
 *
 * Input: licensePlate = "1s3 PSt", words = ["step", "steps", "stripe", "stepple"]
 * Output: "steps"
 * Explanation: The smallest length word that contains the letters "S", "P", "S", and "T".
 * Note that the answer is not "step", because the letter "s" must occur in the word twice.
 * Also note that we ignored case for the purposes of comparing whether a letter exists in the word.
 *
 * Example 2:
 *
 * Input: licensePlate = "1s3 456", words = ["looks", "pest", "stew", "show"]
 * Output: "pest"
 * Explanation: There are 3 smallest length words that contains the letters "s".
 * We return the one that occurred first.
 *
 * Note:
 *
 * licensePlate will be a string with length in range [1, 7].
 * licensePlate will contain digits, spaces, or letters (uppercase or lowercase).
 * words will have a length in the range [10, 1000].
 * Every words[i] will consist of lowercase letters, and have length in range [1, 15].
 */
@RunWith(LeetCodeRunner.class)
public class Q748_ShortestCompletingWord {

    @Answer
    public String shortestCompletingWord(String licensePlate, String[] words) {
        int[] map = new int[26];
        int sum = 0, next = 1;
        for (int i = 0; i < licensePlate.length(); i++) {
            char c = licensePlate.charAt(i);
            int idx = c >= 'a' ? c - 'a' : c - 'A';
            if (0 <= idx && idx < 26) {
                if (map[idx] == 0) {
                    map[idx] = next;
                    next *= 8;
                }
                sum += map[idx];
            }
        }

        String res = null;
        int len = Integer.MAX_VALUE;
        for (String word : words) {
            int val = sum;
            for (int i = 0; i < word.length(); i++) {
                int digit = map[word.charAt(i) - 'a'];
//                if ((val & (digit * 7)) > 0) {
//                    val -= digit;
//                }
                // 和上面的if 判断一样的结果
                val -= (1 - 1 / ((val & (digit * 7)) + 1)) * digit;
            }
            if (val == 0 && len > word.length()) {
                res = word;
                len = word.length();
            }
        }
        return res;
    }

    // 比较简单同时也是LeetCode 上比较快的方法, 不需要什么花哨的东西.
    @Answer
    public String shortestCompletingWord2(String licensePlate, String[] words) {
        int[] plate = new int[licensePlate.length()];
        Arrays.fill(plate, -1);
        int plateLen = 0;
        for (int i = 0; i < licensePlate.length(); i++) {
            char c = licensePlate.charAt(i);
            int idx = c >= 'a' ? c - 'a' : c - 'A';
            if (0 <= idx && idx < 26) {
                plate[plateLen++] = idx;
            }
        }

        int[] count = new int[26];
        String res = null;
        int len = Integer.MAX_VALUE;
        for (String word : words) {
            Arrays.fill(count, 0);
            for (int i = 0; i < word.length(); i++) {
                count[word.charAt(i) - 'a']++;
            }

            boolean match = plateLen <= word.length();
            for (int i = 0; match && i < plateLen; i++) {
                match = count[plate[i]]-- > 0;
            }

            if (match && len > word.length()) {
                res = word;
                len = word.length();
            }
        }
        return res;
    }

    @TestData
    public DataExpectation exmaple1 = DataExpectation
            .createWith("1s3 PSt", new String[]{"step", "steps", "stripe", "stepple"})
            .expect("steps");

    @TestData
    public DataExpectation exmaple2 = DataExpectation
            .createWith("1s3 456", new String[]{"looks", "pest", "stew", "show"})
            .expect("pest");

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith("Ar16259",
                    new String[]{"nature", "though", "party", "food", "any", "democratic", "building", "eat",
                            "structure", "our"})
            .expect("party");

}
