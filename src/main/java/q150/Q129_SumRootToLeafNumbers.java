package q150;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/sum-root-to-leaf-numbers/
 *
 * Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.
 *
 * An example is the root-to-leaf path 1->2->3 which represents the number 123.
 *
 * Find the total sum of all root-to-leaf numbers.
 *
 * Note: A leaf is a node with no children.
 *
 * Example:
 *
 * Input: [1,2,3]
 * >     1
 * >    / \
 * >   2   3
 * Output: 25
 * Explanation:
 * The root-to-leaf path 1->2 represents the number 12.
 * The root-to-leaf path 1->3 represents the number 13.
 * Therefore, sum = 12 + 13 = 25.
 *
 * Example 2:
 *
 * Input: [4,9,0,5,1]
 * >     4
 * >    / \
 * >   9   0
 * >  / \
 * > 5   1
 * Output: 1026
 * Explanation:
 * The root-to-leaf path 4->9->5 represents the number 495.
 * The root-to-leaf path 4->9->1 represents the number 491.
 * The root-to-leaf path 4->0 represents the number 40.
 * Therefore, sum = 495 + 491 + 40 = 1026.
 */
@RunWith(LeetCodeRunner.class)
public class Q129_SumRootToLeafNumbers {

    @Answer
    public int sumNumbers(TreeNode root) {
        dfs(root, 0);
        return res;
    }

    private int res = 0;

    private void dfs(TreeNode node, int sum) {
        if (node == null) {
            return;
        }
        int value = sum * 10 + node.val;
        if (node.left == null && node.right == null) {
            res += value;
        } else {
            dfs(node.left, value);
            dfs(node.right, value);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(1, 2, null, null, 3))
            .expect(25);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(4, 9, 5, null, null, 1, null, null, 0))
            .expect(1026);

    @TestData
    public DataExpectation border = DataExpectation
            .create(TreeNode.createByPreOrderTraversal())
            .expect(0);

}
