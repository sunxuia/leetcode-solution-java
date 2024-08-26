package q2150;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2131. Longest Palindrome by Concatenating Two Letter Words</h3>
 * <a href="https://leetcode.com/problems/longest-palindrome-by-concatenating-two-letter-words/">
 * https://leetcode.com/problems/longest-palindrome-by-concatenating-two-letter-words/
 * </a><br/>
 *
 * <p>You are given an array of strings <code>words</code>. Each element of <code>words</code> consists of
 * <strong>two</strong> lowercase English letters.</p>
 *
 * <p>Create the <strong>longest possible palindrome</strong> by selecting some elements from <code>words</code> and
 * concatenating them in <strong>any order</strong>. Each element can be selected <strong>at most once</strong>.</p>
 *
 * <p>Return <em>the <strong>length</strong> of the longest palindrome that you can create</em>. If it is impossible to
 * create any palindrome, return <code>0</code>.</p>
 *
 * <p>A <strong>palindrome</strong> is a string that reads the same forward and backward.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> words = [&quot;lc&quot;,&quot;cl&quot;,&quot;gg&quot;]
 * <strong>Output:</strong> 6
 * <strong>Explanation:</strong> One longest palindrome is &quot;lc&quot; + &quot;gg&quot; + &quot;cl&quot; = &quot;lcggcl&quot;, of length 6.
 * Note that &quot;clgglc&quot; is another longest palindrome that can be created.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> words = [&quot;ab&quot;,&quot;ty&quot;,&quot;yt&quot;,&quot;lc&quot;,&quot;cl&quot;,&quot;ab&quot;]
 * <strong>Output:</strong> 8
 * <strong>Explanation:</strong> One longest palindrome is &quot;ty&quot; + &quot;lc&quot; + &quot;cl&quot; + &quot;yt&quot; = &quot;tylcclyt&quot;, of length 8.
 * Note that &quot;lcyttycl&quot; is another longest palindrome that can be created.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> words = [&quot;cc&quot;,&quot;ll&quot;,&quot;xx&quot;]
 * <strong>Output:</strong> 2
 * <strong>Explanation:</strong> One longest palindrome is &quot;cc&quot;, of length 2.
 * Note that &quot;ll&quot; is another longest palindrome that can be created, and so is &quot;xx&quot;.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= words.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>words[i].length == 2</code></li>
 * 	<li><code>words[i]</code> consists of lowercase English letters.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2131_LongestPalindromeByConcatenatingTwoLetterWords {

    @Answer
    public int longestPalindrome(String[] words) {
        int[][] bucket = new int[26][26];
        for (String word : words) {
            int first = word.charAt(0) - 'a';
            int second = word.charAt(1) - 'a';
            bucket[first][second]++;
        }

        // 中间自己和自己对称的字符串(aa, bb 这样的)
        int middle = 0;
        // 两边的互相对称字符串长度(ab-ba, lc-cl 这样的)
        int side = 0;
        for (int i = 0; i < 26; i++) {
            if (bucket[i][i] > 0) {
                if (bucket[i][i] % 2 == 1) {
                    middle = 2;
                }
                side += bucket[i][i] / 2 * 4;
            }
            for (int j = i + 1; j < 26; j++) {
                int mirror = Math.min(bucket[i][j], bucket[j][i]);
                side += mirror * 4;
            }
        }
        return middle + side;
    }

    // 更通用的一种解法, 不限制word 的长度, 也能通过OJ 就是稍微慢一点.
    @Answer
    public int longestPalindrome2(String[] words) {
        int middle = 0, side = 0;
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        for (String word : map.keySet()) {
            int count = map.get(word);
            String reversed = new StringBuilder(word).reverse().toString();
            if (word.equals(reversed)) {
                if (count % 2 == 1) {
                    middle = Math.max(middle, word.length());
                }
                side += 2 * (count / 2) * word.length();
            } else {
                int other = map.getOrDefault(reversed, 0);
                side += Math.min(count, other) * word.length();
            }
        }
        return middle + side;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new String[]{"lc", "cl", "gg"}).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new String[]{"ab", "ty", "yt", "lc", "cl", "ab"})
            .expect(8);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new String[]{"cc", "ll", "xx"}).expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new String[]{"dd", "aa", "bb", "dd", "aa", "dd", "bb", "dd", "aa", "cc", "bb", "cc", "dd", "cc"})
            .expect(22);

}
