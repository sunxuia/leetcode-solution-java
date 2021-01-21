package q1550;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1546. Maximum Number of Non-Overlapping Subarrays With Sum Equals Target
 * https://leetcode.com/problems/maximum-number-of-non-overlapping-subarrays-with-sum-equals-target/
 *
 * Given an array nums and an integer target.
 *
 * Return the maximum number of non-empty non-overlapping subarrays such that the sum of values in each subarray is
 * equal to target.
 *
 * Example 1:
 *
 * Input: nums = [1,1,1,1,1], target = 2
 * Output: 2
 * Explanation: There are 2 non-overlapping subarrays [1,1,1,1,1] with sum equals to target(2).
 *
 * Example 2:
 *
 * Input: nums = [-1,3,5,1,4,2,-9], target = 6
 * Output: 2
 * Explanation: There are 3 subarrays with sum equal to 6.
 * ([5,1], [4,2], [3,5,1,4,2,-9]) but only the first 2 are non-overlapping.
 *
 * Example 3:
 *
 * Input: nums = [-2,6,6,3,5,4,1,2,8], target = 10
 * Output: 3
 *
 * Example 4:
 *
 * Input: nums = [0,0,0], target = 0
 * Output: 3
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 * 0 <= target <= 10^6
 */
@RunWith(LeetCodeRunner.class)
public class Q1546_MaximumNumberOfNonOverlappingSubarraysWithSumEqualsTarget {

    @Answer
    public int maxNonOverlapping(int[] nums, int target) {
        // map[sum, res] 表示子数组 nums[0:i] 的和为sum, 最多有res 个结果.
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        int res = 0, sum = 0;
        for (int num : nums) {
            sum += num;
            int count = 1 + map.getOrDefault(sum - target, -1);
            res = Math.max(res, count);
            map.put(sum, res);
        }
        return res;
    }

    /**
     * LeetCode 中的解法.
     */
    @Answer
    public int maxNonOverlapping2(int[] nums, int target) {
        Set<Integer> set = new HashSet<>();
        set.add(0);
        int res = 0, sum = 0;
        for (int num : nums) {
            sum += num;
            // 这个区间内存在以 num 结尾的和为 target 的子区间, 则从此继续开始.
            // 如果存在重叠的比如和为target 的区间 [i, j] 和 [i', j'] (i < i' < j < j'), 对于后面的 [i2, j2] (j' < i2),
            // 选择 [i, j] 还是 [i', j'] 对于最终选择 [i2, j2] 没有影响.
            if (set.contains(sum - target)) {
                res++;
                sum = 0;
                set.clear();
            }
            set.add(sum);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 1, 1, 1, 1}, 2)
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{-1, 3, 5, 1, 4, 2, -9}, 6)
            .expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{-2, 6, 6, 3, 5, 4, 1, 2, 8}, 10)
            .expect(3);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[]{0, 0, 0}, 0)
            .expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(TestDataFileHelper.read(int[].class), 79)
            .expect(459);

}
