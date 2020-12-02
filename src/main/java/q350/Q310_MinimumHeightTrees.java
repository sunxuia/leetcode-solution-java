package q350;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/minimum-height-trees
 *
 * For an undirected graph with tree characteristics, we can choose any node as the root. The result graph is then a
 * rooted tree. Among all possible rooted trees, those with minimum height are called minimum height trees (MHTs).
 * Given such a graph, write a function to find all the MHTs and return a list of their root labels.
 *
 * Format
 * The graph contains n nodes which are labeled from 0 to n - 1. You will be given the number n and a list of
 * undirected edges (each edge is a pair of labels).
 *
 * You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as
 * [1, 0] and thus will not appear together in edges.
 *
 * Example 1 :
 *
 * Input: n = 4, edges = [[1, 0], [1, 2], [1, 3]]
 *
 * >          0
 * >          |
 * >          1
 * >         / \
 * >        2   3
 *
 * Output: [1]
 *
 * Example 2 :
 *
 * Input: n = 6, edges = [[0, 3], [1, 3], [2, 3], [4, 3], [5, 4]]
 *
 * >       0  1  2
 * >        \ | /
 * >          3
 * >          |
 * >          4
 * >          |
 * >          5
 *
 * Output: [3, 4]
 *
 * Note:
 *
 * According to the definition of tree on Wikipedia: “a tree is an undirected graph in which any two vertices are
 * connected by exactly one path. In other words, any connected graph without simple cycles is a tree.”
 * The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.
 */
@RunWith(LeetCodeRunner.class)
public class Q310_MinimumHeightTrees {

    // 可以用减少入度的方式解决
    @Answer
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        Set<Integer>[] es = new HashSet[n];
        for (int i = 0; i < n; i++) {
            es[i] = new HashSet<>();
        }
        for (int[] edge : edges) {
            es[edge[0]].add(edge[1]);
            es[edge[1]].add(edge[0]);
        }
        List<Integer> leaves = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (es[i].size() < 2) {
                leaves.add(i);
            }
        }
        List<Integer> newLeaves = new ArrayList<>();
        List<Integer> oldLeaves;
        do {
            newLeaves.clear();
            for (Integer leaf : leaves) {
                for (Integer neighbor : es[leaf]) {
                    es[leaf].remove(neighbor);
                    es[neighbor].remove(leaf);
                    if (es[neighbor].size() == 1) {
                        newLeaves.add(neighbor);
                    }
                }
            }
            oldLeaves = leaves;
            leaves = newLeaves;
            newLeaves = oldLeaves;
        } while (!leaves.isEmpty());
        return oldLeaves;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(4, new int[][]{{1, 0}, {1, 2}, {1, 3}})
            .expect(new int[]{1});

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(6)
            .addArgument(new int[][]{{0, 3}, {1, 3}, {2, 3}, {4, 3}, {5, 4}})
            .expect(new int[]{3, 4})
            .unorderResult("")
            .build();

    @TestData
    public DataExpectation border = DataExpectation
            .createWith(1, new int[0][])
            .expect(new int[]{0});

    @TestData
    public DataExpectation largeData = DataExpectation.builder()
            .addArgument(5000)
            .addArgument(TestDataFileHelper.read("Q310_LongTestData", int[][].class))
            .expect(new int[]{2499, 2500})
            .unorderResult("")
            .build();

}
