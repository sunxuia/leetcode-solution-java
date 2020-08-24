package q1000;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 968. Binary Tree Cameras
 * https://leetcode.com/problems/binary-tree-cameras/
 *
 * Given a binary tree, we install cameras on the nodes of the tree.
 *
 * Each camera at a node can monitor its parent, itself, and its immediate children.
 *
 * Calculate the minimum number of cameras needed to monitor all nodes of the tree.
 *
 * Example 1:
 * (图Q968_PIC1.png)
 * Input: [0,0,null,0,0]
 * Output: 1
 * Explanation: One camera is enough to monitor all nodes if placed as shown.
 *
 * Example 2:
 * (图Q968_PIC2.png)
 * Input: [0,0,null,0,null,0,null,null,0]
 * Output: 2
 * Explanation: At least two cameras are needed to monitor all nodes of the tree. The above image shows one of the valid
 * configurations of camera placement.
 *
 * Note:
 *
 * The number of nodes in the given tree will be in the range [1, 1000].
 * Every node has value 0.
 */
@RunWith(LeetCodeRunner.class)
public class Q968_BinaryTreeCameras {

    /**
     * 这种解法比较慢
     */
    @Answer
    public int minCameraCover(TreeNode root) {
        Map<TreeNode, Integer>[] caches = new Map[3];
        for (int i = 0; i < caches.length; i++) {
            caches[i] = new HashMap<>();
            caches[i].put(null, 0);
        }
        return dfs(caches, root, 2);
    }

    /**
     * state : 状态,
     * - 0: 父节点需要本节点的摄像头提供监视,
     * - 1: 父节点安装有摄像头,
     * - 2: 父节点没有安装摄像头, 但是受到其他节点监视.
     *
     * @param caches 缓存
     * @param node 当前节点
     * @param state 状态
     * @return 本节点和子节点安装摄像头的数量
     */
    private int dfs(Map<TreeNode, Integer>[] caches, TreeNode node, int state) {
        if (caches[state].containsKey(node)) {
            return caches[state].get(node);
        }
        // 当前节点安装摄像头
        int res = 1 + dfs(caches, node.left, 1) + dfs(caches, node.right, 1);
        if (state == 1) {
            res = Math.min(res, dfs(caches, node.left, 2) + dfs(caches, node.right, 2));
        }
        if (state == 2) {
            if (node.left != null) {
                res = Math.min(res, dfs(caches, node.left, 0) + dfs(caches, node.right, 2));
            }
            if (node.right != null) {
                res = Math.min(res, dfs(caches, node.left, 2) + dfs(caches, node.right, 0));
            }
        }
        caches[state].put(node, res);
        return res;
    }

    /**
     * LeetCode 上比较快的解法. 通过从下往上的方式求出最小值.
     * 这样可以直接避开必须要的分支.
     */
    @Answer
    public int minCameraCover2(TreeNode root) {
        count = 0;
        return (dfs(root) == 0 ? 1 : 0) + count;
    }

    private int count;

    /**
     * 返回状态码.
     * - 0 : 没有安装摄像机, 需要父节点来监控
     * - 1 : 有安装摄像机
     * - 2 : 该节点未安装摄像机, 但已经有子节点来监控了(或不需要监控, 对应null 的情况)
     */
    private int dfs(TreeNode node) {
        if (node == null) {
            return 2;
        }
        int left = dfs(node.left);
        int right = dfs(node.right);
        if (left == 0 || right == 0) {
            // 左右节点有1 个没有安装摄像机, 需要父节点监控
            count++;
            return 1;
        }
        // 如果左右节点有1 个安装了摄像头, 则状态为2,
        // 否则需要父节点提供监控(状态0)
        return left == 1 || right == 1 ? 2 : 0;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(TreeNode.createByLevel(0, 0, null, 0, 0))
            .expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(TreeNode.createByLevel(0, 0, null, 0, null, 0, null, null, 0))
            .expect(2);

}
