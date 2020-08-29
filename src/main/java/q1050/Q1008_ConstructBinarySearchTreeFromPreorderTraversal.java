package q1050;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1008. Construct Binary Search Tree from Preorder Traversal
 * https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/
 *
 * Return the root node of a binary search tree that matches the given preorder traversal.
 *
 * (Recall that a binary search tree is a binary tree where for every node, any descendant of node.left has a value <
 * node.val, and any descendant of node.right has a value > node.val.  Also recall that a preorder traversal displays
 * the value of the node first, then traverses node.left, then traverses node.right.)
 *
 * It's guaranteed that for the given test cases there is always possible to find a binary search tree with the given
 * requirements.
 *
 * Example 1:
 * (图 Q1008_PIC.png)
 * Input: [8,5,1,7,10,12]
 * Output: [8,5,10,1,7,null,12]
 *
 * Constraints:
 *
 * 1 <= preorder.length <= 100
 * 1 <= preorder[i] <= 10^8
 * The values of preorder are distinct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1008_ConstructBinarySearchTreeFromPreorderTraversal {

    // 该树是个二叉搜索树
    @Answer
    public TreeNode bstFromPreorder(int[] preorder) {
        return dfs(preorder, 0, preorder.length);
    }

    private TreeNode dfs(int[] preorder, int start, int endE) {
        if (start >= endE) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[start]);
        int i = start + 1;
        while (i < endE && preorder[i] < preorder[start]) {
            i++;
        }
        root.left = dfs(preorder, start + 1, i);
        root.right = dfs(preorder, i, endE);
        return root;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{8, 5, 1, 7, 10, 12})
            .expect(TreeNode.createByLevel(8, 5, 10, 1, 7, null, 12));

}
