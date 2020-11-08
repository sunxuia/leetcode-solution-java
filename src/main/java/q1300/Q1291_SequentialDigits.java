package q1300;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1291. Sequential Digits
 * https://leetcode.com/problems/sequential-digits/
 *
 * An integer has sequential digits if and only if each digit in the number is one more than the previous digit.
 *
 * Return a sorted list of all the integers in the range [low, high] inclusive that have sequential digits.
 *
 * Example 1:
 * Input: low = 100, high = 300
 * Output: [123,234]
 * Example 2:
 * Input: low = 1000, high = 13000
 * Output: [1234,2345,3456,4567,5678,6789,12345]
 *
 * Constraints:
 *
 * 10 <= low <= high <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1291_SequentialDigits {

    @Answer
    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> res = new ArrayList<>();
        int digitLow = getDigt(low), digitHigh = getDigt(high);
        for (long digit = digitLow; digit <= digitHigh; digit *= 10) {
            int seq = firstSeq((int) digit);
            while (seq < low && seq % 10 != 0) {
                seq = nextSeq(seq, (int) digit);
            }
            while (seq <= high && seq % 10 != 0) {
                res.add(seq);
                seq = nextSeq(seq, (int) digit);
            }
        }
        return res;
    }

    private int getDigt(int val) {
        int res = 1;
        while (val > 9) {
            val /= 10;
            res *= 10;
        }
        return res;
    }

    private int firstSeq(int digit) {
        int res = 0;
        for (int i = 1; digit > 0; i++, digit /= 10) {
            res = res * 10 + i;
        }
        return res;
    }

    private int nextSeq(int val, int digit) {
        return (val - val / digit * digit) * 10 + val % 10 + 1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(100, 300)
            .expect(List.of(123, 234));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(1000, 13000)
            .expect(List.of(1234, 2345, 3456, 4567, 5678, 6789, 12345));

}
