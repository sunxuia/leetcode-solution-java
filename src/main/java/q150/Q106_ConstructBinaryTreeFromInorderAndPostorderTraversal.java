package q150;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
 *
 * Given inorder and postorder traversal of a tree, construct the binary tree.
 *
 * Note:
 * You may assume that duplicates do not exist in the tree.
 *
 * For example, given
 *
 * inorder = [9,3,15,20,7]
 * postorder = [9,15,7,20,3]
 *
 * Return the following binary tree:
 *
 * >     3
 * >    / \
 * >   9  20
 * >     /  \
 * >    15   7
 */
@RunWith(LeetCodeRunner.class)
public class Q106_ConstructBinaryTreeFromInorderAndPostorderTraversal {

    // 和上题类似, 不过反着来就好了
    @Answer
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return dfs(inorder, postorder, inorder.length - 1, postorder.length - 1, 0);
    }

    private TreeNode dfs(int[] inorder, int[] postorder, int is, int ps, int pe) {
        if (ps < pe) {
            return null;
        }
        TreeNode node = new TreeNode(postorder[is]);
        int pi = ps;
        while (inorder[pi] != postorder[is]) {
            pi--;
        }
        node.right = dfs(inorder, postorder, is - 1, ps, pi + 1);
        node.left = dfs(inorder, postorder, is - (ps - pi) - 1, pi - 1, pe);
        return node;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(new int[]{9, 3, 15, 20, 7}, new int[]{9, 15, 7, 20, 3})
            .expect(TreeNode.createByPreOrderTraversal(3, 9, null, null, 20, 15, null, null, 7));

}
