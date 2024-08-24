package q2150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Easy] 2114. Maximum Number of Words Found in Sentences</h3>
 * <a href="https://leetcode.com/problems/maximum-number-of-words-found-in-sentences/">
 * https://leetcode.com/problems/maximum-number-of-words-found-in-sentences/
 * </a><br/>
 *
 * <p>A <strong>sentence</strong> is a list of <strong>words</strong> that are separated by a single space&nbsp;with no
 * leading or trailing spaces.</p>
 *
 * <p>You are given an array of strings <code>sentences</code>, where each <code>sentences[i]</code> represents a
 * single
 * <strong>sentence</strong>.</p>
 *
 * <p>Return <em>the <strong>maximum number of words</strong> that appear in a single sentence</em>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> sentences = [&quot;alice and bob love leetcode&quot;, &quot;i think so too&quot;, <u>&quot;this is great thanks very much&quot;</u>]
 * <strong>Output:</strong> 6
 * <strong>Explanation:</strong>
 * - The first sentence, &quot;alice and bob love leetcode&quot;, has 5 words in total.
 * - The second sentence, &quot;i think so too&quot;, has 4 words in total.
 * - The third sentence, &quot;this is great thanks very much&quot;, has 6 words in total.
 * Thus, the maximum number of words in a single sentence comes from the third sentence, which has 6 words.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> sentences = [&quot;please wait&quot;, <u>&quot;continue to fight&quot;</u>, <u>&quot;continue to win&quot;</u>]
 * <strong>Output:</strong> 3
 * <strong>Explanation:</strong> It is possible that multiple sentences contain the same number of words.
 * In this example, the second and third sentences (underlined) have the same number of words.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= sentences.length &lt;= 100</code></li>
 * 	<li><code>1 &lt;= sentences[i].length &lt;= 100</code></li>
 * 	<li><code>sentences[i]</code> consists only of lowercase English letters and <code>&#39; &#39;</code> only.</li>
 * 	<li><code>sentences[i]</code> does not have leading or trailing spaces.</li>
 * 	<li>All the words in <code>sentences[i]</code> are separated by a single space.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2114_MaximumNumberOfWordsFoundInSentences {

    @Answer
    public int mostWordsFound(String[] sentences) {
        int res = 0;
        for (String sentence : sentences) {
            int count = 1;
            for (int i = 0; i < sentence.length(); i++) {
                if (sentence.charAt(i) == ' ') {
                    count++;
                }
            }
            res = Math.max(res, count);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new String[]{"alice and bob love leetcode", "i think so too", "this is great thanks very much"})
            .expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new String[]{"please wait", "continue to fight", "continue to win"})
            .expect(3);

}
