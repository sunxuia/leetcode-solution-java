package q950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 930. Binary Subarrays With Sum
 * https://leetcode.com/problems/binary-subarrays-with-sum/
 *
 * In an array A of 0s and 1s, how many non-empty subarrays have sum S?
 *
 * Example 1:
 *
 * Input: A = [1,0,1,0,1], S = 2
 * Output: 4
 * Explanation:
 * The 4 subarrays are bolded below:
 * [ ( 1, 0, 1, ) 0, 1]
 * [ ( 1, 0, 1, 0, ) 1]
 * [1, ( 0, 1, 0, 1 ) ]
 * [1, 0, ( 1, 0, 1 ) ]
 *
 * Note:
 *
 * A.length <= 30000
 * 0 <= S <= A.length
 * A[i] is either 0 or 1.
 */
@RunWith(LeetCodeRunner.class)
public class Q930_BinarySubarraysWithSum {

    @Answer
    public int numSubarraysWithSum(int[] A, int S) {
        if (S == 0) {
            int res = 0, prev = -1;
            for (int i = 0; i <= A.length; i++) {
                if (i == A.length || A[i] == 1) {
                    int len = i - prev - 1;
                    res += (len + 1) * len / 2;
                    prev = i;
                }
            }
            return res;
        } else {
            int res = 0, sum = 0, prev = -1;
            for (int i = 0; i <= A.length; i++) {
                sum += i == A.length ? 1 : A[i];
                if (sum > S) {
                    int headZero = 1, tailZero = 1;
                    while (A[prev + headZero] == 0) {
                        headZero++;
                    }
                    while (A[i - tailZero] == 0) {
                        tailZero++;
                    }
                    res += headZero * tailZero;
                    prev += headZero;
                    sum--;
                }
            }
            return res;
        }
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(new int[]{1, 0, 1, 0, 1}, 2).expect(4);

    @TestData
    public DataExpectation border = DataExpectation.createWith(new int[3], 0).expect(6);

}
