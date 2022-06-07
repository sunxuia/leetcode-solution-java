package q1950;

import static util.provided.TreeNode.createByLevel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1932. Merge BSTs to Create Single BST
 * https://leetcode.com/problems/merge-bsts-to-create-single-bst/
 *
 * You are given n BST (binary search tree) root nodes for n separate BSTs stored in an array trees (0-indexed). Each
 * BST in trees has at most 3 nodes, and no two roots have the same value. In one operation, you can:
 *
 * Select two distinct indices i and j such that the value stored at one of the leaves of trees[i] is equal to the root
 * value of trees[j].
 * Replace the leaf node in trees[i] with trees[j].
 * Remove trees[j] from trees.
 *
 * Return the root of the resulting BST if it is possible to form a valid BST after performing n - 1 operations, or null
 * if it is impossible to create a valid BST.
 *
 * A BST (binary search tree) is a binary tree where each node satisfies the following property:
 *
 * Every node in the node's left subtree has a value strictly less than the node's value.
 * Every node in the node's right subtree has a value strictly greater than the node's value.
 *
 * A leaf is a node that has no children.
 *
 * Example 1:
 * (图Q1932_PIC1.png)
 * Input: trees = [[2,1],[3,2,5],[5,4]]
 * Output: [3,2,5,1,null,4]
 * Explanation:
 * In the first operation, pick i=1 and j=0, and merge trees[0] into trees[1].
 * Delete trees[0], so trees = [[3,2,5,1],[5,4]].
 * (图Q1932_PIC2.png)
 * In the second operation, pick i=0 and j=1, and merge trees[1] into trees[0].
 * Delete trees[1], so trees = [[3,2,5,1,null,4]].
 * (图Q1932_PIC3.png)
 * The resulting tree, shown above, is a valid BST, so return its root.
 *
 * Example 2:
 * (图Q1932_PIC4.png)
 * Input: trees = [[5,3,8],[3,2,6]]
 * Output: []
 * Explanation:
 * Pick i=0 and j=1 and merge trees[1] into trees[0].
 * Delete trees[1], so trees = [[5,3,8,2,6]].
 * (图Q1932_PIC5.png)
 * The resulting tree is shown above. This is the only valid operation that can be performed, but the resulting tree is
 * not a valid BST, so return null.
 *
 * Example 3:
 * (图Q1932_PIC6.png)
 * Input: trees = [[5,4],[3]]
 * Output: []
 * Explanation: It is impossible to perform any operations.
 *
 * Constraints:
 *
 * n == trees.length
 * 1 <= n <= 5 * 10^4
 * The number of nodes in each tree is in the range [1, 3].
 * Each node in the input may have children but no grandchildren.
 * No two roots of trees have the same value.
 * All the trees in the input are valid BSTs.
 * 1 <= TreeNode.val <= 5 * 10^4.
 */
@RunWith(LeetCodeRunner.class)
public class Q1932_MergeBstsToCreateSingleBst {

    /**
     * 这题没什么思路, 参考discussion 中的解法.
     */
    @Answer
    public TreeNode canMerge(List<TreeNode> trees) {
        Map<Integer, TreeNode> map = new HashMap<>();
        Set<Integer> leaves = new HashSet<>();
        for (TreeNode tree : trees) {
            map.put(tree.val, tree);

            if (tree.left != null) {
                leaves.add(tree.left.val);
            }
            if (tree.right != null) {
                leaves.add(tree.right.val);
            }
        }

        // 找出没有可放入叶子节点的树, 这个就是根节点
        TreeNode root = null;
        for (TreeNode tree : trees) {
            if (!leaves.contains(tree.val)) {
                if (root != null) {
                    return null;
                }
                root = tree;
            }
        }
        if (root == null) {
            return null;
        }
        map.remove(root.val);

        // 中序遍历构造树.
        int prev = -1;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode dummy = new TreeNode(0);
        dummy.right = root;
        stack.push(dummy);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (prev >= node.val) {
                return null;
            }
            prev = node.val;
            if (canMount(node.right, map)) {
                node.right = map.remove(node.right.val);
            }
            node = node.right;
            while (node != null) {
                if (canMount(node.left, map)) {
                    node.left = map.remove(node.left.val);
                }
                stack.push(node);
                node = node.left;
            }
        }
        if (!map.isEmpty()) {
            return null;
        }
        return root;
    }

    private boolean canMount(TreeNode node, Map<Integer, TreeNode> map) {
        return node != null
                && node.left == null && node.right == null
                && map.containsKey(node.val);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(List.of(createByLevel(2, 1), createByLevel(3, 2, 5), createByLevel(5, 4)))
            .expect(createByLevel(3, 2, 5, 1, null, 4));

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(List.of(createByLevel(5, 3, 8), createByLevel(3, 2, 6)))
            .expect(null);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(List.of(createByLevel(5, 4), createByLevel(3)))
            .expect(null);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(List.of(createByLevel(1, null, 4), createByLevel(2, 1, 5), createByLevel(3),
                    createByLevel(4, 2, 6)))
            .expect(null);

}
