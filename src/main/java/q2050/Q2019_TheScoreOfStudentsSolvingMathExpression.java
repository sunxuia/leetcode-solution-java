package q2050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 2019. The Score of Students Solving Math Expression
 * https://leetcode.com/problems/the-score-of-students-solving-math-expression/
 *
 * You are given a string s that contains digits 0-9, addition symbols '+', and multiplication symbols '*' only,
 * representing a valid math expression of single digit numbers (e.g., 3+5*2). This expression was given to n elementary
 * school students. The students were instructed to get the answer of the expression by following this order of
 * operations:
 *
 * 1. Compute multiplication, reading from left to right; Then,
 * 2. Compute addition, reading from left to right.
 *
 * You are given an integer array answers of length n, which are the submitted answers of the students in no particular
 * order. You are asked to grade the answers, by following these rules:
 *
 * - If an answer equals the correct answer of the expression, this student will be rewarded 5 points;
 * - Otherwise, if the answer could be interpreted as if the student applied the operators in the wrong order but had
 * correct arithmetic, this student will be rewarded 2 points;
 * - Otherwise, this student will be rewarded 0 points.
 *
 * Return the sum of the points of the students.
 *
 * Example 1:
 * (图Q2019_PIC.png)
 * Input: s = "7+3*1*2", answers = [20,13,42]
 * Output: 7
 * Explanation: As illustrated above, the correct answer of the expression is 13, therefore one student is rewarded 5
 * points: [20,13,42]
 * A student might have applied the operators in this wrong order: ((7+3)*1)*2 = 20. Therefore one student is rewarded 2
 * points: [20,13,42]
 * The points for the students are: [2,5,0]. The sum of the points is 2+5+0=7.
 *
 * Example 2:
 *
 * Input: s = "3+5*2", answers = [13,0,10,13,13,16,16]
 * Output: 19
 * Explanation: The correct answer of the expression is 13, therefore three students are rewarded 5 points each:
 * [13,0,10,13,13,16,16]
 * A student might have applied the operators in this wrong order: ((3+5)*2 = 16. Therefore two students are rewarded 2
 * points: [13,0,10,13,13,16,16]
 * The points for the students are: [5,0,0,5,5,2,2]. The sum of the points is 5+0+0+5+5+2+2=19.
 *
 * Example 3:
 *
 * Input: s = "6+0*1", answers = [12,9,6,4,8,6]
 * Output: 10
 * Explanation: The correct answer of the expression is 6.
 * If a student had incorrectly done (6+0)*1, the answer would also be 6.
 * By the rules of grading, the students will still be rewarded 5 points (as they got the correct answer), not 2
 * points.
 * The points for the students are: [0,0,5,0,0,5]. The sum of the points is 10.
 *
 * Constraints:
 *
 * 3 <= s.length <= 31
 * s represents a valid expression that contains only digits 0-9, '+', and '*' only.
 * All the integer operands in the expression are in the inclusive range [0, 9].
 * 1 <= The count of all operators ('+' and '*') in the math expression <= 15
 * Test data are generated such that the correct answer of the expression is in the range of [0, 1000].
 * n == answers.length
 * 1 <= n <= 10^4
 * 0 <= answers[i] <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q2019_TheScoreOfStudentsSolvingMathExpression {

    @Answer
    public int scoreOfStudents(String s, int[] answers) {
        final char[] cs = s.toCharArray();
        final int n = cs.length;

        int[][][] cache = new int[n][n][];
        for (int i = 0; i < n; i++) {
            cache[i][i] = new int[]{cs[i] - '0'};
        }
        int[] possibleAnswers = dc(cache, cs, 0, n - 1);

        int[] points = new int[1001];
        for (int possibleAnswer : possibleAnswers) {
            points[possibleAnswer] = 2;
        }

        int correct = getCorrectAnswer(cs);
        points[correct] = 5;

        int res = 0;
        for (int answer : answers) {
            res += points[answer];
        }
        return res;
    }

    /**
     * 分治算法求cs[s:e] 内不同组合方式的结果.
     * 这里如果返回set 则上层遍历set 时耗时会比较久, 因此返回数组.
     */
    private int[] dc(int[][][] cache, char[] cs, int s, int e) {
        if (cache[s][e] != null) {
            return cache[s][e];
        }

        boolean[] set = new boolean[1001];
        int size = 0;
        for (int i = s + 1; i < e; i += 2) {
            int[] left = dc(cache, cs, s, i - 1);
            int[] right = dc(cache, cs, i + 1, e);
            for (int a : left) {
                for (int b : right) {
                    int val = cs[i] == '*' ? a * b : a + b;
                    if (val <= 1000 && !set[val]) {
                        set[val] = true;
                        size++;
                    }
                }
            }
        }

        int[] res = new int[size];
        for (int i = 0, j = 0; i <= 1000; i++) {
            if (set[i]) {
                res[j++] = i;
            }
        }
        cache[s][e] = res;
        return res;
    }

    private int getCorrectAnswer(char[] cs) {
        int correct = 0;
        int t = cs[0] - '0';
        for (int i = 1; i < cs.length; i += 2) {
            if (cs[i] == '+') {
                correct += t;
                t = cs[i + 1] - '0';
            } else {
                t *= cs[i + 1] - '0';
            }
        }
        correct += t;
        return correct;
    }


    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith("7+3*1*2", new int[]{20, 13, 42})
            .expect(7);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith("3+5*2", new int[]{13, 0, 10, 13, 13, 16, 16})
            .expect(19);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith("6+0*1", new int[]{12, 9, 6, 4, 8, 6})
            .expect(10);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith("1+2*3+4", new int[]{13, 21, 11, 15})
            .expect(11);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith("4+8*8+8+8*8+4*4+8*4+8*8+8*8+8", TestDataFileHelper.read(int[].class))
            .expect(4407);

}
