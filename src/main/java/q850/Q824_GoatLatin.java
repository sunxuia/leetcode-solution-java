package q850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/goat-latin/
 *
 * A sentence S is given, composed of words separated by spaces. Each word consists of lowercase and uppercase
 * letters only.
 *
 * We would like to convert the sentence to "Goat Latin" (a made-up language similar to Pig Latin.)
 *
 * The rules of Goat Latin are as follows:
 *
 * If a word begins with a vowel (a, e, i, o, or u), append "ma" to the end of the word.
 * For example, the word 'apple' becomes 'applema'.
 *
 * If a word begins with a consonant (i.e. not a vowel), remove the first letter and append it to the end, then
 * add "ma".
 * For example, the word "goat" becomes "oatgma".
 *
 * Add one letter 'a' to the end of each word per its word index in the sentence, starting with 1.
 * For example, the first word gets "a" added to the end, the second word gets "aa" added to the end and so on.
 *
 * Return the final sentence representing the conversion from S to Goat Latin.
 *
 *
 *
 * Example 1:
 *
 * Input: "I speak Goat Latin"
 * Output: "Imaa peaksmaaa oatGmaaaa atinLmaaaaa"
 *
 * Example 2:
 *
 * Input: "The quick brown fox jumped over the lazy dog"
 * Output: "heTmaa uickqmaaa rownbmaaaa oxfmaaaaa umpedjmaaaaaa overmaaaaaaa hetmaaaaaaaa azylmaaaaaaaaa ogdmaaaaaaaaaa"
 *
 *
 *
 * Notes:
 *
 * S contains only uppercase, lowercase and spaces. Exactly one space between each word.
 * 1 <= S.length <= 150.
 */
@RunWith(LeetCodeRunner.class)
public class Q824_GoatLatin {

    @Answer
    public String toGoatLatin(String S) {
        StringBuilder resBuilder = new StringBuilder();
        StringBuilder wordBuilder = new StringBuilder();
        char first = ' ', prev = ' ';
        int wordIndex = 0;
        for (int i = 0; i <= S.length(); i++) {
            char c = i == S.length() ? ' ' : S.charAt(i);
            if (c == ' ') {
                if (VOWELS[first]) {
                    resBuilder.append(first);
                    resBuilder.append(wordBuilder);
                } else {
                    resBuilder.append(wordBuilder);
                    resBuilder.append(first);
                }
                resBuilder.append("ma");
                for (int j = 0; j < wordIndex; j++) {
                    resBuilder.append('a');
                }
                resBuilder.append(' ');
                wordBuilder.setLength(0);
            } else {
                if (prev == ' ') {
                    first = c;
                    wordIndex++;
                } else {
                    wordBuilder.append(c);
                }
            }
            prev = c;
        }
        if (resBuilder.length() > 0) {
            resBuilder.setLength(resBuilder.length() - 1);
        }
        return resBuilder.toString();
    }

    private static final boolean[] VOWELS = new boolean[128];

    static {
        VOWELS['a'] = VOWELS['e'] = VOWELS['i'] = VOWELS['o'] = VOWELS['u'] = true;
        VOWELS['A'] = VOWELS['E'] = VOWELS['I'] = VOWELS['O'] = VOWELS['U'] = true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create("I speak Goat Latin")
            .expect("Imaa peaksmaaa oatGmaaaa atinLmaaaaa");

    @TestData
    public DataExpectation example2 = DataExpectation
            .create("The quick brown fox jumped over the lazy dog")
            .expect("heTmaa uickqmaaa rownbmaaaa oxfmaaaaa umpedjmaaaaaa "
                    + "overmaaaaaaa hetmaaaaaaaa azylmaaaaaaaaa ogdmaaaaaaaaaa");

}
