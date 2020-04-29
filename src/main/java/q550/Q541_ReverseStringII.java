package q550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/reverse-string-ii/
 *
 * Given a string and an integer k, you need to reverse the first k characters for every 2k characters counting from
 * the start of the string. If there are less than k characters left, reverse all of them. If there are less than 2k
 * but greater than or equal to k characters, then reverse the first k characters and left the other as original.
 *
 * Example:
 *
 * Input: s = "abcdefg", k = 2
 * Output: "bacdfeg"
 *
 * Restrictions:
 *
 * The string consists of lower English letters only.
 * Length of the given string and k will in the range [1, 10000]
 */
@RunWith(LeetCodeRunner.class)
public class Q541_ReverseStringII {

    // 这种方式比较慢
    @Answer
    public String reverseStr(String s, int k) {
        final int length = s.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i += 2 * k) {
            int middle = Math.min(length, i + k);
            for (int j = middle - 1; j >= i; j--) {
                sb.append(s.charAt(j));
            }
            for (int j = middle; j < Math.min(length, middle + k); j++) {
                sb.append(s.charAt(j));
            }
        }
        return sb.toString();
    }

    // 这种方式要快一点
    @Answer
    public String reverseStr2(String s, int k) {
        char[] sc = s.toCharArray();
        for (int i = 0; i < sc.length; i += 2 * k) {
            int left = i, right = Math.min(sc.length, i + k) - 1;
            while (left < right) {
                char t = sc[left];
                sc[left] = sc[right];
                sc[right] = t;
                left++;
                right--;
            }
        }
        return new String(sc);
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith("abcdefg", 2).expect("bacdfeg");

}
