package q200;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.Assert;
import org.junit.Test;
import util.provided.TreeNode;

/**
 * https://leetcode.com/problems/binary-search-tree-iterator/
 *
 * Implement an iterator over a binary search tree (BST). Your iterator will be initialized with the root node of a BST.
 *
 * Calling next() will return the next smallest number in the BST.
 *
 * (图见 Q173_PIC.png)
 *
 * Example:
 *
 * BSTIterator iterator = new BSTIterator(root);
 * iterator.next();    // return 3
 * iterator.next();    // return 7
 * iterator.hasNext(); // return true
 * iterator.next();    // return 9
 * iterator.hasNext(); // return true
 * iterator.next();    // return 15
 * iterator.hasNext(); // return true
 * iterator.next();    // return 20
 * iterator.hasNext(); // return false
 *
 *
 *
 * Note:
 *
 * next() and hasNext() should run in average O(1) time and uses O(h) memory, where h is the height of the tree.
 * You may assume that next() call will always be valid, that is, there will be at least a next smallest number
 * in the BST when next() is called.
 */
public class Q173_BinarySearchTreeIterator {

    private static class BSTIterator {

        private Deque<TreeNode> stack = new ArrayDeque<>();

        public BSTIterator(TreeNode root) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
        }

        /**
         * @return the next smallest number
         */
        public int next() {
            TreeNode node = stack.pop();
            int res = node.val;
            node = node.right;
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            return res;
        }

        /**
         * @return whether we have a next smallest number
         */
        public boolean hasNext() {
            return !stack.isEmpty();
        }
    }

    @Test
    public void testMethod() {
        TreeNode root = TreeNode.createByPreOrderTraversal(7, 3, null, null, 15, 9, null, null, 20);
        BSTIterator iterator = new BSTIterator(root);
        Assert.assertEquals(3, iterator.next());
        Assert.assertEquals(7, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(9, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(15, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(20, iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }
}
