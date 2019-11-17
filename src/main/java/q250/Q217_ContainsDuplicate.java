package q250;

import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/contains-duplicate/
 *
 * Given an array of integers, find if the array contains any duplicates.
 *
 * Your function should return true if any value appears at least twice in the array, and it should return false if
 * every element is distinct.
 *
 * Example 1:
 *
 * Input: [1,2,3,1]
 * Output: true
 *
 * Example 2:
 *
 * Input: [1,2,3,4]
 * Output: false
 *
 * Example 3:
 *
 * Input: [1,1,1,3,3,4,3,2,4,2]
 * Output: true
 *
 * 相关题目: {@link Q219_ContainsDuplicateII}
 */
@RunWith(LeetCodeRunner.class)
public class Q217_ContainsDuplicate {

    @Answer
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>(nums.length);
        for (int num : nums) {
            if (!set.add(num)) {
                return true;
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 1}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 3, 4}).expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 1, 1, 3, 3, 4, 3, 2, 4, 2}).expect(true);

}
