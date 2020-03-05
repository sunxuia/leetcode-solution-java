package q450;

import java.util.function.BiConsumer;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/string-compression/
 *
 * Given an array of characters, compress it in-place.
 *
 * The length after compression must always be smaller than or equal to the original array.
 *
 * Every element of the array should be a character (not int) of length 1.
 *
 * After you are done modifying the input array in-place, return the new length of the array.
 *
 *
 * Follow up:
 * Could you solve it using only O(1) extra space?
 *
 *
 * Example 1:
 *
 * Input:
 * ["a","a","b","b","c","c","c"]
 *
 * Output:
 * Return 6, and the first 6 characters of the input array should be: ["a","2","b","2","c","3"]
 *
 * Explanation:
 * "aa" is replaced by "a2". "bb" is replaced by "b2". "ccc" is replaced by "c3".
 *
 *
 *
 * Example 2:
 *
 * Input:
 * ["a"]
 *
 * Output:
 * Return 1, and the first 1 characters of the input array should be: ["a"]
 *
 * Explanation:
 * Nothing is replaced.
 *
 *
 *
 * Example 3:
 *
 * Input:
 * ["a","b","b","b","b","b","b","b","b","b","b","b","b"]
 *
 * Output:
 * Return 4, and the first 4 characters of the input array should be: ["a","b","1","2"].
 *
 * Explanation:
 * Since the character "a" does not repeat, it is not compressed. "bbbbbbbbbbbb" is replaced by "b12".
 * Notice each digit has it's own entry in the array.
 *
 *
 *
 * Note:
 *
 * 1. All characters have an ASCII value in [35, 126].
 * 2. 1 <= len(chars) <= 1000.
 */
@RunWith(LeetCodeRunner.class)
public class Q443_StringCompression {

    @Answer
    public int compress(char[] chars) {
        int i = 0, next = 0, count = 1;
        while (i < chars.length) {
            while (i < chars.length - 1 && chars[i] == chars[i + 1]) {
                count++;
                i++;
            }
            chars[next++] = chars[i];
            if (count > 1) {
                int s = next;
                while (count > 0) {
                    chars[next++] = (char) (count % 10 + '0');
                    count /= 10;
                }
                int e = next - 1;
                while (s < e) {
                    char t = chars[s];
                    chars[s] = chars[e];
                    chars[e] = t;
                    s++;
                    e--;
                }
            }
            count = 1;
            i++;
        }
        return next;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new char[]{'a', 'a', 'b', 'b', 'c', 'c', 'c'})
            .argumentAssertMethod(0, makeAssertMethod('a', '2', 'b', '2', 'c', '3'))
            .expect(6)
            .build();

    private BiConsumer<char[], char[]> makeAssertMethod(char... expect) {
        return (e, a) -> {
            String errorMsg = "expect: " + new String(expect)
                    + ", actual: " + new String(a).substring(expect.length);
            for (int i = 0; i < expect.length; i++) {
                Assert.assertEquals(errorMsg, expect[i], a[i]);
            }
        };
    }

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new char[]{'a'})
            .argumentAssertMethod(0, makeAssertMethod('a'))
            .expect(1)
            .build();

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument(new char[]{'a', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b'})
            .argumentAssertMethod(0, makeAssertMethod('a', 'b', '1', '2'))
            .expect(4)
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new char[]{'a', 'a', 'a', 'b', 'b', 'a', 'a'})
            .argumentAssertMethod(0, makeAssertMethod('a', '3', 'b', '2', 'a', '2'))
            .expect(6)
            .build();

}
