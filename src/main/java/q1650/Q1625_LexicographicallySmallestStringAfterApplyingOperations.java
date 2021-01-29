package q1650;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1625. Lexicographically Smallest String After Applying Operations
 * https://leetcode.com/problems/lexicographically-smallest-string-after-applying-operations/
 *
 * You are given a string s of even length consisting of digits from 0 to 9, and two integers a and b.
 *
 * You can apply either of the following two operations any number of times and in any order on s:
 *
 * Add a to all odd indices of s (0-indexed). Digits post 9 are cycled back to 0. For example, if s = "3456" and a = 5,
 * s becomes "3951".
 * Rotate s to the right by b positions. For example, if s = "3456" and b = 1, s becomes "6345".
 *
 * Return the lexicographically smallest string you can obtain by applying the above operations any number of times on
 * s.
 *
 * A string a is lexicographically smaller than a string b (of the same length) if in the first position where a and b
 * differ, string a has a letter that appears earlier in the alphabet than the corresponding letter in b. For example,
 * "0158" is lexicographically smaller than "0190" because the first position they differ is at the third letter, and
 * '5' comes before '9'.
 *
 * Example 1:
 *
 * Input: s = "5525", a = 9, b = 2
 * Output: "2050"
 * Explanation: We can apply the following operations:
 * Start:  "5525"
 * Rotate: "2555"
 * Add:    "2454"
 * Add:    "2353"
 * Rotate: "5323"
 * Add:    "5222"
 * Add:    "5121"
 * Rotate: "2151"
 * Add:    "2050"
 * There is no way to obtain a string that is lexicographically smaller then "2050".
 *
 * Example 2:
 *
 * Input: s = "74", a = 5, b = 1
 * Output: "24"
 * Explanation: We can apply the following operations:
 * Start:  "74"
 * Rotate: "47"
 * Add:    "42"
 * Rotate: "24"
 * There is no way to obtain a string that is lexicographically smaller then "24".
 *
 * Example 3:
 *
 * Input: s = "0011", a = 4, b = 2
 * Output: "0011"
 * Explanation: There are no sequence of operations that will give us a lexicographically smaller string than "0011".
 *
 * Example 4:
 *
 * Input: s = "43987654", a = 7, b = 3
 * Output: "00553311"
 *
 * Constraints:
 *
 * 2 <= s.length <= 100
 * s.length is even.
 * s consists of digits from 0 to 9 only.
 * 1 <= a <= 9
 * 1 <= b <= s.length - 1
 */
@RunWith(LeetCodeRunner.class)
public class Q1625_LexicographicallySmallestStringAfterApplyingOperations {

    /**
     * 很没劲的题目, 暴力求解.
     */
    @Answer
    public String findLexSmallestString(String s, int a, int b) {
        final int n = s.length();
        String res = s;
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(s);
        while (!queue.isEmpty()) {
            String str = queue.poll();
            if (!visited.add(str)) {
                continue;
            }
            res = res.compareTo(str) < 0 ? res : str;

            char[] next = str.toCharArray();
            for (int i = 1; i < n; i += 2) {
                next[i] = (char) ((next[i] - '0' + a) % 10 + '0');
            }
            queue.offer(new String(next));
            queue.offer(str.substring(n - b) + str.substring(0, n - b));
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("5525", 9, 2).expect("2050");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("74", 5, 1).expect("24");

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("0011", 4, 2).expect("0011");

    @TestData
    public DataExpectation example4 = DataExpectation.createWith("43987654", 7, 3).expect("00553311");

}
