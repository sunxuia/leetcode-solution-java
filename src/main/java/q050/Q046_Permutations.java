package q050;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/permutations/
 *
 * Given a collection of distinct integers, return all possible permutations.
 *
 * Example:
 *
 * Input: [1,2,3]
 * Output:
 * [
 * [1,2,3],
 * [1,3,2],
 * [2,1,3],
 * [2,3,1],
 * [3,1,2],
 * [3,2,1]
 * ]
 *
 * 题解:
 * 非重复数字的排列组合.
 */
@RunWith(LeetCodeRunner.class)
public class Q046_Permutations {

    @Answer
    public List<List<Integer>> permute(int[] nums) {
        int resLength = 1;
        for (int i = 2; i <= nums.length; i++) {
            resLength *= i;
        }
        List<List<Integer>> res = new ArrayList<>(resLength);
        dfs(nums, 0, res);
        return res;
    }

    private void dfs(int[] nums, int cur, List<List<Integer>> res) {
        if (cur == nums.length) {
            List<Integer> array = new ArrayList<>(nums.length);
            for (int num : nums) {
                array.add(num);
            }
            res.add(array);
            return;
        }
        for (int i = cur; i < nums.length; i++) {
            swap(nums, cur, i);
            dfs(nums, cur + 1, res);
            swap(nums, cur, i);
        }
    }

    private void swap(int[] nums, int a, int b) {
        int t = nums[a];
        nums[a] = nums[b];
        nums[b] = t;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[]{1, 2, 3})
            .expect(new int[][]{
                    {1, 2, 3},
                    {1, 3, 2},
                    {2, 1, 3},
                    {2, 3, 1},
                    {3, 1, 2},
                    {3, 2, 1}
            })
            .unorderResult()
            .build();

    @TestData
    public DataExpectation border = DataExpectation.builder()
            .addArgument(new int[0])
            .expect(new int[][]{{}})
            .unorderResult()
            .build();
}
