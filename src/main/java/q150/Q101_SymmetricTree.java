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
 * https://leetcode.com/problems/symmetric-tree/
 *
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
 *
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
 *
 * >     1
 * >    / \
 * >   2   2
 * >  / \ / \
 * > 3  4 4  3
 *
 *
 *
 * But the following [1,2,2,null,3,null,3] is not:
 *
 * >    1
 * >  / \
 * > 2   2
 * >  \   \
 * >  3    3
 *
 *
 *
 * Note:
 * Bonus points if you could solve it both recursively and iteratively.
 */
@RunWith(LeetCodeRunner.class)
public class Q101_SymmetricTree {

    @Answer
    public boolean isSymmetric(TreeNode root) {
        return root == null || isSymmetric(root.left, root.right);
    }

    private boolean isSymmetric(TreeNode left, TreeNode right) {
        if (left == right) {
            return true;
        }
        if (left == null || right == null || left.val != right.val) {
            return false;
        }
        return isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left);
    }

    @Answer
    public boolean bfs(TreeNode root) {
        Queue<TreeNode> q1 = new LinkedList<>();
        q1.offer(root);
        Queue<TreeNode> q2 = new LinkedList<>();
        q2.offer(root);
        while (!q1.isEmpty()) {
            TreeNode node1 = q1.poll();
            TreeNode node2 = q2.poll();
            if (node1 == null && node2 == null) {
                continue;
            }
            if (node1 == null || node2 == null || node1.val != node2.val) {
                return false;
            }
            q1.offer(node1.left);
            q2.offer(node2.right);
            q1.offer(node1.right);
            q2.offer(node2.left);
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(1, 2, 3, null, null, 4, null, null, 2, 4, null, null, 3))
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(1, 2, null, 3, null, null, 2, null, 3))
            .expect(false);

}
