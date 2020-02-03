package q150;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/binary-tree-postorder-traversal/
 *
 * Given a binary tree, return the postorder traversal of its nodes' values.
 *
 * Example:
 *
 * Input: [1,null,2,3]
 * >    1
 * >     \
 * >      2
 * >     /
 * >    3
 *
 * Output: [3,2,1]
 *
 * Follow up: Recursive solution is trivial, could you do it iteratively?
 */
@RunWith(LeetCodeRunner.class)
public class Q145_BinaryTreePostorderTraversal {

    // 题目要求非递归方式
    @Answer
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<Object> stack = new Stack<>();
        stack.push(root);
        stack.push(false);
        while (!stack.isEmpty()) {
            boolean isBack = (boolean) stack.pop();
            TreeNode node = (TreeNode) stack.pop();
            if (node == null) {
                continue;
            }
            if (isBack) {
                res.add(node.val);
            } else {
                stack.push(node);
                stack.push(true);
                stack.push(node.right);
                stack.push(false);
                stack.push(node.left);
                stack.push(false);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(1, null, 2, 3))
            .expect(new int[]{3, 2, 1});

    @TestData
    public DataExpectation border = DataExpectation.create(null).expect(Collections.emptyList());

}
