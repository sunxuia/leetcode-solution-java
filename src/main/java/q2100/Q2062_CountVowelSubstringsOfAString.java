package q2100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Easy] 2062. Count Vowel Substrings of a String</h3>
 * <a href="https://leetcode.com/problems/count-vowel-substrings-of-a-string/">
 * https://leetcode.com/problems/count-vowel-substrings-of-a-string/
 * </a><br/>
 *
 * <p>A <strong>substring</strong> is a contiguous (non-empty) sequence of characters within a string.</p>
 *
 * <p>A <strong>vowel substring</strong> is a substring that <strong>only</strong> consists of vowels
 * (<code>&#39;a&#39;</code>, <code>&#39;e&#39;</code>, <code>&#39;i&#39;</code>, <code>&#39;o&#39;</code>, and
 * <code>&#39;u&#39;</code>) and has <strong>all five</strong> vowels present in it.</p>
 *
 * <p>Given a string <code>word</code>, return <em>the number of <strong>vowel substrings</strong> in</em>
 * <code>word</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> word = &quot;aeiouu&quot;
 * <strong>Output:</strong> 2
 * <strong>Explanation:</strong> The vowel substrings of word are as follows (underlined):
 * - &quot;<strong><u>aeiou</u></strong>u&quot;
 * - &quot;<strong><u>aeiouu</u></strong>&quot;
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> word = &quot;unicornarihan&quot;
 * <strong>Output:</strong> 0
 * <strong>Explanation:</strong> Not all 5 vowels are present, so there are no vowel substrings.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> word = &quot;cuaieuouac&quot;
 * <strong>Output:</strong> 7
 * <strong>Explanation:</strong> The vowel substrings of word are as follows (underlined):
 * - &quot;c<strong><u>uaieuo</u></strong>uac&quot;
 * - &quot;c<strong><u>uaieuou</u></strong>ac&quot;
 * - &quot;c<strong><u>uaieuoua</u></strong>c&quot;
 * - &quot;cu<strong><u>aieuo</u></strong>uac&quot;
 * - &quot;cu<strong><u>aieuou</u></strong>ac&quot;
 * - &quot;cu<strong><u>aieuoua</u></strong>c&quot;
 * - &quot;cua<strong><u>ieuoua</u></strong>c&quot;
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= word.length &lt;= 100</code></li>
 * 	<li><code>word</code> consists of lowercase English letters only.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2062_CountVowelSubstringsOfAString {

    @Answer
    public int countVowelSubstrings(String word) {
        int res = 0;
        // last[0] 表示 word[0, i] 中最后一个非元音字符下标(word 下标从 1 开始, 下同),
        // last[1:5] 表示 word[0, i] 中最后一个 aeiou 字符下标.
        int[] last = new int[6];
        for (int i = 1; i <= word.length(); i++) {
            int type = TYPES[word.charAt(i - 1) - 'a'];
            last[type] = i;
            // word[min, i] 是最小元音都全的子字符串,
            // word(last[0], i] 是最大全元音子字符串,
            // 如果 min < last[0] 说明小元音都全的子字符串中含有非元音字符, 不符合题意
            int min = Math.min(last[1], last[2]);
            min = Math.min(min, last[3]);
            min = Math.min(min, last[4]);
            min = Math.min(min, last[5]);
            res += Math.max(0, min - last[0]);
        }
        return res;
    }

    private static int[] TYPES = new int[26];

    static {
        TYPES['a' - 'a'] = 1;
        TYPES['e' - 'a'] = 2;
        TYPES['i' - 'a'] = 3;
        TYPES['o' - 'a'] = 4;
        TYPES['u' - 'a'] = 5;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("aeiouu").expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create("unicornarihan").expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create("cuaieuouac").expect(7);

}
