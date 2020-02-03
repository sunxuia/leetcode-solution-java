package q150;

import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/longest-consecutive-sequence/
 *
 * Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
 *
 * Your algorithm should run in O(n) complexity.
 *
 * Example:
 *
 * Input: [100, 4, 200, 1, 3, 2]
 * Output: 4
 * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
 */
@RunWith(LeetCodeRunner.class)
public class Q128_LongestConsecutiveSequence {

    @Answer
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>(nums.length);
        for (int num : nums) {
            set.add(num);
        }
        int res = 0;
        for (int num : nums) {
            if (!set.contains(num - 1)) {
                int count = 1;
                while (set.contains(num + count)) {
                    count++;
                }
                res = Math.max(res, count);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{100, 4, 200, 1, 3, 2}).expect(4);

    @TestData
    public DataExpectation boder = DataExpectation.create(new int[]{}).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{0}).expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{-1, 1, 0}).expect(3);

    @TestData
    public DataExpectation largeNumber = DataExpectation
            .create(TestDataFileHelper.readIntegerArray("Q128_LongTestData"))
            .expect(290);

}
