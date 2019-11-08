package q250;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/implement-trie-prefix-tree/
 *
 * Implement a trie with insert, search, and startsWith methods.
 *
 * Example:
 *
 * Trie trie = new Trie();
 *
 * trie.insert("apple");
 * trie.search("apple");   // returns true
 * trie.search("app");     // returns false
 * trie.startsWith("app"); // returns true
 * trie.insert("app");
 * trie.search("app");     // returns true
 *
 * Note:
 *
 * You may assume that all inputs are consist of lowercase letters a-z.
 * All inputs are guaranteed to be non-empty strings.
 */
public class Q208_ImplementTrie_PrefixTree {

    private static class Trie {

        private static class Node {

            private boolean isWord;

            private Map<Character, Node> children = new HashMap<>();

        }

        private Node root = new Node();

        /**
         * Initialize your data structure here.
         */
        public Trie() {

        }

        /**
         * Inserts a word into the trie.
         */
        public void insert(String word) {
            Node node = root;
            for (int i = 0; i < word.length(); i++) {
                node = node.children.computeIfAbsent(word.charAt(i), key -> new Node());
            }
            node.isWord = true;
        }

        /**
         * Returns if the word is in the trie.
         */
        public boolean search(String word) {
            Node node = root;
            for (int i = 0; i < word.length() && node != null; i++) {
                node = node.children.get(word.charAt(i));
            }
            return node != null && node.isWord;
        }

        /**
         * Returns if there is any word in the trie that starts with the given prefix.
         */
        public boolean startsWith(String prefix) {
            Node node = root;
            for (int i = 0; i < prefix.length() && node != null; i++) {
                node = node.children.get(prefix.charAt(i));
            }
            return node != null;
        }
    }

    @Test
    public void testMethod() {
        Trie trie = new Trie();
        trie.insert("apple");
        Assert.assertTrue(trie.search("apple"));
        Assert.assertFalse(trie.search("app"));
        Assert.assertTrue(trie.startsWith("app"));
        trie.insert("app");
        Assert.assertTrue(trie.search("app"));
    }


}
