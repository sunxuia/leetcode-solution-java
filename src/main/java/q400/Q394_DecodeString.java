package q400;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/decode-string/
 *
 * Given an encoded string, return its decoded string.
 *
 * The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated
 * exactly k times. Note that k is guaranteed to be a positive integer.
 *
 * You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.
 *
 * Furthermore, you may assume that the original data does not contain any digits and that digits are only for those
 * repeat numbers, k. For example, there won't be input like 3a or 2[4].
 *
 * Examples:
 *
 * s = "3[a]2[bc]", return "aaabcbc".
 * s = "3[a2[c]]", return "accaccacc".
 * s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
 */
@RunWith(LeetCodeRunner.class)
public class Q394_DecodeString {

    // 题目中没写的条件: 测试用例中重复次数都是正整数, 重复的字符中没有'[', ']' 和数字符号.
    @Answer
    public String decodeString(String s) {
        StringBuilder sb = new StringBuilder();
        Deque<Integer> stack = new ArrayDeque<>();
        int times = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ('0' <= c && c <= '9') {
                times = times * 10 + c - '0';
            } else if (c == '[') {
                stack.push(times);
                stack.push(sb.length());
                times = 0;
            } else if (c == ']') {
                int start = stack.pop();
                int count = stack.pop();
                int end = sb.length();
                while (--count > 0) {
                    for (int j = start; j < end; j++) {
                        sb.append(sb.charAt(j));
                    }
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("3[a]2[bc]").expect("aaabcbc");

    @TestData
    public DataExpectation example2 = DataExpectation.create("3[a2[c]]").expect("accaccacc");

    @TestData
    public DataExpectation example3 = DataExpectation.create("2[abc]3[cd]ef").expect("abcabccdcdcdef");

}
