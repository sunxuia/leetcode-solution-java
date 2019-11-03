package q150;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
 *
 * Given preorder and inorder traversal of a tree, construct the binary tree.
 *
 * Note:
 * You may assume that duplicates do not exist in the tree.
 *
 * For example, given
 *
 * preorder = [3,9,20,15,7]
 * inorder = [9,3,15,20,7]
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
public class Q105_ConstructBinaryTreeFromPreorderAndInorderTraversal {

    /**
     * 这题的思路是: 对于某个节点, 比如示例中前序遍历中的 3, 那么中序遍历时3 的前面的未遍历到的数字9 就是
     * 3 的左子节点, 右边的就是3 的右子节点. 所以这题只需要采用dfs, 并确定左子节点/右子节点的边界.
     */
    @Answer
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return dfs(preorder, inorder, 0, 0, inorder.length);
    }

    private TreeNode dfs(int[] preorder, int[] inorder, int preIndex, int inStart, int inEnd) {
        if (inStart >= inEnd) {
            return null;
        }
        TreeNode node = new TreeNode(preorder[preIndex]);
        // 找到这个元素在中序数组中的位置, 这样在i 之前的就是左子节点, 之后的就是右子节点.
        int i = inStart;
        while (inorder[i] != preorder[preIndex]) {
            i++;
        }
        node.left = dfs(preorder, inorder, preIndex + 1, inStart, i);
        // preIndex + (i - inStart) + 1 就是前序数组中这个右子节点开始的位置.
        node.right = dfs(preorder, inorder, preIndex + i - inStart + 1, i + 1, inEnd);
        return node;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7})
            .expect(TreeNode.createByPreOrderTraversal(3, 9, null, null, 20, 15, null, null, 7));

}
