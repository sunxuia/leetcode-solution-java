package q550;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/relative-ranks/
 *
 * Given scores of N athletes, find their relative ranks and the people with the top three highest scores, who will
 * be awarded medals: "Gold Medal", "Silver Medal" and "Bronze Medal".
 *
 * Example 1:
 *
 * Input: [5, 4, 3, 2, 1]
 * Output: ["Gold Medal", "Silver Medal", "Bronze Medal", "4", "5"]
 * Explanation: The first three athletes got the top three highest scores, so they got "Gold Medal", "Silver Medal"
 * and "Bronze Medal".
 * For the left two athletes, you just need to output their relative ranks according to their scores.
 *
 * Note:
 *
 * N is a positive integer and won't exceed 10,000.
 * All the scores of athletes are guaranteed to be unique.
 */
@RunWith(LeetCodeRunner.class)
public class Q506_RelativeRanks {

    @Answer
    public String[] findRelativeRanks(int[] nums) {
        final int n = nums.length;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
        }
        Arrays.sort(order, (a, b) -> nums[b] - nums[a]);
        String[] res = new String[n];
        if (n > 0) {
            res[order[0]] = "Gold Medal";
        }
        if (n > 1) {
            res[order[1]] = "Silver Medal";
        }
        if (n > 2) {
            res[order[2]] = "Bronze Medal";
        }
        for (int i = 3; i < n; i++) {
            res[order[i]] = String.valueOf(i + 1);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{5, 4, 3, 2, 1})
            .expect(new String[]{"Gold Medal", "Silver Medal", "Bronze Medal", "4", "5"});

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{10, 3, 8, 9, 4})
            .expect(new String[]{"Gold Medal", "5", "Bronze Medal", "Silver Medal", "4"});

}
