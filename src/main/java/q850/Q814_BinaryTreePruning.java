package q850;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/binary-tree-pruning/
 *
 * We are given the head node root of a binary tree, where additionally every node's value is either a 0 or a 1.
 *
 * Return the same tree where every subtree (of the given tree) not containing a 1 has been removed.
 *
 * (Recall that the subtree of a node X is X, plus every node that is a descendant of X.)
 *
 * Example 1:
 * Input: [1,null,0,0,1]
 * Output: [1,null,0,null,1]
 *
 * Explanation:
 * Only the red nodes satisfy the property "every subtree not containing a 1".
 * The diagram on the right represents the answer.
 *
 * (图 Q814_PIC1.png)
 *
 * Example 2:
 * Input: [1,0,1,0,0,0,1]
 * Output: [1,null,1,null,1]
 *
 * (图 Q814_PIC2.png)
 *
 *
 * Example 3:
 * Input: [1,1,0,1,1,0,1,0]
 * Output: [1,1,0,1,1,null,1]
 *
 * (图 Q814_PIC3.png)
 *
 *
 * Note:
 *
 * The binary tree will have at most 100 nodes.
 * The value of each node will only be 0 or 1.
 */
@RunWith(LeetCodeRunner.class)
public class Q814_BinaryTreePruning {

    @Answer
    public TreeNode pruneTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        root.left = pruneTree(root.left);
        root.right = pruneTree(root.right);
        if (root.val == 0 && root.left == null && root.right == null) {
            return null;
        }
        return root;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByLevel(1, null, 0, 0, 1))
            .expect(TreeNode.createByLevel(1, null, 0, null, 1));

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(TreeNode.createByLevel(1, 0, 1, 0, 0, 0, 1))
            .expect(TreeNode.createByLevel(1, null, 1, null, 1));

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(TreeNode.createByLevel(1, 1, 0, 1, 1, 0, 1, 0))
            .expect(TreeNode.createByLevel(1, 1, 0, 1, 1, null, 1));

}
