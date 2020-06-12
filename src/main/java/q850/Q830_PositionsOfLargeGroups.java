package q850;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/positions-of-large-groups/
 *
 * In a string S of lowercase letters, these letters form consecutive groups of the same character.
 *
 * For example, a string like S = "abbxxxxzyy" has the groups "a", "bb", "xxxx", "z" and "yy".
 *
 * Call a group large if it has 3 or more characters.  We would like the starting and ending positions of every large
 * group.
 *
 * The final answer should be in lexicographic order.
 *
 *
 *
 * Example 1:
 *
 * Input: "abbxxxxzzy"
 * Output: [[3,6]]
 * Explanation: "xxxx" is the single large group with starting  3 and ending positions 6.
 *
 * Example 2:
 *
 * Input: "abc"
 * Output: []
 * Explanation: We have "a","b" and "c" but no large group.
 *
 * Example 3:
 *
 * Input: "abcdddeeeeaabbbcd"
 * Output: [[3,5],[6,9],[12,14]]
 *
 *
 *
 * Note:  1 <= S.length <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q830_PositionsOfLargeGroups {

    @Answer
    public List<List<Integer>> largeGroupPositions(String S) {
        List<List<Integer>> res = new ArrayList<>();
        char prev = ' ';
        int count = 0;
        for (int i = 0; i <= S.length(); i++) {
            char c = i == S.length() ? ' ' : S.charAt(i);
            if (prev == c) {
                count++;
            } else {
                if (count > 2) {
                    res.add(Arrays.asList(i - count, i - 1));
                }
                count = 1;
                prev = c;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("abbxxxxzzy").expect(new int[][]{{3, 6}});

    @TestData
    public DataExpectation example2 = DataExpectation.create("abc").expect(new int[0][0]);
    ;

    @TestData
    public DataExpectation example3 = DataExpectation.create("abcdddeeeeaabbbcd")
            .expect(new int[][]{{3, 5}, {6, 9}, {12, 14}});

}
