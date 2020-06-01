package q800;

import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/reorganize-string/
 *
 * Given a string S, check if the letters can be rearranged so that two characters that are adjacent to each other are
 * not the same.
 *
 * If possible, output any possible result.  If not possible, return the empty string.
 *
 * Example 1:
 *
 * Input: S = "aab"
 * Output: "aba"
 *
 * Example 2:
 *
 * Input: S = "aaab"
 * Output: ""
 *
 * Note:
 *
 * S will consist of lowercase letters and have length in range [1, 500].
 */
@RunWith(LeetCodeRunner.class)
public class Q767_ReorganizeString {

    @Answer
    public String reorganizeString(String S) {
        int[] counts = new int[128];
        for (int i = 0; i < S.length(); i++) {
            counts[S.charAt(i)]++;
        }

        PriorityQueue<Character> pq = new PriorityQueue<>(
                26, (a, b) -> counts[b] - counts[a]);
        for (char i = 'a'; i <= 'z'; i++) {
            if (counts[i] > 0) {
                pq.add(i);
            }
        }

        // 每次都取出第二多的字符和第一多的进行组合
        char most = 0;
        StringBuilder sb = new StringBuilder(S.length());
        while (!pq.isEmpty()) {
            char next = pq.remove();
            if (counts[most] >= counts[next]) {
                sb.append(most).append(next);
                counts[most]--;
                counts[next]--;
            } else {
                char t = most;
                most = next;
                next = t;
            }
            if (counts[next] > 0) {
                pq.add(next);
            }
        }
        if (counts[most] > 1) {
            return "";
        }
        if (counts[most] == 1) {
            sb.append(most);
        }
        return sb.toString();
    }

    /**
     * LeetCode 上最快的解法.
     */
    @Answer
    public String reorganizeString2(String S) {
        int[] counts = new int[26];
        for (int i = 0; i < S.length(); i++) {
            counts[S.charAt(i) - 'a']++;
        }

        // 找到数量最多的字符
        // 如果这个字符超过了一半, 那么就不会有解, 反之有解
        int max = 0;
        for (int i = 1; i < counts.length; i++) {
            if (counts[i] > counts[max]) {
                max = i;
            }
        }
        if (counts[max] > (S.length() + 1) / 2) {
            return "";
        }

        char[] res = new char[S.length()];
        int idx = 0;
        // 先设置最多数量的
        while (counts[max] > 0) {
            res[idx] = (char) (max + 'a');
            counts[max]--;
            idx += 2;
        }

        for (int i = 0; i < counts.length; i++) {
            while (counts[i] > 0) {
                if (idx >= S.length()) {
                    // (偶数位设置完成, 设置奇数位)
                    idx = 1;
                }
                res[idx] = (char) ('a' + i);
                counts[i]--;
                idx += 2;
            }
        }

        return String.valueOf(res);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("aab").expect("aba");

    @TestData
    public DataExpectation example2 = DataExpectation.create("aaab").expect("");

    @TestData
    public DataExpectation normal1 = DataExpectation.create("abbabbaaab").expect("ababababab");

    @TestData
    public DataExpectation normal2 = DataExpectation.create("vvvlo").expect("vlvov");

}
