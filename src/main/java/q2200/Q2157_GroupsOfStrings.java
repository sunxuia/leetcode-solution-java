package q2200;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Hard] 2157. Groups of Strings</h3>
 * <a href="https://leetcode.com/problems/groups-of-strings/">
 * https://leetcode.com/problems/groups-of-strings/
 * </a><br/>
 *
 * <p>You are given a <strong>0-indexed</strong> array of strings <code>words</code>. Each string consists of
 * <strong>lowercase English letters</strong> only. No letter occurs more than once in any string of
 * <code>words</code>.</p>
 *
 * <p>Two strings <code>s1</code> and <code>s2</code> are said to be <strong>connected</strong> if the set of letters
 * of
 * <code>s2</code> can be obtained from the set of letters of <code>s1</code> by any <strong>one</strong> of the
 * following operations:</p>
 *
 * <ul>
 * 	<li>Adding exactly one letter to the set of the letters of <code>s1</code>.</li>
 * 	<li>Deleting exactly one letter from the set of the letters of <code>s1</code>.</li>
 * 	<li>Replacing exactly one letter from the set of the letters of <code>s1</code> with any letter,
 * 	<strong>including</strong> itself.</li>
 * </ul>
 *
 * <p>The array <code>words</code> can be divided into one or more non-intersecting <strong>groups</strong>. A string
 * belongs to a group if any <strong>one</strong> of the following is true:</p>
 *
 * <ul>
 * 	<li>It is connected to <strong>at least one</strong> other string of the group.</li>
 * 	<li>It is the <strong>only</strong> string present in the group.</li>
 * </ul>
 *
 * <p>Note that the strings in <code>words</code> should be grouped in such a manner that a string belonging to a
 * group cannot be connected to a string present in any other group. It can be proved that such an arrangement is
 * always unique.</p>
 *
 * <p>Return <em>an array</em> <code>ans</code> <em>of size</em> <code>2</code> <em>where:</em></p>
 *
 * <ul>
 * 	<li><code>ans[0]</code> <em>is the <strong>maximum number</strong> of groups</em> <code>words</code> <em>can be
 * 	divided into, and</em></li>
 * 	<li><code>ans[1]</code> <em>is the <strong>size of the largest</strong> group</em>.</li>
 * </ul>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> words = [&quot;a&quot;,&quot;b&quot;,&quot;ab&quot;,&quot;cde&quot;]
 * <strong>Output:</strong> [2,3]
 * <strong>Explanation:</strong>
 * - words[0] can be used to obtain words[1] (by replacing &#39;a&#39; with &#39;b&#39;), and words[2] (by adding &#39;b&#39;). So words[0] is connected to words[1] and words[2].
 * - words[1] can be used to obtain words[0] (by replacing &#39;b&#39; with &#39;a&#39;), and words[2] (by adding &#39;a&#39;). So words[1] is connected to words[0] and words[2].
 * - words[2] can be used to obtain words[0] (by deleting &#39;b&#39;), and words[1] (by deleting &#39;a&#39;). So words[2] is connected to words[0] and words[1].
 * - words[3] is not connected to any string in words.
 * Thus, words can be divided into 2 groups [&quot;a&quot;,&quot;b&quot;,&quot;ab&quot;] and [&quot;cde&quot;]. The size of the largest group is 3.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> words = [&quot;a&quot;,&quot;ab&quot;,&quot;abc&quot;]
 * <strong>Output:</strong> [1,3]
 * <strong>Explanation:</strong>
 * - words[0] is connected to words[1].
 * - words[1] is connected to words[0] and words[2].
 * - words[2] is connected to words[1].
 * Since all strings are connected to each other, they should be grouped together.
 * Thus, the size of the largest group is 3.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= words.length &lt;= 2 * 10<sup>4</sup></code></li>
 * 	<li><code>1 &lt;= words[i].length &lt;= 26</code></li>
 * 	<li><code>words[i]</code> consists of lowercase English letters only.</li>
 * 	<li>No letter occurs more than once in <code>words[i]</code>.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2157_GroupsOfStrings {

    // 时间复杂度 O(N)
    @Answer
    public int[] groupStrings(String[] words) {
        final int n = words.length;
        int[] parents = new int[n];
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }

        // 缓存不同掩码对应的 root
        Map<Integer, Integer> roots = new HashMap<>();
        for (int i = 0; i < n; i++) {
            // 计算掩码
            int mask = 0;
            for (int j = 0; j < words[i].length(); j++) {
                mask |= 1 << words[i].charAt(j) - 'a';
            }

            // 找出或更新 root 信息
            int root = i;
            if (roots.containsKey(mask)) {
                // 与其他 word 相等, 或与其他 word-1 相等
                root = findRoot(parents, roots.get(mask));
                parents[i] = root;
            } else {
                roots.put(mask, root);
            }

            // 将 word-1 的结果供后面的检索, 并更新 word-1 可以与其他组连接的信息
            for (int j = 0; j < 26; j++) {
                int op = mask ^ (1 << j);
                if (op < mask) {
                    Integer other = roots.get(op);
                    if (other == null) {
                        // word-1 的信息更新到此
                        roots.put(op, root);
                    } else {
                        // 更新其他连接的组的信息
                        // 其他 word 得到当前 word-1, 或其他 word -1 后得到当前 word-1 (替换) 视为在同一个组
                        int otherRoot = findRoot(parents, other);
                        if (otherRoot != root) {
                            parents[otherRoot] = root;
                        }
                    }
                }
            }
        }

        // 统计结果
        Map<Integer, Integer> counts = new HashMap<>();
        int max = 1;
        for (int i = 0; i < n; i++) {
            int root = findRoot(parents, i);
            Integer sum = counts.get(root);
            if (sum == null) {
                counts.put(root, 1);
            } else {
                sum = sum + 1;
                counts.put(root, sum);
                max = Math.max(max, sum);
            }
        }
        return new int[]{counts.size(), max};
    }

    private int findRoot(int[] parents, int i) {
        if (parents[i] == i) {
            return i;
        }
        return parents[i] = findRoot(parents, parents[i]);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new String[]{"a", "b", "ab", "cde"})
            .expect(new int[]{2, 3});

    @TestData
    public DataExpectation example2 = DataExpectation.create(new String[]{"a", "ab", "abc"}).expect(new int[]{1, 3});

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new String[]{"a", "b"}).expect(new int[]{1, 2});

}
