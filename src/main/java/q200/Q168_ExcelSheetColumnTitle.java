package q200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/excel-sheet-column-title/
 *
 * Given a positive integer, return its corresponding column title as appear in an Excel sheet.
 *
 * For example:
 *
 * 1 -> A
 * 2 -> B
 * 3 -> C
 * ...
 * 26 -> Z
 * 27 -> AA
 * 28 -> AB
 * ...
 *
 * Example 1:
 *
 * Input: 1
 * Output: "A"
 *
 * Example 2:
 *
 * Input: 28
 * Output: "AB"
 *
 * Example 3:
 *
 * Input: 701
 * Output: "ZY"
 *
 * 题解: 类似问题 {@link Q171_ExcelSheetColumnNumber}
 */
@RunWith(LeetCodeRunner.class)
public class Q168_ExcelSheetColumnTitle {

    @Answer
    public String convertToTitle(int n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            int complement = n % 26;
            if (complement == 0) {
                sb.append('Z');
                n = n / 26 - 1;
            } else {
                sb.append((char) ('A' + complement - 1));
                n /= 26;
            }
        }
        return sb.reverse().toString();
    }

    // LeetCode 上的优化解法
    @Answer
    public String convertToTitle2(int n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            n--;
            sb.append((char) ('A' + n % 26));
            n /= 26;
        }
        return sb.reverse().toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(1).expect("A");

    @TestData
    public DataExpectation example2 = DataExpectation.create(28).expect("AB");

    @TestData
    public DataExpectation example3 = DataExpectation.create(701).expect("ZY");

    @TestData
    public DataExpectation normal1 = DataExpectation.create(26).expect("Z");

    @TestData
    public DataExpectation normal2 = DataExpectation.create(702).expect("ZZ");

}
