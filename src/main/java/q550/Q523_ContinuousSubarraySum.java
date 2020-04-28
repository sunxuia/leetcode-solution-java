package q550;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/continuous-subarray-sum/
 *
 * Given a list of non-negative numbers and a target integer k, write a function to check if the array has a
 * continuous subarray of size at least 2 that sums up to a multiple of k, that is, sums up to n*k where n is also an
 * integer.
 *
 *
 *
 * Example 1:
 *
 * Input: [23, 2, 4, 6, 7],  k=6
 * Output: True
 * Explanation: Because [2, 4] is a continuous subarray of size 2 and sums up to 6.
 *
 * Example 2:
 *
 * Input: [23, 2, 6, 4, 7],  k=6
 * Output: True
 * Explanation: Because [23, 2, 6, 4, 7] is an continuous subarray of size 5 and sums up to 42.
 *
 *
 *
 * Note:
 *
 * The length of the array won't exceed 10,000.
 * You may assume the sum of all the numbers is in the range of a signed 32-bit integer.
 */
@RunWith(LeetCodeRunner.class)
public class Q523_ContinuousSubarraySum {

    @Answer
    public boolean checkSubarraySum(int[] nums, int k) {
        final int len = nums.length;
        int[] sums = new int[len + 1];
        for (int i = 0; i < len; i++) {
            sums[i + 1] = sums[i] + nums[i];
        }
        for (int i = 1; i < len; i++) {
            for (int j = 0; j <= i - 1; j++) {
                int sum = sums[i + 1] - sums[j];
                if (k == 0 && sum == 0 ||
                        k != 0 && sum % k == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    // LeetCode 上比较快的解法, 通过求救
    @Answer
    public boolean checkSubarraySum2(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        // 哨兵, 针对测试用例 normal2 对应的情况(k=0 且有2 个连续0 元素)
        map.put(0, -1);
        // 前面所有数字和与k 的余数
        int preSum = 0;
        for (int i = 0; i < nums.length; i++) {
            preSum += nums[i];
            if (k != 0) {
                preSum %= k;
            }
            // 如果余数存在这说明此数字减去对应的数字结果是k 的倍数(余数相抵消了),
            // 加上题目要求的子数组长度 > 1, 条件都满足则符合题意
            if (map.containsKey(preSum)) {
                if (i - map.get(preSum) > 1) {
                    return true;
                }
            } else {
                map.put(preSum, i);
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{23, 2, 4, 6, 7}, 6).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{23, 2, 6, 4, 7}, 6).expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{23, 2, 6, 4, 7}, 0).expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(new int[]{0, 0}, 0).expect(true);

    @TestData
    public DataExpectation normal3 = DataExpectation.createWith(new int[]{23, 2, 4, 6, 7}, -6).expect(true);

//    @TestData
//    public DataExpectation normal= DataExpectation.createWith(new int[]{}, ).expect(true)

}
