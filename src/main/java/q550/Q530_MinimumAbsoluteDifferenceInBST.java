package q550;

import org.junit.runner.RunWith;
import q800.Q783_MinimumDistanceBetweenBSTNodes;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/minimum-absolute-difference-in-bst/
 *
 * Given a binary search tree with non-negative values, find the minimum absolute difference between values of any
 * two nodes.
 *
 * Example:
 *
 * Input:
 *
 * >    1
 * >     \
 * >      3
 * >     /
 * >    2
 *
 * Output:
 * 1
 *
 * Explanation:
 * The minimum absolute difference is 1, which is the difference between 2 and 1 (or between 2 and 3).
 *
 *
 *
 * Note:
 *
 * There are at least two nodes in this BST.
 * This question is the same as 783: https://leetcode.com/problems/minimum-distance-between-bst-nodes/
 *
 * 相同题目 {@link Q783_MinimumDistanceBetweenBSTNodes}
 */
@RunWith(LeetCodeRunner.class)
public class Q530_MinimumAbsoluteDifferenceInBST {

    @Answer
    public int getMinimumDifference(TreeNode root) {
        prev = -1;
        res = Integer.MAX_VALUE;
        inorder(root);
        return res;
    }

    int res, prev;

    private void inorder(TreeNode node) {
        if (node == null) {
            return;
        }
        inorder(node.left);
        if (prev > -1) {
            res = Math.min(res, node.val - prev);
        }
        prev = node.val;
        inorder(node.right);
    }

    @TestData
    public DataExpectation example = DataExpectation.create(TreeNode.createByLevel(1, null, 3, 2)).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(TreeNode.createByLevel(1, null, 3, 2)).expect(1);

}
