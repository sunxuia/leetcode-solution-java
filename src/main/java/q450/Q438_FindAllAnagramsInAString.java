package q450;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/find-all-anagrams-in-a-string/
 *
 * Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.
 *
 * Strings consists of lowercase English letters only and the length of both strings s and p will not be larger than
 * 20,100.
 *
 * The order of output does not matter.
 *
 * Example 1:
 *
 * Input:
 * s: "cbaebabacd" p: "abc"
 *
 * Output:
 * [0, 6]
 *
 * Explanation:
 * The substring with start index = 0 is "cba", which is an anagram of "abc".
 * The substring with start index = 6 is "bac", which is an anagram of "abc".
 *
 * Example 2:
 *
 * Input:
 * s: "abab" p: "ab"
 *
 * Output:
 * [0, 1, 2]
 *
 * Explanation:
 * The substring with start index = 0 is "ab", which is an anagram of "ab".
 * The substring with start index = 1 is "ba", which is an anagram of "ab".
 * The substring with start index = 2 is "ab", which is an anagram of "ab".
 */
@RunWith(LeetCodeRunner.class)
public class Q438_FindAllAnagramsInAString {

    @Answer
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        final int pLen = p.length(), sLen = s.length();
        if (pLen > sLen) {
            return res;
        }
        // LeetCode 中, 使用数组并比较26 位比使用Map 记录次数要快.
        int[] pc = new int[26];
        for (int i = 0; i < pLen; i++) {
            pc[p.charAt(i) - 'a']++;
        }
        int[] counts = new int[26];
        for (int i = 0; i < sLen; i++) {
            counts[s.charAt(i) - 'a']++;
            if (i >= pLen) {
                counts[s.charAt(i - pLen) - 'a']--;
            }
            if (i >= pLen - 1) {
                boolean allMatch = true;
                for (int j = 0; j < 26; j++) {
                    if (pc[j] != counts[j]) {
                        allMatch = false;
                        break;
                    }
                }
                if (allMatch) {
                    res.add(i - pLen + 1);
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("cbaebabacd")
            .addArgument("abc")
            .expect(Arrays.asList(0, 6))
            .unorderResult()
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("abab")
            .addArgument("ab")
            .expect(Arrays.asList(0, 1, 2))
            .unorderResult()
            .build();

}
