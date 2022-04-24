package q1900;

import java.math.MathContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.junit.runner.RunWith;
import util.provided.Pair;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Easy] 1859. Sorting the Sentence
 * https://leetcode.com/problems/sorting-the-sentence/
 *
 * A sentence is a list of words that are separated by a single space with no leading or trailing spaces. Each word
 * consists of lowercase and uppercase English letters.
 *
 * A sentence can be shuffled by appending the 1-indexed word position to each word then rearranging the words in the
 * sentence.
 *
 * For example, the sentence "This is a sentence" can be shuffled as "sentence4 a3 is2 This1" or "is2 sentence4 This1
 * a3".
 *
 * Given a shuffled sentence s containing no more than 9 words, reconstruct and return the original sentence.
 *
 * Example 1:
 *
 * Input: s = "is2 sentence4 This1 a3"
 * Output: "This is a sentence"
 * Explanation: Sort the words in s to their original positions "This1 is2 a3 sentence4", then remove the numbers.
 *
 * Example 2:
 *
 * Input: s = "Myself2 Me1 I4 and3"
 * Output: "Me Myself and I"
 * Explanation: Sort the words in s to their original positions "Me1 Myself2 and3 I4", then remove the numbers.
 *
 * Constraints:
 *
 * 2 <= s.length <= 200
 * s consists of lowercase and uppercase English letters, spaces, and digits from 1 to 9.
 * The number of words in s is between 1 and 9.
 * The words in s are separated by a single space.
 * s contains no leading or trailing spaces.
 */
@RunWith(LeetCodeRunner.class)
public class Q1859_SortingTheSentence {

    @Answer
    public String sortSentence(String s) {
        return Arrays.stream(s.split(" "))
                .sorted(Comparator.comparingInt(str -> str.charAt(str.length() - 1)))
                .map(str -> str.substring(0, str.length() - 1))
                .collect(Collectors.joining(" "));
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("is2 sentence4 This1 a3").expect("This is a sentence");

    @TestData
    public DataExpectation example2 = DataExpectation.create("Myself2 Me1 I4 and3").expect("Me Myself and I");

}
