package q700;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/two-sum-iv-input-is-a-bst/
 *
 * Given a Binary Search Tree and a target number, return true if there exist two elements in the BST such that their
 * sum is equal to the given target.
 *
 * Example 1:
 *
 * Input:
 * >     5
 * >    / \
 * >   3   6
 * >  / \   \
 * > 2   4   7
 *
 * Target = 9
 *
 * Output: True
 *
 *
 *
 * Example 2:
 *
 * Input:
 * >     5
 * >    / \
 * >   3   6
 * >  / \   \
 * > 2   4   7
 *
 * Target = 28
 *
 * Output: False
 */
@RunWith(LeetCodeRunner.class)
public class Q653_TwoSumIV_InputIsABST {

    @Answer
    public boolean findTarget(TreeNode root, int k) {
        List<Integer> list = new ArrayList<>();
        inOrder(root, list);
        int left = 0, right = list.size() - 1;
        while (left < right) {
            int sum = list.get(left) + list.get(right);
            if (sum == k) {
                return true;
            } else if (sum < k) {
                left++;
            } else {
                right--;
            }
        }
        return false;
    }

    private void inOrder(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        inOrder(node.left, list);
        list.add(node.val);
        inOrder(node.right, list);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(TreeNode.createByLevel(5, 3, 6, 2, 4, null, 7), 9)
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(TreeNode.createByLevel(5, 3, 6, 2, 4, null, 7), 28)
            .expect(false);

}
