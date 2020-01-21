package q350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/reverse-vowels-of-a-string/
 *
 * Write a function that takes a string as input and reverse only the vowels of a string.
 *
 * Example 1:
 *
 * Input: "hello"
 * Output: "holle"
 *
 * Example 2:
 *
 * Input: "leetcode"
 * Output: "leotcede"
 *
 * Note:
 * The vowels does not include the letter "y".
 *
 * 题解: 只翻转字符串中的元音字符, 英文中的元音: a e i o u
 */
@RunWith(LeetCodeRunner.class)
public class Q345_ReverseVowelsOfAString {

    @Answer
    public String reverseVowels(String s) {
        char[] sc = s.toCharArray();
        int front = 0, end = s.length() - 1;
        while (front < end) {
            while (front < end && !isVowel(sc[front])) {
                front++;
            }
            while (front < end && !isVowel(sc[end])) {
                end--;
            }
            if (front < end) {
                char t = sc[front];
                sc[front] = sc[end];
                sc[end] = t;
                front++;
                end--;
            }
        }
        return new String(sc);
    }

    private boolean isVowel(char c) {
            return "aeiouAEIOU".indexOf(c) >= 0;
    }

    @TestData
    public DataExpectation exmaple1 = DataExpectation.create("hello").expect("holle");

    @TestData
    public DataExpectation example2 = DataExpectation.create("leetcode").expect("leotcede");

    @TestData
    public DataExpectation normal1 = DataExpectation.create("aA").expect("Aa");

}
