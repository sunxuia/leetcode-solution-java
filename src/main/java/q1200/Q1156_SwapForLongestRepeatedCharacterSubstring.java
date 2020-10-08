package q1200;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1156. Swap For Longest Repeated Character Substring
 * https://leetcode.com/problems/swap-for-longest-repeated-character-substring/
 *
 * Given a string text, we are allowed to swap two of the characters in the string. Find the length of the longest
 * substring with repeated characters.
 *
 * Example 1:
 *
 * Input: text = "ababa"
 * Output: 3
 * Explanation: We can swap the first 'b' with the last 'a', or the last 'b' with the first 'a'. Then, the longest
 * repeated character substring is "aaa", which its length is 3.
 *
 * Example 2:
 *
 * Input: text = "aaabaaa"
 * Output: 6
 * Explanation: Swap 'b' with the last 'a' (or the first 'a'), and we get longest repeated character substring "aaaaaa",
 * which its length is 6.
 *
 * Example 3:
 *
 * Input: text = "aaabbaaa"
 * Output: 4
 *
 * Example 4:
 *
 * Input: text = "aaaaa"
 * Output: 5
 * Explanation: No need to swap, longest repeated character substring is "aaaaa", length is 5.
 *
 * Example 5:
 *
 * Input: text = "abcdef"
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= text.length <= 20000
 * text consist of lowercase English characters only.
 */
@RunWith(LeetCodeRunner.class)
public class Q1156_SwapForLongestRepeatedCharacterSubstring {

    @Answer
    public int maxRepOpt1(String text) {
        List<int[]> blocks = new ArrayList<>();
        for (int i = 0, pair[] = {-1, 0}; i < text.length(); i++) {
            int val = text.charAt(i) - 'a';
            if (val == pair[0]) {
                pair[1]++;
            } else {
                pair = new int[]{val, 1};
                blocks.add(pair);
            }
        }

        int[] counts = new int[26];
        for (int[] block : blocks) {
            counts[block[0]]++;
        }

        int res = 0;
        for (int i = 0; i < blocks.size(); i++) {
            int val = blocks.get(i)[0];
            int cnt = blocks.get(i)[1];
            if (i < blocks.size() - 2 && blocks.get(i + 1)[1] == 1 && val == blocks.get(i + 2)[0]) {
                // 可以替换中间字符(i+1) 的情况.
                if (counts[val] == 2) {
                    // aabaa 这种格式, 无法从其他区间找来字符替换中间的.
                    res = Math.max(res, cnt + blocks.get(i + 2)[1]);
                } else {
                    // aabaaba 这种格式, 可以从其他区间找来字符替换中间的.
                    res = Math.max(res, cnt + 1 + blocks.get(i + 2)[1]);
                }
            } else if (counts[val] > 1) {
                // aaba 这种格式, 可以从其他区间找来字符附在边上.
                res = Math.max(res, cnt + 1);
            } else {
                // aabb 这种格式, 无法从其他区间找来相同的字符.
                res = Math.max(res, cnt);
            }
        }
        return res;
    }


    /**
     * LeetCode 上比较快的解法, 和上面的解法差不多.
     */
    @Answer
    public int maxRepOpt1_leetCode(String text) {
        final char[] cs = text.toCharArray();
        final int n = cs.length;
        int[] counts = new int[26];
        for (int i = 0; i < n; i++) {
            counts[cs[i] - 'a']++;
        }
        int res = 1;
        for (int i = 0, next = 0; i < n; i = next) {
            while (next < n && cs[next] == cs[i]) {
                next++;
            }
            int extend = next + 1;
            while (extend < n && cs[extend] == cs[i]) {
                extend++;
            }
            // 区间 [i, next-1] 和 [next+1, extend-1] 相等 (第2 个区间可能不存在)
            // extend - i - 1 表示这2 个区间内和 cs[i] 相同字符的个数
            if (extend - i - 1 == counts[cs[i] - 'a']) {
                // 只能从 next 边上2 个区间转1 个字符来替换 next 位置.
                res = Math.max(res, extend - i - 1);
            } else {
                // 可以从其他区间转1 个字符来替换 next 位置.
                res = Math.max(res, extend - i);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("ababa").expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create("aaabaaa").expect(6);

    @TestData
    public DataExpectation example3 = DataExpectation.create("aaabbaaa").expect(4);

    @TestData
    public DataExpectation example4 = DataExpectation.create("aaaaa").expect(5);

    @TestData
    public DataExpectation example5 = DataExpectation.create("abcdef").expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("bbababaaaa").expect(6);

    @TestData
    public DataExpectation normal2 = DataExpectation.create("aabbaba").expect(3);

}
