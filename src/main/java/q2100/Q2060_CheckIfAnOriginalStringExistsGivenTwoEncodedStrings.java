package q2100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Hard] 2060. Check if an Original String Exists Given Two Encoded Strings</h3>
 * <a href="https://leetcode.com/problems/check-if-an-original-string-exists-given-two-encoded-strings/">
 * https://leetcode.com/problems/check-if-an-original-string-exists-given-two-encoded-strings/
 * </a><br/>
 *
 * <p>An original string, consisting of lowercase English letters, can be encoded by the following steps:</p>
 *
 * <ul>
 * 	<li>Arbitrarily <strong>split</strong> it into a <strong>sequence</strong> of some number of
 * 	<strong>non-empty</strong> substrings.</li>
 * 	<li>Arbitrarily choose some elements (possibly none) of the sequence, and <strong>replace</strong> each with
 * 	<strong>its length</strong> (as a numeric string).</li>
 * 	<li><strong>Concatenate</strong> the sequence as the encoded string.</li>
 * </ul>
 *
 * <p>For example, <strong>one way</strong> to encode an original string <code>&quot;abcdefghijklmnop&quot;</code>
 * might be:</p>
 *
 * <ul>
 * 	<li>Split it as a sequence: <code>[&quot;ab&quot;, &quot;cdefghijklmn&quot;, &quot;o&quot;, &quot;p&quot;]</code>
 * 	.</li>
 * 	<li>Choose the second and third elements to be replaced by their lengths, respectively. The sequence becomes
 * 	<code>[&quot;ab&quot;, &quot;12&quot;, &quot;1&quot;, &quot;p&quot;]</code>.</li>
 * 	<li>Concatenate the elements of the sequence to get the encoded string: <code>&quot;ab121p&quot;</code>.</li>
 * </ul>
 *
 * <p>Given two encoded strings <code>s1</code> and <code>s2</code>, consisting of lowercase English letters and
 * digits <code>1-9</code> (inclusive), return <code>true</code><em> if there exists an original string that could be
 * encoded as <strong>both</strong> </em><code>s1</code><em> and </em><code>s2</code><em>. Otherwise, return
 * </em><code>false</code>.</p>
 *
 * <p><strong>Note</strong>: The test cases are generated such that the number of consecutive digits in
 * <code>s1</code> and <code>s2</code> does not exceed <code>3</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> s1 = &quot;internationalization&quot;, s2 = &quot;i18n&quot;
 * <strong>Output:</strong> true
 * <strong>Explanation:</strong> It is possible that &quot;internationalization&quot; was the original string.
 * - &quot;internationalization&quot;
 *   -&gt; Split:       [&quot;internationalization&quot;]
 *   -&gt; Do not replace any element
 *   -&gt; Concatenate:  &quot;internationalization&quot;, which is s1.
 * - &quot;internationalization&quot;
 *   -&gt; Split:       [&quot;i&quot;, &quot;nternationalizatio&quot;, &quot;n&quot;]
 *   -&gt; Replace:     [&quot;i&quot;, &quot;18&quot;,                 &quot;n&quot;]
 *   -&gt; Concatenate:  &quot;i18n&quot;, which is s2
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> s1 = &quot;l123e&quot;, s2 = &quot;44&quot;
 * <strong>Output:</strong> true
 * <strong>Explanation:</strong> It is possible that &quot;leetcode&quot; was the original string.
 * - &quot;leetcode&quot;
 *   -&gt; Split:      [&quot;l&quot;, &quot;e&quot;, &quot;et&quot;, &quot;cod&quot;, &quot;e&quot;]
 *   -&gt; Replace:    [&quot;l&quot;, &quot;1&quot;, &quot;2&quot;,  &quot;3&quot;,   &quot;e&quot;]
 *   -&gt; Concatenate: &quot;l123e&quot;, which is s1.
 * - &quot;leetcode&quot;
 *   -&gt; Split:      [&quot;leet&quot;, &quot;code&quot;]
 *   -&gt; Replace:    [&quot;4&quot;,    &quot;4&quot;]
 *   -&gt; Concatenate: &quot;44&quot;, which is s2.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> s1 = &quot;a5b&quot;, s2 = &quot;c5b&quot;
 * <strong>Output:</strong> false
 * <strong>Explanation:</strong> It is impossible.
 * - The original string encoded as s1 must start with the letter &#39;a&#39;.
 * - The original string encoded as s2 must start with the letter &#39;c&#39;.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= s1.length, s2.length &lt;= 40</code></li>
 * 	<li><code>s1</code> and <code>s2</code> consist of digits <code>1-9</code> (inclusive), and lowercase English
 * 	letters only.</li>
 * 	<li>The number of consecutive digits in <code>s1</code> and <code>s2</code> does not exceed <code>3</code>.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2060_CheckIfAnOriginalStringExistsGivenTwoEncodedStrings {

    // dfs
    @Answer
    public boolean possiblyEquals(String s1, String s2) {
        final char[] cs1 = s1.toCharArray();
        final char[] cs2 = s2.toCharArray();
        boolean[][][] cache = new boolean[cs1.length + 1][cs2.length + 1][2000];
        return dfs(cs1, 0, cs2, 0, 0, cache);
    }

    // cs1[i] 和 cs2[j] 如果都是字符, 则需要判断是否相等, 不等则无法转换.
    // 如果一个是数字, 则可以扩展这个长度, 和另一个字符进行泛匹配, 一直到无法匹配为止.
    // 如果都是数字, 则扩展这个长度, 判断哪个能匹配.
    private boolean dfs(char[] cs1, final int i, char[] cs2, int j, int spare, boolean[][][] cache) {
        final int m = cs1.length;
        final int n = cs2.length;

        if (spare > 0) {
            if (j == n) {
                return false;
            }
            if (cache[i][j][spare + 1000]) {
                return false;
            }

            // cs1 中还有多余的数字没有用完
            if (isChar(cs2[j])) {
                return dfs(cs1, i, cs2, j + 1, spare - 1, cache);
            } else {
                int num = 0;
                for (int k = j; k < n && isNum(cs2[k]); k++) {
                    num = num * 10 + cs2[k] - '0';
                    if (dfs(cs1, i, cs2, k + 1, spare - num, cache)) {
                        return true;
                    }
                }
                cache[i][j][spare + 1000] = true;
                return false;
            }
        }
        if (spare < 0) {
            // cs2 中还有多余的数字没有用完
            if (i == m) {
                return false;
            }
            if (cache[i][j][spare + 1000]) {
                return false;
            }
            // cs1 中还有多余的数字没有用完
            if (isChar(cs1[i])) {
                return dfs(cs1, i + 1, cs2, j, spare + 1, cache);
            } else {
                int num = 0;
                for (int k = i; k < m && isNum(cs1[k]); k++) {
                    num = num * 10 + cs1[k] - '0';
                    if (dfs(cs1, k + 1, cs2, j, spare + num, cache)) {
                        return true;
                    }
                }
                cache[i][j][spare + 1000] = true;
                return false;
            }
        }

        if (i == m || j == n) {
            return i == m && j == n;
        }

        if (isChar(cs1[i]) && isChar(cs2[j])) {
            // 都是字符, 需要判断是否相等
            if (cs1[i] != cs2[j]) {
                cache[i][j][spare + 1000] = true;
                return false;
            }
            return dfs(cs1, i + 1, cs2, j + 1, 0, cache);
        }

        if (isNum(cs1[i])) {
            // cs1 是数字
            int num = 0;
            for (int k = i; k < m && isNum(cs1[k]); k++) {
                num = num * 10 + cs1[k] - '0';
                if (dfs(cs1, k + 1, cs2, j, num, cache)) {
                    return true;
                }
            }
            cache[i][j][spare + 1000] = true;
            return false;
        } else {
            // cs2 是数字
            int num = 0;
            for (int k = j; k < n && isNum(cs2[k]); k++) {
                num = num * 10 + cs2[k] - '0';
                if (dfs(cs1, i, cs2, k + 1, -num, cache)) {
                    return true;
                }
            }
            cache[i][j][spare + 1000] = true;
            return false;
        }
    }

    private boolean isNum(char c) {
        return c <= '9';
    }

    private boolean isChar(char c) {
        return c > '9';
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("internationalization", "i18n").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("l123e", "44").expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("a5b", "c5b").expect(false);

    @TestData
    public DataExpectation overtime = DataExpectation
            .createWith("v375v736v443v897v633v527v781v121v317", "475v899v753v784v438v415v431v216v968")
            .expect(false);

}
