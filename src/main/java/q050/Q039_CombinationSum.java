package q050;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/combination-sum/
 *
 * Given a set of candidate numbers (candidates) (without duplicates) and a target number (target), find all unique
 * combinations in candidates where the candidate numbers sums to target.
 *
 * The same repeated number may be chosen from candidates unlimited number of times.
 *
 * Note:
 *
 * All numbers (including target) will be positive integers.
 * The solution set must not contain duplicate combinations.
 * Example 1:
 *
 * Input: candidates = [2,3,6,7], target = 7,
 * A solution set is:
 * [
 * [7],
 * [2,2,3]
 * ]
 * Example 2:
 *
 * Input: candidates = [2,3,5], target = 8,
 * A solution set is:
 * [
 * [2,2,2,2],
 * [2,3,3],
 * [3,5]
 * ]
 *
 * 题解:
 * 题设中的candidates 和 target 都是正数, candidates 中的每个元素可以重复使用多次.
 */
@RunWith(LeetCodeRunner.class)
public class Q039_CombinationSum {

    @Answer
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> res = new ArrayList<>();
        dfs(candidates, 0, target, new ArrayList<>(), res);
        return res;
    }

    @SuppressWarnings("unchecked")
    private void dfs(int[] candidates, int index, int target, ArrayList<Integer> nums, List<List<Integer>> res) {
        if (target == 0) {
            res.add((ArrayList<Integer>) nums.clone());
            return;
        }
        if (index == candidates.length || target < candidates[index]) {
            return;
        }
        int repeatTimes = target / candidates[index];
        for (int i = 0; i <= repeatTimes; i++) {
            dfs(candidates, index + 1, target - i * candidates[index], nums, res);
            nums.add(candidates[index]);
        }
        for (int i = 0; i <= repeatTimes; i++) {
            nums.remove(nums.size() - 1);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[]{2, 3, 6, 7})
            .addArgument(7)
            .expect(new int[][]{{7}, {2, 2, 3}})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[]{2, 3, 5})
            .addArgument(8)
            .expect(new int[][]{{2, 2, 2, 2}, {2, 3, 3}, {3, 5}})
            .unorderResult()
            .build();
}
