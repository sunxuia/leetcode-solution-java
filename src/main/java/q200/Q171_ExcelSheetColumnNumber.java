package q200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/excel-sheet-column-number/
 *
 * Given a column title as appear in an Excel sheet, return its corresponding column number.
 *
 * For example:
 *
 * A -> 1
 * B -> 2
 * C -> 3
 * ...
 * Z -> 26
 * AA -> 27
 * AB -> 28
 * ...
 *
 * Example 1:
 *
 * Input: "A"
 * Output: 1
 *
 * Example 2:
 *
 * Input: "AB"
 * Output: 28
 *
 * Example 3:
 *
 * Input: "ZY"
 * Output: 701
 *
 * 题解: 类似问题 {@link Q168_ExcelSheetColumnTitle}
 */
@RunWith(LeetCodeRunner.class)
public class Q171_ExcelSheetColumnNumber {

    @Answer
    public int titleToNumber(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            res = res * 26 + s.charAt(i) - 'A' + 1;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("A").expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create("AB").expect(28);

    @TestData
    public DataExpectation example3 = DataExpectation.create("ZY").expect(701);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("Z").expect(26);


}
