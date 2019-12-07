package q300;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.Assert;
import org.junit.Test;
import util.provided.TreeNode;

/**
 * https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
 *
 * Serialization is the process of converting a data structure or object into a sequence of bits so that it can be
 * stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in
 * the same or another computer environment.
 *
 * Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your
 * serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized
 * to a string and this string can be deserialized to the original tree structure.
 *
 * Example:
 *
 * You may serialize the following tree:
 *
 * >      1
 * >     / \
 * >    2   3
 * >       / \
 * >      4   5
 *
 * as "[1,2,3,null,null,4,5]"
 *
 * Clarification: The above format is the same as how LeetCode serializes a binary tree. You do not necessarily need
 * to follow this format, so please be creative and come up with different approaches yourself.
 *
 * Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms
 * should be stateless.
 */
public class Q297_SerializeAndDeserializeBinaryTree {

    private static class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            serializePreOrder(sb, root);
            return sb.toString();
        }

        private void serializePreOrder(StringBuilder sb, TreeNode node) {
            if (node == null) {
                sb.append("x,");
                return;
            }
            sb.append(node.val).append(',');
            serializePreOrder(sb, node.left);
            serializePreOrder(sb, node.right);
        }

        // 这个因为TreeNode 中的静态创建方法已经使用过递归了, 所以这题就使用循环来做了.
        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            Deque<TreeNode> stack = new ArrayDeque<>();
            TreeNode dummy = new TreeNode(0), prev = dummy;
            boolean switchRight = false;
            int num = 0, prod = 1;
            for (int i = 0; i < data.length(); i++) {
                char c = data.charAt(i);
                switch (c) {
                    case ',':
                        if (switchRight) {
                            prev.right = new TreeNode(num);
                            prev = prev.right;
                        } else {
                            stack.push(prev);
                            prev.left = new TreeNode(num);
                            prev = prev.left;
                        }
                        switchRight = false;
                        num = 0;
                        prod = 1;
                        break;
                    case 'x':
                        if (switchRight) {
                            prev = stack.pop();
                        } else {
                            switchRight = true;
                        }
                        i++;
                        break;
                    case '-':
                        prod = -1;
                        break;
                    default:
                        num = num * 10 + prod * (c - '0');
                }
            }
            return dummy.left;
        }
    }

    @Test
    public void testMethod() {
        TreeNode tree = TreeNode.createByLevel(1, 2, 3, null, null, 4, 5);
        Codec codec = new Codec();
        String str = codec.serialize(tree);
        TreeNode res = codec.deserialize(str);
        Assert.assertEquals(tree, res);

        tree = TreeNode.createByLevel(-1, 0, 1);
        codec = new Codec();
        str = codec.serialize(tree);
        res = codec.deserialize(str);
        Assert.assertEquals(tree, res);
    }

}
