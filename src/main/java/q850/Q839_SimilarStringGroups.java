package q850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/similar-string-groups/
 *
 * Two strings X and Y are similar if we can swap two letters (in different positions) of X, so that it equals Y.
 * Also two strings X and Y are similar if they are equal.
 *
 * For example, "tars" and "rats" are similar (swapping at positions 0 and 2), and "rats" and "arts" are similar, but
 * "star" is not similar to "tars", "rats", or "arts".
 *
 * Together, these form two connected groups by similarity: {"tars", "rats", "arts"} and {"star"}.  Notice that
 * "tars" and "arts" are in the same group even though they are not similar.  Formally, each group is such that a
 * word is in the group if and only if it is similar to at least one other word in the group.
 *
 * We are given a list A of strings.  Every string in A is an anagram of every other string in A.  How many groups
 * are there?
 *
 *
 *
 * Example 1:
 *
 * Input: A = ["tars","rats","arts","star"]
 * Output: 2
 *
 *
 *
 * Constraints:
 *
 * 1 <= A.length <= 2000
 * 1 <= A[i].length <= 1000
 * A.length * A[i].length <= 20000
 * All words in A consist of lowercase letters only.
 * All words in A have the same length and are anagrams of each other.
 * The judging time limit has been increased for this question.
 */
@RunWith(LeetCodeRunner.class)
public class Q839_SimilarStringGroups {

    @Answer
    public int numSimilarGroups(String[] A) {
        final int n = A.length;
        int[] parents = new int[n];
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (find(parents, i) != find(parents, j)
                        && isSimilar(A[i], A[j])) {
                    parents[find(parents, i)] = find(parents, j);
                }
            }
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            if (i == find(parents, i)) {
                res++;
            }
        }
        return res;
    }

    private boolean isSimilar(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }
        int diff1 = -1, diff2 = -1;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                if (diff1 == -1) {
                    diff1 = i;
                } else if (diff2 == -1) {
                    diff2 = i;
                } else {
                    return false;
                }
            }
        }
        if (diff1 == -1) {
            return true;
        }
        if (diff2 == -1) {
            return false;
        }
        return a.charAt(diff1) == b.charAt(diff2)
                && a.charAt(diff2) == b.charAt(diff1);
    }

    private int find(int[] parents, int i) {
        if (parents[i] == i) {
            return i;
        }
        return parents[i] = find(parents, parents[i]);
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new String[]{"tars", "rats", "arts", "star"}).expect(2);

}
