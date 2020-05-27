package q750;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/monotone-increasing-digits/
 *
 * Given a non-negative integer N, find the largest number that is less than or equal to N with monotone increasing
 * digits.
 *
 * (Recall that an integer has monotone increasing digits if and only if each pair of adjacent digits x and y satisfy
 * x <= y.)
 *
 * Example 1:
 *
 * Input: N = 10
 * Output: 9
 *
 * Example 2:
 *
 * Input: N = 1234
 * Output: 1234
 *
 * Example 3:
 *
 * Input: N = 332
 * Output: 299
 *
 * Note: N is an integer in the range [0, 10^9].
 */
@RunWith(LeetCodeRunner.class)
public class Q738_MonotoneIncreasingDigits {

    @Answer
    public int monotoneIncreasingDigits(int N) {
        List<Integer> list = new ArrayList<>();
        for (int i = N; i > 0; i /= 10) {
            list.add(i % 10);
        }

        for (int i = 0; i < list.size() - 1; i++) {
            int curr = list.get(i), higher = list.get(i + 1);
            if (curr < higher) {
                int j = i + 1;
                while (list.get(j) == 0) {
                    j++;
                }
                list.set(j, list.get(j) - 1);
                while (--j >= 0 && list.get(j) != 9) {
                    list.set(j, 9);
                }
            }
        }

        int res = 0, digit = 1;
        for (int val : list) {
            res += digit * val;
            digit *= 10;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(10).expect(9);

    @TestData
    public DataExpectation example2 = DataExpectation.create(1234).expect(1234);

    @TestData
    public DataExpectation example3 = DataExpectation.create(332).expect(299);

    @TestData
    public DataExpectation border0 = DataExpectation.create(0).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(20).expect(19);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(100).expect(99);

}
