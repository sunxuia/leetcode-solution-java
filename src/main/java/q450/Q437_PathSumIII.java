package q450;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/path-sum-iii/
 *
 * You are given a binary tree in which each node contains an integer value.
 *
 * Find the number of paths that sum to a given value.
 *
 * The path does not need to start or end at the root or a leaf, but it must go downwards (traveling only from parent
 * nodes to child nodes).
 *
 * The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000.
 *
 * Example:
 *
 * root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8
 *
 * >        10
 * >       /  \
 * >      5   -3
 * >     / \    \
 * >    3   2   11
 * >   / \   \
 * >  3  -2   1
 *
 * Return 3. The paths that sum to 8 are:
 *
 * 1.  5 -> 3
 * 2.  5 -> 2 -> 1
 * 3. -3 -> 11
 */
@RunWith(LeetCodeRunner.class)
public class Q437_PathSumIII {

    // 这种解法比较慢
    @Answer
    public int pathSum(TreeNode root, int sum) {
        return dfs(root, true, 0, sum);
    }

    private int dfs(TreeNode node, boolean isHead, int curSum, int sum) {
        if (node == null) {
            return 0;
        }
        int res = curSum + node.val == sum ? 1 : 0;
        if (isHead) {
            res += dfs(node.left, true, 0, sum);
            res += dfs(node.right, true, 0, sum);
        }
        res += dfs(node.left, false, curSum + node.val, sum);
        res += dfs(node.right, false, curSum + node.val, sum);
        return res;
    }

    /**
     * LeetCode 上比较快的解法, 使用Map 缓存遍历路径上的和与得出这个和的数量.
     */
    @Answer
    public int pathSum2(TreeNode root, int sum) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        return dfsMemo(root, 0, sum, map);
    }

    private int dfsMemo(TreeNode root, int curSum, int sum, Map<Integer, Integer> map) {
        if (root == null) {
            return 0;
        }

        // 从顶点到这个点的总和.
        curSum += root.val;

        // 总和 - sum = 应该扣除的父路径和, 这里求出这些符合条件的路径的数量.
        int res = map.getOrDefault(curSum - sum, 0);
        // 更新从顶点到这个点的路径之和, 遍历子节点的时候可能就会扣除从root 到这个顶点的路径之和.
        map.put(curSum, map.getOrDefault(curSum, 0) + 1);

        // 从左右查找
        res += dfsMemo(root.left, curSum, sum, map) + dfsMemo(root.right, curSum, sum, map);

        // 回溯到父节点, 从缓存中删除自己的计数.
        map.put(curSum, map.get(curSum) - 1);
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(TreeNode.createByLevel(10, 5, -3, 3, 2, null, 11, 3, -2, null, 1), 8)
            .expect(3);

}
