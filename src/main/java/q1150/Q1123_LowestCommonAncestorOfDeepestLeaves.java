package q1150;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1123. Lowest Common Ancestor of Deepest Leaves
 * https://leetcode.com/problems/lowest-common-ancestor-of-deepest-leaves/
 *
 * Given a rooted binary tree, return the lowest common ancestor of its deepest leaves.
 *
 * Recall that:
 *
 * The node of a binary tree is a leaf if and only if it has no children
 * The depth of the root of the tree is 0, and if the depth of a node is d, the depth of each of its children is d+1.
 * The lowest common ancestor of a set S of nodes is the node A with the largest depth such that every node in S is in
 * the subtree with root A.
 *
 * Example 1:
 *
 * Input: root = [1,2,3]
 * Output: [1,2,3]
 * Explanation:
 * The deepest leaves are the nodes with values 2 and 3.
 * The lowest common ancestor of these leaves is the node with value 1.
 * The answer returned is a TreeNode object (not an array) with serialization "[1,2,3]".
 *
 * Example 2:
 *
 * Input: root = [1,2,3,4]
 * Output: [4]
 *
 * Example 3:
 *
 * Input: root = [1,2,3,4,5]
 * Output: [2,4,5]
 *
 * Constraints:
 *
 * The given tree will have between 1 and 1000 nodes.
 * Each node of the tree will have a distinct value between 1 and 1000.
 */
@RunWith(LeetCodeRunner.class)
public class Q1123_LowestCommonAncestorOfDeepestLeaves {

    @Answer
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        return dfs(root, 1, getDepth(root));
    }

    private int getDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getDepth(node.left), getDepth(node.right));
    }

    private TreeNode dfs(TreeNode node, int depth, int maxDepth) {
        if (node == null) {
            return null;
        }
        if (depth == maxDepth) {
            return node;
        }
        var left = dfs(node.left, depth + 1, maxDepth);
        var right = dfs(node.right, depth + 1, maxDepth);
        if (left != null && right != null) {
            return node;
        }
        return left == null ? right : left;
    }


    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByLevel(1, 2, 3))
            .expect(TreeNode.createByLevel(1, 2, 3));

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(TreeNode.createByLevel(1, 2, 3, 4))
            .expect(TreeNode.createByLevel(4));

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(TreeNode.createByLevel(1, 2, 3, 4, 5))
            .expect(TreeNode.createByLevel(2, 4, 5));

}
