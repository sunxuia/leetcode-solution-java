package q900;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 899. Orderly Queue
 * https://leetcode.com/problems/orderly-queue/
 *
 * A string S of lowercase letters is given.  Then, we may make any number of moves.
 *
 * In each move, we choose one of the first K letters (starting from the left), remove it, and place it at the end of
 * the string.
 *
 * Return the lexicographically smallest string we could have after any number of moves.
 *
 * Example 1:
 *
 * Input: S = "cba", K = 1
 * Output: "acb"
 * Explanation:
 * In the first move, we move the 1st character ("c") to the end, obtaining the string "bac".
 * In the second move, we move the 1st character ("b") to the end, obtaining the final result "acb".
 *
 * Example 2:
 *
 * Input: S = "baaca", K = 3
 * Output: "aaabc"
 * Explanation:
 * In the first move, we move the 1st character ("b") to the end, obtaining the string "aacab".
 * In the second move, we move the 3rd character ("c") to the end, obtaining the final result "aaabc".
 *
 * Note:
 *
 * 1 <= K <= S.length <= 1000
 * S consists of lowercase letters only.
 */
@RunWith(LeetCodeRunner.class)
public class Q899_OrderlyQueue {

    /**
     * 参考文档 https://www.cnblogs.com/grandyang/p/10995474.html
     * 这题当K == 1 时有S.length() 种排列,
     * 当 K > 1 时数组可以转换为有序数组(直接对其进行排序).
     */
    @Answer
    public String orderlyQueue(String S, int K) {
        if (K == 1) {
            StringBuilder sb = new StringBuilder(S);
            String res = S;
            for (int i = 1; i < S.length(); i++) {
                sb.append(S.charAt(i - 1));
                String str = sb.substring(i);
                if (res.compareTo(str) > 0) {
                    res = str;
                }
            }
            return res;
        } else {
            char[] sc = S.toCharArray();
            Arrays.sort(sc);
            return new String(sc);
        }
    }

    private static class Node {

        char c;
        Node prev, next;
        Node peer;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("cba", 1).expect("acb");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("baaca", 3).expect("aaabc");

}
