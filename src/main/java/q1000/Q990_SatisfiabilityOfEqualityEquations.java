package q1000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 990. Satisfiability of Equality Equations
 * https://leetcode.com/problems/satisfiability-of-equality-equations/
 *
 * Given an array equations of strings that represent relationships between variables, each string equations[i] has
 * length 4 and takes one of two different forms: "a==b" or "a!=b".  Here, a and b are lowercase letters (not
 * necessarily different) that represent one-letter variable names.
 *
 * Return true if and only if it is possible to assign integers to variable names so as to satisfy all the given
 * equations.
 *
 * Example 1:
 *
 * Input: ["a==b","b!=a"]
 * Output: false
 * Explanation: If we assign say, a = 1 and b = 1, then the first equation is satisfied, but not the second.  There is
 * no way to assign the variables to satisfy both equations.
 *
 * Example 2:
 *
 * Input: ["b==a","a==b"]
 * Output: true
 * Explanation: We could assign a = 1 and b = 1 to satisfy both equations.
 *
 * Example 3:
 *
 * Input: ["a==b","b==c","a==c"]
 * Output: true
 *
 * Example 4:
 *
 * Input: ["a==b","b!=c","c==a"]
 * Output: false
 *
 * Example 5:
 *
 * Input: ["c==c","b==d","x!=z"]
 * Output: true
 *
 * Note:
 *
 * 1 <= equations.length <= 500
 * equations[i].length == 4
 * equations[i][0] and equations[i][3] are lowercase letters
 * equations[i][1] is either '=' or '!'
 * equations[i][2] is '='
 */
@RunWith(LeetCodeRunner.class)
public class Q990_SatisfiabilityOfEqualityEquations {

    @Answer
    public boolean equationsPossible(String[] equations) {
        int[] roots = new int[26];
        for (int i = 0; i < roots.length; i++) {
            roots[i] = i;
        }
        for (String equation : equations) {
            if (equation.charAt(1) == '=') {
                roots[getRoot(roots, equation.charAt(0) - 'a')]
                        = getRoot(roots, equation.charAt(3) - 'a');
            }
        }
        for (String equation : equations) {
            if (equation.charAt(1) == '!') {
                if (getRoot(roots, equation.charAt(0) - 'a')
                        == getRoot(roots, equation.charAt(3) - 'a')) {
                    return false;
                }
            }
        }
        return true;
    }

    private int getRoot(int[] roots, int i) {
        if (roots[i] == i) {
            return i;
        }
        return roots[i] = getRoot(roots, roots[i]);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new String[]{"a==b", "b!=a"}).expect(false);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new String[]{"b==a", "a==b"}).expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new String[]{"a==b", "b==c", "a==c"}).expect(true);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new String[]{"a==b", "b!=c", "c==a"}).expect(false);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new String[]{"c==c", "b==d", "x!=z"}).expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new String[]{"f==a", "a==b", "f!=e", "a==c", "b==e", "c==f"})
            .expect(false);

}
