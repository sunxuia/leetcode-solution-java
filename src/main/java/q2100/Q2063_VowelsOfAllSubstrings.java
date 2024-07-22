package q2100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2063. Vowels of All Substrings</h3>
 * <a href="https://leetcode.com/problems/vowels-of-all-substrings/">
 * https://leetcode.com/problems/vowels-of-all-substrings/
 * </a><br/>
 *
 * <p>Given a string <code>word</code>, return <em>the <strong>sum of the number of vowels</strong>
 * (</em><code>&#39;a&#39;</code>, <code>&#39;e&#39;</code><em>,</em> <code>&#39;i&#39;</code><em>,</em>
 * <code>&#39;o&#39;</code><em>, and</em> <code>&#39;u&#39;</code><em>)</em> <em>in every substring of
 * </em><code>word</code>.</p>
 *
 * <p>A <strong>substring</strong> is a contiguous (non-empty) sequence of characters within a string.</p>
 *
 * <p><strong>Note:</strong> Due to the large constraints, the answer may not fit in a signed 32-bit integer. Please be
 * careful during the calculations.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> word = &quot;aba&quot;
 * <strong>Output:</strong> 6
 * <strong>Explanation:</strong>
 * All possible substrings are: &quot;a&quot;, &quot;ab&quot;, &quot;aba&quot;, &quot;b&quot;, &quot;ba&quot;, and &quot;a&quot;.
 * - &quot;b&quot; has 0 vowels in it
 * - &quot;a&quot;, &quot;ab&quot;, &quot;ba&quot;, and &quot;a&quot; have 1 vowel each
 * - &quot;aba&quot; has 2 vowels in it
 * Hence, the total sum of vowels = 0 + 1 + 1 + 1 + 1 + 2 = 6.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> word = &quot;abc&quot;
 * <strong>Output:</strong> 3
 * <strong>Explanation:</strong>
 * All possible substrings are: &quot;a&quot;, &quot;ab&quot;, &quot;abc&quot;, &quot;b&quot;, &quot;bc&quot;, and &quot;c&quot;.
 * - &quot;a&quot;, &quot;ab&quot;, and &quot;abc&quot; have 1 vowel each
 * - &quot;b&quot;, &quot;bc&quot;, and &quot;c&quot; have 0 vowels each
 * Hence, the total sum of vowels = 1 + 1 + 1 + 0 + 0 + 0 = 3.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> word = &quot;ltcd&quot;
 * <strong>Output:</strong> 0
 * <strong>Explanation:</strong> There are no vowels in any substring of &quot;ltcd&quot;.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= word.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>word</code> consists of lowercase English letters.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2063_VowelsOfAllSubstrings {

    // 以下标i 结尾的 word 子字符串 word[0~i, i] 字符串总共有 i+1 个.
    // 因此如果 word[i] 是元音字符, word[0~i, i] 就相比 word[0~i-1,i-1] 新增 i+1 个元音, 否则与 word[0~i-1,i-1] 相同.
    @Answer
    public long countVowels(String word) {
        long res = 0, count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (VOWEL[word.charAt(i) - 'a']) {
                count += i + 1;
            }
            res += count;
        }
        return res;
    }

    private static final boolean[] VOWEL = new boolean[26];

    static {
        VOWEL['a' - 'a'] = true;
        VOWEL['e' - 'a'] = true;
        VOWEL['i' - 'a'] = true;
        VOWEL['o' - 'a'] = true;
        VOWEL['u' - 'a'] = true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("aba").expect(6L);

    @TestData
    public DataExpectation example2 = DataExpectation.create("abc").expect(3L);

    @TestData
    public DataExpectation example3 = DataExpectation.create("ltcd").expect(0L);

}
