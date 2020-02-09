package q250;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import q050.Q040_CombinationSumII;
import q400.Q377_CombinationSumIV;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/combination-sum-iii/
 *
 * Find all possible combinations of k numbers that add up to a number n, given that only numbers from 1 to 9 can be
 * used and each combination should be a unique set of numbers.
 *
 * Note:
 *
 * All numbers will be positive integers.
 * The solution set must not contain duplicate combinations.
 *
 * Example 1:
 *
 * Input: k = 3, n = 7
 * Output: [[1,2,4]]
 *
 * Example 2:
 *
 * Input: k = 3, n = 9
 * Output: [[1,2,6], [1,3,5], [2,3,4]]
 *
 * 上一题: {@link Q040_CombinationSumII}
 * 下一题: {@link Q377_CombinationSumIV}
 */
@RunWith(LeetCodeRunner.class)
public class Q216_CombinationSumIII {

    // 题目要求结果中的元素 >=0 且 <= 9.
    @Answer
    public List<List<Integer>> combinationSum3(int k, int n) {
        if (k <= 0 || k > 9) {
            return Collections.emptyList();
        }
        List<List<Integer>> res = new ArrayList<>();
        dfs(res, new ArrayList<>(k), k, n, 0, 1);
        return res;
    }

    private void dfs(List<List<Integer>> res, ArrayList<Integer> path, int k, int n, int sum, int start) {
        if (sum + (10 - k + 9) * k / 2 < n || sum + (start + start + k - 1) * k / 2 > n) {
            return;
        }
        if (k == 1) {
            path.add(n - sum);
            res.add((List<Integer>) path.clone());
            path.remove(path.size() - 1);
            return;
        }
        for (int i = start; i <= 10 - k; i++) {
            path.add(i);
            dfs(res, path, k - 1, n, sum + i, i + 1);
            path.remove(path.size() - 1);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(3).addArgument(7)
            .expect(new int[][]{{1, 2, 4}}).unorderResult()
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(3).addArgument(9)
            .expect(new int[][]{{1, 2, 6}, {1, 3, 5}, {2, 3, 4}}).unorderResult()
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(3).addArgument(15)
            .expect(new int[][]{{1, 5, 9}, {1, 6, 8}, {2, 4, 9}, {2, 5, 8}, {2, 6, 7}, {3, 4, 8}, {3, 5, 7}, {4, 5, 6}})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(4).addArgument(24)
            .expect(new int[][]{{1, 6, 8, 9}, {2, 5, 8, 9}, {2, 6, 7, 9}, {3, 4, 8, 9}, {3, 5, 7, 9}, {3, 6, 7, 8},
                    {4, 5, 6, 9}, {4, 5, 7, 8}}).unorderResult()
            .build();

}
