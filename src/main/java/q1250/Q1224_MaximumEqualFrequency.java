package q1250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1224. Maximum Equal Frequency
 * https://leetcode.com/problems/maximum-equal-frequency/
 *
 * Given an array nums of positive integers, return the longest possible length of an array prefix of nums, such that it
 * is possible to remove exactly one element from this prefix so that every number that has appeared in it will have the
 * same number of occurrences.
 *
 * If after removing one element there are no remaining elements, it's still considered that every appeared number has
 * the same number of ocurrences (0).
 *
 * Example 1:
 *
 * Input: nums = [2,2,1,1,5,3,3,5]
 * Output: 7
 * Explanation: For the subarray [2,2,1,1,5,3,3] of length 7, if we remove nums[4]=5, we will get [2,2,1,1,3,3], so that
 * each number will appear exactly twice.
 *
 * Example 2:
 *
 * Input: nums = [1,1,1,2,2,2,3,3,3,4,4,4,5]
 * Output: 13
 *
 * Example 3:
 *
 * Input: nums = [1,1,1,2,2,2]
 * Output: 5
 *
 * Example 4:
 *
 * Input: nums = [10,2,8,9,3,8,1,5,2,3,7,6]
 * Output: 8
 *
 * Constraints:
 *
 * 2 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1224_MaximumEqualFrequency {

    //    @DebugWith("normal3")
    @Answer
    public int maxEqualFreq(int[] nums) {
        final int n = nums.length;
        // 每个数字出现的计数
        int[] counts = new int[10_0001];
        // 每个数字计数对应的计数
        int[] times = new int[n + 1];
        // time 表示times 中有值的数量
        int time = 0, res = 0;
        for (int i = 0; i < n; i++) {
            int cnt = counts[nums[i]];
            times[cnt]--;
            if (times[cnt] == 0) {
                time--;
            }

            counts[nums[i]] = ++cnt;
            times[cnt]++;
            if (times[cnt] == 1) {
                time++;
            }

            if (time == 1) {
                // 只有1 种出现次数
                if (cnt == 1 || times[cnt] == 1) {
                    // 所有数字都只出现一次或全是同一个数字
                    res = i + 1;
                }
            } else if (time == 2) {
                // 只有2 种出现次数
                if (times[1] == 1
                        || times[cnt + 1] == 1
                        || times[cnt] == 1 && times[cnt - 1] > 0) {
                    // 可以扣除只出现1 次的数字
                    // 可以扣除出现 cnt+1 位置只出现1 次的数字 (cnt 位置确定是有数字的)
                    // 可以扣除 cnt 位置只出现1 次的数字 (cnt-1 位置要有数字)
                    res = i + 1;
                }
            }
        }
        return res;
    }

    /**
     * LeetCode 上的主流解法, 与上面解法类似, 在判定条件上有些不同.
     */
    @Answer
    public int maxEqualFreq2(int[] nums) {
        final int n = nums.length;
        int[] counts = new int[10_0001];
        int[] times = new int[n + 1];
        int res = 0;
        for (int len = 1; len <= n; len++) {
            int cnt = counts[nums[len - 1]];
            times[cnt]--;
            counts[nums[len - 1]] = ++cnt;
            times[cnt]++;

            // 当前所有数字的次数一样, 则只需要随便扣除之后的1 个数字即可.
            if (times[cnt] * cnt == len && len < n) {
                res = len + 1;
            }
            // 与当相同次数的数字不同的数字的总数
            int other = len - times[cnt] * cnt;
            // 只有1 个不同的时候, 且是cnt+1 或1 的时候才能扣掉other 中的1 个数字.
            if (times[other] == 1 && (other == cnt + 1 || other == 1)) {
                res = len;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 2, 1, 1, 5, 3, 3/* end */, 5}).expect(7);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[]{1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5/* end */})
            .expect(13);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 1, 1, 2, 2/* end */, 2}).expect(5);

    @TestData
    public DataExpectation example4 = DataExpectation
            .create(new int[]{10, 2, 8, 9, 3, 8, 1, 5/* end */, 2, 3, 7, 6})
            .expect(8);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1, 2}).expect(2);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{1, 1}).expect(2);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .create(new int[]{1, 2, 3, 1, 2, 3, 4, 4, 4, 4, 1, 2, 3/* end */, 5, 6})
            .expect(13);

    @TestData
    public DataExpectation normal4 = DataExpectation
            .create(TestDataFileHelper.readIntegerArray("Q1224_TestData"))
            .expect(25999);

}
