package q1000;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 951. Flip Equivalent Binary Trees
 * https://leetcode.com/problems/flip-equivalent-binary-trees/
 *
 * For a binary tree T, we can define a flip operation as follows: choose any node, and swap the left and right child
 * subtrees.
 *
 * A binary tree X is flip equivalent to a binary tree Y if and only if we can make X equal to Y after some number of
 * flip operations.
 *
 * Write a function that determines whether two binary trees are flip equivalent.  The trees are given by root nodes
 * root1 and root2.
 *
 * Example 1:
 *
 * Input: root1 = [1,2,3,4,5,6,null,null,null,7,8], root2 = [1,3,2,null,6,4,5,null,null,null,null,8,7]
 * Output: true
 * Explanation: We flipped at nodes with values 1, 3, and 5.
 * (图 Q951_PIC.png)
 *
 * Note:
 *
 * Each tree will have at most 100 nodes.
 * Each value in each tree will be a unique integer in the range [0, 99].
 */
@RunWith(LeetCodeRunner.class)
public class Q951_FlipEquivalentBinaryTrees {

    @TestData
    public DataExpectation example = DataExpectation.createWith(
            TreeNode.createByLevel(1, 2, 3, 4, 5, 6, null, null, null, 7, 8),
            TreeNode.createByLevel(1, 3, 2, null, 6, 4, 5, null, null, null, null, 8, 7))
            .expect(true);

    @Answer
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) {
            return root1 == null && root2 == null;
        }
        if (root1.val != root2.val) {
            return false;
        }
        return flipEquiv(root1.left, root2.left)
                && flipEquiv(root1.right, root2.right)
                || flipEquiv(root1.left, root2.right)
                && flipEquiv(root1.right, root2.left);
    }

}
