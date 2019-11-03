package q100;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/combinations/
 *
 * Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.
 *
 * Example:
 *
 * Input: n = 4, k = 2
 * Output:
 * [
 * [2,4],
 * [3,4],
 * [2,3],
 * [1,2],
 * [1,3],
 * [1,4],
 * ]
 */
@RunWith(LeetCodeRunner.class)
public class Q077_Combinations {

    @Answer
    public List<List<Integer>> combine(int n, int k) {
        if (k == 0) {
            return Collections.emptyList();
        }
        List<List<Integer>> res = new ArrayList<>();
        dfs(res, new ArrayList<>(k), 1, n, k);
        return res;
    }

    public void dfs(List<List<Integer>> res, ArrayList<Integer> buffer, int i, int n, int k) {
        if (k == 0) {
            res.add((List<Integer>) buffer.clone());
            return;
        }
        while (i <= n + 1 - k) {
            buffer.add(i);
            dfs(res, buffer, i + 1, n, k - 1);
            buffer.remove(buffer.size() - 1);
            i++;
        }
    }

    // 这个比较慢
    @Answer
    public List<List<Integer>> bfs(int n, int k) {
        if (k == 0) {
            return Collections.emptyList();
        }
        LinkedList<List<Integer>> res = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(i);
            res.add(list);
        }
        while (--k > 0) {
            for (int i = res.size(); i > 0; i--) {
                List<Integer> list = res.poll();
                for (int j = list.get(list.size() - 1) + 1; j <= n; j++) {
                    List<Integer> newList = new ArrayList<>(list);
                    newList.add(j);
                    res.add(newList);
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(4)
            .addArgument(2)
            .expect(new int[][]{
                    {2, 4},
                    {3, 4},
                    {2, 3},
                    {1, 2},
                    {1, 3},
                    {1, 4}
            }).unorderResult()
            .build();

}
