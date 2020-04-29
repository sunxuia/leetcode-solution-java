package q550;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/convert-bst-to-greater-tree/
 *
 * Given a Binary Search Tree (BST), convert it to a Greater Tree such that every key of the original BST is changed
 * to the original key plus sum of all keys greater than the original key in BST.
 *
 * Example:
 *
 * Input: The root of a Binary Search Tree like this:
 * >               5
 * >             /   \
 * >            2     13
 *
 * Output: The root of a Greater Tree like this:
 * >              18
 * >             /   \
 * >           20     13
 *
 * Note: This question is the same as 1038: https://leetcode.com/problems/binary-search-tree-to-greater-sum-tree/
 */
@RunWith(LeetCodeRunner.class)
public class Q538_ConvertBSTToGreaterTree {

    @Answer
    public TreeNode convertBST(TreeNode root) {
        sum = 0;
        traversal(root);
        return root;
    }

    private int sum;

    private void traversal(TreeNode node) {
        if (node == null) {
            return;
        }
        traversal(node.right);
        node.val = sum += node.val;
        traversal(node.left);
    }

    @TestData
    public DataExpectation exmaple = DataExpectation.create(TreeNode.createByLevel(5, 2, 13))
            .expect(TreeNode.createByLevel(18, 20, 13));

}
