package q1000;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 965. Univalued Binary Tree
 * https://leetcode.com/problems/univalued-binary-tree/
 *
 * A binary tree is univalued if every node in the tree has the same value.
 *
 * Return true if and only if the given tree is univalued.
 *
 * Example 1:
 * (图Q965_PIC1.png)
 * Input: [1,1,1,1,1,null,1]
 * Output: true
 *
 * Example 2:
 * (图Q965_PIC2.png)
 * Input: [2,2,2,5,2]
 * Output: false
 *
 * Note:
 *
 * The number of nodes in the given tree will be in the range [1, 100].
 * Each node's value will be an integer in the range [0, 99].
 */
@RunWith(LeetCodeRunner.class)
public class Q965_UnivaluedBinaryTree {

    @Answer
    public boolean isUnivalTree(TreeNode root) {
        return (root.left == null || root.val == root.left.val && isUnivalTree(root.left))
                && (root.right == null || root.val == root.right.val && isUnivalTree(root.right));
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByLevel(1, 1, 1, 1, 1, null, 1))
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(TreeNode.createByLevel(2, 2, 2, 5, 2))
            .expect(false);

}
