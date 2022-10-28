package q2000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1995. Count Special Quadruplets
 * https://leetcode.com/problems/count-special-quadruplets/
 *
 * Given a 0-indexed integer array nums, return the number of distinct quadruplets (a, b, c, d) such that:
 *
 * nums[a] + nums[b] + nums[c] == nums[d], and
 * a < b < c < d
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,6]
 * Output: 1
 * Explanation: The only quadruplet that satisfies the requirement is (0, 1, 2, 3) because 1 + 2 + 3 == 6.
 *
 * Example 2:
 *
 * Input: nums = [3,3,6,4,5]
 * Output: 0
 * Explanation: There are no such quadruplets in [3,3,6,4,5].
 *
 * Example 3:
 *
 * Input: nums = [1,1,1,3,5]
 * Output: 4
 * Explanation: The 4 quadruplets that satisfy the requirement are:
 * - (0, 1, 2, 3): 1 + 1 + 1 == 3
 * - (0, 1, 3, 4): 1 + 1 + 3 == 5
 * - (0, 2, 3, 4): 1 + 1 + 3 == 5
 * - (1, 2, 3, 4): 1 + 1 + 3 == 5
 *
 * Constraints:
 *
 * 4 <= nums.length <= 50
 * 1 <= nums[i] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1995_CountSpecialQuadruplets {

    @Answer
    public int countQuadruplets(int[] nums) {
        final int n = nums.length;
        int[] counts = new int[101];
        for (int i = 0; i < n; i++) {
            counts[nums[i]]++;
        }
        int res = 0;
        for (int a = 0; a < n; a++) {
            counts[nums[a]]--;
            for (int b = a + 1; b < n; b++) {
                counts[nums[b]]--;
                for (int c = b + 1; c < n; c++) {
                    counts[nums[c]]--;
                    int nd = nums[a] + nums[b] + nums[c];
                    if (nd <= 100 && counts[nd] > 0) {
                        res += counts[nd];
                    }
                }
                for (int c = b + 1; c < n; c++) {
                    counts[nums[c]]++;
                }
            }
            for (int b = a + 1; b < n; b++) {
                counts[nums[b]]++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 6}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{3, 3, 6, 4, 5}).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 1, 1, 3, 5}).expect(4);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{8, 73, 11, 9, 28, 92, 52}).expect(2);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{28, 8, 49, 85, 37, 90, 20, 8}).expect(1);

    @TestData
    public DataExpectation normal3 = DataExpectation.create(new int[]{9, 6, 8, 23, 39, 23}).expect(2);

}
