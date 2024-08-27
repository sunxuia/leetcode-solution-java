package q2150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2140. Solving Questions With Brainpower</h3>
 * <a href="https://leetcode.com/problems/solving-questions-with-brainpower/">
 * https://leetcode.com/problems/solving-questions-with-brainpower/
 * </a><br/>
 *
 * <p>You are given a <strong>0-indexed</strong> 2D integer array <code>questions</code> where <code>questions[i] =
 * [points<sub>i</sub>, brainpower<sub>i</sub>]</code>.</p>
 *
 * <p>The array describes the questions of an exam, where you have to process the questions <strong>in order</strong>
 * (i.e., starting from question <code>0</code>) and make a decision whether to <strong>solve</strong> or
 * <strong>skip</strong> each question. Solving question <code>i</code> will <strong>earn</strong> you
 * <code>points<sub>i</sub></code> points but you will be <strong>unable</strong> to solve each of the next
 * <code>brainpower<sub>i</sub></code> questions. If you skip question <code>i</code>, you get to make the decision on
 * the next question.</p>
 *
 * <ul>
 * 	<li>For example, given <code>questions = [[3, 2], [4, 3], [4, 4], [2, 5]]</code>:
 *
 * 	<ul>
 * 		<li>If question <code>0</code> is solved, you will earn <code>3</code> points but you will be unable to solve
 * 		questions <code>1</code> and <code>2</code>.</li>
 * 		<li>If instead, question <code>0</code> is skipped and question <code>1</code> is solved, you will earn
 * 		<code>4</code> points but you will be unable to solve questions <code>2</code> and <code>3</code>.</li>
 * 	</ul>
 * 	</li>
 * </ul>
 *
 * <p>Return <em>the <strong>maximum</strong> points you can earn for the exam</em>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> questions = [[3,2],[4,3],[4,4],[2,5]]
 * <strong>Output:</strong> 5
 * <strong>Explanation:</strong> The maximum points can be earned by solving questions 0 and 3.
 * - Solve question 0: Earn 3 points, will be unable to solve the next 2 questions
 * - Unable to solve questions 1 and 2
 * - Solve question 3: Earn 2 points
 * Total points earned: 3 + 2 = 5. There is no other way to earn 5 or more points.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> questions = [[1,1],[2,2],[3,3],[4,4],[5,5]]
 * <strong>Output:</strong> 7
 * <strong>Explanation:</strong> The maximum points can be earned by solving questions 1 and 4.
 * - Skip question 0
 * - Solve question 1: Earn 2 points, will be unable to solve the next 2 questions
 * - Unable to solve questions 2 and 3
 * - Solve question 4: Earn 5 points
 * Total points earned: 2 + 5 = 7. There is no other way to earn 7 or more points.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= questions.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>questions[i].length == 2</code></li>
 * 	<li><code>1 &lt;= points<sub>i</sub>, brainpower<sub>i</sub> &lt;= 10<sup>5</sup></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2140_SolvingQuestionsWithBrainpower {

    @Answer
    public long mostPoints(int[][] questions) {
        final int n = questions.length;
        // dp[i] 表示问题子集 questions[i,n) 可取得的最大分数.
        long[] dp = new long[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            int from = Math.min(n, i + questions[i][1] + 1);
            dp[i] = Math.max(dp[i + 1], dp[from] + questions[i][0]);
        }
        return dp[0];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{3, 2}, {4, 3}, {4, 4}, {2, 5}})
            .expect(5L);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{1, 1}, {2, 2}, {3, 3}, {4, 4}, {5, 5}})
            .expect(7L);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[][]{{21, 5}, {92, 3}, {74, 2}, {39, 4}, {58, 2}, {5, 5}, {49, 4}, {65, 3}})
            .expect(157L);

}
