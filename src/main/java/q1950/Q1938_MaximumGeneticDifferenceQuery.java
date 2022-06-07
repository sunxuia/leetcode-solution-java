package q1950;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1938. Maximum Genetic Difference Query
 * https://leetcode.com/problems/maximum-genetic-difference-query/
 *
 * There is a rooted tree consisting of n nodes numbered 0 to n - 1. Each node's number denotes its unique genetic value
 * (i.e. the genetic value of node x is x). The genetic difference between two genetic values is defined as the
 * bitwise-XOR of their values. You are given the integer array parents, where parents[i] is the parent for node i. If
 * node x is the root of the tree, then parents[x] == -1.
 *
 * You are also given the array queries where queries[i] = [nodei, vali]. For each query i, find the maximum genetic
 * difference between vali and pi, where pi is the genetic value of any node that is on the path between nodei and the
 * root (including nodei and the root). More formally, you want to maximize vali XOR pi.
 *
 * Return an array ans where ans[i] is the answer to the ith query.
 *
 * Example 1:
 * (图Q1938_PIC1.png)
 * Input: parents = [-1,0,1,1], queries = [[0,2],[3,2],[2,5]]
 * Output: [2,3,7]
 * Explanation: The queries are processed as follows:
 * - [0,2]: The node with the maximum genetic difference is 0, with a difference of 2 XOR 0 = 2.
 * - [3,2]: The node with the maximum genetic difference is 1, with a difference of 2 XOR 1 = 3.
 * - [2,5]: The node with the maximum genetic difference is 2, with a difference of 5 XOR 2 = 7.
 *
 * Example 2:
 * (图Q1938_PIC2.png)
 * Input: parents = [3,7,-1,2,0,7,0,2], queries = [[4,6],[1,15],[0,5]]
 * Output: [6,14,7]
 * Explanation: The queries are processed as follows:
 * - [4,6]: The node with the maximum genetic difference is 0, with a difference of 6 XOR 0 = 6.
 * - [1,15]: The node with the maximum genetic difference is 1, with a difference of 15 XOR 1 = 14.
 * - [0,5]: The node with the maximum genetic difference is 2, with a difference of 5 XOR 2 = 7.
 *
 * Constraints:
 *
 * 2 <= parents.length <= 10^5
 * 0 <= parents[i] <= parents.length - 1 for every node i that is not the root.
 * parents[root] == -1
 * 1 <= queries.length <= 3 * 10^4
 * 0 <= nodei <= parents.length - 1
 * 0 <= vali <= 2 * 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1938_MaximumGeneticDifferenceQuery {

    /**
     * 按照提示这题涉及到前缀树
     */
    @Answer
    public int[] maxGeneticDifference(int[] parents, int[][] queries) {
        Map<Integer, List<Query>> queryMap = buildQueryMap(queries);
        TreeNode root = buildTreeNode(parents);
        root.traverse(queryMap, new Trie());

        int[] res = new int[queries.length];
        for (List<Query> list : queryMap.values()) {
            for (Query query : list) {
                res[query.index] = query.res;
            }
        }
        return res;
    }

    // 构造树
    private TreeNode buildTreeNode(int[] parents) {
        final int n = parents.length;
        TreeNode[] treeNodes = new TreeNode[n];
        for (int i = 0; i < n; i++) {
            treeNodes[i] = new TreeNode();
            treeNodes[i].genetic = i;
        }
        TreeNode root = null;
        for (int i = 0; i < n; i++) {
            if (parents[i] == -1) {
                root = treeNodes[i];
            } else {
                treeNodes[parents[i]].children.add(treeNodes[i]);
            }
        }
        return root;
    }

    // 树结构
    private static class TreeNode {

        int genetic;

        List<TreeNode> children = new ArrayList<>();

        // 遍历树, 找出查询结果
        void traverse(Map<Integer, List<Query>> queryMap, Trie trie) {
            trie.add(genetic);
            if (queryMap.containsKey(genetic)) {
                for (Query query : queryMap.get(genetic)) {
                    query.res = trie.findMaxXor(query.value);
                }
            }
            for (TreeNode child : children) {
                child.traverse(queryMap, trie);
            }
            trie.remove(genetic);
        }
    }

    // 构造查询的map
    private Map<Integer, List<Query>> buildQueryMap(int[][] queries) {
        final int m = queries.length;
        Map<Integer, List<Query>> queryMap = new HashMap<>();
        for (int i = 0; i < m; i++) {
            Query query = new Query();
            query.index = i;
            query.value = queries[i][1];
            queryMap.computeIfAbsent(queries[i][0], k -> new ArrayList<>()).add(query);
        }
        return queryMap;
    }

    // 查询的数据结构
    private static class Query {

        int index, value, res;

    }

    // 前缀树的节点
    private static class Trie {

        // trie 的最大位数, 10^5 的最大位数就是17位
        private static final int MAX_LEVEL = 17;

        // 对应的数字的位数
        int level = MAX_LEVEL - 1;

        // count 表示有多少叶子节点
        // value 表示如果该节点是叶子节点, 则值是多少
        int count, value;

        Trie[] next = new Trie[2];

        // 添加数字
        void add(int value) {
            count++;
            if (level == -1) {
                // 叶子节点
                this.value = value;
            } else {
                // 非叶子节点
                int v = value >> level & 1;
                if (next[v] == null) {
                    next[v] = new Trie();
                    next[v].level = level - 1;
                }
                next[v].add(value);
            }
        }

        // 删除数字
        void remove(int value) {
            count--;
            int v = value >> level & 1;
            if (level == 0) {
                // 叶子节点的上一级
                next[v] = null;
            } else {
                next[v].remove(value);
            }
        }

        // 找出最大异或的结果
        int findMaxXor(int query) {
            if (level == -1) {
                // 叶子节点
                return value ^ query;
            }
            int v = query >> level & 1 ^ 1;
            // 尽量往高位的异或结果为1 去匹配
            if (next[v] == null || next[v].count == 0) {
                return next[1 - v].findMaxXor(query);
            } else {
                return next[v].findMaxXor(query);
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{-1, 0, 1, 1}, new int[][]{{0, 2}, {3, 2}, {2, 5}})
            .expect(new int[]{2, 3, 7});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{3, 7, -1, 2, 0, 7, 0, 2}, new int[][]{{4, 6}, {1, 15}, {0, 5}})
            .expect(new int[]{6, 14, 7});

}
