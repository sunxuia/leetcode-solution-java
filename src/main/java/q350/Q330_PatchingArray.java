package q350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/patching-array/
 *
 * Given a sorted positive integer array nums and an integer n, add/patch elements to the array such that any number
 * in range [1, n] inclusive can be formed by the sum of some elements in the array. Return the minimum number of
 * patches required.
 *
 * Example 1:
 *
 * Input: nums = [1,3], n = 6
 * Output: 1
 * Explanation:
 * Combinations of nums are [1], [3], [1,3], which form possible sums of: 1, 3, 4.
 * Now if we add/patch 2 to nums, the combinations are: [1], [2], [3], [1,3], [2,3], [1,2,3].
 * Possible sums are 1, 2, 3, 4, 5, 6, which now covers the range [1, 6].
 * So we only need 1 patch.
 *
 * Example 2:
 *
 * Input: nums = [1,5,10], n = 20
 * Output: 2
 * Explanation: The two patches can be [2, 4].
 *
 * Example 3:
 *
 * Input: nums = [1,2,2], n = 5
 * Output: 0
 */
@RunWith(LeetCodeRunner.class)
public class Q330_PatchingArray {

    // https://www.cnblogs.com/grandyang/p/5165821.html
    @Answer
    public int minPatches(int[] nums, int n) {
        // miss 表示缺失的值, 其以下的值都是可以被组合求出来的
        long miss = 1;
        int res = 0, i = 0;
        while (miss <= n) {
            if (i < nums.length && nums[i] <= miss) {
                // nums_i <= miss, 且 [1, miss), 都是可被求出来的, 则 [1, nums_i] 肯定也可被求出来,
                // 则 [miss, miss + nums_i) 中的值都可以通过 (miss - 1) + [1, nums_i] 求出来.
                miss += nums[i++];
            } else {
                // 没有能够得到 miss 的值了, 则加上miss 作为补丁,
                // 又 [1, miss) 都可被求出来, 则 miss + [1, miss) 都可被求出来.
                miss += miss;
                ++res;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation exmaple1 = DataExpectation.createWith(new int[]{1, 3}, 6).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, 5, 10}, 20).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{1, 2, 2}, 5).expect(0);

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(new int[]{1, 2, 31, 33}, Integer.MAX_VALUE)
            .expect(28);

}
