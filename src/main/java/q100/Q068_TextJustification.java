package q100;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/text-justification/
 *
 * Given an array of words and a width maxWidth, format the text such that each line has exactly maxWidth characters
 * and is fully (left and right) justified.
 *
 * You should pack your words in a greedy approach; that is, pack as many words as you can in each line. Pad extra
 * spaces ' ' when necessary so that each line has exactly maxWidth characters.
 *
 * Extra spaces between words should be distributed as evenly as possible. If the number of spaces on a line do not
 * divide evenly between words, the empty slots on the left will be assigned more spaces than the slots on the right.
 *
 * For the last line of text, it should be left justified and no extra space is inserted between words.
 *
 * Note:
 *
 * A word is defined as a character sequence consisting of non-space characters only.
 * Each word's length is guaranteed to be greater than 0 and not exceed maxWidth.
 * The input array words contains at least one word.
 * Example 1:
 *
 * Input:
 * words = ["This", "is", "an", "example", "of", "text", "justification."]
 * maxWidth = 16
 * Output:
 * [
 * "This    is    an",
 * "example  of text",
 * "justification.  "
 * ]
 * Example 2:
 *
 * Input:
 * words = ["What","must","be","acknowledgment","shall","be"]
 * maxWidth = 16
 * Output:
 * [
 * "What   must   be",
 * "acknowledgment  ",
 * "shall be        "
 * ]
 * Explanation: Note that the last line is "shall be    " instead of "shall     be",
 * because the last line must be left-justified instead of fully-justified.
 * Note that the second line is also left-justified becase it contains only one word.
 * Example 3:
 *
 * Input:
 * words = ["Science","is","what","we","understand","well","enough","to","explain",
 * "to","a","computer.","Art","is","everything","else","we","do"]
 * maxWidth = 20
 * Output:
 * [
 * "Science  is  what we",
 * "understand      well",
 * "enough to explain to",
 * "a  computer.  Art is",
 * "everything  else  we",
 * "do                  "
 * ]
 */
@RunWith(LeetCodeRunner.class)
public class Q068_TextJustification {

    @Answer
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new ArrayList<>();
        int scan = 0;
        StringBuilder sb = new StringBuilder(maxWidth * 2);
        for (int i = 0, length = 0; i < words.length; i++) {
            if (length + i - scan + words[i].length() > maxWidth) {
                sb.setLength(0);
                if (scan == i - 1) {
                    sb.append(words[scan]);
                    fillSpace(sb, maxWidth - sb.length());
                } else {
                    int extraInterval = (maxWidth - length) / (i - scan - 1);
                    int additionInterval = (maxWidth - length) % (i - scan - 1);
                    for (int j = scan; j < i; j++) {
                        sb.append(words[j]);
                        fillSpace(sb, extraInterval + (additionInterval-- > 0 ? 1 : 0));
                    }
                    sb.setLength(maxWidth);
                }
                res.add(sb.toString());
                scan = i;
                length = 0;
            }
            length += words[i].length();
        }

        {
            sb.setLength(0);
            for (int i = scan; i < words.length; i++) {
                sb.append(words[i]).append(' ');
            }
            fillSpace(sb, maxWidth - sb.length());
            sb.setLength(maxWidth);
            res.add(sb.toString());
        }

        return res;
    }

    private void fillSpace(StringBuilder sb, int count) {
        while (count-- > 0) {
            sb.append(' ');
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new String[]{"This", "is", "an", "example", "of", "text", "justification."})
            .addArgument(16)
            .expect(new String[]{
                    "This    is    an",
                    "example  of text",
                    "justification.  "
            }).build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new String[]{"What", "must", "be", "acknowledgment", "shall", "be"})
            .addArgument(16)
            .expect(new String[]{
                    "What   must   be",
                    "acknowledgment  ",
                    "shall be        "
            }).build();

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument(new String[]{
                    "Science", "is", "what", "we", "understand", "well", "enough", "to", "explain",
                    "to", "a", "computer.", "Art", "is", "everything", "else", "we", "do"
            })
            .addArgument(20)
            .expect(new String[]{
                    "Science  is  what we",
                    "understand      well",
                    "enough to explain to",
                    "a  computer.  Art is",
                    "everything  else  we",
                    "do                  "
            }).build();
}
