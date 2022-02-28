package q1800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1769. Minimum Number of Operations to Move All Balls to Each Box
 * https://leetcode.com/problems/minimum-number-of-operations-to-move-all-balls-to-each-box/
 *
 * You have n boxes. You are given a binary string boxes of length n, where boxes[i] is '0' if the ith box is empty, and
 * '1' if it contains one ball.
 *
 * In one operation, you can move one ball from a box to an adjacent box. Box i is adjacent to box j if abs(i - j) == 1.
 * Note that after doing so, there may be more than one ball in some boxes.
 *
 * Return an array answer of size n, where answer[i] is the minimum number of operations needed to move all the balls to
 * the ith box.
 *
 * Each answer[i] is calculated considering the initial state of the boxes.
 *
 * Example 1:
 *
 * Input: boxes = "110"
 * Output: [1,1,3]
 * Explanation: The answer for each box is as follows:
 * 1) First box: you will have to move one ball from the second box to the first box in one operation.
 * 2) Second box: you will have to move one ball from the first box to the second box in one operation.
 * 3) Third box: you will have to move one ball from the first box to the third box in two operations, and move one ball
 * from the second box to the third box in one operation.
 *
 * Example 2:
 *
 * Input: boxes = "001011"
 * Output: [11,8,5,4,3,4]
 *
 * Constraints:
 *
 * n == boxes.length
 * 1 <= n <= 2000
 * boxes[i] is either '0' or '1'.
 */
@RunWith(LeetCodeRunner.class)
public class Q1769_MinimumNumberOfOperationsToMoveAllBallsToEachBox {

    @Answer
    public int[] minOperations(String boxes) {
        final char[] cs = boxes.toCharArray();
        final int n = cs.length;

        int[] leftMoves = new int[n + 1];
        for (int i = 0, count = 0; i < n; i++) {
            leftMoves[i + 1] = leftMoves[i] + count;
            count += cs[i] - '0';
        }
        int[] rightMoves = new int[n + 1];
        for (int i = n - 1, count = 0; i >= 0; i--) {
            rightMoves[i] = rightMoves[i + 1] + count;
            count += cs[i] - '0';
        }

        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = leftMoves[i + 1] + rightMoves[i];
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("110").expect(new int[]{1, 1, 3});

    @TestData
    public DataExpectation example2 = DataExpectation.create("001011").expect(new int[]{11, 8, 5, 4, 3, 4});

}
