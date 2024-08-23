package q2150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Easy] 2108. Find First Palindromic String in the Array</h3>
 * <a href="https://leetcode.com/problems/find-first-palindromic-string-in-the-array/">
 * https://leetcode.com/problems/find-first-palindromic-string-in-the-array/
 * </a><br/>
 *
 * <p>Given an array of strings <code>words</code>, return <em>the first <strong>palindromic</strong> string in the
 * array</em>. If there is no such string, return <em>an <strong>empty string</strong>
 * </em><code>&quot;&quot;</code>.</p>
 *
 * <p>A string is <strong>palindromic</strong> if it reads the same forward and backward.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> words = [&quot;abc&quot;,&quot;car&quot;,&quot;ada&quot;,&quot;racecar&quot;,&quot;cool&quot;]
 * <strong>Output:</strong> &quot;ada&quot;
 * <strong>Explanation:</strong> The first string that is palindromic is &quot;ada&quot;.
 * Note that &quot;racecar&quot; is also palindromic, but it is not the first.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> words = [&quot;notapalindrome&quot;,&quot;racecar&quot;]
 * <strong>Output:</strong> &quot;racecar&quot;
 * <strong>Explanation:</strong> The first and only string that is palindromic is &quot;racecar&quot;.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> words = [&quot;def&quot;,&quot;ghi&quot;]
 * <strong>Output:</strong> &quot;&quot;
 * <strong>Explanation:</strong> There are no palindromic strings, so the empty string is returned.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= words.length &lt;= 100</code></li>
 * 	<li><code>1 &lt;= words[i].length &lt;= 100</code></li>
 * 	<li><code>words[i]</code> consists only of lowercase English letters.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2108_FindFirstPalindromicStringInTheArray {

    @Answer
    public String firstPalindrome(String[] words) {
        for (String word : words) {
            int i = 0, j = word.length() - 1;
            while (i < j && word.charAt(i) == word.charAt(j)) {
                i++;
                j--;
            }
            if (i == j || i == j + 1) {
                return word;
            }
        }
        return "";
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new String[]{"abc", "car", "ada", "racecar", "cool"})
            .expect("ada");

    @TestData
    public DataExpectation example2 = DataExpectation.create(new String[]{"notapalindrome", "racecar"})
            .expect("racecar");

    @TestData
    public DataExpectation example3 = DataExpectation.create(new String[]{"def", "ghi"}).expect("");

}
