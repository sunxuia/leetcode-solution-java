package q300;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/longest-increasing-subsequence/
 *
 * Given an unsorted array of integers, find the length of longest increasing subsequence.
 *
 * Example:
 *
 * Input: [10,9,2,5,3,7,101,18]
 * Output: 4
 * Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
 *
 * Note:
 *
 * There may be more than one LIS combination, it is only necessary for you to return the length.
 * Your algorithm should run in O(n^2) complexity.
 *
 * Follow up: Could you improve it to O(n log n) time complexity?
 */
@RunWith(LeetCodeRunner.class)
public class Q300_LongestIncreasingSubsequence {

    // O(n^2) 的解法, dp 解法向前找就好了.
    @Answer
    public int lengthOfLIS_ON(int[] nums) {
        // count[i] 表示到 nums[i] 元素的最大连续数.
        int[] count = new int[nums.length];
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            int max = 0;
            for (int p = i - 1; p >= 0; p--) {
                if (nums[p] < nums[i] && count[p] > max) {
                    max = count[p];
                }
            }
            count[i] = max + 1;
            res = Math.max(res, count[i]);
        }
        return res;
    }

    /**
     * O(NlogN) 的解法, 网上的一个比较简洁的写法, 将查找过程变为二分查找.
     * 构造一个最长的子数组 dp, 找出当前数字 nums[i] 在子数组中的位置, 如果nums[i] 比dp 中所有元素都大, 那么就加入list 的后面,
     * 如果nums[i] 的值在dp 中间, 则将对应位置比它大的值修改成它(虽然它不能组成最长子数组, 但是包含它的最长子数组就是 dp 开头到它这
     * 个位置元素组成的子数组), 确保之后的比它大的值可以放在它的后面.
     */
    @Answer
    public int lengthOfLIS_ONLogN(int[] nums) {
        List<Integer> dp = new ArrayList<>();
        for (int i = 0; i < nums.length; ++i) {
            int left = 0, right = dp.size();
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (dp.get(mid) < nums[i]) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            if (right >= dp.size()) {
                dp.add(nums[i]);
            } else {
                dp.set(right, nums[i]);
            }
        }
        return dp.size();
    }


    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{10, 9, 2, 5, 3, 7, 101, 18}).expect(4);

    @TestData
    public DataExpectation border0 = DataExpectation.create(new int[0]).expect(0);

    @TestData
    public DataExpectation border1 = DataExpectation.create(new int[]{1}).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{2, 1}).expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{1, 3, 6, 7, 9, 4, 10, 5, 6}).expect(6);

}
