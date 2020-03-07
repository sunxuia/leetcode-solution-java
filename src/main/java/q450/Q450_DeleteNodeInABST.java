package q450;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/delete-node-in-a-bst/
 */
@RunWith(LeetCodeRunner.class)
public class Q450_DeleteNodeInABST {

    @Answer
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        if (key < root.val) {
            root.left = deleteNode(root.left, key);
            return root;
        }

        if (root.val < key) {
            root.right = deleteNode(root.right, key);
            return root;
        }

        if (root.left == null) {
            return root.right;
        }

        TreeNode node = root.left;
        while (node.right != null) {
            node = node.right;
        }
        node.right = root.right;
        return root.left;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(TreeNode.createByLevel(5, 3, 6, 2, 4, null, 7))
            .addArgument(3)
            .expect(TreeNode.createByLevel(5, 4, 6, 2, null, null, 7))
            .orExpect(TreeNode.createByLevel(5, 2, 6, null, 4, null, 7))
            .build();

}
