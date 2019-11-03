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
 * https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
 *
 * Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie, from left to right, then
 * right to left for the next level and alternate between).
 *
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
 *
 * >    3
 * >   / \
 * >  9  20
 * >    /  \
 * >   15   7
 *
 * return its zigzag level order traversal as:
 *
 * > [
 * >   [3],
 * >   [20,9],
 * >   [15,7]
 * > ]
 */
@RunWith(LeetCodeRunner.class)
public class Q103_BinaryTreeZigzagLevelOrderTraversal {

    @Answer
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            for (int i = queue.size(); i > 0; i--) {
                TreeNode node = queue.poll();
                if (node != null) {
                    level.add(node.val);
                    queue.offer(node.left);
                    queue.offer(node.right);
                }
            }
            if (res.size() % 2 > 0) {
                Collections.reverse(level);
            }
            res.add(level);
        }
        res.remove(res.size() - 1);
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(3, 9, null, null, 20, 15, null, null, 7))
            .expect(new int[][]{
                    {3},
                    {20, 9},
                    {15, 7}
            });
}
