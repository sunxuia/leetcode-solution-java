package q1000;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 954. Array of Doubled Pairs
 * https://leetcode.com/problems/array-of-doubled-pairs/
 *
 * Given an array of integers A with even length, return true if and only if it is possible to reorder it such that
 * A[2 * i + 1] = 2 * A[2 * i] for every 0 <= i < len(A) / 2.
 *
 * Example 1:
 *
 * Input: [3,1,3,6]
 * Output: false
 *
 * Example 2:
 *
 * Input: [2,1,2,6]
 * Output: false
 *
 * Example 3:
 *
 * Input: [4,-2,2,-4]
 * Output: true
 * Explanation: We can take two groups, [-2,-4] and [2,4] to form [-2,-4,2,4] or [2,4,-2,-4].
 *
 * Example 4:
 *
 * Input: [1,2,4,16,8,4]
 * Output: false
 *
 * Note:
 *
 * 0 <= A.length <= 30000
 * A.length is even
 * -100000 <= A[i] <= 100000
 */
@RunWith(LeetCodeRunner.class)
public class Q954_ArrayOfDoubledPairs {

    @Answer
    public boolean canReorderDoubled(int[] A) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int a : A) {
            counts.put(a, counts.getOrDefault(a, 0) + 1);
        }

        Integer zeroCount = counts.remove(0);
        if (zeroCount != null && zeroCount % 2 == 1) {
            return false;
        }

        while (!counts.isEmpty()) {
            int key = counts.keySet().iterator().next();
            while (key % 2 == 0 && counts.containsKey(key / 2)) {
                key /= 2;
            }
            for (int curr = counts.remove(key);
                    curr != 0;
                    curr = counts.remove(key)) {
                int other = counts.getOrDefault(key * 2, 0);
                if (curr > other) {
                    return false;
                }
                key *= 2;
                counts.put(key, other - curr);
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 1, 3, 6}).expect(false);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 1, 2, 6}).expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{4, -2, 2, -4}).expect(true);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{1, 2, 4, 16, 8, 4}).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{2, 1, 2, 1, 1, 1, 2, 2}).expect(true);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{10, 20, 40, 80}).expect(true);

}
