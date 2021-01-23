package q1600;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1560. Most Visited Sector in  a Circular Track
 * https://leetcode.com/problems/most-visited-sector-in-a-circular-track/
 *
 * Given an integer n and an integer array rounds. We have a circular track which consists of n sectors labeled from 1
 * to n. A marathon will be held on this track, the marathon consists of m rounds. The ith round starts at sector
 * rounds[i - 1] and ends at sector rounds[i]. For example, round 1 starts at sector rounds[0] and ends at sector
 * rounds[1]
 *
 * Return an array of the most visited sectors sorted in ascending order.
 *
 * Notice that you circulate the track in ascending order of sector numbers in the counter-clockwise direction (See the
 * first example).
 *
 * Example 1:
 * <img src="./Q1560_PIC.png">
 * Input: n = 4, rounds = [1,3,1,2]
 * Output: [1,2]
 * Explanation: The marathon starts at sector 1. The order of the visited sectors is as follows:
 * 1 --> 2 --> 3 (end of round 1) --> 4 --> 1 (end of round 2) --> 2 (end of round 3 and the marathon)
 * We can see that both sectors 1 and 2 are visited twice and they are the most visited sectors. Sectors 3 and 4 are
 * visited only once.
 *
 * Example 2:
 *
 * Input: n = 2, rounds = [2,1,2,1,2,1,2,1,2]
 * Output: [2]
 *
 * Example 3:
 *
 * Input: n = 7, rounds = [1,3,5,7]
 * Output: [1,2,3,4,5,6,7]
 *
 * Constraints:
 *
 * 2 <= n <= 100
 * 1 <= m <= 100
 * rounds.length == m + 1
 * 1 <= rounds[i] <= n
 * rounds[i] != rounds[i + 1] for 0 <= i < m
 */
@RunWith(LeetCodeRunner.class)
public class Q1560_MostVisitedSectorInACircularTrack {

    @Answer
    public List<Integer> mostVisited(int n, int[] rounds) {
        int start = rounds[0], end = rounds[rounds.length - 1];
        if (start <= end) {
            List<Integer> res = new ArrayList<>(end - start + 1);
            for (int i = start; i <= end; i++) {
                res.add(i);
            }
            return res;
        } else {
            List<Integer> res = new ArrayList<>(end + n - start + 1);
            for (int i = 1; i <= end; i++) {
                res.add(i);
            }
            for (int i = start; i <= n; i++) {
                res.add(i);
            }
            return res;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(4, new int[]{1, 3, 1, 2})
            .expect(List.of(1, 2));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(2, new int[]{2, 1, 2, 1, 2, 1, 2, 1, 2})
            .expect(List.of(2));

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(7, new int[]{1, 3, 5, 7})
            .expect(List.of(1, 2, 3, 4, 5, 6, 7));

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(3, new int[]{3, 2, 1, 2, 1, 3, 2, 1, 2, 1, 3, 2, 3, 1})
            .expect(List.of(1, 3));

}
