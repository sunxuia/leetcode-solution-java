package q550;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/find-bottom-left-tree-value/
 *
 * Given a binary tree, find the leftmost value in the last row of the tree.
 *
 * Example 1:
 *
 * Input:
 *
 * >     2
 * >    / \
 * >   1   3
 *
 * Output:
 * 1
 *
 * Example 2:
 *
 * Input:
 *
 * >         1
 * >        / \
 * >       2   3
 * >      /   / \
 * >     4   5   6
 * >        /
 * >       7
 *
 * Output:
 * 7
 *
 * Note: You may assume the tree (i.e., the given root node) is not NULL.
 */
@RunWith(LeetCodeRunner.class)
public class Q513_FindBottomLeftTreeValue {

    @Answer
    public int findBottomLeftValue(TreeNode root) {
        maxDepth = -1;
        dfs(root, 0);
        return val;
    }

    private int maxDepth, val;

    private void dfs(TreeNode node, int depth) {
        if (node == null) {
            return;
        }
        if (maxDepth < depth) {
            val = node.val;
            maxDepth = depth;
        }
        dfs(node.left, depth + 1);
        dfs(node.right, depth + 1);
    }

    @TestData
    public DataExpectation exmaple1 = DataExpectation.create(TreeNode.createByLevel(2, 1, 3)).expect(1);

    @TestData
    public DataExpectation exmaple2 = DataExpectation
            .create(TreeNode.createByLevel(1, 2, 3, 4, null, 5, 6, null, null, 7)).expect(7);

}
