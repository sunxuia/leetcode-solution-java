package q2200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Hard] 2156. Find Substring With Given Hash Value</h3>
 * <a href="https://leetcode.com/problems/find-substring-with-given-hash-value/">
 * https://leetcode.com/problems/find-substring-with-given-hash-value/
 * </a><br/>
 *
 * <p>The hash of a <strong>0-indexed</strong> string <code>s</code> of length <code>k</code>, given integers
 * <code>p</code> and <code>m</code>, is computed using the following function:</p>
 *
 * <ul>
 * 	<li><code>hash(s, p, m) = (val(s[0]) * p<sup>0</sup> + val(s[1]) * p<sup>1</sup> + ... + val(s[k-1]) *
 * 	p<sup>k-1</sup>) mod m</code>.</li>
 * </ul>
 *
 * <p>Where <code>val(s[i])</code> represents the index of <code>s[i]</code> in the alphabet from <code>val(&#39;
 * a&#39;) = 1</code> to <code>val(&#39;z&#39;) = 26</code>.</p>
 *
 * <p>You are given a string <code>s</code> and the integers <code>power</code>, <code>modulo</code>, <code>k</code>,
 * and <code>hashValue.</code> Return <code>sub</code>,<em> the <strong>first</strong> <strong>substring</strong> of
 * </em><code>s</code><em> of length </em><code>k</code><em> such that </em><code>hash(sub, power, modulo) ==
 * hashValue</code>.</p>
 *
 * <p>The test cases will be generated such that an answer always <strong>exists</strong>.</p>
 *
 * <p>A <b>substring</b> is a contiguous non-empty sequence of characters within a string.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> s = &quot;leetcode&quot;, power = 7, modulo = 20, k = 2, hashValue = 0
 * <strong>Output:</strong> &quot;ee&quot;
 * <strong>Explanation:</strong> The hash of &quot;ee&quot; can be computed to be hash(&quot;ee&quot;, 7, 20) = (5 * 1 + 5 * 7) mod 20 = 40 mod 20 = 0.
 * &quot;ee&quot; is the first substring of length 2 with hashValue 0. Hence, we return &quot;ee&quot;.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> s = &quot;fbxzaad&quot;, power = 31, modulo = 100, k = 3, hashValue = 32
 * <strong>Output:</strong> &quot;fbx&quot;
 * <strong>Explanation:</strong> The hash of &quot;fbx&quot; can be computed to be hash(&quot;fbx&quot;, 31, 100) = (6 * 1 + 2 * 31 + 24 * 31<sup>2</sup>) mod 100 = 23132 mod 100 = 32.
 * The hash of &quot;bxz&quot; can be computed to be hash(&quot;bxz&quot;, 31, 100) = (2 * 1 + 24 * 31 + 26 * 31<sup>2</sup>) mod 100 = 25732 mod 100 = 32.
 * &quot;fbx&quot; is the first substring of length 3 with hashValue 32. Hence, we return &quot;fbx&quot;.
 * Note that &quot;bxz&quot; also has a hash of 32 but it appears later than &quot;fbx&quot;.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= k &lt;= s.length &lt;= 2 * 10<sup>4</sup></code></li>
 * 	<li><code>1 &lt;= power, modulo &lt;= 10<sup>9</sup></code></li>
 * 	<li><code>0 &lt;= hashValue &lt; modulo</code></li>
 * 	<li><code>s</code> consists of lowercase English letters only.</li>
 * 	<li>The test cases are generated such that an answer always <strong>exists</strong>.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2156_FindSubstringWithGivenHashValue {

    // 从后往前查找, 避免除法造成的精度问题
    @Answer
    public String subStrHash(String s, int power, int modulo, int k, int hashValue) {
        final int n = s.length();
        long hash = 0, p = 1;
        for (int i = n - k + 1; i < n; i++) {
            int val = s.charAt(i) - 'a' + 1;
            hash = (hash + val * p) % modulo;
            p = p * power % modulo;
        }

        int match = -1;
        long prev = 0;
        for (int i = n - k; i >= 0; i--) {
            int val = s.charAt(i) - 'a' + 1;
            hash = (val + (hash - prev + modulo) * power) % modulo;
            if (hash == hashValue) {
                match = i;
            }
            int pv = s.charAt(i + k - 1) - 'a' + 1;
            prev = pv * p % modulo;
        }
        return s.substring(match, match + k);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith("leetcode", 7, 20, 2, 0)
            .expect("ee");

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith("fbxzaad", 31, 100, 3, 32)
            .expect("fbx");

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith("xxterzixjqrghqyeketqeynekvqhc", 15, 94, 4, 16)
            .expect("nekv");

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith("xmmhdakfursinye", 96, 45, 15, 21)
            .expect("xmmhdakfursinye");

}
