package q1300;

import org.junit.Assert;
import org.junit.Test;
import util.provided.TreeNode;

/**
 * [Medium] 1261. Find Elements in a Contaminated Binary Tree
 * https://leetcode.com/problems/find-elements-in-a-contaminated-binary-tree/
 *
 * Given a binary tree with the following rules:
 *
 * root.val == 0
 * If treeNode.val == x and treeNode.left != null, then treeNode.left.val == 2 * x + 1
 * If treeNode.val == x and treeNode.right != null, then treeNode.right.val == 2 * x + 2
 *
 * Now the binary tree is contaminated, which means all treeNode.val have been changed to -1.
 *
 * You need to first recover the binary tree and then implement the FindElements class:
 *
 * FindElements(TreeNode* root) Initializes the object with a contamined binary tree, you need to recover it first.
 * bool find(int target) Return if the target value exists in the recovered binary tree.
 *
 * Example 1:
 * <img src="./Q1261_PIC1.png">
 * Input
 * ["FindElements","find","find"]
 * [[[-1,null,-1]],[1],[2]]
 * Output
 * [null,false,true]
 * Explanation
 * FindElements findElements = new FindElements([-1,null,-1]);
 * findElements.find(1); // return False
 * findElements.find(2); // return True
 *
 * Example 2:
 * <img src="./Q1261_PIC2.png">
 * Input
 * ["FindElements","find","find","find"]
 * [[[-1,-1,-1,-1,-1]],[1],[3],[5]]
 * Output
 * [null,true,true,false]
 * Explanation
 * FindElements findElements = new FindElements([-1,-1,-1,-1,-1]);
 * findElements.find(1); // return True
 * findElements.find(3); // return True
 * findElements.find(5); // return False
 *
 * Example 3:
 * <img src="./Q1261_PIC3.png">
 * Input
 * ["FindElements","find","find","find","find"]
 * [[[-1,null,-1,-1,null,-1]],[2],[3],[4],[5]]
 * Output
 * [null,true,false,false,true]
 * Explanation
 * FindElements findElements = new FindElements([-1,null,-1,-1,null,-1]);
 * findElements.find(2); // return True
 * findElements.find(3); // return False
 * findElements.find(4); // return False
 * findElements.find(5); // return True
 *
 * Constraints:
 *
 * TreeNode.val == -1
 * The height of the binary tree is less than or equal to 20
 * The total number of nodes is between [1, 10^4]
 * Total calls of find() is between [1, 10^4]
 * 0 <= target <= 10^6
 */
public class Q1261_FindElementsInAContaminatedBinaryTree {

    /**
     * 这题节点的值就是对应满二叉树的节点(root.val = 1) 的值-1
     */
    private static class FindElements {

        final TreeNode root;

        public FindElements(TreeNode root) {
            this.root = root;
        }

        public boolean find(int target) {
            int path = 1;
            // 从下到上的左右路径选择
            for (int i = target + 1; i > 1; i /= 2) {
                path = path * 2 + i % 2;
            }
            // 从上到下走到期望路径
            TreeNode node = root;
            while (path > 1 && node != null) {
                node = path % 2 == 0 ? node.left : node.right;
                path /= 2;
            }
            return node != null;
        }
    }

    @Test
    public void example1() {
        FindElements tested = new FindElements(TreeNode.createByLevel(-1, null, -1));
        Assert.assertFalse(tested.find(1));
        Assert.assertTrue(tested.find(2));
    }

    @Test
    public void example2() {
        FindElements tested = new FindElements(TreeNode.createByLevel(-1, -1, -1, -1, -1));
        Assert.assertTrue(tested.find(1));
        Assert.assertTrue(tested.find(3));
        Assert.assertFalse(tested.find(5));
    }

    @Test
    public void example3() {
        FindElements tested = new FindElements(TreeNode.createByLevel(-1, null, -1, -1, null, -1));
        Assert.assertTrue(tested.find(2));
        Assert.assertFalse(tested.find(3));
        Assert.assertFalse(tested.find(4));
        Assert.assertTrue(tested.find(5));
    }

}
