package q1600;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1592. Rearrange Spaces Between Words
 * https://leetcode.com/problems/rearrange-spaces-between-words/
 *
 * You are given a string text of words that are placed among some number of spaces. Each word consists of one or more
 * lowercase English letters and are separated by at least one space. It's guaranteed that text contains at least one
 * word.
 *
 * Rearrange the spaces so that there is an equal number of spaces between every pair of adjacent words and that number
 * is maximized. If you cannot redistribute all the spaces equally, place the extra spaces at the end, meaning the
 * returned string should be the same length as text.
 *
 * Return the string after rearranging the spaces.
 *
 * Example 1:
 *
 * Input: text = "  this   is  a sentence "
 * Output: "this   is   a   sentence"
 * Explanation: There are a total of 9 spaces and 4 words. We can evenly divide the 9 spaces between the words: 9 /
 * (4-1) = 3 spaces.
 *
 * Example 2:
 *
 * Input: text = " practice   makes   perfect"
 * Output: "practice   makes   perfect "
 * Explanation: There are a total of 7 spaces and 3 words. 7 / (3-1) = 3 spaces plus 1 extra space. We place this extra
 * space at the end of the string.
 *
 * Example 3:
 *
 * Input: text = "hello   world"
 * Output: "hello   world"
 *
 * Example 4:
 *
 * Input: text = "  walks  udp package   into  bar a"
 * Output: "walks  udp  package  into  bar  a "
 *
 * Example 5:
 *
 * Input: text = "a"
 * Output: "a"
 *
 * Constraints:
 *
 * 1 <= text.length <= 100
 * text consists of lowercase English letters and ' '.
 * text contains at least one word.
 */
@RunWith(LeetCodeRunner.class)
public class Q1592_RearrangeSpacesBetweenWords {

    @Answer
    public String reorderSpaces(String text) {
        final int n = text.length();
        final char[] sc = text.toCharArray();
        int space = 0, word = 0;
        for (int i = 0; i < n; i++) {
            if (sc[i] == ' ') {
                space++;
            } else if (i == 0 || sc[i - 1] == ' ') {
                word++;
            }
        }
        int interval = word < 2 ? space : space / (word - 1);

        char[] ret = new char[n];
        Arrays.fill(ret, ' ');
        for (int i = 0, j = 0; i < n && j < n; i++) {
            if (sc[i] != ' ') {
                ret[j++] = sc[i];
                if (i == n - 1 || sc[i + 1] == ' ') {
                    j += interval;
                }
            }
        }
        return new String(ret);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create("  this   is  a sentence ")
            .expect("this   is   a   sentence");

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(" practice   makes   perfect")
            .expect("practice   makes   perfect ");

    @TestData
    public DataExpectation example3 = DataExpectation
            .create("hello   world")
            .expect("hello   world");

    @TestData
    public DataExpectation example4 = DataExpectation
            .create("  walks  udp package   into  bar a")
            .expect("walks  udp  package  into  bar  a ");

    @TestData
    public DataExpectation example5 = DataExpectation.create("a").expect("a");

    @TestData
    public DataExpectation normal1 = DataExpectation.create("  hello").expect("hello  ");

    @TestData
    public DataExpectation normal2 = DataExpectation.create("a b   c d").expect("a b c d  ");

}
