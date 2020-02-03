package q250;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/isomorphic-strings/
 *
 * Given two strings s and t, determine if they are isomorphic.
 *
 * Two strings are isomorphic if the characters in s can be replaced to get t.
 *
 * All occurrences of a character must be replaced with another character while preserving the order of characters.
 * No two characters may map to the same character but a character may map to itself.
 *
 * Example 1:
 *
 * Input: s = "egg", t = "add"
 * Output: true
 *
 * Example 2:
 *
 * Input: s = "foo", t = "bar"
 * Output: false
 *
 * Example 3:
 *
 * Input: s = "paper", t = "title"
 * Output: true
 *
 * Note:
 * You may assume both s and t have the same length.
 */
@RunWith(LeetCodeRunner.class)
public class Q205_IsomorphicStrings {

    // 使用Map 的方式比较慢
    @Answer
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Character> stMap = new HashMap<>();
        Map<Character, Character> tsMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char sc = s.charAt(i);
            char tc = t.charAt(i);
            if (stMap.computeIfAbsent(sc, key -> tc) != tc
                    || tsMap.computeIfAbsent(tc, key -> sc) != sc) {
                return false;
            }
        }
        return true;
    }

    /**
     * 这题因为测试用例都是ascii 字符, 所以可以使用数字代替hash map
     */
    @Answer
    public boolean isIsomorphic_array(String s, String t) {
        char[] stMap = new char[256];
        char[] tsMap = new char[256];
        for (int i = 0; i < s.length(); i++) {
            char sc = s.charAt(i);
            char tc = t.charAt(i);
            if (stMap[sc] == 0) {
                stMap[sc] = tc;
            }
            if (tsMap[tc] == 0) {
                tsMap[tc] = sc;
            }
            if (stMap[sc] != tc || tsMap[tc] != sc) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("egg", "add").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("foo", "bar").expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("paper", "title").expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("ab", "aa").expect(false);

}
