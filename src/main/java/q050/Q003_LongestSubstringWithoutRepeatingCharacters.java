package q050;

import java.util.Iterator;
import java.util.LinkedHashSet;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * Given a string, find the length of the longest substring without repeating characters.
 *
 * Example 1:
 *
 * Input: "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 *
 * Example 2:
 *
 * Input: "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 *
 * Example 3:
 *
 * Input: "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 * Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 */
@RunWith(LeetCodeRunner.class)
public class Q003_LongestSubstringWithoutRepeatingCharacters {

    /**
     * 使用LinkedHashSet 的方法, 这种方法的速度不够快. 时间复杂度O(n), 耗时13ms
     */
    @Answer
    public int usingLinkedHashSet(String s) {
        LinkedHashSet<Character> chars = new LinkedHashSet<>();
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (chars.contains(c)) {
                for (Iterator<Character> it = chars.iterator(); it.hasNext(); ) {
                    Character start = it.next();
                    it.remove();
                    if (start.equals(c)) {
                        break;
                    }
                }
            }
            chars.add(c);
            res = Math.max(chars.size(), res);
        }
        return res;
    }

    /**
     * 自己实现简单的检测的方法, 这种方法比上面的方法快, 时间复杂度O(n), 耗时2ms
     */
    @Answer
    public int lengthOfLongestSubstring(String s) {
        // 因为字符都是ascii 字符, 所以使用木桶方法进行检测就好了.
        boolean[] occupies = new boolean[128];
        int res = 0;
        // i表示当前扫描位置, start 表示[start, i] 没有重复字符
        for (int i = 0, start = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (occupies[c]) {
                while (true) {
                    char sc = s.charAt(start++);
                    occupies[sc] = false;
                    if (sc == c) {
                        break;
                    }
                }
            }
            occupies[c] = true;
            res = Math.max(i - start + 1, res);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("abcabcbb")
            .expect(3)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("bbbbb")
            .expect(1)
            .build();

    @TestData
    public DataExpectation exmaple3 = DataExpectation.builder()
            .addArgument("pwwkew")
            .expect(3)
            .build();

    @TestData
    public DataExpectation border1 = DataExpectation.builder()
            .addArgument("")
            .expect(0)
            .build();

    @TestData
    public DataExpectation border2 = DataExpectation.builder()
            .addArgument("a")
            .expect(1)
            .build();
}
