package q150;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/binary-tree-maximum-path-sum/
 *
 * Given a non-empty binary tree, find the maximum path sum.
 *
 * For this problem, a path is defined as any sequence of nodes from some starting node to any node in the tree along
 * the parent-child connections. The path must contain at least one node and does not need to go through the root.
 *
 * Example 1:
 *
 * Input: [1,2,3]
 *
 * >        1
 * >       / \
 * >      2   3
 *
 * Output: 6
 *
 * Example 2:
 *
 * Input: [-10,9,20,null,null,15,7]
 *
 * >    -10
 * >    / \
 * >   9  20
 * >     /  \
 * >    15   7
 *
 * Output: 42
 */
@RunWith(LeetCodeRunner.class)
public class Q124_BinaryTreeMaximumPathSum {

    /**
     * 这题不知道题目什么意思?
     */
    @Answer
    public int maxPathSum(TreeNode root) {
        max = root.val;
        dfs(root);
        return max;
    }

    private int max;

    private int dfs(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = dfs(node.left);
        int right = dfs(node.right);
        max = Math.max(max, left + right + node.val);
        return Math.max(0, Math.max(left, right) + node.val);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(1, 2, null, null, 3))
            .expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(-10, 9, null, null, 20, 15, null, null, 7))
            .expect(42);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(-10, -9, null, null, -20, -15, null, null, -7))
            .expect(-7);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(1, 2))
            .expect(3);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(1, -2, null, null, 3))
            .expect(4);

    @TestData
    public DataExpectation normal4 = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(1, -2, 1, -1, null, null, null, 3, null, null, -3, -2))
            .expect(3);

    @TestData
    public DataExpectation border1 = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(-10))
            .expect(-10);

}
