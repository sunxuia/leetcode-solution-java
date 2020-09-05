package q1050;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1023. Camelcase Matching
 * https://leetcode.com/problems/camelcase-matching/
 *
 * A query word matches a given pattern if we can insert lowercase letters to the pattern word so that it equals the
 * query. (We may insert each character at any position, and may insert 0 characters.)
 *
 * Given a list of queries, and a pattern, return an answer list of booleans, where answer[i] is true if and only if
 * queries[i] matches the pattern.
 *
 * Example 1:
 *
 * Input: queries = ["FooBar","FooBarTest","FootBall","FrameBuffer","ForceFeedBack"], pattern = "FB"
 * Output: [true,false,true,true,false]
 * Explanation:
 * "FooBar" can be generated like this "F" + "oo" + "B" + "ar".
 * "FootBall" can be generated like this "F" + "oot" + "B" + "all".
 * "FrameBuffer" can be generated like this "F" + "rame" + "B" + "uffer".
 *
 * Example 2:
 *
 * Input: queries = ["FooBar","FooBarTest","FootBall","FrameBuffer","ForceFeedBack"], pattern = "FoBa"
 * Output: [true,false,true,false,false]
 * Explanation:
 * "FooBar" can be generated like this "Fo" + "o" + "Ba" + "r".
 * "FootBall" can be generated like this "Fo" + "ot" + "Ba" + "ll".
 *
 * Example 3:
 *
 * Input: queries = ["FooBar","FooBarTest","FootBall","FrameBuffer","ForceFeedBack"], pattern = "FoBaT"
 * Output: [false,true,false,false,false]
 * Explanation:
 * "FooBarTest" can be generated like this "Fo" + "o" + "Ba" + "r" + "T" + "est".
 *
 * Note:
 *
 * 1 <= queries.length <= 100
 * 1 <= queries[i].length <= 100
 * 1 <= pattern.length <= 100
 * All strings consists only of lower and upper case English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1023_CamelcaseMatching {

    @Answer
    public List<Boolean> camelMatch(String[] queries, String pattern) {
        List<Boolean> res = new ArrayList<>(queries.length);
        for (String query : queries) {
            res.add(match(query, pattern));
        }
        return res;
    }

    private boolean match(String query, String pattern) {
        int pi = 0;
        for (int i = 0; i < query.length(); i++) {
            char c = query.charAt(i);
            if (pi < pattern.length() && c == pattern.charAt(pi)) {
                pi++;
            } else if (Character.isUpperCase(c)) {
                return false;
            }
        }
        return pi == pattern.length();
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{"FooBar", "FooBarTest", "FootBall", "FrameBuffer", "ForceFeedBack"}, "FB")
            .expect(Arrays.asList(true, false, true, true, false));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{"FooBar", "FooBarTest", "FootBall", "FrameBuffer", "ForceFeedBack"}, "FoBa")
            .expect(Arrays.asList(true, false, true, false, false));

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new String[]{"FooBar", "FooBarTest", "FootBall", "FrameBuffer", "ForceFeedBack"}, "FoBaT")
            .expect(Arrays.asList(false, true, false, false, false));

}
