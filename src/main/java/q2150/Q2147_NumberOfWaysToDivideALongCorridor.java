package q2150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Hard] 2147. Number of Ways to Divide a Long Corridor</h3>
 * <a href="https://leetcode.com/problems/number-of-ways-to-divide-a-long-corridor/">
 * https://leetcode.com/problems/number-of-ways-to-divide-a-long-corridor/
 * </a><br/>
 *
 * <p>Along a long library corridor, there is a line of seats and decorative plants. You are given a
 * <strong>0-indexed</strong> string <code>corridor</code> of length <code>n</code> consisting of letters
 * <code>&#39;S&#39;</code> and <code>&#39;P&#39;</code> where each <code>&#39;S&#39;</code> represents a seat and each
 * <code>&#39;P&#39;</code> represents a plant.</p>
 *
 * <p>One room divider has <strong>already</strong> been installed to the left of index <code>0</code>, and
 * <strong>another</strong> to the right of index <code>n - 1</code>. Additional room dividers can be installed. For
 * each position between indices <code>i - 1</code> and <code>i</code> (<code>1 &lt;= i &lt;= n - 1</code>), at most
 * one
 * divider can be installed.</p>
 *
 * <p>Divide the corridor into non-overlapping sections, where each section has <strong>exactly two seats</strong> with
 * any number of plants. There may be multiple ways to perform the division. Two ways are <strong>different</strong> if
 * there is a position with a room divider installed in the first way but not in the second way.</p>
 *
 * <p>Return <em>the number of ways to divide the corridor</em>. Since the answer may be very large, return it
 * <strong>modulo</strong> <code>10<sup>9</sup> + 7</code>. If there is no way, return <code>0</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/12/04/1.png" style="width: 410px; height: 199px;" />
 * <pre>
 * <strong>Input:</strong> corridor = &quot;SSPPSPS&quot;
 * <strong>Output:</strong> 3
 * <strong>Explanation:</strong> There are 3 different ways to divide the corridor.
 * The black bars in the above image indicate the two room dividers already installed.
 * Note that in each of the ways, <strong>each</strong> section has exactly <strong>two</strong> seats.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/12/04/2.png" style="width: 357px; height: 68px;" />
 * <pre>
 * <strong>Input:</strong> corridor = &quot;PPSPSP&quot;
 * <strong>Output:</strong> 1
 * <strong>Explanation:</strong> There is only 1 way to divide the corridor, by not installing any additional dividers.
 * Installing any would create some section that does not have exactly two seats.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/12/12/3.png" style="width: 115px; height: 68px;" />
 * <pre>
 * <strong>Input:</strong> corridor = &quot;S&quot;
 * <strong>Output:</strong> 0
 * <strong>Explanation:</strong> There is no way to divide the corridor because there will always be a section that does not have exactly two seats.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>n == corridor.length</code></li>
 * 	<li><code>1 &lt;= n &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>corridor[i]</code> is either <code>&#39;S&#39;</code> or <code>&#39;P&#39;</code>.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2147_NumberOfWaysToDivideALongCorridor {

    @Answer
    public int numberOfWays(String corridor) {
        final long mod = 10_0000_0007L;
        long ways = 1;
        int totalSeats = 0, seats = 0, planets = 0;
        for (int i = 0; i < corridor.length(); i++) {
            if (corridor.charAt(i) == 'S') {
                totalSeats++;
                seats++;
            } else {
                planets++;
            }
            if (seats == 3) {
                ways = (ways * (planets + 1)) % mod;
                seats = 1;
            }
            if (seats != 2) {
                planets = 0;
            }
        }
        if (totalSeats < 2 || totalSeats % 2 == 1) {
            return 0;
        }
        return (int) ways;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("SSPPSPS").expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create("PPSPSP").expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.create("S").expect(0);

    @TestData
    public DataExpectation border = DataExpectation.create("P").expect(0);

    @TestData
    public DataExpectation overflow = DataExpectation
            .create("PPPPPPPSPPPSPPPPSPPPSPPPPPSPPPSPPSPPSPPPPPSPSPPPPPSPPSPPPPPSPPSPPSPPPSPPPPSPPPPSPPPPPSPSPPPPSPSPPPSPPPPSPPPPPSPSPPSPPPPSPPSPPSPPSPPPSPPSPSPPSSSS")
            .expect(18335643);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("SS").expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation.create("SPSPPSSPSSSS").expect(6);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .create("SPPPPPPPSPPPSPSSSPPPPPPPPPPPPPPPPPSPPPPPPPPPPPPPPPPSPPPPPSPSPPPPPPSPSPPSPSPPPSPSPPSSPPPPPSPPSSPP")
            .expect(0);

}
