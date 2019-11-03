package q050;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/permutations-ii/
 *
 * Given a collection of numbers that might contain duplicates, return all possible unique permutations.
 *
 * Example:
 *
 * Input: [1,1,2]
 * Output:
 * [
 * [1,1,2],
 * [1,2,1],
 * [2,1,1]
 * ]
 *
 * 题解:
 * 包含重复数字的排列组合.
 */
@RunWith(LeetCodeRunner.class)
public class Q047_PermutationsII {

    @Answer
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(nums, res, new ArrayList<>());
        return res;
    }

    private void dfs(int[] nums, List<List<Integer>> res, ArrayList<Integer> buffer) {
        if (buffer.size() == nums.length) {
            res.add((ArrayList<Integer>) buffer.clone());
            return;
        }

        int val = nums[buffer.size()];

        // 添加数字, 如果有相同数字则只能在相同数字后面进行插入.
        int i = 0;
        for (int j = buffer.size() - 1; j >= 0; j--) {
            if (buffer.get(j) == val) {
                i = j + 1;
                break;
            }
        }
        while (i < buffer.size()) {
            buffer.add(i, val);
            dfs(nums, res, buffer);
            buffer.remove(i);
            i++;
        }

        // 添加到尾部
        buffer.add(val);
        dfs(nums, res, buffer);
        buffer.remove(buffer.size() - 1);
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[]{1, 1, 2})
            .expect(new int[][]{
                    {1, 1, 2},
                    {1, 2, 1},
                    {2, 1, 1}
            })
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[]{2, 2, 1, 1})
            .expect(new int[][]{
                    {1, 1, 2, 2},
                    {1, 2, 1, 2},
                    {1, 2, 2, 1},
                    {2, 1, 1, 2},
                    {2, 1, 2, 1},
                    {2, 2, 1, 1}
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
