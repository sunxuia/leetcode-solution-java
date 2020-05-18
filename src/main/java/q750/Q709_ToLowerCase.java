package q750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/to-lower-case/
 *
 * Implement function ToLowerCase() that has a string parameter str, and returns the same string in lowercase.
 *
 *
 *
 * Example 1:
 *
 * Input: "Hello"
 * Output: "hello"
 *
 * Example 2:
 *
 * Input: "here"
 * Output: "here"
 *
 * Example 3:
 *
 * Input: "LOVELY"
 * Output: "lovely"
 */
@RunWith(LeetCodeRunner.class)
public class Q709_ToLowerCase {

    // 最简单的做法是 str.toLowerCase()
    @Answer
    public String toLowerCase(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ('A' <= c && c <= 'Z') {
                c = (char) (c - 'A' + 'a');
            }
            sb.append(c);
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("Hello").expect("hello");

    @TestData
    public DataExpectation example2 = DataExpectation.create("here").expect("here");

    @TestData
    public DataExpectation example3 = DataExpectation.create("LOVELY").expect("lovely");

    @TestData
    public DataExpectation normal1 = DataExpectation.create("al&phaBET").expect("al&phabet");

}

