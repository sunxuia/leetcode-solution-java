package q2100;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Easy] 2099. Find Subsequence of Length K With the Largest Sum</h3>
 * <a href="https://leetcode.com/problems/find-subsequence-of-length-k-with-the-largest-sum/">
 * https://leetcode.com/problems/find-subsequence-of-length-k-with-the-largest-sum/
 * </a><br/>
 *
 * <p>You are given an integer array <code>nums</code> and an integer <code>k</code>. You want to find a
 * <strong>subsequence </strong>of <code>nums</code> of length <code>k</code> that has the <strong>largest</strong>
 * sum.</p>
 *
 * <p>Return<em> </em><em><strong>any</strong> such subsequence as an integer array of length </em><code>k</code>.</p>
 *
 * <p>A <strong>subsequence</strong> is an array that can be derived from another array by deleting some or no elements
 * without changing the order of the remaining elements.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [2,1,3,3], k = 2
 * <strong>Output:</strong> [3,3]
 * <strong>Explanation:</strong>
 * The subsequence has the largest sum of 3 + 3 = 6.</pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [-1,-2,3,4], k = 3
 * <strong>Output:</strong> [-1,3,4]
 * <strong>Explanation:</strong>
 * The subsequence has the largest sum of -1 + 3 + 4 = 6.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [3,4,3,3], k = 2
 * <strong>Output:</strong> [3,4]
 * <strong>Explanation:</strong>
 * The subsequence has the largest sum of 3 + 4 = 7.
 * Another possible subsequence is [4, 3].
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= nums.length &lt;= 1000</code></li>
 * 	<li><code>-10<sup>5</sup>&nbsp;&lt;= nums[i] &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>1 &lt;= k &lt;= nums.length</code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2099_FindSubsequenceOfLengthKWithTheLargestSum {

    @Answer
    public int[] maxSubsequence(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(k, Comparator.comparingInt(i -> nums[i]));
        for (int i = 0; i < k; i++) {
            pq.offer(i);
        }
        for (int i = k; i < nums.length; i++) {
            pq.offer(i);
            pq.poll();
        }

        int[] indexes = new int[k];
        for (int i = 0; i < k; i++) {
            indexes[i] = pq.poll();
        }
        Arrays.sort(indexes);

        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = nums[indexes[i]];
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{2, 1, 3, 3}, 2)
            .expect(new int[]{3, 3});

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{-1, -2, 3, 4}, 3)
            .expect(new int[]{-1, 3, 4});

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument(new int[]{3, 4, 3, 3})
            .addArgument(2)
            .expect(new int[]{3, 4})
            .orExpect(new int[]{4, 3})
            .build();

}
