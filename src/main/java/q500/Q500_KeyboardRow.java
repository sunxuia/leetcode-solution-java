package q500;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/keyboard-row/
 *
 * Given a List of words, return the words that can be typed using letters of alphabet on only one row's of American
 * keyboard like the image below.
 *
 * (图 Q500_PIC.png)
 *
 * Example:
 *
 * Input: ["Hello", "Alaska", "Dad", "Peace"]
 * Output: ["Alaska", "Dad"]
 *
 *
 * Note:
 *
 * You may use one character in the keyboard more than once.
 * You may assume the input string will only contain letters of alphabet.
 */
@RunWith(LeetCodeRunner.class)
public class Q500_KeyboardRow {

    @Answer
    public String[] findWords(String[] words) {
        List<String> list = new ArrayList<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                int idx = ROWS[Character.toLowerCase(word.charAt(0)) - 'a'];
                for (int i = 1; i < word.length(); i++) {
                    if (idx != ROWS[Character.toLowerCase(word.charAt(i)) - 'a']) {
                        idx = -1;
                        break;
                    }
                }
                if (idx >= 0) {
                    list.add(word);
                }
            }
        }
        return list.toArray(new String[0]);
    }

    private static int[] ROWS = new int[]{2, 1, 1, 2, 3, 2, 2, 2, 3, 2, 2, 2, 1, 1, 3, 3, 3, 3, 2, 3, 3, 1, 3, 1, 3, 1};

    @TestData
    public DataExpectation example = DataExpectation
            .create(new String[]{"Hello", "Alaska", "Dad", "Peace"})
            .expect(new String[]{"Alaska", "Dad"});

}
