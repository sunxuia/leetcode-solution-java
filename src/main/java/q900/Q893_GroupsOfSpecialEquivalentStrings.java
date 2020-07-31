package q900;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 893. Groups of Special-Equivalent Strings
 * https://leetcode.com/problems/groups-of-special-equivalent-strings/
 *
 * You are given an array A of strings.
 *
 * A move onto S consists of swapping any two even indexed characters of S, or any two odd indexed characters of S.
 *
 * Two strings S and T are special-equivalent if after any number of moves onto S, S == T.
 *
 * For example, S = "zzxy" and T = "xyzz" are special-equivalent because we may make the moves "zzxy" -> "xzzy" ->
 * "xyzz" that swap S[0] and S[2], then S[1] and S[3].
 *
 * Now, a group of special-equivalent strings from A is a non-empty subset of A such that:
 *
 * Every pair of strings in the group are special equivalent, and;
 * The group is the largest size possible (ie., there isn't a string S not in the group such that S is special
 * equivalent to every string in the group)
 *
 * Return the number of groups of special-equivalent strings from A.
 *
 * Example 1:
 *
 * Input: ["abcd","cdab","cbad","xyzz","zzxy","zzyx"]
 * Output: 3
 * Explanation:
 * One group is ["abcd", "cdab", "cbad"], since they are all pairwise special equivalent, and none of the other strings
 * are all pairwise special equivalent to these.
 *
 * The other two groups are ["xyzz", "zzxy"] and ["zzyx"].  Note that in particular, "zzxy" is not special equivalent to
 * "zzyx".
 *
 * Example 2:
 *
 * Input: ["abc","acb","bac","bca","cab","cba"]
 * Output: 3
 *
 * Note:
 *
 * 1 <= A.length <= 1000
 * 1 <= A[i].length <= 20
 * All A[i] have the same length.
 * All A[i] consist of only lowercase letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q893_GroupsOfSpecialEquivalentStrings {

    @Answer
    public int numSpecialEquivGroups(String[] A) {
        List<String> groups = new ArrayList<>();
        groups.add(A[0]);
        loop:
        for (int i = 1; i < A.length; i++) {
            for (String group : groups) {
                if (isSpecialEquivalent(A[i], group)) {
                    continue loop;
                }
            }
            groups.add(A[i]);
        }
        return groups.size();
    }

    private boolean isSpecialEquivalent(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }
        int[][] counts = new int[2][26];
        for (int i = 0; i < a.length(); i++) {
            counts[i % 2][a.charAt(i) - 'a']++;
            counts[i % 2][b.charAt(i) - 'a']--;
        }
        for (int i = 0; i < 26; i++) {
            if (counts[0][i] != 0 || counts[1][i] != 0) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new String[]{"abcd", "cdab", "cbad", "xyzz", "zzxy", "zzyx"})
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new String[]{"abc", "acb", "bac", "bca", "cab", "cba"})
            .expect(3);

}
