package q1750;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1719. Number Of Ways To Reconstruct A Tree
 * https://leetcode.com/problems/number-of-ways-to-reconstruct-a-tree/
 *
 * You are given an array pairs, where pairs[i] = [xi, yi], and:
 *
 * - There are no duplicates.
 * - xi < yi
 *
 * Let ways be the number of rooted trees that satisfy the following conditions:
 *
 * - The tree consists of nodes whose values appeared in pairs.
 * - A pair [xi, yi] exists in pairs if and only if xi is an ancestor of yi or yi is an ancestor of xi.
 * - Note: the tree does not have to be a binary tree.
 *
 * Two ways are considered to be different if there is at least one node that has different parents in both ways.
 *
 * Return:
 *
 * - 0 if ways == 0
 * - 1 if ways == 1
 * - 2 if ways > 1
 *
 * A rooted tree is a tree that has a single root node, and all edges are oriented to be outgoing from the root.
 *
 * An ancestor of a node is any node on the path from the root to that node (excluding the node itself). The root has no
 * ancestors.
 *
 * Example 1:
 * <img src="./Q1719_PIC1.png">
 * Input: pairs = [[1,2],[2,3]]
 * Output: 1
 * Explanation: There is exactly one valid rooted tree, which is shown in the above figure.
 *
 * Example 2:
 * <img src="./Q1719_PIC2.png">
 * Input: pairs = [[1,2],[2,3],[1,3]]
 * Output: 2
 * Explanation: There are multiple valid rooted trees. Three of them are shown in the above figures.
 *
 * Example 3:
 *
 * Input: pairs = [[1,2],[2,3],[2,4],[1,5]]
 * Output: 0
 * Explanation: There are no valid rooted trees.
 *
 * Constraints:
 *
 * > 1 <= pairs.length <= 10^5
 * > 1 <= xi < yi <= 500
 * > The elements in pairs are unique.
 */
@RunWith(LeetCodeRunner.class)
public class Q1719_NumberOfWaysToReconstructATree {

    /**
     * 没看懂什么意思?
     *
     * @formatter:off
     * 参考文档
     * https://leetcode-cn.com/problems/number-of-ways-to-reconstruct-a-tree/solution/xiang-xi-fen-xi-liang-chong-jian-shu-si-eomax/
     * @formatter:on
     */
    @Answer
    public int checkWays(int[][] pairs) {
        // 找出编号最大的节点, 确定联通矩阵的长度
        int limit = 0;
        for (int[] p : pairs) {
            limit = Math.max(limit, Math.max(p[0], p[1]) + 1);
        }

        // 求出关系数, 初始化联通矩阵 connect
        int[] deg = new int[limit];
        boolean[][] connect = new boolean[limit][limit];
        for (int[] pair : pairs) {
            // x 与 y 联通, x 与 y 的度分别+1
            int x = pair[0], y = pair[1];
            connect[x][y] = connect[y][x] = true;
            deg[x]++;
            deg[y]++;
        }

        // 找出pairs 中定义的节点, 按照度数从大到小排列
        List<Integer> nodes = new ArrayList<>();
        for (int i = 1; i < limit; i++) {
            if (deg[i] > 0) {
                nodes.add(i);
            }
        }
        nodes.sort((a, b) -> deg[b] - deg[a]);

        // 根节点必须是其它节点的祖先
        if (deg[nodes.get(0)] != nodes.size() - 1) {
            return 0;
        }

        // 寻找每个节点的父节点和祖先节点
        int[] parents = new int[limit];
        boolean[][] ancestors = new boolean[limit][limit];
        for (int i = 0; i < nodes.size(); i++) {
            // 从度数更大的节点中寻找祖先节点
            for (int j = i - 1; j >= 0; j--) {
                int x = nodes.get(i), y = nodes.get(j);
                if (connect[x][y]) {
                    parents[x] = y;
                    for (int p = y; p != 0; p = parents[p]) {
                        ancestors[x][p] = true;
                    }
                    break;
                }
            }
        }

        // 计算结果
        int res = 1;
        for (int i = 1; i < limit; i++) {
            for (int j = i + 1; j < limit; j++) {
                if (connect[i][j] && deg[i] == deg[j]) {
                    // 有多个解
                    res = 2;
                }
                if (connect[i][j] ^ (ancestors[i][j] || ancestors[j][i])) {
                    // 违反题目要求
                    return 0;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{{1, 2}, {2, 3}}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{{1, 2}, {2, 3}, {1, 3}}).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{{1, 2}, {2, 3}, {2, 4}, {1, 5}}).expect(0);

}
