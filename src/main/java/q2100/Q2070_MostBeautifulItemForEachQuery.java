package q2100;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2070. Most Beautiful Item for Each Query</h3>
 * <a href="https://leetcode.com/problems/most-beautiful-item-for-each-query/">
 * https://leetcode.com/problems/most-beautiful-item-for-each-query/
 * </a><br/>
 *
 * <p>You are given a 2D integer array <code>items</code> where <code>items[i] = [price<sub>i</sub>,
 * beauty<sub>i</sub>]</code> denotes the <strong>price</strong> and <strong>beauty</strong> of an item
 * respectively.</p>
 *
 * <p>You are also given a <strong>0-indexed</strong> integer array <code>queries</code>. For each
 * <code>queries[j]</code>, you want to determine the <strong>maximum beauty</strong> of an item whose
 * <strong>price</strong> is <strong>less than or equal</strong> to <code>queries[j]</code>. If no such item exists,
 * then the answer to this query is <code>0</code>.</p>
 *
 * <p>Return <em>an array </em><code>answer</code><em> of the same length as </em><code>queries</code><em> where
 * </em><code>answer[j]</code><em> is the answer to the </em><code>j<sup>th</sup></code><em> query</em>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> items = [[1,2],[3,2],[2,4],[5,6],[3,5]], queries = [1,2,3,4,5,6]
 * <strong>Output:</strong> [2,4,5,5,6,6]
 * <strong>Explanation:</strong>
 * - For queries[0]=1, [1,2] is the only item which has price &lt;= 1. Hence, the answer for this query is 2.
 * - For queries[1]=2, the items which can be considered are [1,2] and [2,4].
 *   The maximum beauty among them is 4.
 * - For queries[2]=3 and queries[3]=4, the items which can be considered are [1,2], [3,2], [2,4], and [3,5].
 *   The maximum beauty among them is 5.
 * - For queries[4]=5 and queries[5]=6, all items can be considered.
 *   Hence, the answer for them is the maximum beauty of all items, i.e., 6.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> items = [[1,2],[1,2],[1,3],[1,4]], queries = [1]
 * <strong>Output:</strong> [4]
 * <strong>Explanation:</strong>
 * The price of every item is equal to 1, so we choose the item with the maximum beauty 4.
 * Note that multiple items can have the same price and/or beauty.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> items = [[10,1000]], queries = [5]
 * <strong>Output:</strong> [0]
 * <strong>Explanation:</strong>
 * No item has a price less than or equal to 5, so no item can be chosen.
 * Hence, the answer to the query is 0.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= items.length, queries.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>items[i].length == 2</code></li>
 * 	<li><code>1 &lt;= price<sub>i</sub>, beauty<sub>i</sub>, queries[j] &lt;= 10<sup>9</sup></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2070_MostBeautifulItemForEachQuery {

    @Answer
    public int[] maximumBeauty(int[][] items, int[] queries) {
        Arrays.sort(items, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
        // 更新 price <= item[i][0] 的最大 beauty 值
        int max = 0;
        for (int[] item : items) {
            if (max < item[1]) {
                max = item[1];
            } else {
                item[1] = max;
            }
        }

        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            // 找出第1 个价格高于query 的item, 这样下标-1 就是能买得起的最好的(存在1个都买不了的情况)
            int start = 0, end = items.length;
            while (start < end) {
                int mid = (start + end) / 2;
                if (items[mid][0] <= queries[i]) {
                    start = mid + 1;
                } else {
                    end = mid;
                }
            }
            if (start > 0) {
                res[i] = items[start - 1][1];
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{1, 2}, {3, 2}, {2, 4}, {5, 6}, {3, 5}}, new int[]{1, 2, 3, 4, 5, 6})
            .expect(new int[]{2, 4, 5, 5, 6, 6});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{1, 2}, {1, 2}, {1, 3}, {1, 4}}, new int[]{1})
            .expect(new int[]{4});

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[][]{{10, 1000}}, new int[]{5})
            .expect(new int[]{0});

}
