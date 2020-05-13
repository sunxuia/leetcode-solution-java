package q700;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/implement-magic-dictionary/
 *
 * Implement a magic directory with buildDict, and search methods.
 *
 * For the method buildDict, you'll be given a list of non-repetitive words to build a dictionary.
 *
 * For the method search, you'll be given a word, and judge whether if you modify exactly one character into another
 * character in this word, the modified word is in the dictionary you just built.
 *
 * Example 1:
 *
 * Input: buildDict(["hello", "leetcode"]), Output: Null
 * Input: search("hello"), Output: False
 * Input: search("hhllo"), Output: True
 * Input: search("hell"), Output: False
 * Input: search("leetcoded"), Output: False
 *
 * Note:
 *
 * 1. You may assume that all the inputs are consist of lowercase letters a-z.
 * 2. For contest purpose, the test data is rather small by now. You could think about highly efficient algorithm
 * after the contest.
 * 3. Please remember to RESET your class variables declared in class MagicDictionary, as static/class variables are
 * persisted across multiple test cases. Please see here for more details.
 */
public class Q676_ImplementMagicDictionary {

    private static class MagicDictionary {

        /**
         * Initialize your data structure here.
         */
        public MagicDictionary() {

        }

        private Node root = new Node();

        /**
         * Build a dictionary through a list of words
         */
        public void buildDict(String[] dict) {
            Arrays.fill(root.children, null);
            for (String str : dict) {
                Node node = root;
                for (int i = 0; i < str.length(); i++) {
                    int idx = str.charAt(i) - 'a';
                    if (node.children[idx] == null) {
                        node.children[idx] = new Node();
                    }
                    node = node.children[idx];
                }
                node.isEnd = true;
            }
        }

        /**
         * Returns if there is any word in the trie that equals to the given word after modifying exactly one character
         */
        public boolean search(String word) {
            int[] arr = new int[word.length()];
            for (int i = 0; i < word.length(); i++) {
                arr[i] = word.charAt(i) - 'a';
            }
            return search(arr, 0, root, true);
        }

        /**
         * dfs 搜索
         *
         * @param arr 要搜索的数组
         * @param idx 目前搜索到的下标
         * @param parent 之前的节点
         * @param toleration 是否已经有一个不同了
         * @return 是否匹配成功
         */
        private boolean search(int[] arr, int idx, Node parent, boolean toleration) {
            if (idx == arr.length) {
                return parent.isEnd && !toleration;
            }
            Node node = parent.children[arr[idx]];
            if (node != null && search(arr, idx + 1, node, toleration)) {
                return true;
            }
            if (!toleration) {
                return false;
            }
            for (Node child : parent.children) {
                if (child != null && child != node
                        && search(arr, idx + 1, child, false)) {
                    return true;
                }
            }
            return false;
        }

        private static class Node {

            boolean isEnd;

            private Node[] children = new Node[26];

        }
    }

    @Test
    public void testMethod() {
        MagicDictionary dict = new MagicDictionary();

        dict.buildDict(new String[]{"hello", "leetcode"});
        Assert.assertFalse(dict.search("hello"));
        Assert.assertTrue(dict.search("hhllo"));
        Assert.assertFalse(dict.search("hell"));
        Assert.assertFalse(dict.search("leetcoded"));

        dict.buildDict(new String[]{"hello", "hallo", "leetcode"});
        Assert.assertTrue(dict.search("hello"));
        Assert.assertTrue(dict.search("hhllo"));
        Assert.assertFalse(dict.search("hell"));
        Assert.assertFalse(dict.search("leetcoded"));
    }

}
