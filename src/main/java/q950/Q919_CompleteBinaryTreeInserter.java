package q950;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.Assert;
import org.junit.Test;
import util.provided.TreeNode;

/**
 * [Medium] 919. Complete Binary Tree Inserter
 * https://leetcode.com/problems/complete-binary-tree-inserter/
 *
 * A complete binary tree is a binary tree in which every level, except possibly the last, is completely filled, and all
 * nodes are as far left as possible.
 *
 * Write a data structure CBTInserter that is initialized with a complete binary tree and supports the following
 * operations:
 *
 * CBTInserter(TreeNode root) initializes the data structure on a given tree with head node root;
 * CBTInserter.insert(int v) will insert a TreeNode into the tree with value node.val = v so that the tree remains
 * complete, and returns the value of the parent of the inserted TreeNode;
 * CBTInserter.get_root() will return the head node of the tree.
 *
 * Example 1:
 *
 * Input: inputs = ["CBTInserter","insert","get_root"], inputs = [[[1]],[2],[]]
 * Output: [null,1,[1,2]]
 *
 * Example 2:
 *
 * Input: inputs = ["CBTInserter","insert","insert","get_root"], inputs = [[[1,2,3,4,5,6]],[7],[8],[]]
 * Output: [null,3,4,[1,2,3,4,5,6,7,8]]
 *
 * Note:
 *
 * The initial given tree is complete and contains between 1 and 1000 nodes.
 * CBTInserter.insert is called at most 10000 times per test case.
 * Every value of a given or inserted node is between 0 and 5000.
 */
public class Q919_CompleteBinaryTreeInserter {

    private static class CBTInserter {

        final TreeNode root;

        final Queue<TreeNode> queue = new ArrayDeque<>();

        public CBTInserter(TreeNode root) {
            this.root = root;
            queue.add(root);
            while (queue.peek().right != null) {
                TreeNode node = queue.poll();
                queue.offer(node.left);
                queue.offer(node.right);
            }
            if (queue.peek().left != null) {
                queue.offer(queue.peek().left);
            }
        }

        public int insert(int v) {
            TreeNode node = new TreeNode(v);
            TreeNode parent = queue.peek();
            if (parent.left == null) {
                parent.left = node;
            } else {
                parent.right = node;
                queue.poll();
            }
            queue.add(node);
            return parent.val;
        }

        public TreeNode get_root() {
            return root;
        }
    }

    @Test
    public void example1() {
        CBTInserter tested = new CBTInserter(TreeNode.createByLevel(1));
        Assert.assertEquals(1, tested.insert(2));
        Assert.assertEquals(TreeNode.createByLevel(1, 2), tested.get_root());
    }

    @Test
    public void example2() {
        CBTInserter tested = new CBTInserter(TreeNode.createByLevel(1, 2, 3, 4, 5, 6));
        Assert.assertEquals(3, tested.insert(7));
        Assert.assertEquals(4, tested.insert(8));
        Assert.assertEquals(TreeNode.createByLevel(1, 2, 3, 4, 5, 6, 7, 8), tested.get_root());
    }

    @Test
    public void normal1() {
        CBTInserter tested = new CBTInserter(TreeNode.createByLevel(1, 2));
        Assert.assertEquals(1, tested.insert(3));
        Assert.assertEquals(2, tested.insert(4));
        Assert.assertEquals(TreeNode.createByLevel(1, 2, 3, 4), tested.get_root());
    }

}
