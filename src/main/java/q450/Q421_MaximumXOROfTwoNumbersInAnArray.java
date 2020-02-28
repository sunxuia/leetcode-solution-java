package q450;

import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/
 *
 * Given a non-empty array of numbers, a0, a1, a2, … , an-1, where 0 ≤ ai < 2^31.
 *
 * Find the maximum result of ai XOR aj, where 0 ≤ i, j < n.
 *
 * Could you do this in O(n) runtime?
 *
 * Example:
 *
 * Input: [3, 10, 5, 25, 2, 8]
 *
 * Output: 28
 *
 * Explanation: The maximum result is 5 ^ 25 = 28.
 */
@RunWith(LeetCodeRunner.class)
public class Q421_MaximumXOROfTwoNumbersInAnArray {

    /**
     * https://www.cnblogs.com/grandyang/p/5991530.html
     */
    @Answer
    public int findMaximumXOR(int[] nums) {
        int res = 0, mask = 0;
        for (int i = 31; i >= 0; i--) {
            mask |= (1 << i);
            Set<Integer> s = new HashSet<>();
            for (int num : nums) {
                s.add(num & mask);
            }
            int t = res | (1 << i);
            for (int prefix : s) {
                if (s.contains(t ^ prefix)) {
                    res = t;
                    break;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{3, 10, 5, 25, 2, 8}).expect(28);

}
