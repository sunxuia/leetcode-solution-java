package q1000;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 970. Powerful Integers
 * https://leetcode.com/problems/powerful-integers/
 *
 * Given two positive integers x and y, an integer is powerful if it is equal to x^i + y^j for some integers i >= 0 and
 * j >= 0.
 *
 * Return a list of all powerful integers that have value less than or equal to bound.
 *
 * You may return the answer in any order.  In your answer, each value should occur at most once.
 *
 * Example 1:
 *
 * Input: x = 2, y = 3, bound = 10
 * Output: [2,3,4,5,7,9,10]
 * Explanation:
 * 2 = 2^0 + 3^0
 * 3 = 2^1 + 3^0
 * 4 = 2^0 + 3^1
 * 5 = 2^1 + 3^1
 * 7 = 2^2 + 3^1
 * 9 = 2^3 + 3^0
 * 10 = 2^0 + 3^2
 *
 * Example 2:
 *
 * Input: x = 3, y = 5, bound = 15
 * Output: [2,4,6,8,10,14]
 *
 * Note:
 *
 * 1 <= x <= 100
 * 1 <= y <= 100
 * 0 <= bound <= 10^6
 */
@RunWith(LeetCodeRunner.class)
public class Q970_PowerfulIntegers {

    @Answer
    public List<Integer> powerfulIntegers(int x, int y, int bound) {
        x = x == 1 ? bound + 1 : x;
        y = y == 1 ? bound + 1 : y;
        Set<Integer> set = new HashSet<>();
        for (int px = 1; px <= bound; px *= x) {
            for (int py = 1; px + py <= bound; py *= y) {
                set.add(px + py);
            }
        }
        return new ArrayList<>(set);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(2)
            .addArgument(3)
            .addArgument(10)
            .expect(Arrays.asList(2, 3, 4, 5, 7, 9, 10))
            .unorderResult()
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(3)
            .addArgument(5)
            .addArgument(15)
            .expect(Arrays.asList(2, 4, 6, 8, 10, 14))
            .unorderResult()
            .build();

    @TestData
    public DataExpectation border = DataExpectation.builder()
            .addArgument(2)
            .addArgument(1)
            .addArgument(10)
            .expect(Arrays.asList(2, 3, 5, 9))
            .unorderResult()
            .build();

}
