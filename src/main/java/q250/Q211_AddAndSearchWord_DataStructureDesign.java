package q250;

import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/add-and-search-word-data-structure-design/
 *
 * Design a data structure that supports the following two operations:
 *
 * void addWord(word)
 * bool search(word)
 *
 * search(word) can search a literal word or a regular expression string containing only letters a-z or .. A . means
 * it can represent any one letter.
 *
 * Example:
 *
 * addWord("bad")
 * addWord("dad")
 * addWord("mad")
 * search("pad") -> false
 * search("bad") -> true
 * search(".ad") -> true
 * search("b..") -> true
 *
 * Note:
 * You may assume that all words are consist of lowercase letters a-z.
 */
public class Q211_AddAndSearchWord_DataStructureDesign {

    private static class WordDictionary {

        private boolean isWord;

        private WordDictionary[] children = new WordDictionary[26];

        /**
         * Initialize your data structure here.
         */
        public WordDictionary() {

        }

        /**
         * Adds a word into the data structure.
         */
        public void addWord(String word) {
            addWord(word, 0, this);
        }

        private void addWord(String word, int wi, WordDictionary dic) {
            if (wi == word.length()) {
                dic.isWord = true;
                return;
            }
            int index = word.charAt(wi) - 'a';
            if (dic.children[index] == null) {
                dic.children[index] = new WordDictionary();
            }
            addWord(word, wi + 1, dic.children[index]);
        }

        /**
         * Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one
         * letter.
         */
        public boolean search(String word) {
            return search(word, 0, this);
        }

        private boolean search(String word, int wi, WordDictionary dic) {
            if (dic == null) {
                return false;
            }
            if (wi == word.length()) {
                return dic.isWord;
            }
            char c = word.charAt(wi);
            if (c == '.') {
                for (int i = 0; i < 26; i++) {
                    if (search(word, wi + 1, dic.children[i])) {
                        return true;
                    }
                }
                return false;
            } else {
                return search(word, wi + 1, dic.children[c - 'a']);
            }
        }
    }


    @Test
    public void testMethod() {
        WordDictionary dic = new WordDictionary();
        dic.addWord("bad");
        dic.addWord("dad");
        dic.addWord("mad");
        Assert.assertFalse(dic.search("ba"));
        Assert.assertFalse(dic.search("pad"));
        Assert.assertTrue(dic.search("bad"));
        Assert.assertTrue(dic.search(".ad"));
        Assert.assertTrue(dic.search("b.."));
    }

}
