package q1350;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1325. Delete Leaves With a Given Value
 * https://leetcode.com/problems/delete-leaves-with-a-given-value/
 *
 * Given a binary tree root and an integer target, delete all the leaf nodes with value target.
 *
 * Note that once you delete a leaf node with value target, if it's parent node becomes a leaf node and has the value
 * target, it should also be deleted (you need to continue doing that until you can't).
 *
 * Example 1:
 * <img src="./Q1325_PIC1.png">
 * Input: root = [1,2,3,2,null,2,4], target = 2
 * Output: [1,null,3,null,4]
 * Explanation: Leaf nodes in green with value (target = 2) are removed (Picture in left).
 * After removing, new nodes become leaf nodes with value (target = 2) (Picture in center).
 *
 * Example 2:
 * <img src="./Q1325_PIC2.png">
 * Input: root = [1,3,3,3,2], target = 3
 * Output: [1,3,null,null,2]
 *
 * Example 3:
 * <img src="./Q1325_PIC3.png">
 * Input: root = [1,2,null,2,null,2], target = 2
 * Output: [1]
 * Explanation: Leaf nodes in green with value (target = 2) are removed at each step.
 *
 * Example 4:
 *
 * Input: root = [1,1,1], target = 1
 * Output: []
 *
 * Example 5:
 *
 * Input: root = [1,2,3], target = 1
 * Output: [1,2,3]
 *
 * Constraints:
 *
 * 1 <= target <= 1000
 * The given binary tree will have between 1 and 3000 nodes.
 * Each node's value is between [1, 1000].
 */
@RunWith(LeetCodeRunner.class)
public class Q1325_DeleteLeavesWithAGivenValue {

    @Answer
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        if (root == null) {
            return null;
        }
        root.left = removeLeafNodes(root.left, target);
        root.right = removeLeafNodes(root.right, target);
        if (root.left == null && root.right == null
                && root.val == target) {
            return null;
        }
        return root;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(TreeNode.createByLevel(1, 2, 3, 2, null, 2, 4), 2)
            .expect(TreeNode.createByLevel(1, null, 3, null, 4));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(TreeNode.createByLevel(1, 3, 3, 3, 2), 3)
            .expect(TreeNode.createByLevel(1, 3, null, null, 2));

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(TreeNode.createByLevel(1, 2, null, 2, null, 2), 2)
            .expect(TreeNode.createByLevel(1));

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(TreeNode.createByLevel(1, 1, 1), 1)
            .expect(TreeNode.createByLevel());

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(TreeNode.createByLevel(1, 2, 3), 1)
            .expect(TreeNode.createByLevel(1, 2, 3));

}
