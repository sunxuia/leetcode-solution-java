package q550;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/contiguous-array/
 *
 * Given a binary array, find the maximum length of a contiguous subarray with equal number of 0 and 1.
 *
 * Example 1:
 *
 * Input: [0,1]
 * Output: 2
 * Explanation: [0, 1] is the longest contiguous subarray with equal number of 0 and 1.
 *
 * Example 2:
 *
 * Input: [0,1,0]
 * Output: 2
 * Explanation: [0, 1] (or [1, 0]) is a longest contiguous subarray with equal number of 0 and 1.
 *
 * Note: The length of the given binary array will not exceed 50,000.
 */
@RunWith(LeetCodeRunner.class)
public class Q525_ContiguousArray {

    /**
     * 暴力的做法时间辅助度是 O(N^2), 会超时.
     * 参考 Solution 中的做法, 累加的时候遇到1 就+1, 遇到0 就-1, 这样如果结果是0 则说明相等.
     * 另外使用Map 来记录本次累加值的下标, 这样如果下次也得到了相等的数字, 那么这2 个坐标之间的区间中0和1也是相等的.
     * Solution 中对此有动画演示.
     * Solution 中还有一种做法是使用数组来替换Map
     * (因为nums 中只有0 和1, 所以sum 的区间就在于[-nums.length * 2, nums.length * 2] 之间.)
     */
    @Answer
    public int findMaxLength(int[] nums) {
        int sum = 0, res = 0;
        Map<Integer, Integer> map = new HashMap<>();
        // 针对sum = 0 的情况的哨兵
        map.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i] == 0 ? -1 : 1;
            if (map.containsKey(sum)) {
                res = Math.max(res, i - map.get(sum));
            } else {
                map.put(sum, i);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{0, 1}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{0, 1, 0}).expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{0, 1, 1, 0, 1, 1, 1, 0}).expect(4);

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.readIntegerArray("Q525_TestData"))
            .expect(21560);

}
