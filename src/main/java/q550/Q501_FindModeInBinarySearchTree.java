package q550;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/find-mode-in-binary-search-tree/
 *
 * Given a binary search tree (BST) with duplicates, find all the mode(s) (the most frequently occurred element) in
 * the given BST.
 *
 * Assume a BST is defined as follows:
 *
 * The left subtree of a node contains only nodes with keys less than or equal to the node's key.
 * The right subtree of a node contains only nodes with keys greater than or equal to the node's key.
 * Both the left and right subtrees must also be binary search trees.
 *
 * For example:
 * Given BST [1,null,2,2],
 *
 * >    1
 * >     \
 * >      2
 * >     /
 * >    2
 *
 * return [2].
 *
 * Note: If a tree has more than one mode, you can return them in any order.
 *
 * Follow up: Could you do that without using any extra space? (Assume that the implicit stack space incurred due to
 * recursion does not count).
 */
@RunWith(LeetCodeRunner.class)
public class Q501_FindModeInBinarySearchTree {

    /**
     * BST 使用中序遍历找出来的结果就是有序的.
     * 因为这里题目要求堆的空间复杂度 O(1) (结果不算), 所以需要遍历2 次, 1次找出数量, 1次给结果赋值
     */
    @Answer
    public int[] findMode(TreeNode root) {
        count = maxCount = maxSize = nextRes = 0;
        inorder1(root);
        count = 0;
        res = new int[maxSize];
        inorder2(root);
        return res;
    }

    private int prev, count;

    private int maxCount, maxSize;

    private int[] res;

    private int nextRes = 0;

    private void inorder1(TreeNode node) {
        if (node == null) {
            return;
        }
        inorder1(node.left);
        count = prev == node.val ? count + 1 : 1;
        prev = node.val;
        if (count > maxCount) {
            maxCount = count;
            maxSize = 1;
        } else if (count == maxCount) {
            maxSize++;
        }
        inorder1(node.right);
    }

    private void inorder2(TreeNode node) {
        if (node == null) {
            return;
        }
        inorder2(node.left);
        count = prev == node.val ? count + 1 : 1;
        if (count == maxCount) {
            res[nextRes++] = node.val;
        }
        prev = node.val;
        inorder2(node.right);
    }

    @TestData
    public DataExpectation example = DataExpectation.create(TreeNode.createByLevel(1, null, 2, 2)).expect(new int[]{2});

}
