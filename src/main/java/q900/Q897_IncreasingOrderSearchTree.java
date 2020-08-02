package q900;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 897. Increasing Order Search Tree
 * https://leetcode.com/problems/increasing-order-search-tree/
 *
 * Given a binary search tree, rearrange the tree in in-order so that the leftmost node in the tree is now the root of
 * the tree, and every node has no left child and only 1 right child.
 *
 * Example 1:
 * Input: [5,3,6,2,4,null,8,1,null,null,null,7,9]
 *
 * >        5
 * >       / \
 * >     3    6
 * >    / \    \
 * >   2   4    8
 * >  /        / \
 * > 1        7   9
 *
 * Output: [1,null,2,null,3,null,4,null,5,null,6,null,7,null,8,null,9]
 *
 * >  1
 * >   \
 * >    2
 * >     \
 * >      3
 * >       \
 * >        4
 * >         \
 * >          5
 * >           \
 * >            6
 * >             \
 * >              7
 * >               \
 * >                8
 * >                 \
 * >                  9
 *
 * Constraints:
 *
 * The number of nodes in the given tree will be between 1 and 100.
 * Each node will have a unique integer value from 0 to 1000.
 */
@RunWith(LeetCodeRunner.class)
public class Q897_IncreasingOrderSearchTree {

    @Answer
    public TreeNode increasingBST(TreeNode root) {
        TreeNode dummy = new TreeNode(0);
        curr = dummy;
        dfs(root);
        return dummy.right;
    }

    private TreeNode curr;

    private void dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        dfs(node.left);
        node.left = null;
        curr.right = node;
        curr = node;
        dfs(node.right);
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(TreeNode.createByLevel(5, 3, 6, 2, 4, null, 8, 1, null, null, null, 7, 9))
            .expect(TreeNode.createByLevel(1, null, 2, null, 3, null, 4, null, 5, null, 6, null, 7, null, 8, null, 9));

}
