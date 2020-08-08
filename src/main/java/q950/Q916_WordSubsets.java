package q950;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 916. Word Subsets
 * https://leetcode.com/problems/word-subsets/
 *
 * We are given two arrays A and B of words.  Each word is a string of lowercase letters.
 *
 * Now, say that word b is a subset of word a if every letter in b occurs in a, including multiplicity.  For example,
 * "wrr" is a subset of "warrior", but is not a subset of "world".
 *
 * Now say a word a from A is universal if for every b in B, b is a subset of a.
 *
 * Return a list of all universal words in A.  You can return the words in any order.
 *
 * Example 1:
 *
 * Input: A = ["amazon","apple","facebook","google","leetcode"], B = ["e","o"]
 * Output: ["facebook","google","leetcode"]
 *
 * Example 2:
 *
 * Input: A = ["amazon","apple","facebook","google","leetcode"], B = ["l","e"]
 * Output: ["apple","google","leetcode"]
 *
 * Example 3:
 *
 * Input: A = ["amazon","apple","facebook","google","leetcode"], B = ["e","oo"]
 * Output: ["facebook","google"]
 *
 * Example 4:
 *
 * Input: A = ["amazon","apple","facebook","google","leetcode"], B = ["lo","eo"]
 * Output: ["google","leetcode"]
 *
 * Example 5:
 *
 * Input: A = ["amazon","apple","facebook","google","leetcode"], B = ["ec","oc","ceo"]
 * Output: ["facebook","leetcode"]
 *
 * Note:
 *
 * 1 <= A.length, B.length <= 10000
 * 1 <= A[i].length, B[i].length <= 10
 * A[i] and B[i] consist only of lowercase letters.
 * All words in A[i] are unique: there isn't i != j with A[i] == A[j].
 */
@RunWith(LeetCodeRunner.class)
public class Q916_WordSubsets {

    @Answer
    public List<String> wordSubsets(String[] A, String[] B) {
        int[] temp = new int[26];
        // 统计B 中每个字母的最大数量
        int[] counts = new int[26];
        for (String b : B) {
            Arrays.fill(temp, 0);
            for (int i = 0; i < b.length(); i++) {
                temp[b.charAt(i) - 'a']++;
            }
            for (int i = 0; i < 26; i++) {
                counts[i] = Math.max(temp[i], counts[i]);
            }
        }

        // 只有a 中每个字母的数量都 >= B 的最大数量才行
        List<String> res = new ArrayList<>();
        for (String a : A) {
            Arrays.fill(temp, 0);
            for (int i = 0; i < a.length(); i++) {
                temp[a.charAt(i) - 'a']++;
            }
            boolean pass = true;
            for (int i = 0; i < 26 && pass; i++) {
                pass = counts[i] <= temp[i];
            }
            if (pass) {
                res.add(a);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{"amazon", "apple", "facebook", "google", "leetcode"}, new String[]{"e", "o"})
            .expect(Arrays.asList("facebook", "google", "leetcode"));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{"amazon", "apple", "facebook", "google", "leetcode"}, new String[]{"l", "e"})
            .expect(Arrays.asList("apple", "google", "leetcode"));

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new String[]{"amazon", "apple", "facebook", "google", "leetcode"}, new String[]{"e", "oo"})
            .expect(Arrays.asList("facebook", "google"));

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new String[]{"amazon", "apple", "facebook", "google", "leetcode"}, new String[]{"lo", "eo"})
            .expect(Arrays.asList("google", "leetcode"));

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(new String[]{"amazon", "apple", "facebook", "google", "leetcode"},
                    new String[]{"ec", "oc", "ceo"})
            .expect(Arrays.asList("facebook", "leetcode"));

}
