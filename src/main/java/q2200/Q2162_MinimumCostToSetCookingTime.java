package q2200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2162. Minimum Cost to Set Cooking Time</h3>
 * <a href="https://leetcode.com/problems/minimum-cost-to-set-cooking-time/">
 * https://leetcode.com/problems/minimum-cost-to-set-cooking-time/
 * </a><br/>
 *
 * <p>A generic microwave supports cooking times for:</p>
 *
 * <ul>
 * 	<li>at least <code>1</code> second.</li>
 * 	<li>at most <code>99</code> minutes and <code>99</code> seconds.</li>
 * </ul>
 *
 * <p>To set the cooking time, you push <strong>at most four digits</strong>. The microwave normalizes what you push
 * as four digits by <strong>prepending zeroes</strong>. It interprets the <strong>first</strong> two digits as the
 * minutes and the <strong>last</strong> two digits as the seconds. It then <strong>adds</strong> them up as the
 * cooking time. For example,</p>
 *
 * <ul>
 * 	<li>You push <code>9</code> <code>5</code> <code>4</code> (three digits). It is normalized as <code>0954</code>
 * 	and interpreted as <code>9</code> minutes and <code>54</code> seconds.</li>
 * 	<li>You push <code>0</code> <code>0</code> <code>0</code> <code>8</code> (four digits). It is interpreted as
 * 	<code>0</code> minutes and <code>8</code> seconds.</li>
 * 	<li>You push <code>8</code> <code>0</code> <code>9</code> <code>0</code>. It is interpreted as <code>80</code>
 * 	minutes and <code>90</code> seconds.</li>
 * 	<li>You push <code>8</code> <code>1</code> <code>3</code> <code>0</code>. It is interpreted as <code>81</code>
 * 	minutes and <code>30</code> seconds.</li>
 * </ul>
 *
 * <p>You are given integers <code>startAt</code>, <code>moveCost</code>, <code>pushCost</code>, and
 * <code>targetSeconds</code>. <strong>Initially</strong>, your finger is on the digit <code>startAt</code>. Moving
 * the finger above <strong>any specific digit</strong> costs <code>moveCost</code> units of fatigue. Pushing the
 * digit below the finger <strong>once</strong> costs <code>pushCost</code> units of fatigue.</p>
 *
 * <p>There can be multiple ways to set the microwave to cook for <code>targetSeconds</code> seconds but you are
 * interested in the way with the minimum cost.</p>
 *
 * <p>Return <em>the <strong>minimum cost</strong> to set</em> <code>targetSeconds</code> <em>seconds of cooking
 * time</em>.</p>
 *
 * <p>Remember that one minute consists of <code>60</code> seconds.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/12/30/1.png" style="width: 506px; height: 210px;" />
 * <pre>
 * <strong>Input:</strong> startAt = 1, moveCost = 2, pushCost = 1, targetSeconds = 600
 * <strong>Output:</strong> 6
 * <strong>Explanation:</strong> The following are the possible ways to set the cooking time.
 * - 1 0 0 0, interpreted as 10 minutes and 0 seconds.
 * &nbsp; The finger is already on digit 1, pushes 1 (with cost 1), moves to 0 (with cost 2), pushes 0 (with cost 1), pushes 0 (with cost 1), and pushes 0 (with cost 1).
 * &nbsp; The cost is: 1 + 2 + 1 + 1 + 1 = 6. This is the minimum cost.
 * - 0 9 6 0, interpreted as 9 minutes and 60 seconds. That is also 600 seconds.
 * &nbsp; The finger moves to 0 (with cost 2), pushes 0 (with cost 1), moves to 9 (with cost 2), pushes 9 (with cost 1), moves to 6 (with cost 2), pushes 6 (with cost 1), moves to 0 (with cost 2), and pushes 0 (with cost 1).
 * &nbsp; The cost is: 2 + 1 + 2 + 1 + 2 + 1 + 2 + 1 = 12.
 * - 9 6 0, normalized as 0960 and interpreted as 9 minutes and 60 seconds.
 * &nbsp; The finger moves to 9 (with cost 2), pushes 9 (with cost 1), moves to 6 (with cost 2), pushes 6 (with cost 1), moves to 0 (with cost 2), and pushes 0 (with cost 1).
 * &nbsp; The cost is: 2 + 1 + 2 + 1 + 2 + 1 = 9.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/12/30/2.png" style="width: 505px; height: 73px;" />
 * <pre>
 * <strong>Input:</strong> startAt = 0, moveCost = 1, pushCost = 2, targetSeconds = 76
 * <strong>Output:</strong> 6
 * <strong>Explanation:</strong> The optimal way is to push two digits: 7 6, interpreted as 76 seconds.
 * The finger moves to 7 (with cost 1), pushes 7 (with cost 2), moves to 6 (with cost 1), and pushes 6 (with cost 2). The total cost is: 1 + 2 + 1 + 2 = 6
 * Note other possible ways are 0076, 076, 0116, and 116, but none of them produces the minimum cost.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>0 &lt;= startAt &lt;= 9</code></li>
 * 	<li><code>1 &lt;= moveCost, pushCost &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>1 &lt;= targetSeconds &lt;= 6039</code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2162_MinimumCostToSetCookingTime {

    @Answer
    public int minCostSetTime(int startAt, int moveCost, int pushCost, int targetSeconds) {
        this.moveCost = moveCost;
        this.pushCost = pushCost;
        if (targetSeconds < 10) {
            return choose(targetSeconds, startAt);
        }
        if (targetSeconds < 60) {
            return second10Cost(targetSeconds, startAt);
        }
        if (targetSeconds < 100) {
            int s10 = second10Cost(targetSeconds, startAt);
            int m1 = minute1Cost(targetSeconds, startAt);
            return Math.min(s10, m1);
        }
        if (targetSeconds < 600) {
            return minute1Cost(targetSeconds, startAt);
        }
        if (targetSeconds < 640) {
            int m1 = minute1Cost(targetSeconds, startAt);
            int m10 = minute10Cost(targetSeconds, startAt);
            return Math.min(m1, m10);
        }
        return minute10Cost(targetSeconds, startAt);
    }

    private int moveCost, pushCost;

    private static final int MAX = Integer.MAX_VALUE / 2;

    private int minute10Cost(int target, int startAt) {
        int res = MAX;
        for (int i = 0; i * 600 <= target && i < 10; i++) {
            if (target - i * 600 > 639) {
                continue;
            }
            int cost = choose(i, startAt);
            int minute1Cost = minute1Cost(target - i * 600, i);
            res = Math.min(res, cost + minute1Cost);
        }
        return res;
    }

    private int choose(int targetNum, int startAt) {
        return startAt == targetNum ? pushCost : moveCost + pushCost;
    }

    private int minute1Cost(int target, int startAt) {
        int res = MAX;
        for (int i = 0; i * 60 <= target && i < 10; i++) {
            if (target - i * 60 > 99) {
                continue;
            }
            int cost = choose(i, startAt);
            int secondCost = second10Cost(target - i * 60, i);
            res = Math.min(res, cost + secondCost);
        }
        return res;
    }

    private int second10Cost(int target, int startAt) {
        int num = target / 10;
        int cost = choose(num, startAt);
        return cost + choose(target % 10, num);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(1, 2, 1, 600).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(0, 1, 2, 76).expect(6);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(9, 100000, 1, 6039).expect(4);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(3, 12, 1, 478).expect(39);

}
