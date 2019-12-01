package q250;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/valid-anagram/
 *
 * Given two strings s and t , write a function to determine if t is an anagram of s.
 *
 * Example 1:
 *
 * Input: s = "anagram", t = "nagaram"
 * Output: true
 *
 * Example 2:
 *
 * Input: s = "rat", t = "car"
 * Output: false
 *
 * Note:
 * You may assume the string contains only lowercase alphabets.
 *
 * Follow up:
 * What if the inputs contain unicode characters? How would you adapt your solution to such case?
 */
@RunWith(LeetCodeRunner.class)
public class Q242_ValidAnagram {

    @Answer
    public boolean isAnagram(String s, String t) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        for (int i = 0; i < t.length(); i++) {
            Character c = t.charAt(i);
            int count = map.getOrDefault(c, 0);
            if (count == 0) {
                return false;
            }
            if (count == 1) {
                map.remove(c);
            } else {
                map.put(c, count - 1);
            }
        }
        return map.isEmpty();
    }

    /**
     * 非unicode 字符的解法, LeetCode 上最快的就是这种解法.
     */
    @Answer
    public boolean isAnagram_Asci(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            if (count[t.charAt(i) - 'a']-- == 0) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("anagram", "nagaram").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("rat", "car").expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("aaaa", "aaa").expect(false);

//    @TestData
    public DataExpectation unicode = DataExpectation.createWith("测试字符串串", "串串字符测试").expect(true);

}
