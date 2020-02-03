package q150;

import java.util.LinkedList;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/maximum-depth-of-binary-tree/
 *
 * Given a binary tree, find its maximum depth.
 *
 * The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
 *
 * Note: A leaf is a node with no children.
 *
 * Example:
 *
 * Given binary tree [3,9,20,null,null,15,7],
 *
 * >    3
 * >   / \
 * >  9  20
 * >    /  \
 * >   15   7
 *
 * return its depth = 3.
 */
@RunWith(LeetCodeRunner.class)
public class Q104_MaximumDepthOfBinaryTree {

    @Answer
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    @Answer
    public int bfs(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int level = 0;
        while (!queue.isEmpty()) {
            level++;
            for (int i = queue.size(); i > 0; i--) {
                TreeNode node = queue.remove();
                if (node != null) {
                    queue.add(node.left);
                    queue.add(node.right);
                }
            }
        }
        return level - 1;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(3, 9, null, null, 20, 15, null, null, 7))
            .expect(3);

}
