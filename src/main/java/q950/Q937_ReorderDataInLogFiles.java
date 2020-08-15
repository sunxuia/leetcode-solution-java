package q950;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 937. Reorder Data in Log Files
 * https://leetcode.com/problems/reorder-data-in-log-files/
 *
 * You have an array of logs.  Each log is a space delimited string of words.
 *
 * For each log, the first word in each log is an alphanumeric identifier.  Then, either:
 *
 * Each word after the identifier will consist only of lowercase letters, or;
 * Each word after the identifier will consist only of digits.
 *
 * We will call these two varieties of logs letter-logs and digit-logs.  It is guaranteed that each log has at least one
 * word after its identifier.
 *
 * Reorder the logs so that all of the letter-logs come before any digit-log.  The letter-logs are ordered
 * lexicographically ignoring identifier, with the identifier used in case of ties.  The digit-logs should be put in
 * their original order.
 *
 * Return the final order of the logs.
 *
 * Example 1:
 * Input: logs = ["dig1 8 1 5 1","let1 art can","dig2 3 6","let2 own kit dig","let3 art zero"]
 * Output: ["let1 art can","let3 art zero","let2 own kit dig","dig1 8 1 5 1","dig2 3 6"]
 *
 * Constraints:
 *
 * 0 <= logs.length <= 100
 * 3 <= logs[i].length <= 100
 * logs[i] is guaranteed to have an identifier, and a word after the identifier.
 */
@RunWith(LeetCodeRunner.class)
public class Q937_ReorderDataInLogFiles {

    @TestData
    public DataExpectation example = DataExpectation
            .create(new String[]{"dig1 8 1 5 1", "let1 art can", "dig2 3 6", "let2 own kit dig", "let3 art zero"})
            .expect(new String[]{"let1 art can", "let3 art zero", "let2 own kit dig", "dig1 8 1 5 1", "dig2 3 6"});

    @Answer
    public String[] reorderLogFiles(String[] logs) {
        int line = logs.length - 1;
        for (int i = logs.length - 1; i >= 0; i--) {
            String text = logs[i].substring(logs[i].indexOf(' ') + 1);
            if (Character.isDigit(text.charAt(0))) {
                String t = logs[i];
                logs[i] = logs[line];
                logs[line] = t;
                line--;
            }
        }

        Arrays.sort(logs, 0, line + 1, (a, b) -> {
            String ta = a.substring(a.indexOf(' ') + 1);
            String tb = b.substring(b.indexOf(' ') + 1);
            if (ta.equals(tb)) {
                return a.compareTo(b);
            } else {
                return ta.compareTo(tb);
            }
        });
        return logs;
    }

}
