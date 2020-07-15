package q900;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 863. All Nodes Distance K in Binary Tree
 * https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/
 *
 * We are given a binary tree (with root node root), a target node, and an integer value K.
 *
 * Return a list of the values of all nodes that have a distance K from the target node.
 * The answer can be returned in any order.
 *
 * Example 1:
 *
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, K = 2
 *
 * Output: [7,4,1]
 *
 * Explanation:
 * The nodes that are a distance 2 from the target node (with value 5)
 * have values 7, 4, and 1.
 *
 * (图 Q863_PIC.png)
 *
 * Note that the inputs "root" and "target" are actually TreeNodes.
 * The descriptions of the inputs above are just serializations of these objects.
 *
 * Note:
 *
 * The given tree is non-empty.
 * Each node in the tree has unique values 0 <= node.val <= 500.
 * The target node is a node in the tree.
 * 0 <= K <= 1000.
 */
@RunWith(LeetCodeRunner.class)
public class Q863_AllNodesDistanceKInBinaryTree {

    // 就是无环无向图的遍历, 找出target 周边距离为i 的节点
    @Answer
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        Map<TreeNode, TreeNode> nodes = new HashMap<>();
        setParent(nodes, root, null);

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(target);
        while (K-- > 0 && !queue.isEmpty()) {
            for (int size = queue.size(); size > 0; size--) {
                TreeNode node = queue.poll();
                if (!nodes.containsKey(node)) {
                    continue;
                }
                TreeNode parent = nodes.remove(node);
                queue.add(parent);
                queue.add(node.left);
                queue.add(node.right);
            }
        }
        return queue.stream()
                .distinct()
                .filter(nodes::containsKey)
                .map(n -> n.val)
                .collect(Collectors.toList());
    }

    private void setParent(Map<TreeNode, TreeNode> parents, TreeNode node, TreeNode parent) {
        if (node != null) {
            parents.put(node, parent);
            setParent(parents, node.left, node);
            setParent(parents, node.right, node);
        }
    }

    @TestData
    public DataExpectation example() {
        TreeNode root = TreeNode.createByLevel(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4);
        return DataExpectation.builder()
                .addArgument(root)
                .addArgument(root.left)
                .addArgument(2)
                .expect(Arrays.asList(7, 4, 1))
                .unorderResult("")
                .build();
    }

}
