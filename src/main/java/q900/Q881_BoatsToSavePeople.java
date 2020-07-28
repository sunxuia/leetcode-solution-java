package q900;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 881. Boats to Save People
 * https://leetcode.com/problems/boats-to-save-people/
 *
 * The i-th person has weight people[i], and each boat can carry a maximum weight of limit.
 *
 * Each boat carries at most 2 people at the same time, provided the sum of the weight of those people is at most
 * limit.
 *
 * Return the minimum number of boats to carry every given person.  (It is guaranteed each person can be carried by a
 * boat.)
 *
 * Example 1:
 *
 * Input: people = [1,2], limit = 3
 * Output: 1
 * Explanation: 1 boat (1, 2)
 *
 * Example 2:
 *
 * Input: people = [3,2,2,1], limit = 3
 * Output: 3
 * Explanation: 3 boats (1, 2), (2) and (3)
 *
 * Example 3:
 *
 * Input: people = [3,5,3,4], limit = 5
 * Output: 4
 * Explanation: 4 boats (3), (3), (4), (5)
 *
 * Note:
 *
 * 1 <= people.length <= 50000
 * 1 <= people[i] <= limit <= 30000
 */
@RunWith(LeetCodeRunner.class)
public class Q881_BoatsToSavePeople {

    @Answer
    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);
        int left = 0, right = people.length - 1;
        int res = 0;
        while (left <= right) {
            if (people[left] + people[right] <= limit) {
                left++;
            }
            right--;
            res++;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 2}, 3).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{3, 2, 2, 1}, 3).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{3, 5, 3, 4}, 5).expect(4);

}
