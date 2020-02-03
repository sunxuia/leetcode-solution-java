package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/roman-to-integer/
 *
 * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
 *
 * Symbol       Value
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * For example, two is written as II in Roman numeral, just two one's added together. Twelve is written as, XII,
 * which is simply X + II. The number twenty seven is written as XXVII, which is XX + V + II.
 *
 * Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not
 * IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it making four.
 * The same principle applies to the number nine, which is written as IX. There are six instances where subtraction
 * is used:
 *
 * I can be placed before V (5) and X (10) to make 4 and 9.
 * X can be placed before L (50) and C (100) to make 40 and 90.
 * C can be placed before D (500) and M (1000) to make 400 and 900.
 * Given a roman numeral, convert it to an integer. Input is guaranteed to be within the range from 1 to 3999.
 *
 * Example 1:
 *
 * Input: "III"
 * Output: 3
 * Example 2:
 *
 * Input: "IV"
 * Output: 4
 * Example 3:
 *
 * Input: "IX"
 * Output: 9
 * Example 4:
 *
 * Input: "LVIII"
 * Output: 58
 * Explanation: L = 50, V= 5, III = 3.
 * Example 5:
 *
 * Input: "MCMXCIV"
 * Output: 1994
 * Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
 */
@RunWith(LeetCodeRunner.class)
public class Q013_RomanToInteger {

    @Answer
    public int romanToInt(String s) {
        int res = 0, prev = Integer.MAX_VALUE;
        for (char c : s.toCharArray()) {
            int cur;
            switch (c) {
                case 'M':
                    cur = 1000;
                    break;
                case 'D':
                    cur = 500;
                    break;
                case 'C':
                    cur = 100;
                    break;
                case 'L':
                    cur = 50;
                    break;
                case 'X':
                    cur = 10;
                    break;
                case 'V':
                    cur = 5;
                    break;
                default:
                    cur = 1;
            }
            res += cur;
            if (prev < cur) {
                res -= prev << 1;
            }
            prev = cur;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("III")
            .expect(3)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("IV")
            .expect(4)
            .build();

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument("IX")
            .expect(9)
            .build();

    @TestData
    public DataExpectation example4 = DataExpectation.builder()
            .addArgument("LVIII")
            .expect(58)
            .build();

    @TestData
    public DataExpectation example5 = DataExpectation.builder()
            .addArgument("MCMXCIV")
            .expect(1994)
            .build();

}
