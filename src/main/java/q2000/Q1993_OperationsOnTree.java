package q2000;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import org.junit.Assert;
import org.junit.Test;

/**
 * [Medium] 1993. Operations on Tree
 * https://leetcode.com/problems/operations-on-tree/
 *
 * You are given a tree with n nodes numbered from 0 to n - 1 in the form of a parent array parent where parent[i] is
 * the parent of the ith node. The root of the tree is node 0, so parent[0] = -1 since it has no parent. You want to
 * design a data structure that allows users to lock, unlock, and upgrade nodes in the tree.
 *
 * The data structure should support the following functions:
 *
 * - Lock: Locks the given node for the given user and prevents other users from locking the same node. You may only
 * lock a node using this function if the node is unlocked.
 * - Unlock: Unlocks the given node for the given user. You may only unlock a node using this function if it is
 * currently locked by the same user.
 * - Upgrade: Locks the given node for the given user and unlocks all of its descendants regardless of who locked it.
 * You may only upgrade a node if all 3 conditions are true:
 *
 * -- The node is unlocked,
 * -- It has at least one locked descendant (by any user), and
 * -- It does not have any locked ancestors.
 *
 *
 *
 * Implement the LockingTree class:
 *
 * - LockingTree(int[] parent) initializes the data structure with the parent array.
 * - lock(int num, int user) returns true if it is possible for the user with id user to lock the node num, or false
 * otherwise. If it is possible, the node num will become locked by the user with id user.
 * - unlock(int num, int user) returns true if it is possible for the user with id user to unlock the node num, or
 * false
 * otherwise. If it is possible, the node num will become unlocked.
 * - upgrade(int num, int user) returns true if it is possible for the user with id user to upgrade the node num, or
 * false otherwise. If it is possible, the node num will be upgraded.
 *
 * Example 1:
 *
 * Input
 * ["LockingTree", "lock", "unlock", "unlock", "lock", "upgrade", "lock"]
 * [[[-1, 0, 0, 1, 1, 2, 2]], [2, 2], [2, 3], [2, 2], [4, 5], [0, 1], [0, 1]]
 * Output
 * [null, true, false, true, true, true, false]
 *
 * Explanation
 * LockingTree lockingTree = new LockingTree([-1, 0, 0, 1, 1, 2, 2]);
 * lockingTree.lock(2, 2);    // return true because node 2 is unlocked.
 * // Node 2 will now be locked by user 2.
 * lockingTree.unlock(2, 3);  // return false because user 3 cannot unlock a node locked by user 2.
 * lockingTree.unlock(2, 2);  // return true because node 2 was previously locked by user 2.
 * // Node 2 will now be unlocked.
 * lockingTree.lock(4, 5);    // return true because node 4 is unlocked.
 * // Node 4 will now be locked by user 5.
 * lockingTree.upgrade(0, 1); // return true because node 0 is unlocked and has at least one locked descendant (node
 * 4).
 * // Node 0 will now be locked by user 1 and node 4 will now be unlocked.
 * lockingTree.lock(0, 1);    // return false because node 0 is already locked.
 *
 * Constraints:
 *
 * n == parent.length
 * 2 <= n <= 2000
 * 0 <= parent[i] <= n - 1 for i != 0
 * parent[0] == -1
 * 0 <= num <= n - 1
 * 1 <= user <= 10^4
 * parent represents a valid tree.
 * At most 2000 calls in total will be made to lock, unlock, and upgrade.
 */
public class Q1993_OperationsOnTree {

    private static class LockingTree {

        static class Node {

            Node parent;

            List<Node> children = new ArrayList<>();

            // 加锁的用户, 0 表示没有锁
            int user;

            // 子节点的锁的数量
            int descendantLocks;
        }

        final Node[] nodes;

        public LockingTree(int[] parent) {
            final int n = parent.length;
            nodes = new Node[n];
            for (int i = 0; i < n; i++) {
                nodes[i] = new Node();
            }
            for (int i = 1; i < n; i++) {
                nodes[i].parent = nodes[parent[i]];
                nodes[parent[i]].children.add(nodes[i]);
            }
        }

        public boolean lock(int num, int user) {
            Node node = nodes[num];
            if (node.user != 0) {
                return false;
            }
            node.user = user;
            node = node.parent;
            while (node != null) {
                node.descendantLocks++;
                node = node.parent;
            }
            return true;
        }

        public boolean unlock(int num, int user) {
            Node node = nodes[num];
            if (node.user != user) {
                return false;
            }
            node.user = 0;
            node = node.parent;
            while (node != null) {
                node.descendantLocks--;
                node = node.parent;
            }
            return true;
        }

        public boolean upgrade(int num, int user) {
            Node node = nodes[num];
            if (node.user != 0 || node.descendantLocks == 0) {
                return false;
            }
            for (Node p = node.parent; p != null; p = p.parent) {
                if (p.user != 0) {
                    return false;
                }
            }
            node.user = user;
            for (Node p = node.parent; p != null; p = p.parent) {
                p.descendantLocks -= node.descendantLocks - 1;
            }
            Queue<Node> stack = new ArrayDeque<>();
            stack.addAll(node.children);
            while (!stack.isEmpty()) {
                Node curr = stack.poll();
                curr.user = 0;
                if (curr.descendantLocks != 0) {
                    stack.addAll(curr.children);
                }
                curr.descendantLocks = 0;
            }
            return true;
        }
    }

    /**
     * Your LockingTree object will be instantiated and called as such:
     * LockingTree obj = new LockingTree(parent);
     * boolean param_1 = obj.lock(num,user);
     * boolean param_2 = obj.unlock(num,user);
     * boolean param_3 = obj.upgrade(num,user);
     */

    @Test
    public void testMethod() {
        LockingTree lockingTree = new LockingTree(new int[]{-1, 0, 0, 1, 1, 2, 2});
        Assert.assertTrue(lockingTree.lock(2, 2));
        Assert.assertFalse(lockingTree.unlock(2, 3));
        Assert.assertTrue(lockingTree.unlock(2, 2));
        Assert.assertTrue(lockingTree.lock(4, 5));
        Assert.assertTrue(lockingTree.upgrade(0, 1));
        Assert.assertFalse(lockingTree.lock(0, 1));
    }

}
