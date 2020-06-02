package q800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/swim-in-rising-water/
 *
 * On the first row, we write a 0. Now in every subsequent row, we look at the previous row and replace each
 * occurrence of 0 with 01, and each occurrence of 1 with 10.
 *
 * Given row N and index K, return the K-th indexed symbol in row N. (The values of K are 1-indexed.) (1 indexed).
 *
 * Examples:
 * Input: N = 1, K = 1
 * Output: 0
 *
 * Input: N = 2, K = 1
 * Output: 0
 *
 * Input: N = 2, K = 2
 * Output: 1
 *
 * Input: N = 4, K = 5
 * Output: 1
 *
 * Explanation:
 * row 1: 0
 * row 2: 01
 * row 3: 0110
 * row 4: 01101001
 *
 * Note:
 *
 * 1. N will be an integer in the range [1, 30].
 * 2. K will be an integer in the range [1, 2^(N-1)].
 */
@RunWith(LeetCodeRunner.class)
public class Q779_KthSymbolInGrammar {

    @Answer
    public int kthGrammar(int N, int K) {
        if (K == 1) {
            return 0;
        }
        int prev = kthGrammar(N - 1, (K + 1) / 2);
        return K % 2 == 1 ? prev : prev ^ 1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(1, 1).expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(2, 1).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(2, 2).expect(1);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(4, 5).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(3, 3).expect(1);

}
