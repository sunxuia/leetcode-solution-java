package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/total-hamming-distance/
 *
 * The Hamming distance between two integers is the number of positions at which the corresponding bits are different.
 *
 * Now your job is to find the total Hamming distance between all pairs of the given numbers.
 *
 * Example:
 *
 * Input: 4, 14, 2
 *
 * Output: 6
 *
 * Explanation: In binary representation, the 4 is 0100, 14 is 1110, and 2 is 0010 (just
 * showing the four bits relevant in this case). So the answer will be:
 * HammingDistance(4, 14) + HammingDistance(4, 2) + HammingDistance(14, 2) = 2 + 2 + 2 = 6.
 *
 * Note:
 *
 * Elements of the given array are in the range of 0 to 10^9
 * Length of the array will not exceed 10^4.
 *
 * 题解:
 * 汉明距离:  两个等长字符串之间的汉明距离, 是两个字符串对应位置的不同字符的个数.
 * 换句话说, 它就是将一个字符串变换成另外一个字符串所需要替换的字符个数.
 */
@RunWith(LeetCodeRunner.class)
public class Q477_TotalHammingDistance {

    @Answer
    public int totalHammingDistance(int[] nums) {
        int res = 0;
        for (int i = 0; i < 32; i++) {
            int one = 0;
            for (int num : nums) {
                one += (num >> i) & 1;
            }
            res += one * (nums.length - one);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{4, 14, 2}).expect(6);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{6, 1, 8, 6, 8}).expect(22);

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.readIntegerArray("Q477_LongTestData")).expect(748405562);

}
