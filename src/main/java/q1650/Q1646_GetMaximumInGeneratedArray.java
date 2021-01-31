package q1650;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1646. Get Maximum in Generated Array
 * https://leetcode.com/problems/get-maximum-in-generated-array/
 *
 * You are given an integer n. An array nums of length n + 1 is generated in the following way:
 *
 * nums[0] = 0
 * nums[1] = 1
 * nums[2 * i] = nums[i] when 2 <= 2 * i <= n
 * nums[2 * i + 1] = nums[i] + nums[i + 1] when 2 <= 2 * i + 1 <= n
 *
 * Return the maximum integer in the array nums???.
 *
 * Example 1:
 *
 * Input: n = 7
 * Output: 3
 * Explanation: According to the given rules:
 * nums[0] = 0
 * nums[1] = 1
 * nums[(1 * 2) = 2] = nums[1] = 1
 * nums[(1 * 2) + 1 = 3] = nums[1] + nums[2] = 1 + 1 = 2
 * nums[(2 * 2) = 4] = nums[2] = 1
 * nums[(2 * 2) + 1 = 5] = nums[2] + nums[3] = 1 + 2 = 3
 * nums[(3 * 2) = 6] = nums[3] = 2
 * nums[(3 * 2) + 1 = 7] = nums[3] + nums[4] = 2 + 1 = 3
 * Hence, nums = [0,1,1,2,1,3,2,3], and the maximum is 3.
 *
 * Example 2:
 *
 * Input: n = 2
 * Output: 1
 * Explanation: According to the given rules, the maximum between nums[0], nums[1], and nums[2] is 1.
 *
 * Example 3:
 *
 * Input: n = 3
 * Output: 2
 * Explanation: According to the given rules, the maximum between nums[0], nums[1], nums[2], and nums[3] is 2.
 *
 * Constraints:
 *
 * 0 <= n <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1646_GetMaximumInGeneratedArray {

    @Answer
    public int getMaximumGenerated(int n) {
        if (n == 0) {
            return 0;
        }
        int[] arr = new int[n + 1];
        arr[1] = 1;
        int res = 1;
        for (int i = 2; i <= n; i++) {
            if (i % 2 == 0) {
                arr[i] = arr[i / 2];
            } else {
                arr[i] = arr[i / 2] + arr[i / 2 + 1];
                res = Math.max(res, arr[i]);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(7).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(2).expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.create(3).expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(0).expect(0);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(4).expect(2);

    @TestData
    public DataExpectation normal3 = DataExpectation.create(15).expect(5);

    @TestData
    public DataExpectation normal4 = DataExpectation.create(11).expect(5);

}
