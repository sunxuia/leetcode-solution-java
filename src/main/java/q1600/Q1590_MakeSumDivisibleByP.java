package q1600;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1590. Make Sum Divisible by P
 * https://leetcode.com/problems/make-sum-divisible-by-p/
 *
 * Given an array of positive integers nums, remove the smallest subarray (possibly empty) such that the sum of the
 * remaining elements is divisible by p. It is not allowed to remove the whole array.
 *
 * Return the length of the smallest subarray that you need to remove, or -1 if it's impossible.
 *
 * A subarray is defined as a contiguous block of elements in the array.
 *
 * Example 1:
 *
 * Input: nums = [3,1,4,2], p = 6
 * Output: 1
 * Explanation: The sum of the elements in nums is 10, which is not divisible by 6. We can remove the subarray [4], and
 * the sum of the remaining elements is 6, which is divisible by 6.
 *
 * Example 2:
 *
 * Input: nums = [6,3,5,2], p = 9
 * Output: 2
 * Explanation: We cannot remove a single element to get a sum divisible by 9. The best way is to remove the subarray
 * [5,2], leaving us with [6,3] with sum 9.
 *
 * Example 3:
 *
 * Input: nums = [1,2,3], p = 3
 * Output: 0
 * Explanation: Here the sum is 6. which is already divisible by 3. Thus we do not need to remove anything.
 *
 * Example 4:
 *
 * Input: nums = [1,2,3], p = 7
 * Output: -1
 * Explanation: There is no way to remove a subarray in order to get a sum divisible by 7.
 *
 * Example 5:
 *
 * Input: nums = [1000000000,1000000000,1000000000], p = 3
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^9
 * 1 <= p <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1590_MakeSumDivisibleByP {

    /**
     * 参考网上解法.
     */
    @Answer
    public int minSubarray(int[] nums, int p) {
        final int n = nums.length;
        int mod = 0;
        for (int num : nums) {
            mod = (mod + num) % p;
        }
        if (mod == 0) {
            return 0;
        }

        // 使用 map[v, s] 保存遍历时以当前下标结尾的区间
        // 其和 %p 结果为 v 的最小区间为 [s, curr]
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int res = n, offset = 0;
        for (int i = 0; i < n; i++) {
            // 算上nums[i] 的偏移量
            offset = (offset + nums[i]) % p;
            // map[expect] - nums[i] 的和 = mod + x*p
            int expect = (offset - mod + p) % p;
            if (map.containsKey(expect)) {
                res = Math.min(res, i - map.get(expect));
            }
            map.put(offset, i);
        }
        return res == n ? -1 : res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{3, 1, 4, 2}, 6).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{6, 3, 5, 2}, 9).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{1, 2, 3}, 3).expect(0);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(new int[]{1, 2, 3}, 7).expect(-1);

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(new int[]{10_0000_0000, 10_0000_0000, 10_0000_0000}, 3)
            .expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{3, 6, 8, 1}, 8).expect(-1);

    // 10万个数据
    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(TestDataFileHelper.read(int[].class), 9_4946_7102)
            .expect(4008);

}
