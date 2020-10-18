package q1200;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1177. Can Make Palindrome from Substring
 * https://leetcode.com/problems/can-make-palindrome-from-substring/
 *
 * Given a string s, we make queries on substrings of s.
 *
 * For each query queries[i] = [left, right, k], we may rearrange the substring s[left], ..., s[right], and then choose
 * up to k of them to replace with any lowercase English letter.
 *
 * If the substring is possible to be a palindrome string after the operations above, the result of the query is true.
 * Otherwise, the result is false.
 *
 * Return an array answer[], where answer[i] is the result of the i-th query queries[i].
 *
 * Note that: Each letter is counted individually for replacement so if for example s[left..right] = "aaa", and k = 2,
 * we can only replace two of the letters.  (Also, note that the initial string s is never modified by any query.)
 *
 * Example :
 *
 * Input: s = "abcda", queries = [[3,3,0],[1,2,0],[0,3,1],[0,3,2],[0,4,1]]
 * Output: [true,false,false,true,true]
 * Explanation:
 * queries[0] : substring = "d", is palidrome.
 * queries[1] : substring = "bc", is not palidrome.
 * queries[2] : substring = "abcd", is not palidrome after replacing only 1 character.
 * queries[3] : substring = "abcd", could be changed to "abba" which is palidrome. Also this can be changed to "baab"
 * first rearrange it "bacd" then replace "cd" with "ab".
 * queries[4] : substring = "abcda", could be changed to "abcba" which is palidrome.
 *
 * Constraints:
 *
 * 1 <= s.length, queries.length <= 10^5
 * 0 <= queries[i][0] <= queries[i][1] < s.length
 * 0 <= queries[i][2] <= s.length
 * s only contains lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1177_CanMakePalindromeFromSubstring {

    @Answer
    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
        List<Boolean> res = new ArrayList<>(queries.length);
        int[][] counts = new int[s.length() + 1][];
        counts[0] = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counts[i + 1] = counts[i].clone();
            counts[i + 1][s.charAt(i) - 'a']++;
        }
        for (int[] query : queries) {
            int left = query[0], right = query[1];
            int change = query[2] * 2 + (right - left + 1) % 2;
            boolean possible = true;
            for (int i = 0; i < 26 && possible; i++) {
                if ((counts[right + 1][i] - counts[left][i]) % 2 == 1) {
                    if (change-- == 0) {
                        possible = false;
                    }
                }
            }
            res.add(possible);
        }
        return res;
    }

    /**
     * LeetCode 上比较快的方法, 是位运算相关的做法.
     */
    @Answer
    public List<Boolean> canMakePaliQueries2(String s, int[][] queries) {
        List<Boolean> result = new ArrayList<>();
        int[] arr = new int[s.length()];
        arr[0] ^= (1 << (s.charAt(0) - 'a'));
        for (int i = 1; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            arr[i] = arr[i - 1] ^ (1 << idx);
        }
        for (int[] q : queries) {
            if (q[2] >= 13) {
                result.add(true);
                continue;
            }
            int diff = Integer.bitCount(arr[q[1]] ^ (q[0] > 0 ? arr[q[0] - 1] : 0));
            diff /= 2;
            if (diff <= q[2]) {
                result.add(true);
            } else {
                result.add(false);
            }
        }
        return result;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith("abcda", new int[][]{{3, 3, 0}, {1, 2, 0}, {0, 3, 1}, {0, 3, 2}, {0, 4, 1}})
            .expect(Arrays.asList(true, false, false, true, true));

}
