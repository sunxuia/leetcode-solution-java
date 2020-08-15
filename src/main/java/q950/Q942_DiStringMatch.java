package q950;

import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 942. DI String Match
 * https://leetcode.com/problems/di-string-match/
 *
 * Given a string S that only contains "I" (increase) or "D" (decrease), let N = S.length.
 *
 * Return any permutation A of [0, 1, ..., N] such that for all i = 0, ..., N-1:
 *
 * If S[i] == "I", then A[i] < A[i+1]
 * If S[i] == "D", then A[i] > A[i+1]
 *
 * Example 1:
 *
 * Input: "IDID"
 * Output: [0,4,1,3,2]
 *
 * Example 2:
 *
 * Input: "III"
 * Output: [0,1,2,3]
 *
 * Example 3:
 *
 * Input: "DDI"
 * Output: [3,2,0,1]
 *
 * Note:
 *
 * 1 <= S.length <= 10000
 * S only contains characters "I" or "D".
 */
@RunWith(LeetCodeRunner.class)
public class Q942_DiStringMatch {

    @TestData
    public DataExpectation example1 = createDataExpectation("IDID");
    @TestData
    public DataExpectation example2 = createDataExpectation("III");
    @TestData
    public DataExpectation example3 = createDataExpectation("DDI");

    @Answer
    public int[] diStringMatch(String S) {
        final int n = S.length();
        Set<Integer> existed = new HashSet<>(n);
        existed.add(0);
        int[] res = new int[n + 1];
        int curr = 0;
        for (int i = 0; i < S.length(); i++) {
            int offset = S.charAt(i) == 'I' ? 1 : -1;
            while (!existed.add(curr)) {
                curr += offset;
            }
            res[i + 1] = curr;
        }

        int offset = 0;
        for (int i = 0; i <= n; i++) {
            offset = Math.max(offset, -res[i]);
        }
        for (int i = 0; i <= n; i++) {
            res[i] += offset;
        }
        return res;
    }

    // LeetCode 上比较快的方式
    @Answer
    public int[] diStringMatch2(String S) {
        final int n = S.length();
        int[] res = new int[n + 1];
        int left = 0, right = n;

        for (int i = 0; i < n; i++) {
            res[i] = S.charAt(i) == 'I' ? left++ : right--;
        }
        res[n] = left;
        return res;
    }

    private DataExpectation createDataExpectation(String s) {
        return DataExpectation.builder()
                .addArgument(s)
                .expect(s)
                .assertMethod((int[] actual) -> {
                    final int n = s.length();
                    Assert.assertEquals(n + 1, actual.length);

                    boolean[] visited = new boolean[n + 1];
                    for (int i = 0; i <= n; i++) {
                        Assert.assertTrue(0 <= actual[i] && actual[i] <= n);
                        Assert.assertFalse(visited[actual[i]]);
                        visited[actual[i]] = true;
                    }

                    for (int i = 0; i < n; i++) {
                        if (s.charAt(i) == 'I') {
                            Assert.assertTrue(actual[i] < actual[i + 1]);
                        } else {
                            Assert.assertTrue(actual[i] > actual[i + 1]);
                        }
                    }
                }).build();
    }

}
