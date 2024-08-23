package q2150;

import java.util.TreeMap;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Hard] 2111. Minimum Operations to Make the Array K-Increasing</h3>
 * <a href="https://leetcode.com/problems/minimum-operations-to-make-the-array-k-increasing/">
 * https://leetcode.com/problems/minimum-operations-to-make-the-array-k-increasing/
 * </a><br/>
 *
 * <p>You are given a <strong>0-indexed</strong> array <code>arr</code> consisting of <code>n</code> positive integers,
 * and a positive integer <code>k</code>.</p>
 *
 * <p>The array <code>arr</code> is called <strong>K-increasing</strong> if <code>arr[i-k] &lt;= arr[i]</code> holds
 * for
 * every index <code>i</code>, where <code>k &lt;= i &lt;= n-1</code>.</p>
 *
 * <ul>
 * 	<li>For example, <code>arr = [4, 1, 5, 2, 6, 2]</code> is K-increasing for <code>k = 2</code> because:
 *
 * 	<ul>
 * 		<li><code>arr[0] &lt;= arr[2] (4 &lt;= 5)</code></li>
 * 		<li><code>arr[1] &lt;= arr[3] (1 &lt;= 2)</code></li>
 * 		<li><code>arr[2] &lt;= arr[4] (5 &lt;= 6)</code></li>
 * 		<li><code>arr[3] &lt;= arr[5] (2 &lt;= 2)</code></li>
 * 	</ul>
 * 	</li>
 * 	<li>However, the same <code>arr</code> is not K-increasing for <code>k = 1</code> (because <code>arr[0] &gt;
 * 	arr[1]</code>) or <code>k = 3</code> (because <code>arr[0] &gt; arr[3]</code>).</li>
 * </ul>
 *
 * <p>In one <strong>operation</strong>, you can choose an index <code>i</code> and <strong>change</strong>
 * <code>arr[i]</code> into <strong>any</strong> positive integer.</p>
 *
 * <p>Return <em>the <strong>minimum number of operations</strong> required to make the array K-increasing for the
 * given </em><code>k</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> arr = [5,4,3,2,1], k = 1
 * <strong>Output:</strong> 4
 * <strong>Explanation:
 * </strong>For k = 1, the resultant array has to be non-decreasing.
 * Some of the K-increasing arrays that can be formed are [5,<u><strong>6</strong></u>,<u><strong>7</strong></u>,<u><strong>8</strong></u>,<u><strong>9</strong></u>], [<u><strong>1</strong></u>,<u><strong>1</strong></u>,<u><strong>1</strong></u>,<u><strong>1</strong></u>,1], [<u><strong>2</strong></u>,<u><strong>2</strong></u>,3,<u><strong>4</strong></u>,<u><strong>4</strong></u>]. All of them require 4 operations.
 * It is suboptimal to change the array to, for example, [<u><strong>6</strong></u>,<u><strong>7</strong></u>,<u><strong>8</strong></u>,<u><strong>9</strong></u>,<u><strong>10</strong></u>] because it would take 5 operations.
 * It can be shown that we cannot make the array K-increasing in less than 4 operations.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> arr = [4,1,5,2,6,2], k = 2
 * <strong>Output:</strong> 0
 * <strong>Explanation:</strong>
 * This is the same example as the one in the problem description.
 * Here, for every index i where 2 &lt;= i &lt;= 5, arr[i-2] &lt;=<b> </b>arr[i].
 * Since the given array is already K-increasing, we do not need to perform any operations.</pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> arr = [4,1,5,2,6,2], k = 3
 * <strong>Output:</strong> 2
 * <strong>Explanation:</strong>
 * Indices 3 and 5 are the only ones not satisfying arr[i-3] &lt;= arr[i] for 3 &lt;= i &lt;= 5.
 * One of the ways we can make the array K-increasing is by changing arr[3] to 4 and arr[5] to 5.
 * The array will now be [4,1,5,<u><strong>4</strong></u>,6,<u><strong>5</strong></u>].
 * Note that there can be other ways to make the array K-increasing, but none of them require less than 2 operations.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= arr.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>1 &lt;= arr[i], k &lt;= arr.length</code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2111_MinimumOperationsToMakeTheArrayKIncreasing {

    /**
     * 难点在于一个子数组中修改最小次, 让子数组弱递增.
     * 根据 hint, 可以通过找出最长连续子序列的方式来计算(非连续的点可以直接修改为与前一个点相等的值).
     * 如下解法时间复杂度 O(NlogN), 使用 TreeMap 比较慢.
     */
    @Answer
    public int kIncreasing(int[] arr, int k) {
        final int n = arr.length;
        int res = n;
        for (int r = 0; r < k; r++) {
            // map[k,v] 表示以 >=k 元素结束的最大子数组长度
            TreeMap<Integer, Integer> map = new TreeMap<>();
            int maxLength = 1;
            for (int i = r; i < n; i += k) {
                int length = 1;
                // 找出比他小的元素(中最长的长度)
                Integer floor = map.floorKey(arr[i]);
                if (floor != null) {
                    length = map.get(floor) + 1;
                }
                // 移除 map 中比当前元素大且长度更小(或相等)的元素
                for (Integer higher = map.higherKey(arr[i]);
                        higher != null && map.get(higher) <= length;
                        higher = map.higherKey(higher)) {
                    map.remove(higher);
                }
                map.put(arr[i], length);
                maxLength = Math.max(maxLength, length);
            }
            // 减去不需要改变的元素数量
            res -= maxLength;
        }
        return res;
    }

    /**
     * leetcode 上比较快的解法思路.
     */
    @Answer
    public int kIncreasing2(int[] arr, int k) {
        final int n = arr.length;
        int res = n;
        // 单调(弱)递增数组, 表示最长的递增子序列
        int[] seq = new int[n];
        for (int r = 0; r < k; r++) {
            int size = 0;
            for (int i = r; i < n; i += k) {
                final int v = arr[i];
                // 找出 seq 中 <= v 的最大的下标
                int start = -1, end = size - 1;
                while (start < end) {
                    int mid = (start + end + 1) / 2;
                    if (seq[mid] <= v) {
                        start = mid;
                    } else {
                        end = mid - 1;
                    }
                }

                if (start == size - 1) {
                    // v 最大, 直接添加到数组末尾 (包括空数组的情况)
                    seq[size++] = v;
                } else if (start == -1) {
                    // v 最小, 替换到数组开头, 不影响最长递增子序列的长度
                    seq[0] = v;
                } else {
                    // v 在中间, 替换掉后面一个比v 大的元素, 不影响最长递增子序列的长度
                    seq[start + 1] = v;
                }
            }
            res -= size;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{5, 4, 3, 2, 1}, 1).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{4, 1, 5, 2, 6, 2}, 2).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{4, 1, 5, 2, 6, 2}, 3).expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{12, 6, 12, 6, 14, 2, 13, 17, 3, 8, 11, 7, 4, 11, 18, 8, 8, 3}, 1)
            .expect(12);

}
