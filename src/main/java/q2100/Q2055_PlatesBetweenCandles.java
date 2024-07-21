package q2100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2055. Plates Between Candles</h3>
 * <a href="https://leetcode.com/problems/plates-between-candles/">
 * https://leetcode.com/problems/plates-between-candles/
 * </a><br/>
 *
 * <p>There is a long table with a line of plates and candles arranged on top of it. You are given a
 * <strong>0-indexed</strong> string <code>s</code> consisting of characters <code>&#39;*&#39;</code> and
 * <code>&#39;|&#39;</code> only, where a <code>&#39;*&#39;</code> represents a <strong>plate</strong> and a
 * <code>&#39;|&#39;</code> represents a <strong>candle</strong>.</p>
 *
 * <p>You are also given a <strong>0-indexed</strong> 2D integer array <code>queries</code> where <code>queries[i] =
 * [left<sub>i</sub>, right<sub>i</sub>]</code> denotes the <strong>substring</strong>
 * <code>s[left<sub>i</sub>...right<sub>i</sub>]</code> (<strong>inclusive</strong>). For each query, you need to find
 * the <strong>number</strong> of plates <strong>between candles</strong> that are <strong>in the substring</strong>. A
 * plate is considered <strong>between candles</strong> if there is at least one candle to its left <strong>and</strong>
 * at least one candle to its right <strong>in the substring</strong>.</p>
 *
 * <ul>
 * 	<li>For example, <code>s = &quot;||**||**|*&quot;</code>, and a query <code>[3, 8]</code> denotes the substring
 * 	<code>&quot;*||<strong><u>**</u></strong>|&quot;</code>. The number of plates between candles in this substring is
 * 	<code>2</code>, as each of the two plates has at least one candle <strong>in the substring</strong> to its left
 * 	<strong>and</strong> right.</li>
 * </ul>
 *
 * <p>Return <em>an integer array</em> <code>answer</code> <em>where</em> <code>answer[i]</code> <em>is the answer to
 * the</em> <code>i<sup>th</sup></code> <em>query</em>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="ex-1" src="https://assets.leetcode.com/uploads/2021/10/04/ex-1.png" style="width: 400px; height: 134px;" />
 * <pre>
 * <strong>Input:</strong> s = &quot;**|**|***|&quot;, queries = [[2,5],[5,9]]
 * <strong>Output:</strong> [2,3]
 * <strong>Explanation:</strong>
 * - queries[0] has two plates between candles.
 * - queries[1] has three plates between candles.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 * <img alt="ex-2" src="https://assets.leetcode.com/uploads/2021/10/04/ex-2.png" style="width: 600px; height: 193px;" />
 * <pre>
 * <strong>Input:</strong> s = &quot;***|**|*****|**||**|*&quot;, queries = [[1,17],[4,5],[14,17],[5,11],[15,16]]
 * <strong>Output:</strong> [9,0,0,0,0]
 * <strong>Explanation:</strong>
 * - queries[0] has nine plates between candles.
 * - The other queries have zero plates between candles.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>3 &lt;= s.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>s</code> consists of <code>&#39;*&#39;</code> and <code>&#39;|&#39;</code> characters.</li>
 * 	<li><code>1 &lt;= queries.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>queries[i].length == 2</code></li>
 * 	<li><code>0 &lt;= left<sub>i</sub> &lt;= right<sub>i</sub> &lt; s.length</code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2055_PlatesBetweenCandles {

    @Answer
    public int[] platesBetweenCandles(String s, int[][] queries) {
        final int m = s.length();

        // s[0, i) 区间内的蜡烛数量(不含自己)
        int[] leftCounts = new int[m + 1];
        int leftCount = 0;
        // 左边最近蜡烛的下标(含自己)
        int[] leftCandles = new int[m];
        int leftCandle = -1;
        for (int i = 0; i < m; i++) {
            leftCounts[i] = leftCount;
            if (s.charAt(i) == '|') {
                leftCount++;
                leftCandle = i;
            }
            leftCandles[i] = leftCandle;
        }
        leftCounts[m] = leftCount;

        // 右边最近蜡烛的下标(含自己)
        int[] rightCandles = new int[m];
        int rightCandle = m;
        for (int i = m - 1; i >= 0; i--) {
            if (s.charAt(i) == '|') {
                rightCandle = i;
            }
            rightCandles[i] = rightCandle;
        }

        // 计算结果
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int start = queries[i][0];
            int end = queries[i][1];
            if (leftCounts[end + 1] - leftCounts[start] >= 2) {
                // 区间内, 最后一个蜡烛下标 - 最前一个蜡烛下标 - 蜡烛数量(-1因为最后一个蜡烛没算进去)
                res[i] += leftCandles[end] - rightCandles[start] - (leftCounts[end + 1] - leftCounts[start] - 1);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith("**|**|***|", new int[][]{{2, 5}, {5, 9}})
            .expect(new int[]{2, 3});

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("***|**|*****|**||**|*",
                    new int[][]{{1, 17}, {4, 5}, {14, 17}, {5, 11}, {15, 16}})
            .expect(new int[]{9, 0, 0, 0, 0});

}
