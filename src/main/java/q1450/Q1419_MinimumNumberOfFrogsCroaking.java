package q1450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1419. Minimum Number of Frogs Croaking
 * https://leetcode.com/problems/minimum-number-of-frogs-croaking/
 *
 * Given the string croakOfFrogs, which represents a combination of the string "croak" from different frogs, that is,
 * multiple frogs can croak at the same time, so multiple “croak” are mixed. Return the minimum number of different
 * frogs to finish all the croak in the given string.
 *
 * A valid "croak" means a frog is printing 5 letters ‘c’, ’r’, ’o’, ’a’, ’k’ sequentially. The frogs have to print all
 * five letters to finish a croak. If the given string is not a combination of valid "croak" return -1.
 *
 * Example 1:
 *
 * Input: croakOfFrogs = "croakcroak"
 * Output: 1
 * Explanation: One frog yelling "croak" twice.
 *
 * Example 2:
 *
 * Input: croakOfFrogs = "crcoakroak"
 * Output: 2
 * Explanation: The minimum number of frogs is two.
 * The first frog could yell "crcoakroak".
 * The second frog could yell later "crcoakroak".
 *
 * Example 3:
 *
 * Input: croakOfFrogs = "croakcrook"
 * Output: -1
 * Explanation: The given string is an invalid combination of "croak" from different frogs.
 *
 * Example 4:
 *
 * Input: croakOfFrogs = "croakcroa"
 * Output: -1
 *
 * Constraints:
 *
 * 1 <= croakOfFrogs.length <= 10^5
 * All characters in the string are: 'c', 'r', 'o', 'a' or 'k'.
 */
@RunWith(LeetCodeRunner.class)
public class Q1419_MinimumNumberOfFrogsCroaking {

    @Answer
    public int minNumberOfFrogs(String croakOfFrogs) {
        int max = 0, sum = 0;
        int c = 0, r = 0, o = 0, a = 0;
        for (int i = 0; i < croakOfFrogs.length(); i++) {
            switch (croakOfFrogs.charAt(i)) {
                case 'c':
                    c++;
                    max = Math.max(max, ++sum);
                    break;
                case 'r':
                    if (c == 0) {
                        return -1;
                    }
                    c--;
                    r++;
                    break;
                case 'o':
                    if (r == 0) {
                        return -1;
                    }
                    r--;
                    o++;
                    break;
                case 'a':
                    if (o == 0) {
                        return -1;
                    }
                    o--;
                    a++;
                    break;
                case 'k':
                    if (a == 0) {
                        return -1;
                    }
                    sum--;
                    break;
                default:
            }
        }
        return sum == 0 ? max : -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("croakcroak").expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create("crcoakroak").expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create("croakcrook").expect(-1);

    @TestData
    public DataExpectation example4 = DataExpectation.create("croakcroa").expect(-1);

}
