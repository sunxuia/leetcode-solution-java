package q2150;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Hard] 2122. Recover the Original Array</h3>
 * <a href="https://leetcode.com/problems/recover-the-original-array/">
 * https://leetcode.com/problems/recover-the-original-array/
 * </a><br/>
 *
 * <p>Alice had a <strong>0-indexed</strong> array <code>arr</code> consisting of <code>n</code>
 * <strong>positive</strong> integers. She chose an arbitrary <strong>positive integer</strong> <code>k</code> and
 * created two new <strong>0-indexed</strong> integer arrays <code>lower</code> and <code>higher</code> in the following
 * manner:</p>
 *
 * <ol>
 * 	<li><code>lower[i] = arr[i] - k</code>, for every index <code>i</code> where <code>0 &lt;= i &lt; n</code></li>
 * 	<li><code>higher[i] = arr[i] + k</code>, for every index <code>i</code> where <code>0 &lt;= i &lt; n</code></li>
 * </ol>
 *
 * <p>Unfortunately, Alice lost all three arrays. However, she remembers the integers that were present in the arrays
 * <code>lower</code> and <code>higher</code>, but not the array each integer belonged to. Help Alice and recover the
 * original array.</p>
 *
 * <p>Given an array <code>nums</code> consisting of <code>2n</code> integers, where <strong>exactly</strong>
 * <code>n</code> of the integers were present in <code>lower</code> and the remaining in <code>higher</code>, return
 * <em>the <strong>original</strong> array</em> <code>arr</code>. In case the answer is not unique, return
 * <em><strong>any</strong> valid array</em>.</p>
 *
 * <p><strong>Note:</strong> The test cases are generated such that there exists <strong>at least one</strong> valid
 * array <code>arr</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [2,10,6,4,8,12]
 * <strong>Output:</strong> [3,7,11]
 * <strong>Explanation:</strong>
 * If arr = [3,7,11] and k = 1, we get lower = [2,6,10] and higher = [4,8,12].
 * Combining lower and higher gives us [2,6,10,4,8,12], which is a permutation of nums.
 * Another valid possibility is that arr = [5,7,9] and k = 3. In that case, lower = [2,4,6] and higher = [8,10,12].
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [1,1,3,3]
 * <strong>Output:</strong> [2,2]
 * <strong>Explanation:</strong>
 * If arr = [2,2] and k = 1, we get lower = [1,1] and higher = [3,3].
 * Combining lower and higher gives us [1,1,3,3], which is equal to nums.
 * Note that arr cannot be [1,3] because in that case, the only possible way to obtain [1,1,3,3] is with k = 0.
 * This is invalid since k must be positive.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [5,435]
 * <strong>Output:</strong> [220]
 * <strong>Explanation:</strong>
 * The only possible combination is arr = [220] and k = 215. Using them, we get lower = [5] and higher = [435].
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>2 * n == nums.length</code></li>
 * 	<li><code>1 &lt;= n &lt;= 1000</code></li>
 * 	<li><code>1 &lt;= nums[i] &lt;= 10<sup>9</sup></code></li>
 * 	<li>The test cases are generated such that there exists <strong>at least one</strong> valid array <code>arr</code>
 * 	.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2122_RecoverTheOriginalArray {

    /**
     * 首先给数组排序,
     * 最小的肯定是 nums[0] = lower[0] = arr[0]-k, 之后找出与 lower[0] 对应的 higher[0],
     * 假设 higher[0] = lower[0] + 2k = nums[h0], 则 nums(0:h0) 之间的元素就都是 lower 的元素, 因为 lower[0] 是lower 中最小的.
     * 根据这个规律找出所有的数字.
     */
    @Answer
    public int[] recoverArray(int[] nums) {
        final int n2 = nums.length;
        Arrays.sort(nums);
        int[] states = new int[n2];
        for (int i = 1; i <= n2; i++) {
            int k2 = nums[i] - nums[0];
            if (k2 > 0 && k2 % 2 == 0) {
                dfs(nums, states, k2, 0, 1, 0);
                if (arr != null) {
                    return arr;
                }
            }
        }
        return null;
    }

    private int[] arr;

    private void dfs(int[] nums, int[] states, int k2, int low, int high, int matched) {
        final int n2 = nums.length;
        if (matched == n2) {
            arr = new int[n2 / 2];
            for (int i = 0, j = 0; i < n2; i++) {
                if (states[i] == -1) {
                    arr[j++] = nums[i] + k2 / 2;
                }
            }
            return;
        }
        if (high == n2) {
            return;
        }

        // 找出第一个可用的元素, 这个元素一定是 lower 中的元素
        for (; low < n2; low++) {
            if (states[low] == 0) {
                break;
            }
        }
        states[low] = -1;

        // higher 遍历的过程肯定state 都是0, 所以这里不需要判断 state[higher] 是否为0
        // 只需要在打算跳过 nums[i] 的时候(此时表示 nums[i] 属于lower, 需要检查是否有对应的 higher),
        // 检查是否有对应的 higher
        int expect = nums[low] + k2;
        int nextHigher = high + 1;
        // 如果这个higher 不匹配, 则说明这个higher 不属于当前的lower, 需要找到下一个匹配的higher
        // 此时 nums[higher] = lower[lower+1]
        for (int i = high; i < n2 && nums[i] <= expect; i++) {
            if (nums[i] == expect) {
                states[i] = 1;
                dfs(nums, states, k2, low + 1, i + 1, matched + 2);
                if (arr != null) {
                    return;
                }
                states[i] = 0;
            }

            // 如果打算跳过 nums[i], 则表示 nums[i] 属于 lower, 需要找到对应的 higher,
            // 如果找不到, 则后续的lower 无法进行匹配, 直接失败
            while (nextHigher < n2 && nums[nextHigher] < nums[i] + k2) {
                nextHigher++;
            }
            if (nextHigher == n2 || nums[nextHigher] != nums[i] + k2) {
                break;
            }
            nextHigher++;
        }
        states[low] = 0;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[]{2, 10, 6, 4, 8, 12})
            .unOrder()
            .expect(new int[]{3, 7, 11})
            .orExpect(new int[]{5, 7, 9});

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 1, 3, 3}).expect(new int[]{2, 2});

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{5, 435}).expect(new int[]{220});

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{3, 7, 7, 1, 5, 3}).expect(new int[]{3, 5, 5});

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(new int[]{79, 96, 98, 60, 77, 80, 97, 22, 27, 4, 89, 61, 91, 58, 98, 42, 75, 40, 65, 64, 73, 14, 97,
                    78, 75, 69, 92, 58, 10, 60, 28, 13, 96, 63, 11, 67, 53, 20, 77, 99, 68, 32, 95, 100, 88, 26, 60, 85,
                    8, 97, 90, 83, 10, 90, 25, 38, 75, 38, 73, 66, 98, 16, 40, 95, 19, 54, 52, 23, 62, 44, 30, 71, 2,
                    77, 12, 51, 66, 21, 25, 75})
            .expect(new int[]{3, 9, 11, 12, 15, 20, 21, 24, 26, 27, 31, 39, 39, 43, 52, 53, 59, 59, 61, 62, 65, 66, 67,
                    70, 74, 74, 76, 76, 78, 79, 84, 89, 90, 91, 96, 96, 97, 97, 98, 99});

    @TestData
    public DataExpectation normal3 = DataExpectation
            .create(new int[]{97, 46, 43, 84, 64, 11, 41, 35, 88, 44, 52, 45, 66, 93, 58, 64, 79, 50, 21, 23, 85, 56,
                    94, 11, 60, 72, 63, 91, 43, 71, 33, 17, 79, 73, 67, 51, 73, 61, 42, 62, 65, 87, 29, 58, 36, 78, 76,
                    54, 46, 84, 39, 37, 76, 1, 5, 47, 7, 37, 76, 82, 17, 40, 82, 78, 27, 57, 55, 70})
            .expect(new int[]{4, 8, 14, 20, 24, 32, 36, 39, 40, 40, 43, 44, 47, 48, 49, 57, 58, 59, 60, 61, 61, 68, 69,
                    70, 73, 76, 79, 79, 81, 81, 82, 90, 91, 94});

}
