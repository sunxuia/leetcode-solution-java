package q2150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Easy] 2119. A Number After a Double Reversal</h3>
 * <a href="https://leetcode.com/problems/a-number-after-a-double-reversal/">
 * https://leetcode.com/problems/a-number-after-a-double-reversal/
 * </a><br/>
 *
 * <p><strong>Reversing</strong> an integer means to reverse all its digits.</p>
 *
 * <ul>
 * 	<li>For example, reversing <code>2021</code> gives <code>1202</code>. Reversing <code>12300</code> gives
 * 	<code>321</code> as the <strong>leading zeros are not retained</strong>.</li>
 * </ul>
 *
 * <p>Given an integer <code>num</code>, <strong>reverse</strong> <code>num</code> to get <code>reversed1</code>,
 * <strong>then reverse</strong> <code>reversed1</code> to get <code>reversed2</code>. Return <code>true</code>
 * <em>if</em> <code>reversed2</code> <em>equals</em> <code>num</code>. Otherwise return <code>false</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> num = 526
 * <strong>Output:</strong> true
 * <strong>Explanation:</strong> Reverse num to get 625, then reverse 625 to get 526, which equals num.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> num = 1800
 * <strong>Output:</strong> false
 * <strong>Explanation:</strong> Reverse num to get 81, then reverse 81 to get 18, which does not equal num.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> num = 0
 * <strong>Output:</strong> true
 * <strong>Explanation:</strong> Reverse num to get 0, then reverse 0 to get 0, which equals num.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>0 &lt;= num &lt;= 10<sup>6</sup></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2119_ANumberAfterADoubleReversal {

    @Answer
    public boolean isSameAfterReversals(int num) {
        return num == 0 || num % 10 != 0;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(526).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(1800).expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create(0).expect(true);

}
