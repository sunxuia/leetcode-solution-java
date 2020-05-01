package q600;

import java.util.BitSet;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/distribute-candies/
 *
 * Given an integer array with even length, where different numbers in this array represent different kinds of
 * candies. Each number means one candy of the corresponding kind. You need to distribute these candies equally in
 * number to brother and sister. Return the maximum number of kinds of candies the sister could gain.
 *
 * Example 1:
 *
 * Input: candies = [1,1,2,2,3,3]
 * Output: 3
 * Explanation:
 * There are three different kinds of candies (1, 2 and 3), and two candies for each kind.
 * Optimal distribution: The sister has candies [1,2,3] and the brother has candies [1,2,3], too.
 * The sister has three different kinds of candies.
 *
 * Example 2:
 *
 * Input: candies = [1,1,2,3]
 * Output: 2
 * Explanation: For example, the sister has candies [2,3] and the brother has candies [1,1].
 * The sister has two different kinds of candies, the brother has only one kind of candies.
 *
 * Note:
 *
 * 1. The length of the given array is in range [2, 10,000], and will be even.
 * 2. The number in given array is in range [-100,000, 100,000].
 */
@RunWith(LeetCodeRunner.class)
public class Q575_DistributeCandies {

    @Answer
    public int distributeCandies(int[] candies) {
        BitSet set = new BitSet();
        int size = 0;
        for (int candy : candies) {
            if (!set.get(candy + 10_0000)) {
                size++;
                set.set(candy + 10_0000);
            }
        }
        return Math.min(size, candies.length / 2);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 1, 2, 2, 3, 3}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 1, 2, 3}).expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(TestDataFileHelper.readIntegerArray("Q575_TestData"))
            .expect(20);

}
