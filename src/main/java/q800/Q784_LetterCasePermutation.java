package q800;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/letter-case-permutation/
 *
 * Given a string S, we can transform every letter individually to be lowercase or uppercase to create another
 * string. Return a list of all possible strings we could create.
 *
 * Examples:
 * Input: S = "a1b2"
 * Output: ["a1b2", "a1B2", "A1b2", "A1B2"]
 *
 * Input: S = "3z4"
 * Output: ["3z4", "3Z4"]
 *
 * Input: S = "12345"
 * Output: ["12345"]
 *
 * Note:
 *
 * S will be a string with length between 1 and 12.
 * S will consist only of letters or digits.
 */
@RunWith(LeetCodeRunner.class)
public class Q784_LetterCasePermutation {

    @Answer
    public List<String> letterCasePermutation(String S) {
        char[] sc = S.toCharArray();
        List<String> res = new ArrayList<>();
        dfs(res, sc, 0);
        return res;
    }

    private void dfs(List<String> res, char[] sc, int i) {
        if (i == sc.length) {
            res.add(new String(sc));
            return;
        }
        dfs(res, sc, i + 1);
        char c = sc[i];
        if (Character.isLowerCase(c)) {
            sc[i] = Character.toUpperCase(c);
            dfs(res, sc, i + 1);
        } else if (Character.isUpperCase(c)) {
            sc[i] = Character.toLowerCase(c);
            dfs(res, sc, i + 1);
        }
        sc[i] = c;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("a1b2").expect(Arrays.asList("a1b2", "a1B2", "A1b2", "A1B2"));

    @TestData
    public DataExpectation example2 = DataExpectation.create("3z4").expect(Arrays.asList("3z4", "3Z4"));

    @TestData
    public DataExpectation example3 = DataExpectation.create("12345").expect(Collections.singletonList("12345"));

}
