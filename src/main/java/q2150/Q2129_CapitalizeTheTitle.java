package q2150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Easy] 2129. Capitalize the Title</h3>
 * <a href="https://leetcode.com/problems/capitalize-the-title/">
 * https://leetcode.com/problems/capitalize-the-title/
 * </a><br/>
 *
 * <p>You are given a string <code>title</code> consisting of one or more words separated by a single space, where each
 * word consists of English letters. <strong>Capitalize</strong> the string by changing the capitalization of each word
 * such that:</p>
 *
 * <ul>
 * 	<li>If the length of the word is <code>1</code> or <code>2</code> letters, change all letters to lowercase.</li>
 * 	<li>Otherwise, change the first letter to uppercase and the remaining letters to lowercase.</li>
 * </ul>
 *
 * <p>Return <em>the <strong>capitalized</strong> </em><code>title</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> title = &quot;capiTalIze tHe titLe&quot;
 * <strong>Output:</strong> &quot;Capitalize The Title&quot;
 * <strong>Explanation:</strong>
 * Since all the words have a length of at least 3, the first letter of each word is uppercase, and the remaining letters are lowercase.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> title = &quot;First leTTeR of EACH Word&quot;
 * <strong>Output:</strong> &quot;First Letter of Each Word&quot;
 * <strong>Explanation:</strong>
 * The word &quot;of&quot; has length 2, so it is all lowercase.
 * The remaining words have a length of at least 3, so the first letter of each remaining word is uppercase, and the remaining letters are lowercase.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> title = &quot;i lOve leetcode&quot;
 * <strong>Output:</strong> &quot;i Love Leetcode&quot;
 * <strong>Explanation:</strong>
 * The word &quot;i&quot; has length 1, so it is lowercase.
 * The remaining words have a length of at least 3, so the first letter of each remaining word is uppercase, and the remaining letters are lowercase.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= title.length &lt;= 100</code></li>
 * 	<li><code>title</code> consists of words separated by a single space without any leading or trailing spaces.</li>
 * 	<li>Each word consists of uppercase and lowercase English letters and is <strong>non-empty</strong>.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2129_CapitalizeTheTitle {

    @Answer
    public String capitalizeTitle(String title) {
        final int offset = 'a' - 'A';
        final int n = title.length();
        char[] cs = title.toCharArray();
        int prevSpace = -1, nextSpace = 0;
        while (nextSpace < n && cs[nextSpace] != ' ') {
            nextSpace++;
        }
        for (int i = 0; i < n; i++) {
            if (cs[i] == ' ') {
                prevSpace = i;
                nextSpace = i + 1;
                while (nextSpace < n && cs[nextSpace] != ' ') {
                    nextSpace++;
                }
            } else if (nextSpace - prevSpace - 1 <= 2) {
                if (cs[i] <= 'Z') {
                    cs[i] += offset;
                }
            } else if (i == prevSpace + 1) {
                if ('a' <= cs[i]) {
                    cs[i] -= offset;
                }
            } else {
                if (cs[i] <= 'Z') {
                    cs[i] += offset;
                }
            }
        }
        return new String(cs);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("capiTalIze tHe titLe")
            .expect("Capitalize The Title");

    @TestData
    public DataExpectation example2 = DataExpectation.create("First leTTeR of EACH Word")
            .expect("First Letter of Each Word");

    @TestData
    public DataExpectation example3 = DataExpectation.create("i lOve leetcode")
            .expect("i Love Leetcode");

}
