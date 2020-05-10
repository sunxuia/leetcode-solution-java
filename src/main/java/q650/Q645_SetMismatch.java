package q650;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/set-mismatch/
 *
 * The set S originally contains numbers from 1 to n. But unfortunately, due to the data error, one of the numbers
 * in the set got duplicated to another number in the set, which results in repetition of one number and loss of
 * another number.
 *
 * Given an array nums representing the data status of this set after the error. Your task is to firstly find the
 * number occurs twice and then find the number that is missing. Return them in the form of an array.
 *
 * Example 1:
 *
 * Input: nums = [1,2,2,4]
 * Output: [2,3]
 *
 * Note:
 *
 * 1. The given array size will in the range [2, 10000].
 * 2. The given array's numbers won't have any order.
 */
@RunWith(LeetCodeRunner.class)
public class Q645_SetMismatch {

    @Answer
    public int[] findErrorNums(int[] nums) {
        int[] bucket = new int[nums.length + 1];
        for (int num : nums) {
            bucket[num]++;
        }
        int[] res = new int[2];
        for (int i = 1; i < bucket.length; i++) {
            if (bucket[i] == 2) {
                res[0] = i;
            } else if (bucket[i] == 0) {
                res[1] = i;
            }
        }
        return res;
    }

    // 解法2 :利用nums 来作为桶
    @Answer
    public int[] findErrorNums2(int[] nums) {
        int[] res = new int[2];
        for (int i = 1; i <= nums.length; i++) {
            int j = i, val = nums[j - 1];
            while (j != val) {
                if (nums[val - 1] == val) {
                    res[0] = val;
                    break;
                }
                nums[j - 1] = nums[val - 1];
                nums[val - 1] = val;
                val = nums[j - 1];
            }
        }
        for (int i = 1; i <= nums.length; i++) {
            if (nums[i - 1] != i) {
                res[1] = i;
                break;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{1, 2, 2, 4}).expect(new int[]{2, 3});

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{8, 7, 3, 5, 3, 6, 1, 4}).expect(new int[]{3, 2});
//    public DataExpectation normal1 = DataExpectation.create(new int[]{3, 7, 3, 4, 5, 6, 1, 8}).expect(new int[]{3,
//    2});

}
