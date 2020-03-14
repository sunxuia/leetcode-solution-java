package q500;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/assign-cookies/
 *
 * Assume you are an awesome parent and want to give your children some cookies. But, you should give each child at
 * most one cookie. Each child i has a greed factor gi, which is the minimum size of a cookie that the child will be
 * content with; and each cookie j has a size sj. If sj >= gi, we can assign the cookie j to the child i, and the
 * child i will be content. Your goal is to maximize the number of your content children and output the maximum number.
 *
 * Note:
 * You may assume the greed factor is always positive.
 * You cannot assign more than one cookie to one child.
 *
 * Example 1:
 *
 * Input: [1,2,3], [1,1]
 *
 * Output: 1
 *
 * Explanation: You have 3 children and 2 cookies. The greed factors of 3 children are 1, 2, 3.
 * And even though you have 2 cookies, since their size is both 1, you could only make the child whose greed factor
 * is 1 content.
 * You need to output 1.
 *
 * Example 2:
 *
 * Input: [1,2], [1,2,3]
 *
 * Output: 2
 *
 * Explanation: You have 2 children and 3 cookies. The greed factors of 2 children are 1, 2.
 * You have 3 cookies and their sizes are big enough to gratify all of the children,
 * You need to output 2.
 */
@RunWith(LeetCodeRunner.class)
public class Q455_AssignCookies {

    @Answer
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int res = 0;
        int i = 0;
        for (int j = 0; j < g.length; j++) {
            while (i < s.length && s[i] < g[j]) {
                i++;
            }
            if (i == s.length) {
                break;
            }
            res++;
            i++;
        }
        return res;
    }

    @TestData
    public DataExpectation exmaple1 = DataExpectation.createWith(new int[]{1, 2, 3}, new int[]{1, 1}).expect(1);

    @TestData
    public DataExpectation exmaple2 = DataExpectation.createWith(new int[]{1, 2}, new int[]{1, 2, 3}).expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{10, 9, 8, 7}, new int[]{5, 6, 7, 8})
            .expect(2);

}
