package q150;

import java.util.ArrayList;
import java.util.Collections;
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
 * https://leetcode.com/problems/binary-tree-level-order-traversal-ii
 *
 * Given a binary tree, return the bottom-up level order traversal of its nodes' values. (ie, from left to right,
 * level by level from leaf to root).
 *
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
 *
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 *
 * return its bottom-up level order traversal as:
 *
 * [
 * [15,7],
 * [9,20],
 * [3]
 * ]
 */
@RunWith(LeetCodeRunner.class)
public class Q107_BinaryTreeLevelOrderTraversalII {

    @Answer
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> line = new ArrayList<>();
            for (int i = queue.size(); i > 0; i--) {
                TreeNode node = queue.remove();
                if (node != null) {
                    line.add(node.val);
                    queue.add(node.left);
                    queue.add(node.right);
                }
            }
            res.add(line);
        }
        res.remove(res.size() - 1);
        Collections.reverse(res);
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(3, 9, null, null, 20, 15, null, null, 7))
            .expect(new int[][]{
                    {15, 7},
                    {9, 20},
                    {3}
            });
}
