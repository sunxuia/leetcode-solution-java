package q2150;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2121. Intervals Between Identical Elements</h3>
 * <a href="https://leetcode.com/problems/intervals-between-identical-elements/">
 * https://leetcode.com/problems/intervals-between-identical-elements/
 * </a><br/>
 *
 * <p>You are given a <strong>0-indexed</strong> array of <code>n</code> integers <code>arr</code>.</p>
 *
 * <p>The <strong>interval</strong> between two elements in <code>arr</code> is defined as the <strong>absolute
 * difference</strong> between their indices. More formally, the <strong>interval</strong> between <code>arr[i]</code>
 * and <code>arr[j]</code> is <code>|i - j|</code>.</p>
 *
 * <p>Return <em>an array</em> <code>intervals</code> <em>of length</em> <code>n</code> <em>where</em>
 * <code>intervals[i]</code> <em>is <strong>the sum of intervals</strong> between </em><code>arr[i]</code><em> and each
 * element in </em><code>arr</code><em> with the same value as </em><code>arr[i]</code><em>.</em></p>
 *
 * <p><strong>Note:</strong> <code>|x|</code> is the absolute value of <code>x</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> arr = [2,1,3,1,2,3,3]
 * <strong>Output:</strong> [4,2,7,2,4,4,5]
 * <strong>Explanation:</strong>
 * - Index 0: Another 2 is found at index 4. |0 - 4| = 4
 * - Index 1: Another 1 is found at index 3. |1 - 3| = 2
 * - Index 2: Two more 3s are found at indices 5 and 6. |2 - 5| + |2 - 6| = 7
 * - Index 3: Another 1 is found at index 1. |3 - 1| = 2
 * - Index 4: Another 2 is found at index 0. |4 - 0| = 4
 * - Index 5: Two more 3s are found at indices 2 and 6. |5 - 2| + |5 - 6| = 4
 * - Index 6: Two more 3s are found at indices 2 and 5. |6 - 2| + |6 - 5| = 5
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> arr = [10,5,10,10]
 * <strong>Output:</strong> [5,0,3,4]
 * <strong>Explanation:</strong>
 * - Index 0: Two more 10s are found at indices 2 and 3. |0 - 2| + |0 - 3| = 5
 * - Index 1: There is only one 5 in the array, so its sum of intervals to identical elements is 0.
 * - Index 2: Two more 10s are found at indices 0 and 3. |2 - 0| + |2 - 3| = 3
 * - Index 3: Two more 10s are found at indices 0 and 2. |3 - 0| + |3 - 2| = 4
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>n == arr.length</code></li>
 * 	<li><code>1 &lt;= n &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>1 &lt;= arr[i] &lt;= 10<sup>5</sup></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2121_IntervalsBetweenIdenticalElements {

    @Answer
    public long[] getDistances(int[] arr) {
        final int n = arr.length;
        // 这个改成数组可以快一些, 时间复杂度不变
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
        }

        long[] res = new long[n];
        for (List<Integer> list : map.values()) {
            long left = 0, right = 0;
            final int size = list.size();
            for (int index : list) {
                right += index;
            }
            for (int i = 0; i < size; i++) {
                long index = list.get(i);
                right -= index;
                if (i > 0) {
                    left += list.get(i - 1);
                }
                res[(int) index] = index * i - left + right - index * (size - 1 - i);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[]{2, 1, 3, 1, 2, 3, 3})
            .expect(new long[]{4, 2, 7, 2, 4, 4, 5});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[]{10, 5, 10, 10})
            .expect(new long[]{5, 0, 3, 4});

}
