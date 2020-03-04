package q450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/number-of-segments-in-a-string/
 *
 * Count the number of segments in a string, where a segment is defined to be a contiguous sequence of non-space
 * characters.
 *
 * Please note that the string does not contain any non-printable characters.
 *
 * Example:
 *
 * Input: "Hello, my name is John"
 * Output: 5
 */
@RunWith(LeetCodeRunner.class)
public class Q434_NumberOfSegmentsInAString {

    @Answer
    public int countSegments(String s) {
        int res = 0;
        char[] sc = s.toCharArray();
        for (int i = 0; i < sc.length; i++) {
            if (sc[i] == ' ' && (i > 0 && sc[i - 1] != ' ')) {
                res++;
            }
        }
        if (!s.isEmpty() && !s.endsWith(" ")) {
            res++;
        }
        return res;
    }

    // 更为简洁的做法, 在 LeetCode 上这种做法的时间和上面的时间一样都是0ms
    @Answer
    public int countSegments2(String s) {
        int res = 0;
        for (String str : s.split(" ")) {
            if (str.trim().length() > 0) {
                res++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create("Hello, my name is John").expect(5);

}
