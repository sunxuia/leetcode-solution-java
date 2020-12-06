package q1400;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1371. Find the Longest Substring Containing Vowels in Even Counts
 * https://leetcode.com/problems/find-the-longest-substring-containing-vowels-in-even-counts/
 *
 * Given the string s, return the size of the longest substring containing each vowel an even number of times. That is,
 * 'a', 'e', 'i', 'o', and 'u' must appear an even number of times.
 *
 * Example 1:
 *
 * Input: s = "eleetminicoworoep"
 * Output: 13
 * Explanation: The longest substring is "leetminicowor" which contains two each of the vowels: e, i and o and zero of
 * the vowels: a and u.
 *
 * Example 2:
 *
 * Input: s = "leetcodeisgreat"
 * Output: 5
 * Explanation: The longest substring is "leetc" which contains two e's.
 *
 * Example 3:
 *
 * Input: s = "bcbcbc"
 * Output: 6
 * Explanation: In this case, the given string "bcbcbc" is the longest because all vowels: a, e, i, o and u appear zero
 * times.
 *
 * Constraints:
 *
 * 1 <= s.length <= 5 x 10^5
 * s contains only lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1371_FindTheLongestSubstringContainingVowelsInEvenCounts {

    /**
     * 由hint 中的提示使用位运算得到启发, 得出如下结果.
     * 因为只需要判断是否是偶数, 则对于元音, 做位的异或运算, 如果是0 则说明是偶数.
     */
    @Answer
    public int findTheLongestSubstring(String s) {
        final int n = s.length();
        // 保存出现这奇偶排列的位置, [0, i] 的排列是这样的.
        Map<Integer, Integer> prefixes = new HashMap<>(n);
        prefixes.put(0, -1);
        int mask = 0;
        for (int i = 0; i < n; i++) {
            mask ^= VOWEL_MAP[s.charAt(i)];
            prefixes.putIfAbsent(mask, i);
        }

        // 从后向前遍历, 找出上一个同样是这样奇偶排列的位置,
        // 则中间的子字符串(prev, i] 就是需要的结果.
        int res = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (prefixes.containsKey(mask)) {
                res = Math.max(res, i - prefixes.get(mask));
            }
            mask ^= VOWEL_MAP[s.charAt(i)];
        }
        return res;
    }

    private static int[] VOWEL_MAP = new int[127];

    static {
        VOWEL_MAP['a'] = 1;
        VOWEL_MAP['e'] = 2;
        VOWEL_MAP['i'] = 4;
        VOWEL_MAP['o'] = 8;
        VOWEL_MAP['u'] = 16;
    }

    /**
     * leetcode 上最快的解法, 是对上面算法的优化.
     */
    @Answer
    public int findTheLongestSubstring2(String s) {
        int res = 0;
        // 使用数组代替map
        int[] prefix = new int[32];
        Arrays.fill(prefix, -2);
        prefix[0] = -1;
        // 在1 个循环中遍历并检查(合并了上面的2 个循环)
        for (int i = 0, mask = 0; i < s.length(); i++) {
            mask ^= VOWEL_MAP[s.charAt(i)];
            if (prefix[mask] == -2) {
                prefix[mask] = i;
            }
            res = Math.max(res, i - prefix[mask]);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("eleetminicoworoep").expect(13);

    @TestData
    public DataExpectation example2 = DataExpectation.create("leetcodeisgreat").expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation.create("bcbcbc").expect(6);

}
