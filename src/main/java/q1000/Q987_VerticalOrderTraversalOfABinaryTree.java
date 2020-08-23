package q1000;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import org.junit.runner.RunWith;
import util.provided.TreeNode;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 987. Vertical Order Traversal of a Binary Tree
 * https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/
 *
 * Given a binary tree, return the vertical order traversal of its nodes values.
 *
 * For each node at position (X, Y), its left and right children respectively will be at positions (X-1, Y-1) and (X+1,
 * Y-1).
 *
 * Running a vertical line from X = -infinity to X = +infinity, whenever the vertical line touches some nodes, we report
 * the values of the nodes in order from top to bottom (decreasing Y coordinates).
 *
 * If two nodes have the same position, then the value of the node that is reported first is the value that is smaller.
 *
 * Return an list of non-empty reports in order of X coordinate.  Every report will have a list of values of nodes.
 *
 * Example 1:
 * (图 Q987_PIC1.png)
 * Input: [3,9,20,null,null,15,7]
 * Output: [[9],[3,15],[20],[7]]
 * Explanation:
 * Without loss of generality, we can assume the root node is at position (0, 0):
 * Then, the node with value 9 occurs at position (-1, -1);
 * The nodes with values 3 and 15 occur at positions (0, 0) and (0, -2);
 * The node with value 20 occurs at position (1, -1);
 * The node with value 7 occurs at position (2, -2).
 *
 * Example 2:
 * (图 Q987_PIC2.png)
 * Input: [1,2,3,4,5,6,7]
 * Output: [[4],[2],[1,5,6],[3],[7]]
 * Explanation:
 * The node with value 5 and the node with value 6 have the same position according to the given scheme.
 * However, in the report "[1,5,6]", the node value of 5 comes first since 5 is smaller than 6.
 *
 * Note:
 *
 * The tree will have between 1 and 1000 nodes.
 * Each node's value will be between 0 and 1000.
 */
@RunWith(LeetCodeRunner.class)
public class Q987_VerticalOrderTraversalOfABinaryTree {

    @Answer
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        int[] size = getSize(root);
        int width = size[0] + size[1] - 1, height = size[2];

        List<Integer>[][] grid = new List[width][height];
        dfs(grid, root, size[0] - 1, 0);

        List<List<Integer>> res = new ArrayList<>(width);
        for (int i = 0; i < width; i++) {
            res.add(new ArrayList<>());
            for (int j = 0; j < height; j++) {
                if (grid[i][j] != null) {
                    grid[i][j].sort((a, b) -> a - b);
                    res.get(res.size() - 1).addAll(grid[i][j]);
                }
            }
        }
        return res;
    }

    private int[] getSize(TreeNode node) {
        int[] res = new int[3];
        if (node == null) {
            return res;
        }
        int[] left = getSize(node.left);
        int[] right = getSize(node.right);
        res[0] = Math.max(left[0] + 1, right[0] - 1);
        res[1] = Math.max(left[1] - 1, right[1] + 1);
        res[2] = 1 + Math.max(left[2], right[2]);
        return res;
    }

    private void dfs(List<Integer>[][] grid, TreeNode node, int i, int j) {
        if (node == null) {
            return;
        }
        if (grid[i][j] == null) {
            grid[i][j] = new ArrayList<>();
        }
        grid[i][j].add(node.val);
        dfs(grid, node.left, i - 1, j + 1);
        dfs(grid, node.right, i + 1, j + 1);
    }

    // 参考LeetCode 中的解法, 使用Map 来保存坐标信息, 这种方式更简单.
    @Answer
    public List<List<Integer>> verticalTraversal2(TreeNode root) {
        TreeMap<Integer, TreeMap<Integer, List<Integer>>> map = new TreeMap<>();
        dfs(map, root, 0, 0);

        List<List<Integer>> res = new ArrayList<>();
        for (Integer x = map.ceilingKey(Integer.MIN_VALUE); x != null; x = map.higherKey(x)) {
            res.add(new ArrayList<>());
            TreeMap<Integer, List<Integer>> columnMap = map.get(x);
            for (Integer y = columnMap.ceilingKey(Integer.MIN_VALUE); y != null; y = columnMap.higherKey(y)) {
                List<Integer> list = columnMap.get(y);
                list.sort((a, b) -> a - b);
                res.get(res.size() - 1).addAll(list);
            }
        }
        return res;
    }

    private void dfs(TreeMap<Integer, TreeMap<Integer, List<Integer>>> map, TreeNode node, int i, int j) {
        if (node == null) {
            return;
        }
        map.computeIfAbsent(i, k -> new TreeMap<>())
                .computeIfAbsent(j, k -> new ArrayList<>())
                .add(node.val);
        dfs(map, node.left, i - 1, j + 1);
        dfs(map, node.right, i + 1, j + 1);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(TreeNode.createByLevel(3, 9, 20, null, null, 15, 7))
            .expect(asList(asList(9), asList(3, 15), asList(20), asList(7)));

    @TestData
    public DataExpectation example2 = DataExpectation.create(TreeNode.createByLevel(1, 2, 3, 4, 5, 6, 7))
            .expect(asList(asList(4), asList(2), asList(1, 5, 6), asList(3), asList(7)));

}
