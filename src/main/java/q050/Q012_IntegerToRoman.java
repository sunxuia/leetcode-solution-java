package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.UsingTestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/integer-to-roman/
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
 * Given an integer, convert it to a roman numeral. Input is guaranteed to be within the range from 1 to 3999.
 *
 * Example 1:
 *
 * Input: 3
 * Output: "III"
 * Example 2:
 *
 * Input: 4
 * Output: "IV"
 * Example 3:
 *
 * Input: 9
 * Output: "IX"
 * Example 4:
 *
 * Input: 58
 * Output: "LVIII"
 * Explanation: L = 50, V = 5, III = 3.
 * Example 5:
 *
 * Input: 1994
 * Output: "MCMXCIV"
 * Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
 */
@RunWith(LeetCodeRunner.class)
public class Q012_IntegerToRoman {

    /**
     * 罗马数字规则参见 https://www.douban.com/note/335254352/
     */
    @Answer
    @UsingTestData({})
    public String intToRoman(int num) {
        int[] nums = new int[]{1000, 500, 100, 50, 10, 5, 1};
        int[] limit = new int[]{900, 400, 90, 40, 9, 4, 1};
        char[] romans = new char[]{'M', 'D', 'C', 'L', 'X', 'V', 'I'};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            int count = num / nums[i];
            while (count-- > 0) {
                sb.append(romans[i]);
            }
            num = num % nums[i];
            if (num >= limit[i]) {
                // 左减不能跨过一个位数, 且不是5 的倍数
                int minus = i + 2 - i % 2;
                num -= nums[i] - nums[minus];
                sb.append(romans[minus]).append(romans[i]);
            }
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(3)
            .expect("III")
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(4)
            .expect("IV")
            .build();

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument(9)
            .expect("IX")
            .build();

    @TestData
    public DataExpectation example4 = DataExpectation.builder()
            .addArgument(58)
            .expect("LVIII")
            .build();

    @TestData
    public DataExpectation example5 = DataExpectation.builder()
            .addArgument(1994)
            .expect("MCMXCIV")
            .build();
}
