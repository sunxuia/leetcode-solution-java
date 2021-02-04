package q1650;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1609. Even Odd Tree
 * https://leetcode.com/problems/even-odd-tree/
 *
 * A binary tree is named Even-Odd if it meets the following conditions:
 *
 * The root of the binary tree is at level index 0, its children are at level index 1, their children are at level index
 * 2, etc.
 * For every even-indexed level, all nodes at the level have odd integer values in strictly increasing order (from left
 * to right).
 * For every odd-indexed level, all nodes at the level have even integer values in strictly decreasing order (from left
 * to right).
 *
 * Given the root of a binary tree, return true if the binary tree is Even-Odd, otherwise return false.
 *
 * Example 1:
 * <img src="./Q1609_PIC1.png">
 * Input: root = [1,10,4,3,null,7,9,12,8,6,null,null,2]
 * Output: true
 * Explanation: The node values on each level are:
 * Level 0: [1]
 * Level 1: [10,4]
 * Level 2: [3,7,9]
 * Level 3: [12,8,6,2]
 * Since levels 0 and 2 are all odd and increasing, and levels 1 and 3 are all even and decreasing, the tree is
 * Even-Odd.
 *
 * Example 2:
 * <img src="./Q1609_PIC2.png">
 * Input: root = [5,4,2,3,3,7]
 * Output: false
 * Explanation: The node values on each level are:
 * Level 0: [5]
 * Level 1: [4,2]
 * Level 2: [3,3,7]
 * Node values in the level 2 must be in strictly increasing order, so the tree is not Even-Odd.
 *
 * Example 3:
 * <img src="./Q1609_PIC3.png">
 * Input: root = [5,9,1,3,5,7]
 * Output: false
 * Explanation: Node values in the level 1 should be even integers.
 *
 * Example 4:
 *
 * Input: root = [1]
 * Output: true
 *
 * Example 5:
 *
 * Input: root = [11,8,6,1,3,9,11,30,20,18,16,12,10,4,2,17]
 * Output: true
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [1, 10^5].
 * 1 <= Node.val <= 10^6
 */
@RunWith(LeetCodeRunner.class)
public class Q1609_EvenOddTree {

    @Answer
    public boolean isEvenOddTree(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        for (boolean odd = true; !queue.isEmpty(); odd = !odd) {
            int prev = odd ? 0 : Integer.MAX_VALUE;
            for (int len = queue.size(); len > 0; len--) {
                TreeNode node = queue.poll();
                if (odd) {
                    if ((node.val & 1) == 0 || node.val <= prev) {
                        return false;
                    }
                } else {
                    if ((node.val & 1) == 1 || node.val >= prev) {
                        return false;
                    }
                }
                prev = node.val;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByLevel(1, 10, 4, 3, null, 7, 9, 12, 8, 6, null, null, 2))
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(TreeNode.createByLevel(5, 4, 2, 3, 3, 7))
            .expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create(TreeNode.createByLevel(5, 9, 1, 3, 5, 7))
            .expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation.create(TreeNode.createByLevel(1))
            .expect(true);

    @TestData
    public DataExpectation example5 = DataExpectation
            .create(TreeNode.createByLevel(11, 8, 6, 1, 3, 9, 11, 30, 20, 18, 16, 12, 10, 4, 2, 17))
            .expect(true);

}
