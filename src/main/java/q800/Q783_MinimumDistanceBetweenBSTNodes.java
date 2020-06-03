package q800;

import org.junit.runner.RunWith;
import q550.Q530_MinimumAbsoluteDifferenceInBST;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/minimum-distance-between-bst-nodes/
 *
 * Given a Binary Search Tree (BST) with the root node root, return the minimum difference between the values of any
 * two different nodes in the tree.
 *
 * Example :
 *
 * Input: root = [4,2,6,1,3,null,null]
 * Output: 1
 * Explanation:
 * Note that root is a TreeNode object, not an array.
 *
 * The given tree [4,2,6,1,3,null,null] is represented by the following diagram:
 *
 * >           4
 * >         /   \
 * >       2      6
 * >      / \
 * >     1   3
 *
 * while the minimum difference in this tree is 1, it occurs between node 1 and node 2, also between node 3 and node 2.
 *
 * Note:
 *
 * 1. The size of the BST will be between 2 and 100.
 * 2. The BST is always valid, each node's value is an integer, and each node's value is different.
 * 3. This question is the same as 530: https://leetcode.com/problems/minimum-absolute-difference-in-bst/
 *
 *  相同题目 {@link Q530_MinimumAbsoluteDifferenceInBST}
 */
@RunWith(LeetCodeRunner.class)
public class Q783_MinimumDistanceBetweenBSTNodes {

    @Answer
    public int minDiffInBST(TreeNode root) {
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
    public DataExpectation example = DataExpectation.createWith(TreeNode.createByLevel(4, 2, 6, 1, 3)).expect(1);

}
