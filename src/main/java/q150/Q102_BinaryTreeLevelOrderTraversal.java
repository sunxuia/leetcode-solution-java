package q150;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/binary-tree-level-order-traversal/
 *
 * Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).
 *
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
 *
 * >   3
 * >  / \
 * > 9  20
 * >   /  \
 * >  15   7
 *
 * return its level order traversal as:
 *
 * > [
 * >   [3],
 * >   [9,20],
 * >   [15,7]
 * > ]
 */
@RunWith(LeetCodeRunner.class)
public class Q102_BinaryTreeLevelOrderTraversal {

    @Answer
    public List<List<Integer>> levelOrder(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        List<List<Integer>> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            for (int i = queue.size(); i > 0; i--) {
                TreeNode node = queue.poll();
                if (node != null) {
                    level.add(node.val);
                    queue.add(node.left);
                    queue.add(node.right);
                }
            }
            res.add(level);
        }
        // 最后一层肯定全是空的.
        res.remove(res.size() - 1);
        return res;
    }

    /**
     * LeetCode 上dfs 的遍历速度要稍微快一些. (不过我的提交没有)
     */
    @Answer
    public List<List<Integer>> levelOrderDfs(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(res, root, 0);
        return res;
    }

    private void dfs(List<List<Integer>> res, TreeNode node, int level) {
        if (node == null) {
            return;
        }
        if (res.size() == level) {
            res.add(new ArrayList<>());
        }
        res.get(level).add(node.val);
        dfs(res, node.left, level + 1);
        dfs(res, node.right, level + 1);
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(3, 9, null, null, 20, 15, null, null, 7))
            .expect(new int[][]{
                    {3},
                    {9, 20},
                    {15, 7}
            });

}
