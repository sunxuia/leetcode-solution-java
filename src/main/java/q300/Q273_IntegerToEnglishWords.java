package q300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/integer-to-english-words/
 *
 * Convert a non-negative integer to its english words representation. Given input is guaranteed to be less than 231 -
 * 1.
 *
 * Example 1:
 *
 * Input: 123
 * Output: "One Hundred Twenty Three"
 * Example 2:
 *
 * Input: 12345
 * Output: "Twelve Thousand Three Hundred Forty Five"
 * Example 3:
 *
 * Input: 1234567
 * Output: "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
 * Example 4:
 *
 * Input: 1234567891
 * Output: "One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety One"
 */
@RunWith(LeetCodeRunner.class)
public class Q273_IntegerToEnglishWords {

    @Answer
    public String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }
        int digit = 0, t = num;
        while ((t /= 10) > 0) {
            digit++;
        }
        digit = digit / 3;
        int level = (int) Math.pow(1000, digit);
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            int i = num / level;
            num -= i * level;
            if (i > 0) {
                if (i / 100 > 0) {
                    sb.append(numsLessThan20[i / 100]).append(" Hundred ");
                    i %= 100;
                }
                if (i >= 20) {
                    sb.append(digit2s[i / 10]).append(' ');
                    i %= 10;
                }
                if (i > 0) {
                    sb.append(numsLessThan20[i]).append(' ');
                }
                if (digit > 0) {
                    sb.append(levels[digit]).append(' ');
                }
            }
            level /= 1000;
            digit--;
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private static final String[] levels = {"", "Thousand", "Million", "Billion"};

    private static final String[] numsLessThan20 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight",
            "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen",
            "Nineteen"};

    private static final String[] digit2s = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty",
            "Ninety"};

    @TestData
    public DataExpectation example1 = DataExpectation.create(123).expect("One Hundred Twenty Three");

    @TestData
    public DataExpectation example2 = DataExpectation.create(12345).expect("Twelve Thousand Three Hundred Forty Five");

    @TestData
    public DataExpectation example3 = DataExpectation.create(1234567)
            .expect("One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven");

    @TestData
    public DataExpectation example4 = DataExpectation.create(1234567891)
            .expect("One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred "
                    + "Ninety One");

    @TestData
    public DataExpectation normal1000010 = DataExpectation.create(1000010).expect("One Million Ten");

}

