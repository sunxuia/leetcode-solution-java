package q450;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import util.provided.TreeNode;

/**
 * https://leetcode.com/problems/serialize-and-deserialize-bst/
 *
 * Serialization is the process of converting a data structure or object into a sequence of bits so that it can be
 * stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in
 * the same or another computer environment.
 *
 * Design an algorithm to serialize and deserialize a binary search tree. There is no restriction on how your
 * serialization/deserialization algorithm should work. You just need to ensure that a binary search tree can be
 * serialized to a string and this string can be deserialized to the original tree structure.
 *
 * The encoded string should be as compact as possible.
 *
 * Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms
 * should be stateless.
 */
public class Q449_SerializeAndDeserializeBST {

    private static class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            preorderDfs(sb, root);
            return sb.toString();
        }

        private void preorderDfs(StringBuilder sb, TreeNode node) {
            if (node == null) {
                return;
            }
            sb.append(node.val).append(',');
            preorderDfs(sb, node.left);
            preorderDfs(sb, node.right);
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            List<Integer> list = new ArrayList<>();
            for (int i = 0, sign = 1, value = 0; i < data.length(); i++) {
                char c = data.charAt(i);
                if (c == '-') {
                    sign = -1;
                } else if (c == ',') {
                    list.add(sign * value);
                    sign = 1;
                    value = 0;
                } else {
                    value = value * 10 + c - '0';
                }
            }

            return dfs(list, 0, list.size() - 1);
        }

        private TreeNode dfs(List<Integer> list, int start, int end) {
            if (start > end) {
                return null;
            }
            TreeNode node = new TreeNode(list.get(start));
            int i = start + 1;
            while (i <= end && list.get(start) > list.get(i)) {
                i++;
            }
            node.left = dfs(list, start + 1, i - 1);
            node.right = dfs(list, i, end);
            return node;
        }
    }

    @Test
    public void testMethod() {
        Codec codec = new Codec();

        TreeNode expect = new TreeNode(10);
        expect.left = new TreeNode(5);
        expect.right = new TreeNode(15);
        TreeNode actual = codec.deserialize(codec.serialize(expect));
        Assert.assertEquals(expect, actual);

        Assert.assertNull(codec.deserialize(codec.serialize(null)));
    }

}
