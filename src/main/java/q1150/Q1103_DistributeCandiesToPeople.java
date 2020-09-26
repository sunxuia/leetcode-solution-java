package q1150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1103. Distribute Candies to People
 * https://leetcode.com/problems/distribute-candies-to-people/
 *
 * We distribute some number of candies, to a row of n = num_people people in the following way:
 *
 * We then give 1 candy to the first person, 2 candies to the second person, and so on until we give n candies to the
 * last person.
 *
 * Then, we go back to the start of the row, giving n + 1 candies to the first person, n + 2 candies to the second
 * person, and so on until we give 2 * n candies to the last person.
 *
 * This process repeats (with us giving one more candy each time, and moving to the start of the row after we reach the
 * end) until we run out of candies.  The last person will receive all of our remaining candies (not necessarily one
 * more than the previous gift).
 *
 * Return an array (of length num_people and sum candies) that represents the final distribution of candies.
 *
 * Example 1:
 *
 * Input: candies = 7, num_people = 4
 * Output: [1,2,3,1]
 * Explanation:
 * On the first turn, ans[0] += 1, and the array is [1,0,0,0].
 * On the second turn, ans[1] += 2, and the array is [1,2,0,0].
 * On the third turn, ans[2] += 3, and the array is [1,2,3,0].
 * On the fourth turn, ans[3] += 1 (because there is only one candy left), and the final array is [1,2,3,1].
 *
 * Example 2:
 *
 * Input: candies = 10, num_people = 3
 * Output: [5,2,3]
 * Explanation:
 * On the first turn, ans[0] += 1, and the array is [1,0,0].
 * On the second turn, ans[1] += 2, and the array is [1,2,0].
 * On the third turn, ans[2] += 3, and the array is [1,2,3].
 * On the fourth turn, ans[0] += 4, and the final array is [5,2,3].
 *
 * Constraints:
 *
 * 1 <= candies <= 10^9
 * 1 <= num_people <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1103_DistributeCandiesToPeople {

    /**
     * 数组找规律的题目
     */
    @Answer
    public int[] distributeCandies(int candies, int num_people) {
        // 能满额分配的人次, 第 max 个人次(从0 开始) 不会分配满或没有
        int max = (int) (Math.sqrt(2 * candies + 0.25) - 0.5);
        int[] res = new int[num_people];
        for (int i = 1; i <= num_people; i++) {
            int k = max / num_people;
            if (i <= max % num_people) {
                res[i - 1] = (k * (k + 1) * num_people / 2) + (k + 1) * i;
            } else if (i == max % num_people + 1) {
                res[i - 1] = ((k - 1) * k * num_people / 2) + k * i
                        + candies - max * (max + 1) / 2;
            } else {
                res[i - 1] = ((k - 1) * k * num_people / 2) + k * i;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(7, 4).expect(new int[]{1, 2, 3, 1});

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(10, 3).expect(new int[]{5, 2, 3});

}
