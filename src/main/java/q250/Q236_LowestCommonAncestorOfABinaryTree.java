package q250;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
 *
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
 *
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q
 * as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”
 *
 * Given the following binary tree:  root = [3,5,1,6,2,0,8,null,null,7,4]
 *
 * (图见 Q236_PIC.png)
 *
 * Example 1:
 *
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
 * Output: 3
 * Explanation: The LCA of nodes 5 and 1 is 3.
 *
 * Example 2:
 *
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
 * Output: 5
 * Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA
 * definition.
 *
 *
 *
 * Note:
 *
 * All of the nodes' values will be unique.
 * p and q are different and both values will exist in the binary tree.
 */
@RunWith(LeetCodeRunner.class)
public class Q236_LowestCommonAncestorOfABinaryTree {

    // 相比上一题少了BST 的条件.
    @Answer
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        // 这段可以避免对right 做额外的检查, 不过在 LeetCode 中没有性能提升.
//        if (left != null && left != p && left != q) {
//            return left;
//        }
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        return right == null ? left : left == null ? right : root;
    }

    @TestData
    public DataExpectation example1() {
        TreeNode root = TreeNode.createByLevel(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4);
        return DataExpectation.createWith(root, root.left, root.right).expect(root);
    }

    @TestData
    public DataExpectation example2() {
        TreeNode root = TreeNode.createByLevel(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4);
        return DataExpectation.createWith(root, root.left, root.left.right.right).expect(root.left);
    }

}
