package q1950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1925. Count Square Sum Triples
 * https://leetcode.com/problems/count-square-sum-triples/
 *
 * A square triple (a,b,c) is a triple where a, b, and c are integers and a^2 + b^2 = c^2.
 *
 * Given an integer n, return the number of square triples such that 1 <= a, b, c <= n.
 *
 * Example 1:
 *
 * Input: n = 5
 * Output: 2
 * Explanation: The square triples are (3,4,5) and (4,3,5).
 *
 * Example 2:
 *
 * Input: n = 10
 * Output: 4
 * Explanation: The square triples are (3,4,5), (4,3,5), (6,8,10), and (8,6,10).
 *
 * Constraints:
 *
 * 1 <= n <= 250
 */
@RunWith(LeetCodeRunner.class)
public class Q1925_CountSquareSumTriples {

    /**
     * 时间复杂度 O(N*N*logN)
     */
    @Answer
    public int countTriples(int n) {
        int res = 0;
        for (int a = 1; a * a < n * n; a++) {
            for (int b = a + 1; a * a + b * b <= n * n; b++) {
                int target = a * a + b * b;
                int start = 0, end = n;
                while (start < end) {
                    int mid = (start + end) / 2;
                    if (mid * mid < target) {
                        start = mid + 1;
                    } else {
                        end = mid;
                    }
                }
                if (start * start == target) {
                    // a2+b2和b2+a2
                    res += 2;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(5).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(10).expect(4);

}
