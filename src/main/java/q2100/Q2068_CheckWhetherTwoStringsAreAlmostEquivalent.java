package q2100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Easy] 2068. Check Whether Two Strings are Almost Equivalent</h3>
 * <a href="https://leetcode.com/problems/check-whether-two-strings-are-almost-equivalent/">
 * https://leetcode.com/problems/check-whether-two-strings-are-almost-equivalent/
 * </a><br/>
 *
 * <p>Two strings <code>word1</code> and <code>word2</code> are considered <strong>almost equivalent</strong> if the
 * differences between the frequencies of each letter from <code>&#39;a&#39;</code> to <code>&#39;z&#39;</code> between
 * <code>word1</code> and <code>word2</code> is <strong>at most</strong> <code>3</code>.</p>
 *
 * <p>Given two strings <code>word1</code> and <code>word2</code>, each of length <code>n</code>, return
 * <code>true</code> <em>if </em><code>word1</code> <em>and</em> <code>word2</code> <em>are <strong>almost
 * equivalent</strong>, or</em> <code>false</code> <em>otherwise</em>.</p>
 *
 * <p>The <strong>frequency</strong> of a letter <code>x</code> is the number of times it occurs in the string.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> word1 = &quot;aaaa&quot;, word2 = &quot;bccb&quot;
 * <strong>Output:</strong> false
 * <strong>Explanation:</strong> There are 4 &#39;a&#39;s in &quot;aaaa&quot; but 0 &#39;a&#39;s in &quot;bccb&quot;.
 * The difference is 4, which is more than the allowed 3.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> word1 = &quot;abcdeef&quot;, word2 = &quot;abaaacc&quot;
 * <strong>Output:</strong> true
 * <strong>Explanation:</strong> The differences between the frequencies of each letter in word1 and word2 are at most 3:
 * - &#39;a&#39; appears 1 time in word1 and 4 times in word2. The difference is 3.
 * - &#39;b&#39; appears 1 time in word1 and 1 time in word2. The difference is 0.
 * - &#39;c&#39; appears 1 time in word1 and 2 times in word2. The difference is 1.
 * - &#39;d&#39; appears 1 time in word1 and 0 times in word2. The difference is 1.
 * - &#39;e&#39; appears 2 times in word1 and 0 times in word2. The difference is 2.
 * - &#39;f&#39; appears 1 time in word1 and 0 times in word2. The difference is 1.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> word1 = &quot;cccddabba&quot;, word2 = &quot;babababab&quot;
 * <strong>Output:</strong> true
 * <strong>Explanation:</strong> The differences between the frequencies of each letter in word1 and word2 are at most 3:
 * - &#39;a&#39; appears 2 times in word1 and 4 times in word2. The difference is 2.
 * - &#39;b&#39; appears 2 times in word1 and 5 times in word2. The difference is 3.
 * - &#39;c&#39; appears 3 times in word1 and 0 times in word2. The difference is 3.
 * - &#39;d&#39; appears 2 times in word1 and 0 times in word2. The difference is 2.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>n == word1.length == word2.length</code></li>
 * 	<li><code>1 &lt;= n &lt;= 100</code></li>
 * 	<li><code>word1</code> and <code>word2</code> consist only of lowercase English letters.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2068_CheckWhetherTwoStringsAreAlmostEquivalent {

    @Answer
    public boolean checkAlmostEquivalent(String word1, String word2) {
        int[] counts = new int[26];
        for (int i = 0; i < word1.length(); i++) {
            counts[word1.charAt(i) - 'a']++;
        }
        for (int i = 0; i < word2.length(); i++) {
            counts[word2.charAt(i) - 'a']--;
        }

        for (int i = 0; i < 26; i++) {
            if (Math.abs(counts[i]) > 3) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("aaaa", "bccb").expect(false);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("abcdeef", "abaaacc").expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("cccddabba", "babababab").expect(true);

}
