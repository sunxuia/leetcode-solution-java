package q2050;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 2024. Maximize the Confusion of an Exam
 * https://leetcode.com/problems/maximize-the-confusion-of-an-exam/
 *
 * A teacher is writing a test with n true/false questions, with 'T' denoting true and 'F' denoting false. He wants to
 * confuse the students by maximizing the number of consecutive questions with the same answer (multiple trues or
 * multiple falses in a row).
 *
 * You are given a string answerKey, where answerKey[i] is the original answer to the ith question. In addition, you are
 * given an integer k, the maximum number of times you may perform the following operation:
 *
 * Change the answer key for any question to 'T' or 'F' (i.e., set answerKey[i] to 'T' or 'F').
 *
 * Return the maximum number of consecutive 'T's or 'F's in the answer key after performing the operation at most k
 * times.
 *
 * Example 1:
 *
 * Input: answerKey = "TTFF", k = 2
 * Output: 4
 * Explanation: We can replace both the 'F's with 'T's to make answerKey = "TTTT".
 * There are four consecutive 'T's.
 *
 * Example 2:
 *
 * Input: answerKey = "TFFT", k = 1
 * Output: 3
 * Explanation: We can replace the first 'T' with an 'F' to make answerKey = "FFFT".
 * Alternatively, we can replace the second 'T' with an 'F' to make answerKey = "TFFF".
 * In both cases, there are three consecutive 'F's.
 *
 * Example 3:
 *
 * Input: answerKey = "TTFTTFTT", k = 1
 * Output: 5
 * Explanation: We can replace the first 'F' to make answerKey = "TTTTTFTT"
 * Alternatively, we can replace the second 'F' to make answerKey = "TTFTTTTT".
 * In both cases, there are five consecutive 'T's.
 *
 * Constraints:
 *
 * n == answerKey.length
 * 1 <= n <= 5 * 10^4
 * answerKey[i] is either 'T' or 'F'
 * 1 <= k <= n
 */
@RunWith(LeetCodeRunner.class)
public class Q2024_MaximizeTheConfusionOfAnExam {

    @Answer
    public int maxConsecutiveAnswers(String answerKey, int k) {
        int t = check(answerKey, k, 'T');
        int f = check(answerKey, k, 'F');
        return Math.max(t, f);
    }

    private int check(String answerKey, int k, char expect) {
        int max = 0, curr = 0;
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < answerKey.length(); i++) {
            if (answerKey.charAt(i) == expect) {
                if (queue.isEmpty() || queue.getLast() < 0) {
                    queue.addLast(1);
                } else {
                    queue.addLast(queue.removeLast() + 1);
                }
            } else {
                if (k == 0) {
                    if (queue.getFirst() > 0) {
                        curr -= queue.removeFirst();
                    }
                    int gap = queue.removeFirst();
                    if (gap < -1) {
                        queue.addFirst(gap + 1);
                    }
                    curr--;
                    k++;
                }
                k--;
                if (queue.isEmpty() || queue.getLast() > 0) {
                    queue.addLast(-1);
                } else {
                    queue.addLast(queue.removeLast() - 1);
                }
            }
            curr++;
            max = Math.max(max, curr);
        }
        return max;
    }

    /**
     * 根据leetcode 上最快的解法改写的做法, 通过计数的方式来计算.
     */
    @Answer
    public int maxConsecutiveAnswers2(String answerKey, int k) {
        int res = 0;
        // 表示符合条件的 answerKey[s:e] 窗口
        int s = 0, e = 0;
        // 表示T和F 的数量
        int t = 0, f = 0;
        while (e < answerKey.length()) {
            if (answerKey.charAt(e++) == 'T') {
                t++;
            } else {
                f++;
            }
            // 当T和F 都>k, 则无法通过替换T或F 来满足题意
            while (t > k && f > k) {
                if (answerKey.charAt(s++) == 'T') {
                    t--;
                } else {
                    f--;
                }
            }
            res = Math.max(res, t + f);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("TTFF", 2).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("TFFT", 1).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("TTFTTFTT", 1).expect(5);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(
            "FTFFTFTFTTFTTFTTFFTTFFTTTTTFTTTFTFFTTFFFFFTTTTFTTTTTTTTTFTTFFTTFTFFTTTFFFFFTTTFFTTTTFTFTFFTTFTTTTTTF",
            32).expect(85);

}
