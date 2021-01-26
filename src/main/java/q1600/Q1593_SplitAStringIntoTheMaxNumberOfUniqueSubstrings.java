package q1600;

import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1593. Split a String Into the Max Number of Unique Substrings
 * https://leetcode.com/problems/split-a-string-into-the-max-number-of-unique-substrings/
 *
 * Given a string s, return the maximum number of unique substrings that the given string can be split into.
 *
 * You can split string s into any list of non-empty substrings, where the concatenation of the substrings forms the
 * original string. However, you must split the substrings such that all of them are unique.
 *
 * A substring is a contiguous sequence of characters within a string.
 *
 * Example 1:
 *
 * Input: s = "ababccc"
 * Output: 5
 * Explanation: One way to split maximally is ['a', 'b', 'ab', 'c', 'cc']. Splitting like ['a', 'b', 'a', 'b', 'c',
 * 'cc'] is not valid as you have 'a' and 'b' multiple times.
 *
 * Example 2:
 *
 * Input: s = "aba"
 * Output: 2
 * Explanation: One way to split maximally is ['a', 'ba'].
 *
 * Example 3:
 *
 * Input: s = "aa"
 * Output: 1
 * Explanation: It is impossible to split the string any further.
 *
 * Constraints:
 *
 * 1 <= s.length <= 16
 *
 *
 * s contains only lower case English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1593_SplitAStringIntoTheMaxNumberOfUniqueSubstrings {

    @Answer
    public int maxUniqueSplit(String s) {
        return dfs(s, new HashSet<>(), 0);
    }

    private int dfs(String s, Set<String> exists, int start) {
        if (start == s.length()) {
            return 0;
        }
        int res = -1;
        for (int i = start + 1; i <= s.length(); i++) {
            String str = s.substring(start, i);
            if (exists.add(str)) {
                int len = dfs(s, exists, i);
                if (len >= 0) {
                    res = Math.max(res, 1 + len);
                }
                exists.remove(str);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("ababccc").expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create("aba").expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create("aa").expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("wwwzfvedwfvhsww").expect(11);

}
