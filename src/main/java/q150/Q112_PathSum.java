package q150;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/path-sum/
 *
 * Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values
 * along the path equals the given sum.
 *
 * Note: A leaf is a node with no children.
 *
 * Example:
 *
 * Given the below binary tree and sum = 22,
 *
 * >       5
 * >      / \
 * >     4   8
 * >    /   / \
 * >   11  13  4
 * >  /  \      \
 * > 7    2      1
 *
 * 题解: 示例中 22 = 5 + 4 + 11 + 2
 */
@RunWith(LeetCodeRunner.class)
public class Q112_PathSum {

    @Answer
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null && sum == root.val) {
            return true;
        }
        return hasPathSum(root.left, sum - root.val)
                || hasPathSum(root.right, sum - root.val);
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(TreeNode.createByPreOrderTraversal(5, 4, 11, 7, null, null, 2, null, null,
                    8, 13, null, null, 4, null, 1), 22)
            .expect(true);

}
