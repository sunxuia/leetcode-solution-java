package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/zuma-game/
 *
 * Think about Zuma Game. You have a row of balls on the table, colored red(R), yellow(Y), blue(B), green(G), and
 * white(W). You also have several balls in your hand.
 *
 * Each time, you may choose a ball in your hand, and insert it into the row (including the leftmost place and
 * rightmost place). Then, if there is a group of 3 or more balls in the same color touching, remove these balls.
 * Keep doing this until no more balls can be removed.
 *
 * Find the minimal balls you have to insert to remove all the balls on the table. If you cannot remove all the
 * balls, output -1.
 *
 *
 *
 * Example 1:
 * z`
 * Input: board = "WRRBBW", hand = "RB"
 * Output: -1
 * Explanation: WRRBBW -> WRR[R]BBW -> WBBW -> WBB[B]W -> WW
 *
 * Example 2:
 *
 * Input: board = "WWRRBBWW", hand = "WRBRW"
 * Output: 2
 * Explanation: WWRRBBWW -> WWRR[R]BBWW -> WWBBWW -> WWBB[B]WW -> WWWW -> empty
 *
 * Example 3:
 *
 * Input: board = "G", hand = "GGGGG"
 * Output: 2
 * Explanation: G -> G[G] -> GG[G] -> empty
 *
 * Example 4:
 *
 * Input: board = "RBYYBBRRB", hand = "YRBGB"
 * Output: 3
 * Explanation: RBYYBBRRB -> RBYY[Y]BBRRB -> RBBBRRB -> RRRB -> B -> B[B] -> BB[B] -> empty
 *
 *
 *
 * Constraints:
 *
 * You may assume that the initial row of balls on the table won’t have any 3 or more consecutive balls with the
 * same color.
 * The number of balls on the table won't exceed 16, and the string represents these balls is called "board" in
 * the input.
 * The number of balls in your hand won't exceed 5, and the string represents these balls is called "hand" in the
 * input.
 * Both input strings will be non-empty and only contain characters 'R','Y','B','G','W'.
 */
@RunWith(LeetCodeRunner.class)
public class Q488_ZumaGame {

    // 暴力破解, 这种方式可以通过OJ 不过很慢
    @Answer
    public int findMinStep(String board, String hand) {
        int[] handCount = new int[128];
        for (int i = 0; i < hand.length(); i++) {
            handCount[hand.charAt(i)]++;
        }
        return eliminate(board, handCount);
    }

    private int eliminate(String board, int[] handCount) {
        StringBuilder sb = new StringBuilder(board);
        removeConsecutive(sb);
        if (sb.length() == 0) {
            return 0;
        }
        int res = -1;
        for (char c : new char[]{'R', 'Y', 'B', 'G', 'W'}) {
            if (handCount[c] > 0) {
                handCount[c]--;
                for (int i = 0; i <= sb.length(); i++) {
                    if (i >= sb.length() - 1 || sb.charAt(i) != sb.charAt(i + 1)) {
                        sb.insert(i, c);
                        int e = eliminate(sb.toString(), handCount);
                        if (e >= 0) {
                            res = res == -1 ? 1 + e : Math.min(res, 1 + e);
                        }
                        sb.deleteCharAt(i);
                    }
                }
                handCount[c]++;
            }
        }
        return res;
    }

    private void removeConsecutive(StringBuilder sb) {
        boolean hasConsecutive;
        do {
            hasConsecutive = false;
            char lastChar = 'X';
            int count = 0;
            for (int i = sb.length() - 1; i >= 0; i--) {
                char c = sb.charAt(i);
                if (c == lastChar) {
                    count++;
                } else {
                    if (count >= 3) {
                        hasConsecutive = true;
                        sb.delete(i + 1, i + 1 + count);
                    }
                    count = 1;
                    lastChar = c;
                }
            }
            if (count >= 3) {
                sb.delete(0, count);
            }
        } while (hasConsecutive);
    }

    // LeetCode 上这种方式比较快
    @Answer
    public int findMinStep2(String board, String hand) {
        int[] handCount = new int[128];
        for (int i = 0; i < hand.length(); i++) {
            handCount[hand.charAt(i)]++;
        }
        return aux(board, handCount);
    }

    private int aux(String s, int[] c) {
        if (s.isEmpty()) {
            return 0;
        }
        // 最坏情况, 每个字符都需要插入额外2 个字符, +1 用于下面的边界判断
        int res = 2 * s.length() + 1;
        for (int i = 0; i < s.length(); ) {
            int j = i++;
            while (i < s.length() && s.charAt(i) == s.charAt(j)) {
                i++;
            }
            int inc = 3 - i + j;
            if (c[s.charAt(j)] >= inc) {
                int used = inc <= 0 ? 0 : inc;
                c[s.charAt(j)] -= used;
                // 在这里插入字符并消去>3 的重复字符, 由此提升了性能
                int temp = aux(s.substring(0, j) + s.substring(i), c);
                if (temp >= 0) {
                    res = Math.min(res, used + temp);
                }
                c[s.charAt(j)] += used;
            }
        }
        return res == 2 * s.length() + 1 ? -1 : res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("WRRBBW", "RB").expect(-1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("WWRRBBWW", "WRBRW").expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("G", "GGGGG").expect(2);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith("RBYYBBRRB", "YRBGB").expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("WR", "WWRR").expect(4);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("RRWWRRBBRR", "WB").expect(2);

    @TestData
    public DataExpectation normal3 = DataExpectation.createWith("WWGWGW", "GWBWR").expect(3);

}
