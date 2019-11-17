package q250;

import java.util.TreeSet;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/contains-duplicate-iii/
 *
 * Given an array of integers, find out whether there are two distinct indices i and j in the array such that the
 * absolute difference between nums[i] and nums[j] is at most t and the absolute difference between i and j is at
 * most k.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,1], k = 3, t = 0
 * Output: true
 *
 * Example 2:
 *
 * Input: nums = [1,0,1,1], k = 1, t = 2
 * Output: true
 *
 * Example 3:
 *
 * Input: nums = [1,5,9,1,5,9], k = 2, t = 3
 * Output: false
 */
@RunWith(LeetCodeRunner.class)
public class Q220_ContainsDuplicateIII {

    // 简单的做法
    @Answer
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        for (int i = 0; i < nums.length; i++) {
            long v = nums[i];
            for (int j = Math.min(i + k, nums.length - 1); j > i; j--) {
                if (v - t <= nums[j] && nums[j] <= v + t) {
                    return true;
                }
            }
        }
        return false;
    }

    // 使用二叉树上下界的特性
    @Answer
    public boolean containsNearbyAlmostDuplicate2(int[] nums, int k, int t) {
        if (t < 0) {
            return false;
        }
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            int v = nums[i];
            Integer floor = set.floor(Integer.MAX_VALUE - t < v ? Integer.MAX_VALUE : v + t);
            Integer ceil = set.ceiling(Integer.MIN_VALUE + t > v ? Integer.MIN_VALUE : v - t);
            if ((floor != null && floor >= nums[i]) || (ceil != null && ceil <= nums[i])) {
                return true;
            }
            set.add(v);
            if (i >= k) {
                set.remove(nums[i - k]);
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 2, 3, 1}, 3, 0).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, 0, 1, 1}, 1, 2).expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{1, 5, 9, 1, 5, 9}, 2, 3).expect(false);

    @TestData
    public DataExpectation border = DataExpectation
            .createWith(new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE + 1}, 3, 3)
            .expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{1, 2}, 0, 1).expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(new int[]{-1, -1}, 1, -1).expect(false);

}
