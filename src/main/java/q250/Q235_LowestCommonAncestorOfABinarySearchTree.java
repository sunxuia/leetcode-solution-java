package q250;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
 *
 * Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.
 *
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q
 * as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”
 *
 * Given binary search tree:  root = [6,2,8,0,4,7,9,null,null,3,5]
 *
 * (图见 Q235_PIC.png)
 *
 * Example 1:
 *
 * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
 * Output: 6
 * Explanation: The LCA of nodes 2 and 8 is 6.
 *
 * Example 2:
 *
 * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
 * Output: 2
 * Explanation: The LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself according to the LCA
 * definition.
 *
 *
 *
 * Note:
 *
 * All of the nodes' values will be unique.
 * p and q are different and both values will exist in the BST.
 */
@RunWith(LeetCodeRunner.class)
public class Q235_LowestCommonAncestorOfABinarySearchTree {

    // 题目要求在二叉搜索树(BST) 中找到p 和q 的公共父节点, 可以利用BST 的特性.
    // 不使用BST 特性的解法可以见下一题
    @Answer
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val > q.val) {
            TreeNode t = p;
            p = q;
            q = t;
        }
        return dfs(root, p, q);
    }

    private TreeNode dfs(TreeNode node, TreeNode p, TreeNode q) {
        if (node.val > q.val) {
            return dfs(node.left, p, q);
        } else if (node.val < p.val) {
            return dfs(node.right, p, q);
        } else {
            return node;
        }
    }

    @TestData
    public DataExpectation example1() {
        TreeNode root = TreeNode.createByLevel(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5);
        return DataExpectation.createWith(root, root.left, root.right).expect(root);
    }

    @TestData
    public DataExpectation example2() {
        TreeNode root = TreeNode.createByLevel(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5);
        return DataExpectation.createWith(root, root.left, root.left.right).expect(root.left);
    }

}
