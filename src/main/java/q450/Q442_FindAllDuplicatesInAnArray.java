package q450;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/find-all-duplicates-in-an-array/
 *
 * Given an array of integers, 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.
 *
 * Find all the elements that appear twice in this array.
 *
 * Could you do it without extra space and in O(n) runtime?
 *
 * Example:
 *
 * Input:
 * [4,3,2,7,8,2,3,1]
 *
 * Output:
 * [2,3]
 */
@RunWith(LeetCodeRunner.class)
public class Q442_FindAllDuplicatesInAnArray {

    // 根据 1 ≤ a[i] ≤ n 的提示即可知道把数字放到对应位上就行了.
    @Answer
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i + 1 && nums[i] != -1) {
                int t = nums[nums[i] - 1];
                if (t == nums[i]) {
                    // 找到重复值, 将当前为设为-1, 表示没有值
                    res.add(nums[i]);
                    nums[i] = -1;
                } else {
                    // 将nums[i] 的数字放到对应位置上
                    nums[nums[i] - 1] = nums[i];
                    nums[i] = t;
                }
            }
        }
        return res;
    }

    // LeetCode 上另一种比较巧妙的方法, 利用了数字只会出现 0/1/2 次的特点, 将出现的数字的对应位的元素设为负数,
    // 这样在遍历的过程中, 如果发现元素值对应下标的元素值为负数, 那么这个元素就是出现了2 次.
    @Answer
    public List<Integer> findDuplicates2(int[] nums) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int idx = Math.abs(nums[i]) - 1;
            if (nums[idx] < 0) {
                res.add(idx + 1);
            }
            nums[idx] = -nums[idx];
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[]{4, 3, 2, 7, 8, 2, 3, 1})
            .expect(Arrays.asList(2, 3))
            .unorderResult()
            .build();

}
