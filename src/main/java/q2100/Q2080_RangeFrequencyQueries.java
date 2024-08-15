package q2100;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 * <h3>[Medium] 2080. Range Frequency Queries</h3>
 * <a href="https://leetcode.com/problems/range-frequency-queries/">
 * https://leetcode.com/problems/range-frequency-queries/
 * </a><br/>
 *
 * <p>Design a data structure to find the <strong>frequency</strong> of a given value in a given subarray.</p>
 *
 * <p>The <strong>frequency</strong> of a value in a subarray is the number of occurrences of that value in the
 * subarray.</p>
 *
 * <p>Implement the <code>RangeFreqQuery</code> class:</p>
 *
 * <ul>
 * 	<li><code>RangeFreqQuery(int[] arr)</code> Constructs an instance of the class with the given
 * 	<strong>0-indexed</strong> integer array <code>arr</code>.</li>
 * 	<li><code>int query(int left, int right, int value)</code> Returns the <strong>frequency</strong> of
 * 	<code>value</code> in the subarray <code>arr[left...right]</code>.</li>
 * </ul>
 *
 * <p>A <strong>subarray</strong> is a contiguous sequence of elements within an array. <code>arr[left..
 * .right]</code> denotes the subarray that contains the elements of <code>nums</code> between indices
 * <code>left</code> and <code>right</code> (<strong>inclusive</strong>).</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input</strong>
 * [&quot;RangeFreqQuery&quot;, &quot;query&quot;, &quot;query&quot;]
 * [[[12, 33, 4, 56, 22, 2, 34, 33, 22, 12, 34, 56]], [1, 2, 4], [0, 11, 33]]
 * <strong>Output</strong>
 * [null, 1, 2]
 *
 * <strong>Explanation</strong>
 * RangeFreqQuery rangeFreqQuery = new RangeFreqQuery([12, 33, 4, 56, 22, 2, 34, 33, 22, 12, 34, 56]);
 * rangeFreqQuery.query(1, 2, 4); // return 1. The value 4 occurs 1 time in the subarray [33, 4]
 * rangeFreqQuery.query(0, 11, 33); // return 2. The value 33 occurs 2 times in the whole array.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= arr.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>1 &lt;= arr[i], value &lt;= 10<sup>4</sup></code></li>
 * 	<li><code>0 &lt;= left &lt;= right &lt; arr.length</code></li>
 * 	<li>At most <code>10<sup>5</sup></code> calls will be made to <code>query</code></li>
 * </ul>
 */
public class Q2080_RangeFrequencyQueries {

    private static class RangeFreqQuery {

        private static final int MAX = 10000;

        // indexes[v] 表示值 v 在 arr 中出现的下标 (按顺序从前往后排列)
        int[][] indexes;

        public RangeFreqQuery(int[] arr) {
            int[] counts = new int[MAX + 1];
            for (int v : arr) {
                counts[v]++;
            }

            indexes = new int[MAX + 1][];
            for (int v = 1; v <= MAX; v++) {
                if (counts[v] > 0) {
                    indexes[v] = new int[counts[v]];
                }
            }

            for (int i = 0; i < arr.length; i++) {
                int v = arr[i];
                int[] seq = indexes[v];
                seq[seq.length - counts[v]] = i;
                counts[v]--;
            }
        }

        public int query(int left, int right, int value) {
            int[] seq = indexes[value];
            if (seq == null) {
                return 0;
            }
            int from = Arrays.binarySearch(seq, left);
            if (from < 0) {
                from = -from - 1;
            }
            int to = Arrays.binarySearch(seq, right);
            if (to < 0) {
                to = -to - 2;
            }
            return to - from + 1;
        }
    }

    /**
     * Your RangeFreqQuery object will be instantiated and called as such:
     * RangeFreqQuery obj = new RangeFreqQuery(arr);
     * int param_1 = obj.query(left,right,value);
     */

    @Test
    public void testMethod() {
        int[] arr = new int[]{12, 33, 4, 56, 22, 2, 34, 33, 22, 12, 34, 56};
        RangeFreqQuery tested = new RangeFreqQuery(arr);
        int res = tested.query(1, 2, 4);
        Assert.assertEquals(1, res);
        res = tested.query(0, 11, 33);
        Assert.assertEquals(2, res);
    }

}
