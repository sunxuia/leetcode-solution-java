package q1000;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 996. Number of Squareful Arrays
 * https://leetcode.com/problems/number-of-squareful-arrays/
 *
 * Given an array A of non-negative integers, the array is squareful if for every pair of adjacent elements, their sum
 * is a perfect square.
 *
 * Return the number of permutations of A that are squareful.  Two permutations A1 and A2 differ if and only if there is
 * some index i such that A1[i] != A2[i].
 *
 * Example 1:
 *
 * Input: [1,17,8]
 * Output: 2
 * Explanation:
 * [1,8,17] and [17,8,1] are the valid permutations.
 *
 * Example 2:
 *
 * Input: [2,2,2]
 * Output: 1
 *
 * Note:
 *
 * 1 <= A.length <= 12
 * 0 <= A[i] <= 1e9
 */
@RunWith(LeetCodeRunner.class)
public class Q996_NumberOfSquarefulArrays {

    /**
     * 参考 Solution 中的解法.
     */
    @Answer
    public int numSquarefulPerms(int[] A) {
        counts = new HashMap<>();
        graph = new HashMap<>();

        // 计算元素和次数
        for (int a : A) {
            counts.put(a, counts.getOrDefault(a, 0) + 1);
        }

        // 构造连通图
        for (int a : counts.keySet()) {
            graph.put(a, new ArrayList<>());
        }
        for (int x : counts.keySet()) {
            for (int y : counts.keySet()) {
                // +0.5 避免double 误差
                int r = (int) (Math.sqrt(x + y) + 0.5);
                if (r * r == x + y) {
                    graph.get(x).add(y);
                }
            }
        }

        // 遍历图, 找出所有组合的数量
        int res = 0;
        for (int x : counts.keySet()) {
            res += dfs(x, A.length - 1);
        }
        return res;
    }

    // A 中元素和对应的次数
    private Map<Integer, Integer> counts;

    // A 中元素和对应和为平方数的数字列表 (组成一个无向连通图)
    private Map<Integer, List<Integer>> graph;

    public int dfs(int x, int remain) {
        // 减去当前节点数量
        counts.put(x, counts.get(x) - 1);
        int res = 1;
        if (remain > 0) {
            res = 0;
            // 遍历相邻节点, 继续构造
            for (int y : graph.get(x)) {
                if (counts.get(y) != 0) {
                    res += dfs(y, remain - 1);
                }
            }
        }
        counts.put(x, counts.get(x) + 1);
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 17, 8}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 2, 2}).expect(1);

}
