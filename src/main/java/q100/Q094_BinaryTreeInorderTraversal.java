package q100;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/binary-tree-inorder-traversal/
 *
 * Given a binary tree, return the inorder traversal of its nodes' values.
 *
 * Example:
 *
 * Input: [1,null,2,3]
 * >   1
 * >    \
 * >     2
 * >    /
 * >   3
 *
 * Output: [1,3,2]
 */
@RunWith(LeetCodeRunner.class)
public class Q094_BinaryTreeInorderTraversal {

    @Answer
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        dfs(res, root);
        return res;
    }

    private void dfs(List<Integer> res, TreeNode node) {
        if (node == null) {
            return;
        }
        dfs(res, node.left);
        res.add(node.val);
        dfs(res, node.right);
    }

    // 使用stack 而不是递归, 这个在leetcode 中要比递归慢
    @Answer
    public List<Integer> stack(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<Boolean> leftStack = new Stack<>();
        nodeStack.push(root);
        leftStack.push(false);
        while (!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            Boolean leftVisited = leftStack.pop();
            if (node != null) {
                if (leftVisited) {
                    nodeStack.push(node.right);
                    leftStack.push(false);
                    res.add(node.val);
                } else {
                    nodeStack.push(node);
                    leftStack.push(true);
                    nodeStack.push(node.left);
                    leftStack.push(false);
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(TreeNode.createByPreOrderTraversal(1, null, 2, 3))
            .expect(new int[]{1, 3, 2});

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(TreeNode.createByPreOrderTraversal(2, 3, 1))
            .expect(new int[]{1, 3, 2});

}
