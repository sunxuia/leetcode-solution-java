package q1400;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataAssertMethod;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1382. Balance a Binary Search Tree
 * https://leetcode.com/problems/balance-a-binary-search-tree/
 *
 * Given a binary search tree, return a balanced binary search tree with the same node values.
 *
 * A binary search tree is balanced if and only if the depth of the two subtrees of every node never differ by more than
 * 1.
 *
 * If there is more than one answer, return any of them.
 *
 * Example 1:
 * <img src="./Q1382_PIC.png">
 * Input: root = [1,null,2,null,3,null,4,null,null]
 * Output: [2,1,3,null,null,null,4]
 * Explanation: This is not the only correct answer, [3,1,4,null,2,null,null] is also correct.
 *
 * Constraints:
 *
 * The number of nodes in the tree is between 1 and 10^4.
 * The tree nodes will have distinct values between 1 and 10^5.
 */
@RunWith(LeetCodeRunner.class)
public class Q1382_BalanceABinarySearchTree {

    @Answer
    public TreeNode balanceBST(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
        inorder(list, root);
        return build(list, 0, list.size() - 1);
    }

    private void inorder(List<TreeNode> list, TreeNode root) {
        if (root == null) {
            return;
        }
        inorder(list, root.left);
        list.add(root);
        inorder(list, root.right);
    }

    private TreeNode build(List<TreeNode> list, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = (start + end) / 2;
        TreeNode node = list.get(mid);
        node.left = build(list, start, mid - 1);
        node.right = build(list, mid + 1, end);
        return node;
    }

    @TestData
    public DataExpectation example = createTestData(1, null, 2, null, 3, null, 4, null, null);

    private DataExpectation createTestData(Integer... levelData) {
        TreeNode tree = TreeNode.createByLevel(levelData);
        return DataExpectation.create(tree)
                .expect(null)
                .assertMethod((DataAssertMethod<TreeNode>) (e, res, ori) -> {
                    assertMethod(res);
                });
    }

    private void assertMethod(TreeNode root) {
        if (root == null) {
            return;
        }
        int left = getDepth(root.left);
        int right = getDepth(root.right);
        Assert.assertTrue(left - 1 <= right && right <= left + 1);
        assertMethod(root.left);
        assertMethod(root.right);
    }

    private int getDepth(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int res = -1;
        while (!queue.isEmpty()) {
            res++;
            for (int len = queue.size(); len > 0; len--) {
                TreeNode node = queue.poll();
                if (node != null) {
                    queue.offer(node.left);
                    queue.offer(node.right);
                }
            }
        }
        return res;
    }

}
