package q100;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/minimum-window-substring/
 *
 * Given a string S and a string T, find the minimum window in S which will contain all the characters in T in
 * complexity O(n).
 *
 * Example:
 *
 * Input: S = "ADOBECODEBANC", T = "ABC"
 * Output: "BANC"
 * Note:
 *
 * If there is no such window in S that covers all characters in T, return the empty string "".
 * If there is such window, you are guaranteed that there will always be only one unique minimum window in S.
 *
 * 题解:
 * 找出s 中出现 t 中全部字符的最小子字符串, 最小子字符串中字符出现的顺序可以和 t 不同.
 * t 中的字符串可能会有重复的字符, 最小子字符串中的对应字符的数量应当相同.
 */
@RunWith(LeetCodeRunner.class)
public class Q076_MinimumWindowSubstring {

    @Answer
    public String minWindow(String s, String t) {
        // cs key 表示字符数量, value[0] 表示t 中该字符的次数,
        // value[1] 表示在遍历s 的过程中当前匹配实际匹配到的数量.
        Map<Character, int[]> cs = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            int[] match = cs.computeIfAbsent(t.charAt(i), k -> new int[2]);
            match[0]++;
        }
        int minStart = 0, minLength = Integer.MAX_VALUE;
        // start 表示当前匹配在s 中可能的起始位置, count 表示t 中的字符已经被匹配的数量.
        for (int i = 0, start = 0, count = 0; i < s.length(); i++) {
            int[] match = cs.get(s.charAt(i));
            if (match == null) {
                continue;
            }
            // 增加匹配的字符数
            match[1]++;
            if (match[0] >= match[1]) {
                count++;
            }

            // 存在匹配
            if (count == t.length()) {
                // 找出最小的起始位置
                while (start <= i) {
                    int[] m = cs.get(s.charAt(start));
                    if (m != null) {
                        if (m[0] < m[1]) {
                            m[1]--;
                        } else {
                            break;
                        }
                    }
                    start++;
                }
                if (i + 1 - start < minLength) {
                    minLength = i + 1 - start;
                    minStart = start;
                }

                // 破坏这次匹配, 准备新的匹配
                cs.get(s.charAt(start))[1]--;
                start++;
                count--;
            }
        }
        return minLength == Integer.MAX_VALUE ? ""
                : s.substring(minStart, minStart + minLength);
    }

    /**
     * LeetCode 上根据s 和t 都是ascii, 使用数组简化了上面的 Map 的初始化和判断.
     */
    @Answer
    public String leetCode(String s, String t) {
        int[] missings = new int[127];
        for (int i = 0; i < t.length(); i++) {
            missings[t.charAt(i)]++;
        }
        int minStart = 0, minLength = Integer.MAX_VALUE;
        for (int i = 0, start = 0, count = 0; i < s.length(); i++) {
            missings[s.charAt(i)]--;
            if (missings[s.charAt(i)] >= 0) {
                count++;
            }
            if (count == t.length()) {
                while (missings[s.charAt(start)] < 0) {
                    missings[s.charAt(start)]++;
                    start++;
                }
                if (i + 1 - start < minLength) {
                    minLength = i + 1 - start;
                    minStart = start;
                }
                missings[s.charAt(start)]++;
                start++;
                count--;
            }
        }
        return minLength == Integer.MAX_VALUE ? ""
                : s.substring(minStart, minStart + minLength);
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith("ADOBECODEBANC", "ABC")
            .expect("BANC");

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith("a", "aa")
            .expect("");

}
