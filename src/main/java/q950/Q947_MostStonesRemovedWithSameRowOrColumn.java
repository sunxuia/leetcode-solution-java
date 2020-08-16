package q950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 947. Most Stones Removed with Same Row or Column
 * https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/
 *
 * On a 2D plane, we place stones at some integer coordinate points.  Each coordinate point may have at most one stone.
 *
 * Now, a move consists of removing a stone that shares a column or row with another stone on the grid.
 *
 * What is the largest possible number of moves we can make?
 *
 * Example 1:
 *
 * Input: stones = [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]
 * Output: 5
 *
 * Example 2:
 *
 * Input: stones = [[0,0],[0,2],[1,1],[2,0],[2,2]]
 * Output: 3
 *
 * Example 3:
 *
 * Input: stones = [[0,0]]
 * Output: 0
 *
 * Note:
 *
 * 1 <= stones.length <= 1000
 * 0 <= stones[i][j] < 10000
 *
 * 题解: 现有一个二维平面, stones[x, y] 表示石头的坐标, 现在有规则, 选择1 个石头,
 * 删除与这个石头相同行或列的所有其他石头(必须要有其他石头), 问最多可以执行这种规则多少次?
 */
@RunWith(LeetCodeRunner.class)
public class Q947_MostStonesRemovedWithSameRowOrColumn {

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 2}, {2, 1}, {2, 2}})
            .expect(5);
    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{0, 0}, {0, 2}, {1, 1}, {2, 0}, {2, 2}})
            .expect(3);
    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{{0, 0}}).expect(0);

    /**
     * 参考文档 https://www.cnblogs.com/grandyang/p/13024709.html
     * 这题可以使用并查集, 对于属于同一个并查集的石头, 总有办法能按顺序移除到只剩一个石子,
     * 所以总共有多少个群组, 最终就会剩下多少个石头, 最大的移除个数就是总石子个数减去群组个数.
     */
    @Answer
    public int removeStones(int[][] stones) {
        final int n = stones.length;
        int[] roots = new int[n];
        for (int i = 0; i < n; ++i) {
            roots[i] = i;
        }

        // 相同行列放入相同并查集
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (stones[i][0] == stones[j][0]
                        || stones[i][1] == stones[j][1]) {
                    roots[getRoot(roots, i)] = getRoot(roots, j);
                }
            }
        }

        // 计算并查集的数量
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (roots[i] == i) {
                count++;
            }
        }
        return n - count;
    }

    int getRoot(int[] roots, int i) {
        if (roots[i] == i) {
            return i;
        }
        return roots[i] = getRoot(roots, roots[i]);
    }

}
