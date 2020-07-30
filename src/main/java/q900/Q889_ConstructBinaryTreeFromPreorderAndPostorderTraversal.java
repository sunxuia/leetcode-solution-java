package q900;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 889. Construct Binary Tree from Preorder and Postorder Traversal
 * https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/
 *
 * Return any binary tree that matches the given preorder and postorder traversals.
 *
 * Values in the traversals pre and post are distinct positive integers.
 *
 * Example 1:
 *
 * Input: pre = [1,2,4,5,3,6,7], post = [4,5,2,6,7,3,1]
 * Output: [1,2,3,4,5,6,7]
 *
 * Note:
 *
 * 1 <= pre.length == post.length <= 30
 * pre[] and post[] are both permutations of 1, 2, ..., pre.length.
 * It is guaranteed an answer exists. If there exists multiple answers, you can return any of them.
 */
@RunWith(LeetCodeRunner.class)
public class Q889_ConstructBinaryTreeFromPreorderAndPostorderTraversal {

    @Answer
    public TreeNode constructFromPrePost(int[] pre, int[] post) {
        final int n = pre.length;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(new TreeNode(0));
        for (int i = 0, j = 0; i < n; i++) {
            TreeNode node = new TreeNode(pre[i]);
            TreeNode parent = stack.peek();
            if (parent.left == null) {
                parent.left = node;
            } else {
                parent.right = node;
            }
            while (j < n && post[j] == node.val) {
                node = stack.pop();
                j++;
            }
            stack.push(node);
        }
        return stack.getFirst().left;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(new int[]{1, 2, 4, 5, 3, 6, 7}, new int[]{4, 5, 2, 6, 7, 3, 1})
            .expect(TreeNode.createByLevel(1, 2, 3, 4, 5, 6, 7));

}
