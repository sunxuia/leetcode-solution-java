package q850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/friends-of-appropriate-ages/
 *
 * Some people will make friend requests. The list of their ages is given and ages[i] is the age of the ith person.
 *
 * Person A will NOT friend request person B (B != A) if any of the following conditions are true:
 *
 * age[B] <= 0.5 * age[A] + 7
 * age[B] > age[A]
 * age[B] > 100 && age[A] < 100
 *
 * Otherwise, A will friend request B.
 *
 * Note that if A requests B, B does not necessarily request A.  Also, people will not friend request themselves.
 *
 * How many total friend requests are made?
 *
 * Example 1:
 *
 * Input: [16,16]
 * Output: 2
 * Explanation: 2 people friend request each other.
 *
 * Example 2:
 *
 * Input: [16,17,18]
 * Output: 2
 * Explanation: Friend requests are made 17 -> 16, 18 -> 17.
 *
 * Example 3:
 *
 * Input: [20,30,100,110,120]
 * Output: 3
 * Explanation: Friend requests are made 110 -> 100, 120 -> 110, 120 -> 100.
 *
 *
 *
 * Notes:
 *
 * 1 <= ages.length <= 20000.
 * 1 <= ages[i] <= 120.
 */
@RunWith(LeetCodeRunner.class)
public class Q825_FriendsOfAppropriateAges {

    // Solution 中给出的解答, 比排序后再比较要快
    @Answer
    public int numFriendRequests(int[] ages) {
        int[] count = new int[121];
        for (int age : ages) {
            count[age]++;
        }

        int res = 0;
        for (int ageA = 0; ageA <= 120; ageA++) {
            int countA = count[ageA];
            for (int ageB = 0; ageB <= ageA; ageB++) {
                int countB = count[ageB];
                if (ageA * 0.5 + 7 >= ageB) {
                    continue;
                }
                res += countA * countB;
                if (ageA == ageB) {
                    res -= countA;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{16, 16}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{16, 17, 18}).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{20, 30, 100, 110, 120}).expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[]{73, 106, 39, 6, 26, 15, 30, 100, 71, 35, 46, 112, 6, 60, 110})
            .expect(29);

}
