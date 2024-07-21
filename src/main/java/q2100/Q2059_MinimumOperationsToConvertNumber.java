package q2100;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2059. Minimum Operations to Convert Number</h3>
 * <a href="https://leetcode.com/problems/minimum-operations-to-convert-number/">
 * https://leetcode.com/problems/minimum-operations-to-convert-number/
 * </a><br/>
 *
 * <p>You are given a <strong>0-indexed</strong> integer array <code>nums</code> containing <strong>distinct</strong>
 * numbers, an integer <code>start</code>, and an integer <code>goal</code>. There is an integer <code>x</code> that is
 * initially set to <code>start</code>, and you want to perform operations on <code>x</code> such that it is converted
 * to <code>goal</code>. You can perform the following operation repeatedly on the number <code>x</code>:</p>
 *
 * <p>If <code>0 &lt;= x &lt;= 1000</code>, then for any index <code>i</code> in the array (<code>0 &lt;= i &lt;
 * nums.length</code>), you can set <code>x</code> to any of the following:</p>
 *
 * <ul>
 * 	<li><code>x + nums[i]</code></li>
 * 	<li><code>x - nums[i]</code></li>
 * 	<li><code>x ^ nums[i]</code> (bitwise-XOR)</li>
 * </ul>
 *
 * <p>Note that you can use each <code>nums[i]</code> any number of times in any order. Operations that set
 * <code>x</code> to be out of the range <code>0 &lt;= x &lt;= 1000</code> are valid, but no more operations can be
 * done afterward.</p>
 *
 * <p>Return <em>the <strong>minimum</strong> number of operations needed to convert </em><code>x = start</code><em>
 *     into
 * </em><code>goal</code><em>, and </em><code>-1</code><em> if it is not possible</em>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [2,4,12], start = 2, goal = 12
 * <strong>Output:</strong> 2
 * <strong>Explanation:</strong> We can go from 2 &rarr; 14 &rarr; 12 with the following 2 operations.
 * - 2 + 12 = 14
 * - 14 - 2 = 12
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [3,5,7], start = 0, goal = -4
 * <strong>Output:</strong> 2
 * <strong>Explanation:</strong> We can go from 0 &rarr; 3 &rarr; -4 with the following 2 operations.
 * - 0 + 3 = 3
 * - 3 - 7 = -4
 * Note that the last operation sets x out of the range 0 &lt;= x &lt;= 1000, which is valid.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [2,8,16], start = 0, goal = 1
 * <strong>Output:</strong> -1
 * <strong>Explanation:</strong> There is no way to convert 0 into 1.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= nums.length &lt;= 1000</code></li>
 * 	<li><code>-10<sup>9</sup> &lt;= nums[i], goal &lt;= 10<sup>9</sup></code></li>
 * 	<li><code>0 &lt;= start &lt;= 1000</code></li>
 * 	<li><code>start != goal</code></li>
 * 	<li>All the integers in <code>nums</code> are distinct.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2059_MinimumOperationsToConvertNumber {

    // bfs
    @Answer
    public int minimumOperations(int[] nums, int start, int goal) {
        boolean[] tried = new boolean[1001];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        for (int times = 0; !queue.isEmpty(); times++) {
            for (int size = queue.size(); size > 0; size--) {
                int x = queue.poll();
                if (x == goal) {
                    return times;
                }
                if (x < 0 || 1000 < x || tried[x]) {
                    continue;
                }
                tried[x] = true;
                for (int num : nums) {
                    queue.offer(x + num);
                    queue.offer(x - num);
                    queue.offer(x ^ num);
                }
            }
        }
        return -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{2, 4, 12}, 2, 12).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{3, 5, 7}, 0, -4).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{2, 8, 16}, 0, 1).expect(-1);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{
                    81, -5, 22, -57, 9, -97, 95, 80, 5, -4, -74, 77, -63, 93, 70, -56, 35, -79, -31, -75, 23, 24, -73,
                    -43, -25, -35, -94, 25, 89, 41, 94, 61, -24, -16, 49, -44, 4, -92, -100, 30, -52, -33, 47, 68, -53,
                    78, -2, 11, 39, -20, 44, 34, -8, -22, 99, -86, -40, 2, -90, -85, 14, -14, -99, -84, 55, 28, 8, -88,
                    -62, 88, -38, 38, 15, -6, 45, 73, 72, 90, 84, 16, -69, 21, -89, 29, 57, -15, 50, 27, -18, -60, -70,
                    31, 52, 85
            }, 62, 936)
            .expect(9);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(new int[]{-783, 696}, 43, 161).expect(48);

}
