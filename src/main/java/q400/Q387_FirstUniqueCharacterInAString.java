package q400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/first-unique-character-in-a-string/
 *
 * Given a string, find the first non-repeating character in it and return it's index. If it doesn't exist, return -1.
 *
 * Examples:
 *
 * s = "leetcode"
 * return 0.
 *
 * s = "loveleetcode",
 * return 2.
 *
 * Note: You may assume the string contain only lowercase letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q387_FirstUniqueCharacterInAString {

    @Answer
    public int firstUniqChar(String s) {
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < s.length(); i++) {
            if (count[s.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }

    // 上面的方法稍微优化了一下, 稍微节省了一点时间
    @Answer
    public int firstUniqChar2(String s) {
        int[] count = new int[26];
        // 字符第一次出现的 s 中的下标
        int[] first = new int[26];
        int firstLen = 0;
        char[] sc = s.toCharArray();
        for (int i = 0; i < sc.length; i++) {
            if (count[sc[i] - 'a']++ == 0) {
                first[firstLen++] = i;
            }
        }
        for (int i = 0; i < firstLen; i++) {
            if (count[sc[first[i]] - 'a'] == 1) {
                return first[i];
            }
        }
        return -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("leetcode").expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation.create("loveleetcode").expect(2);

    @TestData
    public DataExpectation border = DataExpectation.create("").expect(-1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("abba").expect(-1);

    @TestData
    public DataExpectation normal2 = DataExpectation.create("dddccdbba").expect(8);

}
