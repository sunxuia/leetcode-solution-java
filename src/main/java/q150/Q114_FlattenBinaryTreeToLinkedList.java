package q150;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/flatten-binary-tree-to-linked-list/
 *
 * Given a binary tree, flatten it to a linked list in-place.
 *
 * For example, given the following tree:
 *
 * >     1
 * >    / \
 * >   2   5
 * >  / \   \
 * > 3   4   6
 *
 * The flattened tree should look like:
 *
 * > 1
 * >  \
 * >   2
 * >    \
 * >     3
 * >      \
 * >       4
 * >        \
 * >         5
 * >          \
 * >           6
 */
@RunWith(LeetCodeRunner.class)
public class Q114_FlattenBinaryTreeToLinkedList {

    @Answer
    public void flatten(TreeNode root) {
        dfs(root);
    }

    private TreeNode dfs(TreeNode node) {
        if (node == null) {
            return null;
        }
        TreeNode right = node.right;
        node.right = dfs(node.left);
        node.left = null;
        TreeNode next = node;
        while (next.right != null) {
            next = next.right;
        }
        next.right = dfs(right);
        return node;
    }

    /**
     * LeetCode 上一个利用类变量实现遍历的方式, 相比上面可以省掉找到右子节点的步骤.
     */
    @Answer
    public void leetCode(TreeNode root) {
        if (root != null) {
            curr = root;
            if(root.left != null) {
                leetCode(root.left);
                curr.right = root.right;
                root.right = root.left;
                root.left = null;
            }
            leetCode(curr.right);
        }
    }

    private TreeNode curr;

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(TreeNode.createByPreOrderTraversal(1, 2, 3, null, null, 4, null, null, 5, null, 6))
            .expectArgument(0, TreeNode.createByPreOrderTraversal(1, null, 2, null, 3, null, 4, null, 5, null, 6))
            .build();

}
