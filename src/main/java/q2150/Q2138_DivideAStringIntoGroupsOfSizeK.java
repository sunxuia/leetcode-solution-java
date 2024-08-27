package q2150;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Easy] 2138. Divide a String Into Groups of Size k</h3>
 * <a href="https://leetcode.com/problems/divide-a-string-into-groups-of-size-k/">
 * https://leetcode.com/problems/divide-a-string-into-groups-of-size-k/
 * </a><br/>
 *
 * <p>A string <code>s</code> can be partitioned into groups of size <code>k</code> using the following procedure:</p>
 *
 * <ul>
 * 	<li>The first group consists of the first <code>k</code> characters of the string, the second group consists of
 * 	the next <code>k</code> characters of the string, and so on. Each character can be a part of <strong>exactly
 * 	one</strong> group.</li>
 * 	<li>For the last group, if the string <strong>does not</strong> have <code>k</code> characters remaining, a
 * 	character <code>fill</code> is used to complete the group.</li>
 * </ul>
 *
 * <p>Note that the partition is done so that after removing the <code>fill</code> character from the last group (if
 * it exists) and concatenating all the groups in order, the resultant string should be <code>s</code>.</p>
 *
 * <p>Given the string <code>s</code>, the size of each group <code>k</code> and the character <code>fill</code>,
 * return <em>a string array denoting the <strong>composition of every group</strong> </em><code>s</code><em> has
 * been divided into, using the above procedure</em>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> s = &quot;abcdefghi&quot;, k = 3, fill = &quot;x&quot;
 * <strong>Output:</strong> [&quot;abc&quot;,&quot;def&quot;,&quot;ghi&quot;]
 * <strong>Explanation:</strong>
 * The first 3 characters &quot;abc&quot; form the first group.
 * The next 3 characters &quot;def&quot; form the second group.
 * The last 3 characters &quot;ghi&quot; form the third group.
 * Since all groups can be completely filled by characters from the string, we do not need to use fill.
 * Thus, the groups formed are &quot;abc&quot;, &quot;def&quot;, and &quot;ghi&quot;.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> s = &quot;abcdefghij&quot;, k = 3, fill = &quot;x&quot;
 * <strong>Output:</strong> [&quot;abc&quot;,&quot;def&quot;,&quot;ghi&quot;,&quot;jxx&quot;]
 * <strong>Explanation:</strong>
 * Similar to the previous example, we are forming the first three groups &quot;abc&quot;, &quot;def&quot;, and &quot;ghi&quot;.
 * For the last group, we can only use the character &#39;j&#39; from the string. To complete this group, we add &#39;x&#39; twice.
 * Thus, the 4 groups formed are &quot;abc&quot;, &quot;def&quot;, &quot;ghi&quot;, and &quot;jxx&quot;.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= s.length &lt;= 100</code></li>
 * 	<li><code>s</code> consists of lowercase English letters only.</li>
 * 	<li><code>1 &lt;= k &lt;= 100</code></li>
 * 	<li><code>fill</code> is a lowercase English letter.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2138_DivideAStringIntoGroupsOfSizeK {

    @Answer
    public String[] divideString(String s, int k, char fill) {
        final int n = s.length();
        final char[] cs = s.toCharArray();
        String[] res = new String[(n + k - 1) / k];
        int i;
        for (i = 0; i + k < n; i += k) {
            res[i / k] = new String(cs, i, k);
        }
        if (i != n) {
            char[] last = new char[k];
            Arrays.fill(last, fill);
            for (; i < n; i++) {
                last[i % k] = cs[i];
            }
            res[(n - 1) / k] = new String(last);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("abcdefghi", 3, 'x')
            .expect(new String[]{"abc", "def", "ghi"});

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("abcdefghij", 3, 'x')
            .expect(new String[]{"abc", "def", "ghi", "jxx"});

}
