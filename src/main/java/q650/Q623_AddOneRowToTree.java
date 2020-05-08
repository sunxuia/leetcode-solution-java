package q650;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/add-one-row-to-tree/
 *
 * Given the root of a binary tree, then value v and depth d, you need to add a row of nodes with value v at the
 * given depth d. The root node is at depth 1.
 *
 * The adding rule is: given a positive integer depth d, for each NOT null tree nodes N in depth d-1, create two tree
 * nodes with value v as N's left subtree root and right subtree root. And N's original left subtree should be the
 * left subtree of the new left subtree root, its original right subtree should be the right subtree of the new right
 * subtree root. If depth d is 1 that means there is no depth d-1 at all, then create a tree node with value v as the
 * new root of the whole original tree, and the original tree is the new root's left subtree.
 *
 * Example 1:
 *
 * Input:
 * A binary tree as following:
 * >        4
 * >      /   \
 * >     2     6
 * >    / \   /
 * >   3   1 5
 *
 * v = 1
 *
 * d = 2
 *
 * Output:
 * >        4
 * >       / \
 * >      1   1
 * >     /     \
 * >    2       6
 * >   / \     /
 * >  3   1   5
 *
 * Example 2:
 *
 * Input:
 * A binary tree as following:
 * >       4
 * >      /
 * >     2
 * >    / \
 * >   3   1
 *
 * v = 1
 *
 * d = 3
 *
 * Output:
 * >       4
 * >      /
 * >     2
 * >    / \
 * >   1   1
 * >  /     \
 * > 3       1
 *
 * Note:
 *
 * 1. The given d is in range [1, maximum depth of the given tree + 1].
 * 2. The given binary tree has at least one tree node.
 */
@RunWith(LeetCodeRunner.class)
public class Q623_AddOneRowToTree {

    @Answer
    public TreeNode addOneRow(TreeNode root, int v, int d) {
        return dfs(root, v, d, true);
    }

    private TreeNode dfs(TreeNode node, int v, int d, boolean isLeft) {
        if (d == 1) {
            TreeNode newNode = new TreeNode(v);
            if (isLeft) {
                newNode.left = node;
            } else {
                newNode.right = node;
            }
            return newNode;
        }
        if (node == null) {
            return null;
        }
        node.left = dfs(node.left, v, d - 1, true);
        node.right = dfs(node.right, v, d - 1, false);
        return node;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(TreeNode.createByLevel(4, 2, 6, 3, 1, 5), 1, 2)
            .expect(TreeNode.createByLevel(4, 1, 1, 2, null, null, 6, 3, 1, 5));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(TreeNode.createByLevel(4, 2, null, 3, 1), 1, 3)
            .expect(TreeNode.createByLevel(4, 2, null, 1, 1, 3, null, null, 1));

}
