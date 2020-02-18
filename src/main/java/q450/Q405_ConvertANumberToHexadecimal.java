package q450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/convert-a-number-to-hexadecimal/
 *
 * Given an integer, write an algorithm to convert it to hexadecimal. For negative integer, two’s complement method
 * is used.
 *
 * Note:
 *
 * All letters in hexadecimal (a-f) must be in lowercase.
 * The hexadecimal string must not contain extra leading 0s. If the number is zero, it is represented by a single
 * zero character '0'; otherwise, the first character in the hexadecimal string will not be the zero character.
 * The given number is guaranteed to fit within the range of a 32-bit signed integer.
 * You must not use any method provided by the library which converts/formats the number to hex directly.
 *
 * Example 1:
 *
 * Input:
 * 26
 *
 * Output:
 * "1a"
 *
 * Example 2:
 *
 * Input:
 * -1
 *
 * Output:
 * "ffffffff"
 */
@RunWith(LeetCodeRunner.class)
public class Q405_ConvertANumberToHexadecimal {

    @Answer
    public String toHex(int num) {
        StringBuilder sb = new StringBuilder(8);
        int i = 28;
        while (i > 0 && (num >>> i) == 0) {
            i -= 4;
        }
        while (i >= 0) {
            int val = (num >>> i) & 15;
            sb.append((char) (val > 9 ? val - 10 + 'a' : val + '0'));
            i -= 4;
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(26).expect("1a");

    @TestData
    public DataExpectation example2 = DataExpectation.create(-1).expect("ffffffff");

    @TestData
    public DataExpectation zero = DataExpectation.create(0).expect("0");

}
