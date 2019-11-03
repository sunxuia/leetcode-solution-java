package q100;

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
 * https://leetcode.com/problems/subsets-ii/
 *
 * Given a collection of integers that might contain duplicates, nums, return all possible subsets (the power set).
 *
 * Note: The solution set must not contain duplicate subsets.
 *
 * Example:
 *
 * Input: [1,2,2]
 * Output:
 * [
 * [2],
 * [1],
 * [1,2,2],
 * [2,2],
 * [1,2],
 * []
 * ]
 *
 * 题解: 上一题是 {@link Q078_Subsets}, 这题多了个nums 中有重复值的条件.
 */
@RunWith(LeetCodeRunner.class)
public class Q090_SubsetsII {

    @Answer
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        res.add(Collections.emptyList());
        for (int i = 0, count; i < nums.length; i += count) {
            int val = nums[i];
            count = 1;
            while (i + count < nums.length && val == nums[i + count]) {
                count++;
            }
            for (int j = 0, len = res.size(); j < len; j++) {
                List<Integer> oldList = res.get(j);
                for (int k = 0; k < count; k++) {
                    List<Integer> newList = new ArrayList<>(oldList);
                    newList.add(val);
                    res.add(newList);
                    oldList = newList;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation exmaple = DataExpectation.builder()
            .addArgument(new int[]{1, 2, 2})
            .expect(new int[][]{
                    {2},
                    {1},
                    {1, 2, 2},
                    {2, 2},
                    {1, 2},
                    {}
            }).unorderResult()
            .build();
}
