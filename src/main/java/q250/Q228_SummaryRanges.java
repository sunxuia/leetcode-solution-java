package q250;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/summary-ranges/
 *
 * Given a sorted integer array without duplicates, return the summary of its ranges.
 *
 * Example 1:
 *
 * Input:  [0,1,2,4,5,7]
 * Output: ["0->2","4->5","7"]
 * Explanation: 0,1,2 form a continuous range; 4,5 form a continuous range.
 *
 * Example 2:
 *
 * Input:  [0,2,3,4,6,8,9]
 * Output: ["0","2->4","6","8->9"]
 * Explanation: 2,3,4 form a continuous range; 8,9 form a continuous range.
 */
@RunWith(LeetCodeRunner.class)
public class Q228_SummaryRanges {

    @Answer
    public List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int start = 0, i = 1; i <= nums.length; i++) {
            if (i < nums.length && nums[i - 1] + 1 == nums[i]) {
                continue;
            }
            sb.setLength(0);
            sb.append(nums[start]);
            if (start < i - 1) {
                sb.append("->").append(nums[i - 1]);
            }
            res.add(sb.toString());
            start = i;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[]{0, 1, 2, 4, 5, 7})
            .expect(Arrays.asList("0->2", "4->5", "7"));

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[]{0, 2, 3, 4, 6, 8, 9})
            .expect(Arrays.asList("0", "2->4", "6", "8->9"));

    @TestData
    public DataExpectation border0 = DataExpectation.create(new int[0]).expect(Collections.emptyList());

    @TestData
    public DataExpectation border1 = DataExpectation.create(new int[]{1}).expect(Collections.singletonList("1"));

}
