package q050;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/combination-sum-ii/
 *
 * Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in
 * candidates where the candidate numbers sums to target.
 *
 * Each number in candidates may only be used once in the combination.
 *
 * Note:
 *
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 * Example 1:
 *
 * Input: candidates = [10,1,2,7,6,1,5], target = 8,
 * A solution set is:
 * [
 * [1, 7],
 * [1, 2, 5],
 * [2, 6],
 * [1, 1, 6]
 * ]
 * Example 2:
 *
 * Input: candidates = [2,5,2,1,2], target = 5,
 * A solution set is:
 * [
 * [1,2,2],
 * [5]
 * ]
 *
 * 题解:
 * 和上一题相比, 每个数字的使用次数变成1 次, 且 candidates 中有重复值
 */
@RunWith(LeetCodeRunner.class)
public class Q040_CombinationSumII {

    @Answer
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> res = new ArrayList<>();
        dfs(candidates, candidates.length - 1, target, res, new ArrayList<>());
        return res;
    }

    @SuppressWarnings("unchecked")
    private void dfs(int[] candidates, int i, int target, List<List<Integer>> res, ArrayList<Integer> nums) {
        if (target == 0) {
            res.add((ArrayList<Integer>) nums.clone());
            return;
        }
        if (i < 0) {
            return;
        }
        int v = candidates[i];
        if (target > v + v * i) {
            return;
        }
        int repeat = 1;
        while (i - repeat >= 0 && candidates[i - repeat] == v) {
            repeat++;
        }
        dfs(candidates, i - repeat, target, res, nums);
        int j;
        for (j = 1; j <= repeat && target - v * j >= 0; j++) {
            nums.add(v);
            dfs(candidates, i - repeat, target - v * j, res, nums);
        }
        while (--j > 0) {
            nums.remove(nums.size() - 1);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[]{10, 1, 2, 7, 6, 1, 5})
            .addArgument(8)
            .expect(new int[][]{{1, 7}, {1, 2, 5}, {2, 6}, {1, 1, 6}})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[]{2, 5, 2, 1, 2})
            .addArgument(5)
            .expect(new int[][]{{1, 2, 2}, {5}})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[]{1, 1})
            .addArgument(2)
            .expect(new int[][]{{1, 1}})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(new int[]{2, 2, 2})
            .addArgument(2)
            .expect(new int[][]{{2}})
            .unorderResult()
            .build();

}
