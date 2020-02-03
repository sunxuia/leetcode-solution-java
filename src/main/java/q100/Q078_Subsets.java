package q100;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/subsets/
 *
 * Given a set of distinct integers, nums, return all possible subsets (the power set).
 *
 * Note: The solution set must not contain duplicate subsets.
 *
 * Example:
 *
 * Input: nums = [1,2,3]
 * Output:
 * [
 * [3],
 * [1],
 * [2],
 * [1,2,3],
 * [1,3],
 * [2,3],
 * [1,2],
 * []
 * ]
 */
@RunWith(LeetCodeRunner.class)
public class Q078_Subsets {

    @Answer
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(Collections.emptyList());
        for (int num : nums) {
            for (int i = 0, len = res.size(); i < len; i++) {
                List<Integer> list = res.get(i);
                List<Integer> newList = new ArrayList<>(list);
                newList.add(num);
                res.add(newList);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[]{1, 2, 3})
            .expect(new int[][]{
                    {3},
                    {1},
                    {2},
                    {1, 2, 3},
                    {1, 3},
                    {2, 3},
                    {1, 2},
                    {}
            }).unorderResult()
            .build();
}
