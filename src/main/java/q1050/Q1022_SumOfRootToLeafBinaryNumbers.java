package q1050;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1022. Sum of Root To Leaf Binary Numbers
 * https://leetcode.com/problems/sum-of-root-to-leaf-binary-numbers/
 *
 * Given a binary tree, each node has value 0 or 1.  Each root-to-leaf path represents a binary number starting with the
 * most significant bit.  For example, if the path is 0 -> 1 -> 1 -> 0 -> 1, then this could represent 01101 in binary,
 * which is 13.
 *
 * For all leaves in the tree, consider the numbers represented by the path from the root to that leaf.
 *
 * Return the sum of these numbers.
 *
 * Example 1:
 * (图Q1022_PIC.png)
 * Input: [1,0,1,0,1,0,1]
 * Output: 22
 * Explanation: (100) + (101) + (110) + (111) = 4 + 5 + 6 + 7 = 22
 *
 * Note:
 *
 * The number of nodes in the tree is between 1 and 1000.
 * node.val is 0 or 1.
 * The answer will not exceed 2^31 - 1.
 */
@RunWith(LeetCodeRunner.class)
public class Q1022_SumOfRootToLeafBinaryNumbers {

    @Answer
    public int sumRootToLeaf(TreeNode root) {
        return dfs(root, 0);
    }

    private int dfs(TreeNode node, int parent) {
        int val = parent * 2 + node.val;
        if (node.left == null && node.right == null) {
            return val;
        }
        int res = 0;
        if (node.left != null) {
            res += dfs(node.left, val);
        }
        if (node.right != null) {
            res += dfs(node.right, val);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(TreeNode.createByLevel(1, 0, 1, 0, 1, 0, 1)).expect(22);

}
