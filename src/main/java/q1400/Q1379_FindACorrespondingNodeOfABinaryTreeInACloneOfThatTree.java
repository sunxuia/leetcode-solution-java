package q1400;

import org.junit.Assert;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataAssertMethod;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1379. Find a Corresponding Node of a Binary Tree in a Clone of That Tree
 * https://leetcode.com/problems/find-a-corresponding-node-of-a-binary-tree-in-a-clone-of-that-tree/
 *
 * Given two binary trees original and cloned and given a reference to a node target in the original tree.
 *
 * The cloned tree is a copy of the original tree.
 *
 * Return a reference to the same node in the cloned tree.
 *
 * Note that you are not allowed to change any of the two trees or the target node and the answer must be a reference
 * to a node in the cloned tree.
 *
 * Follow up: Solve the problem if repeated values on the tree are allowed.
 *
 * Example 1:
 * <img src="./Q1379_PIC1.png">
 * Input: tree = [7,4,3,null,null,6,19], target = 3
 * Output: 3
 * Explanation: In all examples the original and cloned trees are shown. The target node is a green node from the
 * original tree. The answer is the yellow node from the cloned tree.
 *
 * Example 2:
 * <img src="./Q1379_PIC2.png">
 * Input: tree = [7], target =  7
 * Output: 7
 *
 * Example 3:
 * <img src="./Q1379_PIC3.png">
 * Input: tree = [8,null,6,null,5,null,4,null,3,null,2,null,1], target = 4
 * Output: 4
 *
 * Example 4:
 * <img src="./Q1379_PIC4.png">
 * Input: tree = [1,2,3,4,5,6,7,8,9,10], target = 5
 * Output: 5
 *
 * Example 5:
 * <img src="./Q1379_PIC5.png">
 * Input: tree = [1,2,null,3], target = 2
 * Output: 2
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [1, 10^4].
 * The values of the nodes of the tree are unique.
 * target node is a node from the original tree and is not null.
 */
@RunWith(LeetCodeRunner.class)
public class Q1379_FindACorrespondingNodeOfABinaryTreeInACloneOfThatTree {

    @Answer
    public final TreeNode getTargetCopy(final TreeNode original, final TreeNode cloned, final TreeNode target) {
        if (original == null || original == target) {
            return cloned;
        }
        TreeNode node = getTargetCopy(original.left, cloned.left, target);
        if (node == null) {
            node = getTargetCopy(original.right, cloned.right, target);
        }
        return node;
    }

    @TestData
    public DataExpectation example1 = createTestData(3, 7, 4, 3, null, null, 6, 19);

    private DataExpectation createTestData(int targetVal, Integer... nodes) {
        TreeNode original = TreeNode.createByLevel(nodes);
        TreeNode cloned = TreeNode.createByLevel(nodes);
        TreeNode target = getChild(original, targetVal);
        return DataExpectation.createWith(original, cloned, target)
                .expect(null)
                .assertMethod((DataAssertMethod<TreeNode>) (e, res, ori) -> {
                    Assert.assertTrue(target != res);
                    Assert.assertEquals(target, res);
                });
    }

    private TreeNode getChild(TreeNode node, int val) {
        if (node == null) {
            return null;
        }
        if (node.val == val) {
            return node;
        }
        TreeNode res = getChild(node.left, val);
        if (res == null) {
            res = getChild(node.right, val);
        }
        return res;
    }

    @TestData
    public DataExpectation example2 = createTestData(7, 7);

    @TestData
    public DataExpectation example3 = createTestData(4, 8, null, 6, null, 5, null, 4, null, 3, null, 2, null, 1);

    @TestData
    public DataExpectation example4 = createTestData(5, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    @TestData
    public DataExpectation example5 = createTestData(2, 1, 2, null, 3);

}
