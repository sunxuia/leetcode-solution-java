package q1350;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1305. All Elements in Two Binary Search Trees
 * https://leetcode.com/problems/all-elements-in-two-binary-search-trees/
 *
 * Given two binary search trees root1 and root2.
 *
 * Return a list containing all the integers from both trees sorted in ascending order.
 *
 * Example 1:
 * <img src="./Q1305_PIC1.png">
 * Input: root1 = [2,1,4], root2 = [1,0,3]
 * Output: [0,1,1,2,3,4]
 *
 * Example 2:
 *
 * Input: root1 = [0,-10,10], root2 = [5,1,7,0,2]
 * Output: [-10,0,0,1,2,5,7,10]
 *
 * Example 3:
 *
 * Input: root1 = [], root2 = [5,1,7,0,2]
 * Output: [0,1,2,5,7]
 *
 * Example 4:
 *
 * Input: root1 = [0,-10,10], root2 = []
 * Output: [-10,0,10]
 *
 * Example 5:
 * <img src="./Q1305_PIC2.png">
 * Input: root1 = [1,null,8], root2 = [8,1]
 * Output: [1,1,8,8]
 *
 * Constraints:
 *
 * Each tree has at most 5000 nodes.
 * Each node's value is between [-10^5, 10^5].
 */
@RunWith(LeetCodeRunner.class)
public class Q1305_AllElementsInTwoBinarySearchTrees {

    @Answer
    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> res = new ArrayList<>();
        TreeNode dummy = new TreeNode(Integer.MAX_VALUE);
        Deque<TreeNode> stack1 = new ArrayDeque<>();
        Deque<TreeNode> stack2 = new ArrayDeque<>();
        stack1.push(dummy);
        stack2.push(dummy);
        pushLeftMost(stack1, root1);
        pushLeftMost(stack2, root2);
        while (stack1.size() > 1 || stack2.size() > 1) {
            int val1 = stack1.peek().val;
            int val2 = stack2.peek().val;
            Deque<TreeNode> stack = val1 > val2 ? stack2 : stack1;
            TreeNode node = stack.pop();
            res.add(node.val);
            pushLeftMost(stack, node.right);
        }
        return res;
    }

    // 将从root 到这个树的最小节点的路径压入栈中
    private void pushLeftMost(Deque<TreeNode> stack, TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(TreeNode.createByLevel(2, 1, 4), TreeNode.createByLevel(1, 0, 3))
            .expect(List.of(0, 1, 1, 2, 3, 4));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(TreeNode.createByLevel(0, -10, 10), TreeNode.createByLevel(5, 1, 7, 0, 2))
            .expect(List.of(-10, 0, 0, 1, 2, 5, 7, 10));

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(TreeNode.createByLevel(), TreeNode.createByLevel(5, 1, 7, 0, 2))
            .expect(List.of(0, 1, 2, 5, 7));

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(TreeNode.createByLevel(0, -10, 10), TreeNode.createByLevel())
            .expect(List.of(-10, 0, 10));

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(TreeNode.createByLevel(1, null, 8), TreeNode.createByLevel(8, 1))
            .expect(List.of(1, 1, 8, 8));

}
