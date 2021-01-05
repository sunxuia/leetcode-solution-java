package q1500;

import java.util.TreeMap;
import org.junit.Assert;
import org.junit.Test;

/**
 * [Hard] 1483. Kth Ancestor of a Tree Node
 * https://leetcode.com/problems/kth-ancestor-of-a-tree-node/
 *
 * You are given a tree with n nodes numbered from 0 to n-1 in the form of a parent array where parent[i] is the parent
 * of node i. The root of the tree is node 0.
 *
 * Implement the function getKthAncestor(int node, int k) to return the k-th ancestor of the given node. If there is no
 * such ancestor, return -1.
 *
 * The k-th ancestor of a tree node is the k-th node in the path from that node to the root.
 *
 * Example:
 * <img src="./Q1483_PIC.png">
 * Input:
 * ["TreeAncestor","getKthAncestor","getKthAncestor","getKthAncestor"]
 * [[7,[-1,0,0,1,1,2,2]],[3,1],[5,2],[6,3]]
 *
 * Output:
 * [null,1,0,-1]
 *
 * Explanation:
 * TreeAncestor treeAncestor = new TreeAncestor(7, [-1, 0, 0, 1, 1, 2, 2]);
 *
 * treeAncestor.getKthAncestor(3, 1);  // returns 1 which is the parent of 3
 * treeAncestor.getKthAncestor(5, 2);  // returns 0 which is the grandparent of 5
 * treeAncestor.getKthAncestor(6, 3);  // returns -1 because there is no such ancestor
 *
 * Constraints:
 *
 * 1 <= k <= n <= 5*10^4
 * parent[0] == -1 indicating that 0 is the root node.
 * 0 <= parent[i] < n for all 0 < i < n
 * 0 <= node < n
 * There will be at most 5*10^4 queries.
 */
public class Q1483_KthAncestorOfATreeNode {

    /**
     * 路径压缩.
     */
    private static class TreeAncestor {

        final TreeMap<Integer, Integer>[] parents;

        public TreeAncestor(int n, int[] parent) {
            parents = new TreeMap[n];
            for (int i = 0; i < n; i++) {
                parents[i] = new TreeMap<>();
                parents[i].put(1, parent[i]);
            }
        }

        public int getKthAncestor(int node, int k) {
            if (node == -1) {
                return -1;
            }
            TreeMap<Integer, Integer> map = parents[node];
            if (map.containsKey(k)) {
                return map.get(k);
            }
            int lower = map.lowerKey(k);
            int res = getKthAncestor(map.get(lower), k - lower);
            map.put(k, res);
            return res;
        }
    }

    @Test
    public void testMethod() {
        TreeAncestor treeAncestor = new TreeAncestor(7, new int[]{-1, 0, 0, 1, 1, 2, 2});
        Assert.assertEquals(1, treeAncestor.getKthAncestor(3, 1));
        Assert.assertEquals(0, treeAncestor.getKthAncestor(5, 2));
        Assert.assertEquals(-1, treeAncestor.getKthAncestor(6, 3));
    }

}
