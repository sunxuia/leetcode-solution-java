package q1700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1674. Minimum Moves to Make Array Complementary
 * https://leetcode.com/problems/minimum-moves-to-make-array-complementary/
 *
 * You are given an integer array nums of even length n and an integer limit. In one move, you can replace any integer
 * from nums with another integer between 1 and limit, inclusive.
 *
 * The array nums is complementary if for all indices i (0-indexed), nums[i] + nums[n - 1 - i] equals the same number.
 * For example, the array [1,2,3,4] is complementary because for all indices i, nums[i] + nums[n - 1 - i] = 5.
 *
 * Return the minimum number of moves required to make nums complementary.
 *
 * Example 1:
 *
 * Input: nums = [1,2,4,3], limit = 4
 * Output: 1
 * Explanation: In 1 move, you can change nums to [1,2,2,3] (underlined elements are changed).
 * nums[0] + nums[3] = 1 + 3 = 4.
 * nums[1] + nums[2] = 2 + 2 = 4.
 * nums[2] + nums[1] = 2 + 2 = 4.
 * nums[3] + nums[0] = 3 + 1 = 4.
 * Therefore, nums[i] + nums[n-1-i] = 4 for every i, so nums is complementary.
 *
 * Example 2:
 *
 * Input: nums = [1,2,2,1], limit = 2
 * Output: 2
 * Explanation: In 2 moves, you can change nums to [2,2,2,2]. You cannot change any number to 3 since 3 > limit.
 *
 * Example 3:
 *
 * Input: nums = [1,2,1,2], limit = 2
 * Output: 0
 * Explanation: nums is already complementary.
 *
 * Constraints:
 *
 * n == nums.length
 * 2 <= n <= 10^5
 * 1 <= nums[i] <= limit <= 10^5
 * n is even.
 */
@RunWith(LeetCodeRunner.class)
public class Q1674_MinimumMovesToMakeArrayComplementary {

    /**
     * 遍历每组nums[i] + nums[n - 1 - i], 令 a = nums[i], b = nums[n - 1 - i], 则 2 <= a + b <= 2 * limit,
     * 使用x 表示要修改的a + b 的结果, 则有3 种可能:
     * 1. 如果 x = a + b, 则需要修改0 个数字;
     * 2. 1 + min(a, b) <= x <= limit + max(a, b), 则需要修改a 或b 1 个数字;
     * 3. x < 1 + min(a, b) 或 x > limit + max(a,b), 则需要同时修改 a 和 b 2个数字.
     * x 需要的变动 : [2 ...        1     ...     0      ...        1           ... 2   ]
     * a + b的结果 : [2 ...   1+min(a, b)       a+b          limit+max(a, b)   2*limit ]
     *
     * 因为a 和b 是固定不动的, 因此可以通过遍历 x 所在的 [2, 2*limit] 区间, 来寻找最小结果.
     * 这里使用差分数组的方式来寻找变动最小的结果.
     *
     * @formatter:off
     * 参考链接
     * https://leetcode-cn.com/problems/minimum-moves-to-make-array-complementary/solution/jie-zhe-ge-wen-ti-xue-xi-yi-xia-chai-fen-shu-zu-on/
     * @formatter:on
     */
    @Answer
    public int minMoves(int[] nums, int limit) {
        final int n = nums.length;
        int[] xDiffs = new int[limit * 2 + 2];
        for (int i = 0; i < n / 2; i++) {
            int a = nums[i];
            int b = nums[n - 1 - i];

            // [2 - 1+min(a, b) ) 变动2
            xDiffs[2] += 2;
            // [1+min(a, b), a+b) 变动1
            xDiffs[1 + Math.min(a, b)]--;
            // a+b 变动0
            xDiffs[a + b]--;
            // (a+b, limit+max(a, b)] 变动1
            xDiffs[a + b + 1]++;
            // ( limit+max(a, b), 2*limit] 变动2
            xDiffs[limit + Math.max(a, b) + 1]++;
        }

        // 遍历x, 寻找最小的结果.
        int changes = 0, res = Integer.MAX_VALUE;
        for (int x = 2; x <= 2 * limit; x++) {
            changes += xDiffs[x];
            res = Math.min(res, changes);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 2, 4, 3}, 4).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, 2, 2, 1}, 2).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{1, 2, 1, 2}, 2).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{37, 2, 9, 49, 58, 57, 48, 17}, 58)
            .expect(3);

}
