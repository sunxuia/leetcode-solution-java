package q2050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 2011. Final Value of Variable After Performing Operations
 * https://leetcode.com/problems/final-value-of-variable-after-performing-operations/
 *
 * There is a programming language with only four operations and one variable X:
 *
 * ++X and X++ increments the value of the variable X by 1.
 * --X and X-- decrements the value of the variable X by 1.
 *
 * Initially, the value of X is 0.
 *
 * Given an array of strings operations containing a list of operations, return the final value of X after performing
 * all the operations.
 *
 * Example 1:
 *
 * Input: operations = ["--X","X++","X++"]
 * Output: 1
 * Explanation: The operations are performed as follows:
 * Initially, X = 0.
 * --X: X is decremented by 1, X =  0 - 1 = -1.
 * X++: X is incremented by 1, X = -1 + 1 =  0.
 * X++: X is incremented by 1, X =  0 + 1 =  1.
 *
 * Example 2:
 *
 * Input: operations = ["++X","++X","X++"]
 * Output: 3
 * Explanation: The operations are performed as follows:
 * Initially, X = 0.
 * ++X: X is incremented by 1, X = 0 + 1 = 1.
 * ++X: X is incremented by 1, X = 1 + 1 = 2.
 * X++: X is incremented by 1, X = 2 + 1 = 3.
 *
 * Example 3:
 *
 * Input: operations = ["X++","++X","--X","X--"]
 * Output: 0
 * Explanation: The operations are performed as follows:
 * Initially, X = 0.
 * X++: X is incremented by 1, X = 0 + 1 = 1.
 * ++X: X is incremented by 1, X = 1 + 1 = 2.
 * --X: X is decremented by 1, X = 2 - 1 = 1.
 * X--: X is decremented by 1, X = 1 - 1 = 0.
 *
 * Constraints:
 *
 * 1 <= operations.length <= 100
 * operations[i] will be either "++X", "X++", "--X", or "X--".
 */
@RunWith(LeetCodeRunner.class)
public class Q2011_FinalValueOfVariableAfterPerformingOperations {

    @Answer
    public int finalValueAfterOperations(String[] operations) {
        int x = 0;
        for (String operation : operations) {
            switch (operation) {
                case "++X":
                case "X++":
                    x++;
                    break;
                case "--X":
                case "X--":
                    x--;
                    break;
                default:
            }
        }
        return x;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new String[]{"--X", "X++", "X++"}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new String[]{"++X", "++X", "X++"}).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new String[]{"X++", "++X", "--X", "X--"}).expect(0);

}
