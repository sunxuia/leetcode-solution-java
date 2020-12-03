package q1400;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.provided.ListNode;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1367. Linked List in Binary Tree
 * https://leetcode.com/problems/linked-list-in-binary-tree/
 *
 * Given a binary tree root and a linked list with head as the first node.
 *
 * Return True if all the elements in the linked list starting from the head correspond to some downward path connected
 * in the binary tree otherwise return False.
 *
 * In this context downward path means a path that starts at some node and goes downwards.
 *
 * Example 1:
 * <img src="./Q1367_PIC1.png">
 * Input: head = [4,2,8], root = [1,4,4,null,2,2,null,1,null,6,8,null,null,null,null,1,3]
 * Output: true
 * Explanation: Nodes in blue form a subpath in the binary Tree.
 *
 * Example 2:
 * <img src="./Q1367_PIC2.png">
 * Input: head = [1,4,2,6], root = [1,4,4,null,2,2,null,1,null,6,8,null,null,null,null,1,3]
 * Output: true
 *
 * Example 3:
 *
 * Input: head = [1,4,2,6,8], root = [1,4,4,null,2,2,null,1,null,6,8,null,null,null,null,1,3]
 * Output: false
 * Explanation: There is no path in the binary tree that contains all the elements of the linked list from head.
 *
 * Constraints:
 *
 * 1 <= node.val <= 100 for each node in the linked list and binary tree.
 * The given linked list will contain between 1 and 100 nodes.
 * The given binary tree will contain between 1 and 2500 nodes.
 */
@RunWith(LeetCodeRunner.class)
public class Q1367_LinkedListInBinaryTree {

    @Answer
    public boolean isSubPath(ListNode head, TreeNode root) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        int[][] kmp = createKmp(list);
        return dfs(root, kmp, 0);
    }

    private int[][] createKmp(List<Integer> list) {
        int[][] kmp = new int[list.size()][101];
        kmp[0][list.get(0)] = 1;
        int[] reset = kmp[0];
        for (int i = 1; i < list.size(); i++) {
            System.arraycopy(reset, 1, kmp[i], 1, 100);
            kmp[i][list.get(i)] = i + 1;
            reset = kmp[reset[list.get(i)]];
        }
        return kmp;
    }

    private boolean dfs(TreeNode tree, int[][] kmp, int i) {
        if (i == kmp.length) {
            return true;
        }
        if (tree == null) {
            return false;
        }
        return dfs(tree.left, kmp, kmp[i][tree.val])
                || dfs(tree.right, kmp, kmp[i][tree.val]);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(
            ListNode.createListNode(4, 2, 8),
            TreeNode.createByLevel(1, 4, 4, null, 2, 2, null, 1, null, 6, 8, null, null, null, null, 1, 3)
    ).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(
            ListNode.createListNode(1, 4, 2, 6),
            TreeNode.createByLevel(1, 4, 4, null, 2, 2, null, 1, null, 6, 8, null, null, null, null, 1, 3)
    ).expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(
            ListNode.createListNode(1, 4, 2, 6, 8),
            TreeNode.createByLevel(1, 4, 4, null, 2, 2, null, 1, null, 6, 8, null, null, null, null, 1, 3)
    ).expect(false);

}
