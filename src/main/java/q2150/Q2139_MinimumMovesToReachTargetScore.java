package q2150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2139. Minimum Moves to Reach Target Score</h3>
 * <a href="https://leetcode.com/problems/minimum-moves-to-reach-target-score/">
 * https://leetcode.com/problems/minimum-moves-to-reach-target-score/
 * </a><br/>
 *
 * <p>You are playing a game with integers. You start with the integer <code>1</code> and you want to reach the integer
 * <code>target</code>.</p>
 *
 * <p>In one move, you can either:</p>
 *
 * <ul>
 * 	<li><strong>Increment</strong> the current integer by one (i.e., <code>x = x + 1</code>).</li>
 * 	<li><strong>Double</strong> the current integer (i.e., <code>x = 2 * x</code>).</li>
 * </ul>
 *
 * <p>You can use the <strong>increment</strong> operation <strong>any</strong> number of times, however, you can
 * only use the <strong>double</strong> operation <strong>at most</strong> <code>maxDoubles</code> times.</p>
 *
 * <p>Given the two integers <code>target</code> and <code>maxDoubles</code>, return <em>the minimum number of moves
 * needed to reach </em><code>target</code><em> starting with </em><code>1</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> target = 5, maxDoubles = 0
 * <strong>Output:</strong> 4
 * <strong>Explanation:</strong> Keep incrementing by 1 until you reach target.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> target = 19, maxDoubles = 2
 * <strong>Output:</strong> 7
 * <strong>Explanation:</strong> Initially, x = 1
 * Increment 3 times so x = 4
 * Double once so x = 8
 * Increment once so x = 9
 * Double again so x = 18
 * Increment once so x = 19
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> target = 10, maxDoubles = 4
 * <strong>Output:</strong> 4
 * <strong>Explanation:</strong><b> </b>Initially, x = 1
 * Increment once so x = 2
 * Double once so x = 4
 * Increment once so x = 5
 * Double again so x = 10
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= target &lt;= 10<sup>9</sup></code></li>
 * 	<li><code>0 &lt;= maxDoubles &lt;= 100</code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2139_MinimumMovesToReachTargetScore {

    @Answer
    public int minMoves(int target, int maxDoubles) {
        int moves = 0;
        while (maxDoubles > 0 && target > 1) {
            if (target % 2 == 1) {
                moves++;
                target--;
            }
            maxDoubles--;
            target /= 2;
            moves++;
        }
        moves += target - 1;
        return moves;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(5, 0).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(19, 2).expect(7);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(10, 4).expect(4);

}
