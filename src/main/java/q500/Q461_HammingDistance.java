package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/hamming-distance/
 *
 * The Hamming distance between two integers is the number of positions at which the corresponding bits are different.
 *
 * Given two integers x and y, calculate the Hamming distance.
 *
 * Note:
 * 0 ≤ x, y < 231.
 *
 * Example:
 *
 * Input: x = 1, y = 4
 *
 * Output: 2
 *
 * Explanation:
 * 1   (0 0 0 1)
 * 4   (0 1 0 0)
 * ↑   ↑
 *
 * The above arrows point to positions where the corresponding bits are different.
 */
@RunWith(LeetCodeRunner.class)
public class Q461_HammingDistance {

    @Answer
    public int hammingDistance(int x, int y) {
        int res = 0;
        while (x != 0 || y != 0) {
            res += (x & 1) ^ (y & 1);
            x >>= 1;
            y >>= 1;
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(1, 4).expect(2);

}
