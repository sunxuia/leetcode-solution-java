package q100;

import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/recover-binary-search-tree/
 *
 * Two elements of a binary search tree (BST) are swapped by mistake.
 *
 * Recover the tree without changing its structure.
 *
 * Example 1:
 *
 * Input: [1,3,null,null,2]
 *
 * >   1
 * >  /
 * > 3
 * >  \
 * >   2
 *
 * Output: [3,1,null,null,2]
 *
 * >   3
 * >  /
 * > 1
 * >  \
 * >   2
 *
 * Example 2:
 *
 * Input: [3,1,4,null,null,2]
 *
 * >   3
 * >  / \
 * > 1   4
 * >    /
 * >   2
 *
 * Output: [2,1,4,null,null,3]
 *
 * >   2
 * >  / \
 * > 1   4
 * >    /
 * >   3
 *
 * Follow up:
 *
 * A solution using O(n) space is pretty straight forward.
 * Could you devise a constant space solution?
 */
@RunWith(LeetCodeRunner.class)
public class Q099_RecoverBinarySearchTree {

    /**
     * 这个的 LeetCode 上的解体思路是:
     * 正常的BST 中序遍历的结果是递增的, 如果2 个节点反了, 那么这个2 个反了的节点在中序遍历的时候,
     * 第一个数字大于后面的数字, 第二个数字小于前面的数字, 就这样找出2 个异常的节点, 然后交换值.
     *
     * 中序遍历因为涉及到栈/方法栈所以空间复杂度是O(n)
     * O(1) 的方式是采用莫里斯遍历的方式实现的, 原理是中序遍历的时候如果右节点是null, 那么就将其设置为
     * 下一个遍历的节点. 这样在中序遍历的时候就不需要记住当这个节点遍历完成之后, 应该返回到哪个节点了.
     *
     * (不过leetcode 中没有计算方法栈的空间复杂度, 所以这题中序遍历也可以)
     */

    // 中序遍历方式
    @Answer
    public void recoverTreeInOrder(TreeNode root) {
        prev = firstWrong = secondWrong = null;
        inOrderTraversal(root);
        fixError();
    }

    private TreeNode prev, firstWrong, secondWrong;

    private void inOrderTraversal(TreeNode node) {
        if (node != null) {
            inOrderTraversal(node.left);
            judgeNode(node);
            inOrderTraversal(node.right);
        }
    }

    private void judgeNode(TreeNode node) {
        if (prev != null && prev.val > node.val) {
            if (firstWrong == null) {
                firstWrong = prev;
            }
            secondWrong = node;
        }
        prev = node;
    }

    private void fixError() {
        int temp = firstWrong.val;
        firstWrong.val = secondWrong.val;
        secondWrong.val = temp;
    }

    // 莫里斯遍历
    @Answer
    public void recoverTreeMorrisTraversal(TreeNode root) {
        prev = firstWrong = secondWrong = null;
        morrisTraversal(root);
        fixError();
    }

    private void morrisTraversal(TreeNode node) {
        while (node != null) {
            if (node.left == null) {
                // 中序遍历到这里, 判断这个节点, 并向后退
                judgeNode(node);
                node = node.right;
            } else {
                // 当前节点前面还有节点, 将这个节点的最后的一个元素
                // (当前节点中序遍历的前一个节点)的 right child 设置为当前节点.
                TreeNode lead = node.left;
                // 找到这个前导节点
                while (lead.right != null && lead.right != node) {
                    lead = lead.right;
                }
                if (lead.right == null) {
                    // 当前节点还没有被遍历, 设置前导节点的right child, 并向前进.
                    lead.right = node;
                    node = node.left;
                } else {
                    // 当前节点是已经遍历, 现在遍历回来了, 所以取消前导节点的right child设置, 并向后退
                    lead.right = null;
                    judgeNode(node);
                    node = node.right;
                }
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(TreeNode.createByPreOrderTraversal(1, 3, null, 2))
            .expectArgument(0, TreeNode.createByPreOrderTraversal(3, 1, null, 2))
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(TreeNode.createByPreOrderTraversal(3, 1, null, null, 4, 2))
            .expectArgument(0, TreeNode.createByPreOrderTraversal(2, 1, null, null, 4, 3))
            .build();

}
