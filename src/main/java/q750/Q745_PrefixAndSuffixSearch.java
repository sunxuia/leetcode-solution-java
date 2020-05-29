package q750;

import java.util.Arrays;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.ClassDataExpectation;
import util.runner.data.ClassDataExpectationBuilder;

/**
 * https://leetcode.com/problems/prefix-and-suffix-search/
 *
 * Given many words, words[i] has weight i.
 *
 * Design a class WordFilter that supports one function, WordFilter.f(String prefix, String suffix). It will return
 * the word with given prefix and suffix with maximum weight. If no word exists, return -1.
 *
 * Examples:
 *
 * Input:
 * WordFilter(["apple"])
 * WordFilter.f("a", "e") // returns 0
 * WordFilter.f("b", "") // returns -1
 *
 *
 *
 * Note:
 *
 * 1. words has length in range [1, 15000].
 * 2. For each test case, up to words.length queries WordFilter.f may be made.
 * 3. words[i] has length in range [1, 10].
 * 4. prefix, suffix have lengths in range [0, 10].
 * 5. words[i] and prefix, suffix queries consist of lowercase letters only.
 */
@RunWith(LeetCodeRunner.class)
public class Q745_PrefixAndSuffixSearch {

    @Answer
    public static class WordFilter {

        WordDictionary prefixDict, suffixDict;

        public WordFilter(String[] words) {
            final int n = words.length;
            prefixDict = new WordDictionary(n);
            suffixDict = new WordDictionary(n);
            Arrays.fill(prefixDict.words, true);
            Arrays.fill(suffixDict.words, true);

            for (int i = 0; i < n; i++) {
                char[] wc = words[i].toCharArray();

                WordDictionary dict = prefixDict;
                for (int j = 0; j < wc.length; j++) {
                    int idx = wc[j] - 'a';
                    if (dict.children[idx] == null) {
                        dict.children[idx] = new WordDictionary(n);
                    }
                    dict = dict.children[idx];
                    dict.words[i] = true;
                }

                dict = suffixDict;
                for (int j = wc.length - 1; j >= 0; j--) {
                    int idx = wc[j] - 'a';
                    if (dict.children[idx] == null) {
                        dict.children[idx] = new WordDictionary(n);
                    }
                    dict = dict.children[idx];
                    dict.words[i] = true;
                }
            }
        }

        public int f(String prefix, String suffix) {
            WordDictionary p = prefixDict;
            for (int i = 0; i < prefix.length() && p != null; i++) {
                p = p.children[prefix.charAt(i) - 'a'];
            }
            WordDictionary s = suffixDict;
            for (int i = suffix.length() - 1; i >= 0 && s != null; i--) {
                s = s.children[suffix.charAt(i) - 'a'];
            }
            if (p == null || s == null) {
                return -1;
            }

            for (int i = p.words.length - 1; i >= 0; i--) {
                if (p.words[i] && s.words[i]) {
                    return i;
                }
            }
            return -1;
        }

        static class WordDictionary {

            public WordDictionary(int wordLen) {
                this.words = new boolean[wordLen];
            }

            boolean[] words;

            WordDictionary[] children = new WordDictionary[26];
        }
    }

    @Test
    public void example() {
        WordFilter filter = new WordFilter(new String[]{"apple"});
        Assert.assertEquals(0, filter.f("a", "e"));
        Assert.assertEquals(-1, filter.f("b", ""));
    }

    @TestData
    public Map<String, ClassDataExpectation> normal = ClassDataExpectationBuilder
            .readFromFile(WordFilter.class, "Q745_TestData");

}
