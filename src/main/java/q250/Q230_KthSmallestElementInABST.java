package q250;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/kth-smallest-element-in-a-bst/
 *
 * Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.
 *
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ BST's total elements.
 *
 * Example 1:
 *
 * Input: root = [3,1,4,null,2], k = 1
 * >     3
 * >    / \
 * >   1   4
 * >    \
 * >     2
 * Output: 1
 *
 * Example 2:
 *
 * Input: root = [5,3,6,2,4,null,null,1], k = 3
 * >         5
 * >        / \
 * >       3   6
 * >      / \
 * >     2   4
 * >    /
 * >   1
 * Output: 3
 *
 * Follow up:
 * What if the BST is modified (insert/delete operations) often and you need to find the kth smallest frequently? How
 * would you optimize the kthSmallest routine?
 */
@RunWith(LeetCodeRunner.class)
public class Q230_KthSmallestElementInABST {

    @Answer
    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.left == null) {
                if (--k == 0) {
                    return node.val;
                }
                if (node.right != null) {
                    stack.push(node.right);
                }
            } else {
                stack.push(node);
                stack.push(node.left);
                node.left = null;
            }
        }
        throw new RuntimeException("should not run to here");
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(TreeNode.createByLevel(3, 1, 4, null, 2), 1).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(TreeNode.createByLevel(5, 3, 6, 2, 4, null, null, 1), 3)
            .expect(3);

}
