package q850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/push-dominoes/
 *
 * There are N dominoes in a line, and we place each domino vertically upright.
 *
 * In the beginning, we simultaneously push some of the dominoes either to the left or to the right.
 *
 * (图 Q838_PIC.png)
 *
 * After each second, each domino that is falling to the left pushes the adjacent domino on the left.
 *
 * Similarly, the dominoes falling to the right push their adjacent dominoes standing on the right.
 *
 * When a vertical domino has dominoes falling on it from both sides, it stays still due to the balance of the forces.
 *
 * For the purposes of this question, we will consider that a falling domino expends no additional force to a falling
 * or already fallen domino.
 *
 * Given a string "S" representing the initial state. S[i] = 'L', if the i-th domino has been pushed to the left;
 * S[i] = 'R', if the i-th domino has been pushed to the right; S[i] = '.', if the i-th domino has not been pushed.
 *
 * Return a string representing the final state.
 *
 * Example 1:
 *
 * Input: ".L.R...LR..L.."
 * Output: "LL.RR.LLRRLL.."
 *
 * Example 2:
 *
 * Input: "RR.L"
 * Output: "RR.L"
 * Explanation: The first domino expends no additional force on the second domino.
 *
 * Note:
 *
 * 0 <= N <= 10^5
 * String dominoes contains only 'L', 'R' and '.'
 */
@RunWith(LeetCodeRunner.class)
public class Q838_PushDominoes {

    @Answer
    public String pushDominoes(String dominoes) {
        final int n = dominoes.length();
        char[] sc = dominoes.toCharArray();
        int[] fromLeft = new int[n];
        for (int i = 0; i < n; i++) {
            if (sc[i] == 'R') {
                fromLeft[i] = i;
            } else if (sc[i] == 'L' || i == 0) {
                fromLeft[i] = -1;
            } else {
                fromLeft[i] = fromLeft[i - 1];
            }
        }

        int[] fromRight = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            if (sc[i] == 'L') {
                fromRight[i] = i;
            } else if (sc[i] == 'R' || i == n - 1) {
                fromRight[i] = -1;
            } else {
                fromRight[i] = fromRight[i + 1];
            }
        }

        for (int i = 0; i < n; i++) {
            if (fromLeft[i] == -1) {
                if (fromRight[i] != -1) {
                    sc[i] = 'L';
                }
            } else {
                if (fromRight[i] == -1 || i - fromLeft[i] < fromRight[i] - i) {
                    sc[i] = 'R';
                } else if (i - fromLeft[i] > fromRight[i] - i) {
                    sc[i] = 'L';
                }
            }
        }
        return new String(sc);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(".L.R...LR..L..").expect("LL.RR.LLRRLL..");

    @TestData
    public DataExpectation example2 = DataExpectation.create("RR.L").expect("RR.L");

    @TestData
    public DataExpectation border0 = DataExpectation.create("").expect("");

    @TestData
    public DataExpectation border1 = DataExpectation.create("L").expect("L");

}
