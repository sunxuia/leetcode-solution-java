package q250;

import java.util.LinkedList;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/count-complete-tree-nodes/
 *
 * Given a complete binary tree, count the number of nodes.
 *
 * Note:
 *
 * Definition of a complete binary tree from Wikipedia:
 * In a complete binary tree every level, except possibly the last, is completely filled, and all nodes in the last
 * level are as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.
 *
 * Example:
 *
 * Input:
 * >     1
 * >    / \
 * >   2   3
 * >  / \  /
 * > 4  5 6
 *
 * Output: 6
 */
@RunWith(LeetCodeRunner.class)
public class Q222_CountCompleteTreeNodes {

    // BFS 的方法, 这种方式比较慢
    @Answer
    public int countNodes(TreeNode root) {
        int count = 1;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (true) {
            for (int len = queue.size(); len > 0; len--) {
                TreeNode node = queue.remove();
                if (node == null) {
                    return count - len;
                }
                queue.add(node.left);
                queue.add(node.right);
            }
            count = count * 2 + 1;
        }
    }

    // DFS 的方法, 从后往前找, 这个方法就快点了.
    @Answer
    public int countNodes_dfs(TreeNode root) {
        int expectCount = 0;
        for (TreeNode node = root; node != null; node = node.left) {
            expectCount = expectCount * 2 + 1;
        }
        return dfs(root, expectCount);
    }

    private int dfs(TreeNode node, int expectCount) {
        if (node == null) {
            return 0;
        }
        int expectChildCount = (expectCount - 1) / 2;
        int right = dfs(node.right, expectChildCount);
        int left = right > expectChildCount / 2 ? expectChildCount : dfs(node.left, expectChildCount);
        return right + left + 1;
    }

    // LeetCode 上最快的解法
    @Answer
    public int countNodes_leetCode(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int maxDepth = computeDepth(root);
        if (maxDepth == 0) {
            return 1;
        }

        // 底层的节点编号从 0 到 2**d - 1 (left -> right), 使用二分查找找出断开的点.
        int left = 1, right = (int) Math.pow(2, maxDepth) - 1;
        int pivot;
        while (left <= right) {
            pivot = left + (right - left) / 2;
            if (exists(pivot, maxDepth, root)) {
                left = pivot + 1;
            } else {
                right = pivot - 1;
            }
        }

        // 满树总数 - 缺失的节点数
        return (int) Math.pow(2, maxDepth) - 1 + left;
    }

    private int computeDepth(TreeNode node) {
        int res = 0;
        while (node.left != null) {
            node = node.left;
            res++;
        }
        return res;
    }

    // 找到底层 0 到 2**d - 1 (left -> right) 编号index 的节点是否存在, 如果存在返回true.
    // 时间复杂度 O(n)
    private boolean exists(int index, int depth, TreeNode node) {
        int left = 0, right = (int) Math.pow(2, depth) - 1;
        int pivot;
        for (int i = 0; i < depth; ++i) {
            pivot = left + (right - left) / 2;
            if (index <= pivot) {
                node = node.left;
                right = pivot;
            } else {
                node = node.right;
                left = pivot + 1;
            }
        }
        return node != null;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(1, 2, 4, null, null, 5, null, null, 3, 6))
            .expect(6);

    @TestData
    public DataExpectation border = DataExpectation.create(null).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(1, 2, null, null, 3))
            .expect(3);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(TreeNode.createByPreOrderTraversal(1, 2, 4, null, null, null, 3))
            .expect(4);

}
