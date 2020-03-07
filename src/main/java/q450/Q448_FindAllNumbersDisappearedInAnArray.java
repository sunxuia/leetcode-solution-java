package q450;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/
 *
 * Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.
 *
 * Find all the elements of [1, n] inclusive that do not appear in this array.
 *
 * Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count as extra
 * space.
 *
 * Example:
 *
 * Input:
 * [4,3,2,7,8,2,3,1]
 *
 * Output:
 * [5,6]
 */
@RunWith(LeetCodeRunner.class)
public class Q448_FindAllNumbersDisappearedInAnArray {

    //

    /**
     * 根据1 ≤ a[i] ≤ n 和元素只会出现0/1/2 次的条件, 对数组进行修改.
     * 这个和 {@link q450.Q442_FindAllDuplicatesInAnArray} 中的思路一样.
     */
    @Answer
    public List<Integer> findDisappearedNumbers(int[] nums) {
        // 将已有的数字对应的位取负数.
        for (int i = 0; i < nums.length; i++) {
            int idx = Math.abs(nums[i]) - 1;
            if (nums[idx] > 0) {
                nums[idx] = -nums[idx];
            }
        }
        // 剩下的正数所在的位就是不存在的元素了.
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                res.add(i + 1);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[]{4, 3, 2, 7, 8, 2, 3, 1})
            .expect(new int[]{5, 6})
            .unorderResult()
            .build();

}
