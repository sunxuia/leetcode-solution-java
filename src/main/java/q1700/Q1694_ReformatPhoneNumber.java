package q1700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1694. Reformat Phone Number
 * https://leetcode.com/problems/reformat-phone-number/
 *
 * You are given a phone number as a string number. number consists of digits, spaces ' ', and/or dashes '-'.
 *
 * You would like to reformat the phone number in a certain manner. Firstly, remove all spaces and dashes. Then, group
 * the digits from left to right into blocks of length 3 until there are 4 or fewer digits. The final digits are then
 * grouped as follows:
 *
 * 2 digits: A single block of length 2.
 * 3 digits: A single block of length 3.
 * 4 digits: Two blocks of length 2 each.
 *
 * The blocks are then joined by dashes. Notice that the reformatting process should never produce any blocks of length
 * 1 and produce at most two blocks of length 2.
 *
 * Return the phone number after formatting.
 *
 * Example 1:
 *
 * Input: number = "1-23-45 6"
 * Output: "123-456"
 * Explanation: The digits are "123456".
 * Step 1: There are more than 4 digits, so group the next 3 digits. The 1st block is "123".
 * Step 2: There are 3 digits remaining, so put them in a single block of length 3. The 2nd block is "456".
 * Joining the blocks gives "123-456".
 *
 * Example 2:
 *
 * Input: number = "123 4-567"
 * Output: "123-45-67"
 * Explanation: The digits are "1234567".
 * Step 1: There are more than 4 digits, so group the next 3 digits. The 1st block is "123".
 * Step 2: There are 4 digits left, so split them into two blocks of length 2. The blocks are "45" and "67".
 * Joining the blocks gives "123-45-67".
 *
 * Example 3:
 *
 * Input: number = "123 4-5678"
 * Output: "123-456-78"
 * Explanation: The digits are "12345678".
 * Step 1: The 1st block is "123".
 * Step 2: The 2nd block is "456".
 * Step 3: There are 2 digits left, so put them in a single block of length 2. The 3rd block is "78".
 * Joining the blocks gives "123-456-78".
 *
 * Example 4:
 *
 * Input: number = "12"
 * Output: "12"
 *
 * Example 5:
 *
 * Input: number = "--17-5 229 35-39475 "
 * Output: "175-229-353-94-75"
 *
 * Constraints:
 *
 * 2 <= number.length <= 100
 * number consists of digits and the characters '-' and ' '.
 * There are at least two digits in number.
 */
@RunWith(LeetCodeRunner.class)
public class Q1694_ReformatPhoneNumber {

    @Answer
    public String reformatNumber(String number) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < number.length(); i++) {
            char c = number.charAt(i);
            if ('0' <= c && c <= '9') {
                if (sb.length() % 4 == 3) {
                    sb.append('-');
                }
                sb.append(c);
            }
        }
        int len = sb.length();
        if (len >= 5 && sb.charAt(len - 2) == '-') {
            sb.setCharAt(len - 2, sb.charAt(len - 3));
            sb.setCharAt(len - 3, '-');
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("1-23-45 6").expect("123-456");

    @TestData
    public DataExpectation example2 = DataExpectation.create("123 4-567").expect("123-45-67");

    @TestData
    public DataExpectation example3 = DataExpectation.create("123 4-5678").expect("123-456-78");

    @TestData
    public DataExpectation example4 = DataExpectation.create("12").expect("12");

    @TestData
    public DataExpectation example5 = DataExpectation.create("--17-5 229 35-39475 ").expect("175-229-353-94-75");

}
