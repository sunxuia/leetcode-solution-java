package q050;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/substring-with-concatenation-of-all-words/
 *
 * You are given a string, s, and a list of words, words, that are all of the same length. Find all starting indices
 * of substring(s) in s that is a concatenation of each word in words exactly once and without any intervening
 * characters.
 *
 * Example 1:
 *
 * Input:
 * s = "barfoothefoobarman",
 * words = ["foo","bar"]
 * Output: [0,9]
 * Explanation: Substrings starting at index 0 and 9 are "barfoor" and "foobar" respectively.
 * The output order does not matter, returning [9,0] is fine too.
 * Example 2:
 *
 * Input:
 * s = "wordgoodgoodgoodbestword",
 * words = ["word","good","best","word"]
 * Output: []
 *
 * 题解:
 * 找出字符串s 中符合由words (所有字符串长度相同) 中所有字符串排列组合而成的子字符串的下标.
 */
@RunWith(LeetCodeRunner.class)
public class Q030_SubstringWithConcatenationOfAllWords {

    /**
     * 切割字符串, 然后判断子字符串是否存在于words 中.
     */
    @Answer
    public List<Integer> findSubstring(String s, String[] words) {
        if (words.length == 0) {
            return Collections.emptyList();
        }
        final int width = words[0].length();
        if (s.length() < width || width == 0) {
            return Collections.emptyList();
        }

        HashMap<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i <= s.length() - width * words.length; i++) {
            Map<String, Integer> subMap = map;
            for (int j = i; j <= s.length() - width; j += width) {
                String sub = s.substring(j, j + width);
                Integer times = subMap.get(sub);
                if (times == null) {
                    break;
                }
                if (subMap == map) {
                    subMap = (Map<String, Integer>) map.clone();
                }
                if (times == 1) {
                    subMap.remove(sub);
                } else {
                    subMap.put(sub, times - 1);
                }
            }
            if (subMap.isEmpty()) {
                res.add(i);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("barfoothefoobarman")
            .addArgument(new String[]{"foo", "bar"})
            .expect(new int[]{0, 9})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("wordgoodgoodgoodbestword")
            .addArgument(new String[]{"word", "good", "best", "word"})
            .expect(new int[0])
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument("wordgoodgoodgoodbestword")
            .addArgument(new String[]{"word", "good", "best", "good"})
            .expect(new int[]{8})
            .unorderResult()
            .build();

}
