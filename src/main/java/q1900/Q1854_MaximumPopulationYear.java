package q1900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Easy] 1854. Maximum Population Year
 * https://leetcode.com/problems/maximum-population-year/
 *
 * You are given a 2D integer array logs where each logs[i] = [birthi, deathi] indicates the birth and death years of
 * the ith person.
 *
 * The population of some year x is the number of people alive during that year. The ith person is counted in year x's
 * population if x is in the inclusive range [birthi, deathi - 1]. Note that the person is not counted in the year that
 * they die.
 *
 * Return the earliest year with the maximum population.
 *
 * Example 1:
 *
 * Input: logs = [[1993,1999],[2000,2010]]
 * Output: 1993
 * Explanation: The maximum population is 1, and 1993 is the earliest year with this population.
 *
 * Example 2:
 *
 * Input: logs = [[1950,1961],[1960,1971],[1970,1981]]
 * Output: 1960
 * Explanation:
 * The maximum population is 2, and it had happened in years 1960 and 1970.
 * The earlier year between them is 1960.
 *
 * Constraints:
 *
 * 1 <= logs.length <= 100
 * 1950 <= birthi < deathi <= 2050
 */
@RunWith(LeetCodeRunner.class)
public class Q1854_MaximumPopulationYear {

    /**
     * 桶排序
     */
    @Answer
    public int maximumPopulation(int[][] logs) {
        final int base = 1950;
        int[] counts = new int[101];
        for (int[] log : logs) {
            counts[log[0] - base]++;
            counts[log[1] - base]--;
        }

        int res = 0, population = 0, maxPopulation = 0;
        for (int i = 0; i < counts.length; i++) {
            population += counts[i];
            if (maxPopulation < population) {
                res = i;
                maxPopulation = population;
            }
        }
        return res + base;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{1993, 1999}, {2000, 2010}})
            .expect(1993);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{1950, 1961}, {1960, 1971}, {1970, 1981}})
            .expect(1960);

}
