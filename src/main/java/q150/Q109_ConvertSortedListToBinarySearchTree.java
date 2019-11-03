package q150;

import org.junit.Assert;
import org.junit.runner.RunWith;
import util.asserthelper.AssertUtils;
import util.provided.ListNode;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.DataExpectationBuilder;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/
 *
 * Given a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.
 *
 * For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees
 * of every node never differ by more than 1.
 *
 * Example:
 *
 * Given the sorted linked list: [-10,-3,0,5,9],
 *
 * One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:
 *
 * >       0
 * >      / \
 * >    -3   9
 * >    /   /
 * >  -10  5
 */
@RunWith(LeetCodeRunner.class)
public class Q109_ConvertSortedListToBinarySearchTree {

    /**
     * 这题可以使用和前一题一样的算法(将ListNode 转换为数组/List 即可).
     * LeetCode 上的一个解题思路如下所示, 利用前序遍历的方式来赋值.
     */
    @Answer
    public TreeNode sortedListToBST(ListNode head) {
        cur = head;
        int len = 0;
        while (head != null) {
            len++;
            head = head.next;
        }
        return dfs(len);
    }

    private ListNode cur;

    private TreeNode dfs(int len) {
        if (len == 0) {
            return null;
        }
        TreeNode node = new TreeNode(0);
        node.left = dfs(len / 2);
        node.val = cur.val;
        cur = cur.next;
        // len - len/2 - 1  = (len-1)/2,
        // len/2是左子节点, 1是的当前节点, 剩下的都是右子节点
        node.right = dfs((len - 1) / 2);
        return node;
    }

    @TestData
    public DataExpectation example() {
        DataExpectationBuilder builder = DataExpectation.builder();
        int[] arg = new int[]{-10, -3, 0, 5, 9};
        builder.addArgument(ListNode.createListNode(arg));
        builder.expect(null);
        builder.<TreeNode>assertMethod((a, res) -> {
            assertBst(res, Long.MIN_VALUE, Long.MAX_VALUE);
            assertBalancedTree(res);
            AssertUtils.assertEquals(arg, res.inOrderTraversal());
        });
        return builder.build();
    }

    private void assertBst(TreeNode node, long lower, long upper) {
        if (node != null) {
            Assert.assertFalse("expect bst but is not", node.left != null && node.left.val <= lower);
            Assert.assertFalse("expect bst but is not", node.right != null && node.right.val >= upper);
            assertBst(node.left, lower, node.val);
            assertBst(node.right, node.val, upper);
        }
    }

    private int[] assertBalancedTree(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0};
        }
        int[] left = assertBalancedTree(node.left);
        int[] right = assertBalancedTree(node.right);
        int max = Math.max(left[0], right[0]);
        int min = Math.min(left[1], right[1]);
        Assert.assertFalse("not balanced tree", Math.abs(max - min) > 1);
        return new int[]{max + 1, min + 1};
    }
}
