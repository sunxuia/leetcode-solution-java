package util.provided;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class TreeNode {

    public TreeNode left, right;
    public int val;

    public TreeNode(int x) {
        val = x;
    }

    @Override
    public int hashCode() {
        int res = val;
        if (left != null) {
            res += left.hashCode();
        }
        if (right != null) {
            res += right.hashCode();
        }
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TreeNode)) {
            return false;
        }
        TreeNode other = (TreeNode) obj;
        if (val != other.val) {
            return false;
        }
        if (!Objects.equals(left, other.left)) {
            return false;
        }
        if (!Objects.equals(right, other.right)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (left == null && right == null) {
            return String.format("<%d>", val);
        }
        return String.format("{%d: %s, %s}", val, left == null ? "x" : left, right == null ? "x" : right);
    }

    public int[] inOrderTraversal() {
        List<Integer> list = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        Deque<Boolean> visited = new ArrayDeque<>();
        stack.push(this);
        visited.push(false);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            Boolean visit = visited.pop();
            if (visit) {
                list.add(node.val);
                if (node.right != null) {
                    stack.push(node.right);
                    visited.push(false);
                }
            } else {
                stack.push(node);
                visited.push(true);

                if (node.left != null) {
                    stack.push(node.left);
                    visited.push(false);
                }
            }
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    /**
     * Create Tree from pre-order traversal, set null if no node in traversal path.
     *
     * @param nums node values
     * @return root node
     */
    public static TreeNode createByPreOrderTraversal(Integer... nums) {
        return createByPreOrderTraversal(nums, new int[]{0});
    }

    private static TreeNode createByPreOrderTraversal(Integer[] nums, int[] indexHolder) {
        if (indexHolder[0] >= nums.length || nums[indexHolder[0]] == null) {
            indexHolder[0]++;
            return null;
        }
        TreeNode node = new TreeNode(nums[indexHolder[0]++]);
        node.left = createByPreOrderTraversal(nums, indexHolder);
        node.right = createByPreOrderTraversal(nums, indexHolder);
        return node;
    }

    /**
     * leetcode 上的书写方式, 每层行每行层的遍历.
     */
    public static TreeNode createByLevel(Integer... nums) {
        if (nums.length == 0 || nums[0] == null) {
            return null;
        }
        TreeNode root = new TreeNode(nums[0]);
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        for (int i = 1; i < nums.length; i += 2) {
            TreeNode parent = queue.remove();
            if (nums[i] != null) {
                parent.left = new TreeNode(nums[i]);
                queue.add(parent.left);
            }
            if (i + 1 < nums.length && nums[i + 1] != null) {
                parent.right = new TreeNode(nums[i + 1]);
                queue.add(parent.right);
            }
        }
        return root;
    }
}
