package q800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/custom-sort-string/
 *
 * S and T are strings composed of lowercase letters. In S, no letter occurs more than once.
 *
 * S was sorted in some custom order previously. We want to permute the characters of T so that they match the order
 * that S was sorted. More specifically, if x occurs before y in S, then x should occur before y in the returned string.
 *
 * Return any permutation of T (as a string) that satisfies this property.
 *
 * Example :
 * Input:
 * S = "cba"
 * T = "abcd"
 * Output: "cbad"
 * Explanation:
 * "a", "b", "c" appear in S, so the order of "a", "b", "c" should be "c", "b", and "a".
 * Since "d" does not appear in S, it can be at any position in T. "dcba", "cdba", "cbda" are also valid outputs.
 *
 *
 *
 * Note:
 *
 * S has length at most 26, and no character is repeated in S.
 * T has length at most 200.
 * S and T consist of lowercase letters only.
 */
@RunWith(LeetCodeRunner.class)
public class Q791_CustomSortString {

    @Answer
    public String customSortString(String S, String T) {
        int[] sort = new int[26];
        boolean[] visited = new boolean[26];
        int next = 0;
        for (int i = 0; i < S.length(); i++) {
            int idx = S.charAt(i) - 'a';
            if (!visited[idx]) {
                visited[idx] = true;
                sort[next++] = idx;
            }
        }
        for (int i = 0; i < 26; i++) {
            if (!visited[i]) {
                sort[next++] = i;
            }
        }

        int[] count = new int[26];
        for (int i = 0; i < T.length(); i++) {
            count[T.charAt(i) - 'a']++;
        }
        StringBuilder sb = new StringBuilder(T.length());
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < count[sort[i]]; j++) {
                sb.append((char) (sort[i] + 'a'));
            }
        }
        return sb.toString();
    }

    // 由于对不存在的字符, 题目并没有规定如何排序, 所以是可以不排序的,
    // 由此上面的解答可以优化为如下:
    @Answer
    public String customSortString2(String S, String T) {
        int[] count = new int[128];
        for (int i = 0; i < T.length(); i++) {
            count[T.charAt(i)]++;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            while (count[c]-- > 0) {
                sb.append(c);
            }
        }
        for (int i = 0; i < T.length(); i++) {
            char c = T.charAt(i);
            while (count[c]-- > 0) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith("cba", "abcd").expect("cbad");

}
