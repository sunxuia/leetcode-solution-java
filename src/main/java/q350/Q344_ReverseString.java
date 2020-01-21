package q350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/reverse-string/
 *
 * Write a function that reverses a string. The input string is given as an array of characters char[].
 *
 * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1)
 * extra memory.
 *
 * You may assume all the characters consist of printable ascii characters.
 *
 *
 *
 * Example 1:
 *
 * Input: ["h","e","l","l","o"]
 * Output: ["o","l","l","e","h"]
 *
 * Example 2:
 *
 * Input: ["H","a","n","n","a","h"]
 * Output: ["h","a","n","n","a","H"]
 */
@RunWith(LeetCodeRunner.class)
public class Q344_ReverseString {

    @Answer
    public void reverseString(char[] s) {
        for (int i = 0; i < s.length / 2; i++) {
            char t = s[i];
            s[i] = s[s.length - 1 - i];
            s[s.length - 1 - i] = t;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new char[]{'h', 'e', 'l', 'l', 'o'})
            .expectArgument(0, new char[]{'o', 'l', 'l', 'e', 'h'})
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new char[]{'H', 'a', 'n', 'n', 'a', 'h'})
            .expectArgument(0, new char[]{'h', 'a', 'n', 'n', 'a', 'H'})
            .build();

}
