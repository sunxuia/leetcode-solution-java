package q700;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/maximum-width-of-binary-tree/
 *
 * Given a binary tree, write a function to get the maximum width of the given tree. The width of a tree is the
 * maximum width among all levels. The binary tree has the same structure as a full binary tree, but some nodes are
 * null.
 *
 * The width of one level is defined as the length between the end-nodes (the leftmost and right most non-null nodes
 * in the level, where the null nodes between the end-nodes are also counted into the length calculation.
 *
 * Example 1:
 *
 * Input:
 *
 * >            1
 * >          /   \
 * >         3     2
 * >        / \     \
 * >       5   3     9
 *
 * Output: 4
 * Explanation: The maximum width existing in the third level with the length 4 (5,3,null,9).
 *
 * Example 2:
 *
 * Input:
 *
 * >           1
 * >          /
 * >         3
 * >        / \
 * >       5   3
 *
 * Output: 2
 * Explanation: The maximum width existing in the third level with the length 2 (5,3).
 *
 * Example 3:
 *
 * Input:
 *
 * >           1
 * >          / \
 * >         3   2
 * >        /
 * >       5
 *
 * Output: 2
 * Explanation: The maximum width existing in the second level with the length 2 (3,2).
 *
 * Example 4:
 *
 * Input:
 *
 * >           1
 * >          / \
 * >         3   2
 * >        /     \
 * >       5       9
 * >      /         \
 * >     6           7
 * Output: 8
 * Explanation:The maximum width existing in the fourth level with the length 8 (6,null,null,null,null,null,null,7).
 *
 *
 * Note: Answer will in the range of 32-bit signed integer.
 */
@RunWith(LeetCodeRunner.class)
public class Q662_MaximumWidthOfBinaryTree {

    // 这种 bfs 的方式比较慢
    @Answer
    public int widthOfBinaryTree(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int res = 0;
        while (true) {
            while (!queue.isEmpty() && queue.getFirst() == null) {
                queue.removeFirst();
            }
            while (!queue.isEmpty() && queue.getLast() == null) {
                queue.removeLast();
            }
            if (queue.isEmpty()) {
                return res;
            }
            res = Math.max(res, queue.size());
            for (int i = queue.size(); i > 0; i--) {
                TreeNode node = queue.remove();
                if (node == null) {
                    queue.add(null);
                    queue.add(null);
                } else {
                    queue.add(node.left);
                    queue.add(node.right);
                }
            }
        }
    }

    // dfs 的做法
    @Answer
    public int widthOfBinaryTree2(TreeNode root) {
        List<int[]> ranges = new ArrayList<>();
        dfs(root, ranges, 0, 0);
        int res = 0;
        for (int[] range : ranges) {
            res = Math.max(res, range[1] - range[0] + 1);
        }
        return res;
    }

    /**
     * 遍历二叉树
     *
     * @param node 二叉树节点.
     * @param ranges 保存非null 的起止位置的List.
     * @param depth 当前节点深度, root 的深度是0.
     * @param pos 当前节点的在每层中的位置, 从0 开始.
     */
    private void dfs(TreeNode node, List<int[]> ranges, int depth, int pos) {
        if (node == null) {
            return;
        }
        if (ranges.size() == depth) {
            ranges.add(new int[]{-1, -2});
        }
        int[] range = ranges.get(depth);
        if (range[0] < 0) {
            range[0] = pos;
        }
        range[1] = pos;
        dfs(node.left, ranges, depth + 1, pos * 2);
        dfs(node.right, ranges, depth + 1, pos * 2 + 1);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByLevel(1, 3, 2, 5, 3, null, 9))
            .expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(TreeNode.createByLevel(1, 3, null, 5, 3))
            .expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(TreeNode.createByLevel(1, 3, 2, 5))
            .expect(2);

    @TestData
    public DataExpectation example4 = DataExpectation
            .create(TreeNode.createByLevel(1, 3, 2, 5, null, null, 9, 6, null, null, 7))
            .expect(8);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(TreeNode.createByLevel(1))
            .expect(1);

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.readString("Q662_TestData").then(str -> {
                List<Integer> res = new ArrayList<>();
                for (String s : str.split(",")) {
                    if ("null".equals(s)) {
                        res.add(null);
                    } else {
                        res.add(Integer.parseInt(s));
                    }
                }
                return TreeNode.createByLevel(res.toArray(new Integer[0]));
            }))
            .expect(178535);

}
