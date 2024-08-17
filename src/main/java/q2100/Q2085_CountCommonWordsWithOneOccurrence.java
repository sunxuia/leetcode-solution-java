package q2100;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Easy] 2085. Count Common Words With One Occurrence</h3>
 * <a href="https://leetcode.com/problems/count-common-words-with-one-occurrence/">
 * https://leetcode.com/problems/count-common-words-with-one-occurrence/
 * </a><br/>
 *
 * <p>Given two string arrays <code>words1</code> and <code>words2</code>, return <em>the number of strings that appear
 * <strong>exactly once</strong> in <b>each</b>&nbsp;of the two arrays.</em></p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> words1 = [&quot;leetcode&quot;,&quot;is&quot;,&quot;amazing&quot;,&quot;as&quot;,&quot;is&quot;], words2 = [&quot;amazing&quot;,&quot;leetcode&quot;,&quot;is&quot;]
 * <strong>Output:</strong> 2
 * <strong>Explanation:</strong>
 * - &quot;leetcode&quot; appears exactly once in each of the two arrays. We count this string.
 * - &quot;amazing&quot; appears exactly once in each of the two arrays. We count this string.
 * - &quot;is&quot; appears in each of the two arrays, but there are 2 occurrences of it in words1. We do not count this string.
 * - &quot;as&quot; appears once in words1, but does not appear in words2. We do not count this string.
 * Thus, there are 2 strings that appear exactly once in each of the two arrays.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> words1 = [&quot;b&quot;,&quot;bb&quot;,&quot;bbb&quot;], words2 = [&quot;a&quot;,&quot;aa&quot;,&quot;aaa&quot;]
 * <strong>Output:</strong> 0
 * <strong>Explanation:</strong> There are no strings that appear in each of the two arrays.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> words1 = [&quot;a&quot;,&quot;ab&quot;], words2 = [&quot;a&quot;,&quot;a&quot;,&quot;a&quot;,&quot;ab&quot;]
 * <strong>Output:</strong> 1
 * <strong>Explanation:</strong> The only string that appears exactly once in each of the two arrays is &quot;ab&quot;.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= words1.length, words2.length &lt;= 1000</code></li>
 * 	<li><code>1 &lt;= words1[i].length, words2[j].length &lt;= 30</code></li>
 * 	<li><code>words1[i]</code> and <code>words2[j]</code> consists only of lowercase English letters.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2085_CountCommonWordsWithOneOccurrence {

    @Answer
    public int countWords(String[] words1, String[] words2) {
        // map1[word] 表示此字符串在 words1 中是否是唯一的
        Map<String, Boolean> map1 = new HashMap<>();
        for (String word : words1) {
            map1.put(word, !map1.containsKey(word));
        }

        Map<String, Boolean> map2 = new HashMap<>();
        for (String word : words2) {
            if (map1.getOrDefault(word, false)) {
                map2.put(word, !map2.containsKey(word));
            }
        }

        return map2.values()
                .stream()
                .mapToInt(b -> b ? 1 : 0)
                .sum();
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{"leetcode", "is", "amazing", "as", "is"},
                    new String[]{"amazing", "leetcode", "is"})
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{"b", "bb", "bbb"}, new String[]{"a", "aa", "aaa"})
            .expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new String[]{"a", "ab"}, new String[]{"a", "a", "a", "ab"})
            .expect(1);

}
