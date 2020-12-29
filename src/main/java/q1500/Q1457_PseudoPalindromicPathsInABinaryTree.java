package q1500;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1457. Pseudo-Palindromic Paths in a Binary Tree
 * https://leetcode.com/problems/pseudo-palindromic-paths-in-a-binary-tree/
 *
 * Given a binary tree where node values are digits from 1 to 9. A path in the binary tree is said to be
 * pseudo-palindromic if at least one permutation of the node values in the path is a palindrome.
 *
 * Return the number of pseudo-palindromic paths going from the root node to leaf nodes.
 *
 * Example 1:
 * <img src="./Q1457_PIC1.png">
 * Input: root = [2,3,1,3,1,null,1]
 * Output: 2
 * Explanation: The figure above represents the given binary tree. There are three paths going from the root node to
 * leaf nodes: the red path [2,3,3], the green path [2,1,1], and the path [2,3,1]. Among these paths only red path and
 * green path are pseudo-palindromic paths since the red path [2,3,3] can be rearranged in [3,2,3] (palindrome) and the
 * green path [2,1,1] can be rearranged in [1,2,1] (palindrome).
 *
 * Example 2:
 * <img src="./Q1457_PIC2.png">
 * Input: root = [2,1,1,1,3,null,null,null,null,null,1]
 * Output: 1
 * Explanation: The figure above represents the given binary tree. There are three paths going from the root node to
 * leaf nodes: the green path [2,1,1], the path [2,1,3,1], and the path [2,1]. Among these paths only the green path is
 * pseudo-palindromic since [2,1,1] can be rearranged in [1,2,1] (palindrome).
 *
 * Example 3:
 *
 * Input: root = [9]
 * Output: 1
 *
 * Constraints:
 *
 * The given binary tree will have between 1 and 10^5 nodes.
 * Node values are digits from 1 to 9.
 */
@RunWith(LeetCodeRunner.class)
public class Q1457_PseudoPalindromicPathsInABinaryTree {

    @Answer
    public int pseudoPalindromicPaths(TreeNode root) {
        return dfs(root, 0);
    }

    private int dfs(TreeNode node, int mask) {
        mask ^= 1 << node.val;
        if (node.left == null && node.right == null) {
            return mask == 0 || (mask & (mask - 1)) == 0 ? 1 : 0;
        }
        int res = 0;
        if (node.left != null) {
            res += dfs(node.left, mask);
        }
        if (node.right != null) {
            res += dfs(node.right, mask);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByLevel(2, 3, 1, 3, 1, null, 1))
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(TreeNode.createByLevel(2, 1, 1, 1, 3, null, null, null, null, null, 1))
            .expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(TreeNode.createByLevel(9))
            .expect(1);

}
