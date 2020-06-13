package q850;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/find-and-replace-in-string/
 *
 * To some string S, we will perform some replacement operations that replace groups of letters with new ones (not
 * necessarily the same size).
 *
 * Each replacement operation has 3 parameters: a starting index i, a source word x and a target word y.  The rule is
 * that if x starts at position i in the original string S, then we will replace that occurrence of x with y.  If
 * not, we do nothing.
 *
 * For example, if we have S = "abcd" and we have some replacement operation i = 2, x = "cd", y = "ffff", then
 * because "cd" starts at position 2 in the original string S, we will replace it with "ffff".
 *
 * Using another example on S = "abcd", if we have both the replacement operation i = 0, x = "ab", y = "eee", as well
 * as another replacement operation i = 2, x = "ec", y = "ffff", this second operation does nothing because in the
 * original string S[2] = 'c', which doesn't match x[0] = 'e'.
 *
 * All these operations occur simultaneously.  It's guaranteed that there won't be any overlap in replacement: for
 * example, S = "abc", indexes = [0, 1], sources = ["ab","bc"] is not a valid test case.
 *
 * Example 1:
 *
 * Input: S = "abcd", indexes = [0,2], sources = ["a","cd"], targets = ["eee","ffff"]
 * Output: "eeebffff"
 * Explanation: "a" starts at index 0 in S, so it's replaced by "eee".
 * "cd" starts at index 2 in S, so it's replaced by "ffff".
 *
 * Example 2:
 *
 * Input: S = "abcd", indexes = [0,2], sources = ["ab","ec"], targets = ["eee","ffff"]
 * Output: "eeecd"
 * Explanation: "ab" starts at index 0 in S, so it's replaced by "eee".
 * "ec" doesn't starts at index 2 in the original S, so we do nothing.
 *
 * Notes:
 *
 * 0 <= indexes.length = sources.length = targets.length <= 100
 * 0 < indexes[i] < S.length <= 1000
 * All characters in given inputs are lowercase letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q833_FindAndReplaceInString {

    @Answer
    public String findReplaceString(String S, int[] indexes, String[] sources, String[] targets) {
        final int n = indexes.length;
        Integer[] sort = new Integer[n];
        for (int i = 0; i < n; i++) {
            sort[i] = i;
        }
        Arrays.sort(sort, Comparator.comparingInt(i -> indexes[i]));

        StringBuilder sb = new StringBuilder();
        int last = 0;
        for (int i = 0; i < n; i++) {
            int idx = sort[i];
            if (S.startsWith(sources[idx], indexes[idx])) {
                sb.append(S, last, indexes[idx]);
                sb.append(targets[idx]);
                last = indexes[idx] + sources[idx].length();
            }
        }
        sb.append(S, last, S.length());
        return sb.toString();
    }

    // 针对上面的改进, 使用map 来避免给indexes 排序
    @Answer
    public String findReplaceString2(String S, int[] indexes, String[] sources, String[] targets) {
        final int n = indexes.length;
        Map<Integer, Integer> map = new HashMap<>(n);
        for (int i = 0; i < n; i++) {
            map.put(indexes[i], i);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < S.length(); i++) {
            if (map.containsKey(i)) {
                int idx = map.get(i);
                if (S.startsWith(sources[idx], i)) {
                    sb.append(targets[idx]);
                    i += sources[idx].length() - 1;
                    continue;
                }
            }
            sb.append(S.charAt(i));
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("abcd",
            new int[]{0, 2},
            new String[]{"a", "cd"},
            new String[]{"eee", "ffff"}
    ).expect("eeebffff");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("abcd",
            new int[]{0, 2},
            new String[]{"ab", "ec"},
            new String[]{"eee", "ffff"}
    ).expect("eeecd");

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("vmokgggqzp",
            new int[]{3, 5, 1},
            new String[]{"kg", "ggq", "mo"},
            new String[]{"s", "so", "bfr"}
    ).expect("vbfrssozp");

}
