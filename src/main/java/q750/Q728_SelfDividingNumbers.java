package q750;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/self-dividing-numbers/
 *
 * A self-dividing number is a number that is divisible by every digit it contains.
 *
 * For example, 128 is a self-dividing number because 128 % 1 == 0, 128 % 2 == 0, and 128 % 8 == 0.
 *
 * Also, a self-dividing number is not allowed to contain the digit zero.
 *
 * Given a lower and upper number bound, output a list of every possible self dividing number, including the bounds
 * if possible.
 *
 * Example 1:
 *
 * Input:
 * left = 1, right = 22
 * Output: [1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 15, 22]
 *
 * Note:
 * The boundaries of each input argument are 1 <= left <= right <= 10000.
 */
@RunWith(LeetCodeRunner.class)
public class Q728_SelfDividingNumbers {

    @Answer
    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> res = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (isSelfDividingNumber(i)) {
                res.add(i);
            }
        }
        return res;
    }

    private boolean isSelfDividingNumber(int value) {
        for (int j = value; j != 0; j /= 10) {
            if (j % 10 == 0 || value % (j % 10) != 0) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(1, 22)
            .expect(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 15, 22));

}
