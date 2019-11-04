package q200;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/combine-two-tables/
 *
 * Given a list of non negative integers, arrange them such that they form the largest number.
 *
 * Example 1:
 *
 * Input: [10,2]
 * Output: "210"
 *
 * Example 2:
 *
 * Input: [3,30,34,5,9]
 * Output: "9534330"
 *
 * Note: The result may be very large, so you need to return a string instead of an integer.
 */
@RunWith(LeetCodeRunner.class)
public class Q179_LargestNumber {

    @Answer
    public String largestNumber(int[] nums) {
        String[] arr = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            arr[i] = Integer.toString(nums[i]);
        }
        Arrays.sort(arr, (a, b) -> (b + a).compareTo(a + b));
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s);
        }
        return sb.charAt(0) == '0' ? "0" : sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{10, 2}).expect("210");

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{3, 30, 34, 5, 9}).expect("9534330");

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{10, 2, 0, 100}).expect("2101000");

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{0, 0}).expect("0");

    @TestData
    public DataExpectation normal3 = DataExpectation
            .create(new int[]{824, 938, 1399, 5607, 6973, 5703, 9609, 4398, 8247})
            .expect("9609938824824769735703560743981399");

}
