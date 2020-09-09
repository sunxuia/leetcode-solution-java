package q1050;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * [Hard] 1032. Stream of Characters
 * https://leetcode.com/problems/stream-of-characters/
 *
 * Implement the StreamChecker class as follows:
 *
 * StreamChecker(words): Constructor, init the data structure with the given words.
 * query(letter): returns true if and only if for some k >= 1, the last k characters queried (in order from oldest to
 * newest, including this letter just queried) spell one of the words in the given list.
 *
 * Example:
 *
 * StreamChecker streamChecker = new StreamChecker(["cd","f","kl"]); // init the dictionary.
 * streamChecker.query('a');          // return false
 * streamChecker.query('b');          // return false
 * streamChecker.query('c');          // return false
 * streamChecker.query('d');          // return true, because 'cd' is in the wordlist
 * streamChecker.query('e');          // return false
 * streamChecker.query('f');          // return true, because 'f' is in the wordlist
 * streamChecker.query('g');          // return false
 * streamChecker.query('h');          // return false
 * streamChecker.query('i');          // return false
 * streamChecker.query('j');          // return false
 * streamChecker.query('k');          // return false
 * streamChecker.query('l');          // return true, because 'kl' is in the wordlist
 *
 * Note:
 *
 * 1 <= words.length <= 2000
 * 1 <= words[i].length <= 2000
 * Words will only consist of lowercase English letters.
 * Queries will only consist of lowercase English letters.
 * The number of queries is at most 40000.
 */
public class Q1032_StreamOfCharacters {

    private static class StreamChecker {

        private static class TrieNode {

            boolean isWord;

            TrieNode[] children = new TrieNode[26];
        }

        final TrieNode root = new TrieNode();

        final List<Integer> queries = new ArrayList<>();

        public StreamChecker(String[] words) {
            for (String word : words) {
                TrieNode node = root;
                for (int i = word.length() - 1; i >= 0; i--) {
                    int index = word.charAt(i) - 'a';
                    if (node.children[index] == null) {
                        node.children[index] = new TrieNode();
                    }
                    node = node.children[index];
                }
                node.isWord = true;
            }
        }

        public boolean query(char letter) {
            queries.add(letter - 'a');
            TrieNode node = root;
            for (int i = queries.size() - 1; i >= 0; i--) {
                node = node.children[queries.get(i)];
                if (node == null) {
                    return false;
                }
                if (node.isWord) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Your StreamChecker object will be instantiated and called as such:
     * StreamChecker obj = new StreamChecker(words);
     * boolean param_1 = obj.query(letter);
     */

    @Test
    public void testMethod() {
        StreamChecker streamChecker = new StreamChecker(new String[]{"cd", "f", "kl"});
        Assert.assertFalse(streamChecker.query('a'));
        Assert.assertFalse(streamChecker.query('b'));
        Assert.assertFalse(streamChecker.query('c'));
        Assert.assertTrue(streamChecker.query('d'));
        Assert.assertFalse(streamChecker.query('e'));
        Assert.assertTrue(streamChecker.query('f'));
        Assert.assertFalse(streamChecker.query('g'));
        Assert.assertFalse(streamChecker.query('h'));
        Assert.assertFalse(streamChecker.query('i'));
        Assert.assertFalse(streamChecker.query('j'));
        Assert.assertFalse(streamChecker.query('k'));
        Assert.assertTrue(streamChecker.query('l'));
    }

}
