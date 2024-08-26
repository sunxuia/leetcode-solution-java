package q2150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Easy] 2124. Check if All A's Appears Before All B's</h3>
 * <a href="https://leetcode.com/problems/check-if-all-as-appears-before-all-bs/">
 * https://leetcode.com/problems/check-if-all-as-appears-before-all-bs/
 * </a><br/>
 *
 * <p>Given a string <code>s</code> consisting of <strong>only</strong> the characters <code>&#39;a&#39;</code> and
 * <code>&#39;b&#39;</code>, return <code>true</code> <em>if <strong>every</strong> </em><code>&#39;a&#39;</code>
 * <em>appears before <strong>every</strong> </em><code>&#39;b&#39;</code><em> in the string</em>. Otherwise, return
 * <code>false</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> s = &quot;aaabbb&quot;
 * <strong>Output:</strong> true
 * <strong>Explanation:</strong>
 * The &#39;a&#39;s are at indices 0, 1, and 2, while the &#39;b&#39;s are at indices 3, 4, and 5.
 * Hence, every &#39;a&#39; appears before every &#39;b&#39; and we return true.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> s = &quot;abab&quot;
 * <strong>Output:</strong> false
 * <strong>Explanation:</strong>
 * There is an &#39;a&#39; at index 2 and a &#39;b&#39; at index 1.
 * Hence, not every &#39;a&#39; appears before every &#39;b&#39; and we return false.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> s = &quot;bbb&quot;
 * <strong>Output:</strong> true
 * <strong>Explanation:</strong>
 * There are no &#39;a&#39;s, hence, every &#39;a&#39; appears before every &#39;b&#39; and we return true.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= s.length &lt;= 100</code></li>
 * 	<li><code>s[i]</code> is either <code>&#39;a&#39;</code> or <code>&#39;b&#39;</code>.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2124_CheckIfAllAsAppearsBeforeAllBs {

    @Answer
    public boolean checkString(String s) {
        int a = s.lastIndexOf('a');
        int b = s.indexOf('b');
        return a == -1 || b == -1 || a < b;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("aaabbb").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create("abab").expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create("bbb").expect(true);

    @TestData
    public DataExpectation border1 = DataExpectation.create("a").expect(true);

    @TestData
    public DataExpectation border2 = DataExpectation.create("b").expect(true);

}
