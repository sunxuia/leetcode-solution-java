package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/longest-common-prefix/
 *
 * Write a function to find the longest common prefix string amongst an array of strings.
 *
 * If there is no common prefix, return an empty string "".
 *
 * Example 1:
 *
 * Input: ["flower","flow","flight"]
 * Output: "fl"
 * Example 2:
 *
 * Input: ["dog","racecar","car"]
 * Output: ""
 * Explanation: There is no common prefix among the input strings.
 * Note:
 *
 * All given inputs are in lowercase letters a-z.
 */
@RunWith(LeetCodeRunner.class)
public class Q014_LongestCommonPrefix {

    @Answer
    public String longestCommonPrefix(String[] strs) {
        if (strs.length > 0) {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                if (strs[0].length() == i) {
                    return strs[0].substring(0, i);
                }
                for (int j = 1; j < strs.length; j++) {
                    if (strs[j].length() == i ||
                            strs[j].charAt(i) != strs[j - 1].charAt(i)) {
                        return strs[j].substring(0, i);
                    }
                }
            }
        }
        return "";
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new String[]{"flower", "flow", "flight"})
            .expect("fl")
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new String[]{"dog", "racecar", "car"})
            .expect("")
            .build();
}
