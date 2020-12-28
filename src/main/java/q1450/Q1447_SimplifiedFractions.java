package q1450;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1447. Simplified Fractions
 * https://leetcode.com/problems/simplified-fractions/
 *
 * Given an integer n, return a list of all simplified fractions between 0 and 1 (exclusive) such that the denominator
 * is less-than-or-equal-to n. The fractions can be in any order.
 *
 * Example 1:
 *
 * Input: n = 2
 * Output: ["1/2"]
 * Explanation: "1/2" is the only unique fraction with a denominator less-than-or-equal-to 2.
 *
 * Example 2:
 *
 * Input: n = 3
 * Output: ["1/2","1/3","2/3"]
 *
 * Example 3:
 *
 * Input: n = 4
 * Output: ["1/2","1/3","1/4","2/3","3/4"]
 * Explanation: "2/4" is not a simplified fraction because it can be simplified to "1/2".
 *
 * Example 4:
 *
 * Input: n = 1
 * Output: []
 *
 * Constraints:
 *
 * 1 <= n <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1447_SimplifiedFractions {

    @Answer
    public List<String> simplifiedFractions(int n) {
        List<String> res = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                if (gcd(i, j) == 1) {
                    res.add(j + "/" + i);
                }
            }
        }
        return res;
    }

    private int gcd(int x, int y) {
        return y == 0 ? x : gcd(y, x % y);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(2).expect(List.of("1/2"));

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(3)
            .expect(List.of("1/2", "1/3", "2/3"))
            .unOrder();

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(4)
            .expect(List.of("1/2", "1/3", "1/4", "2/3", "3/4"))
            .unOrder();

    @TestData
    public DataExpectation example4 = DataExpectation.create(1).expect(List.of());

}
