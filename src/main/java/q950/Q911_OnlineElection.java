package q950;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 * [Medium] 911. Online Election
 * https://leetcode.com/problems/online-election/
 *
 * In an election, the i-th vote was cast for persons[i] at time times[i].
 *
 * Now, we would like to implement the following query function: TopVotedCandidate.q(int t) will return the number of
 * the person that was leading the election at time t.
 *
 * Votes cast at time t will count towards our query.  In the case of a tie, the most recent vote (among tied
 * candidates) wins.
 *
 * Example 1:
 *
 * Input: ["TopVotedCandidate","q","q","q","q","q","q"], [[[0,1,1,0,0,1,0],[0,5,10,15,20,25,30]],[3],[12],[25],[15],
 * [24],[8]]
 * Output: [null,0,1,1,0,0,1]
 * Explanation:
 * At time 3, the votes are [0], and 0 is leading.
 * At time 12, the votes are [0,1,1], and 1 is leading.
 * At time 25, the votes are [0,1,1,0,0,1], and 1 is leading (as ties go to the most recent vote.)
 * This continues for 3 more queries at time 15, 24, and 8.
 *
 * Note:
 *
 * 1 <= persons.length = times.length <= 5000
 * 0 <= persons[i] <= persons.length
 * times is a strictly increasing array with all elements in [0, 10^9].
 * TopVotedCandidate.q is called at most 10000 times per test case.
 * TopVotedCandidate.q(int t) is always called with t >= times[0].
 */
public class Q911_OnlineElection {

    private static class TopVotedCandidate {

        final int[] times;

        final Map<Integer, Integer> timePoints = new HashMap<>();

        // 题目限定了times 是递增数组
        public TopVotedCandidate(int[] persons, int[] times) {
            this.times = times;
            Map<Integer, Integer> counts = new HashMap<>();
            int maxPerson = -1, maxCount = 0;
            for (int i = 0; i < times.length; i++) {
                final int p = persons[i], t = times[i];
                int ct = counts.getOrDefault(p, 0) + 1;
                counts.put(p, ct);
                if (maxCount <= ct) {
                    maxPerson = p;
                    maxCount = ct;
                }
                timePoints.put(t, maxPerson);
            }
        }

        public int q(int t) {
            int idx = Arrays.binarySearch(times, t);
            idx = idx >= 0 ? idx : -idx - 2;
            return timePoints.get(times[idx]);
        }
    }

    @Test
    public void testMethod() {
        TopVotedCandidate tested = new TopVotedCandidate(
                new int[]{0, 1, 1, 0, 0, 1, 0}, new int[]{0, 5, 10, 15, 20, 25, 30});
        Assert.assertEquals(0, tested.q(3));
        Assert.assertEquals(1, tested.q(12));
        Assert.assertEquals(1, tested.q(25));
        Assert.assertEquals(0, tested.q(15));
        Assert.assertEquals(0, tested.q(24));
        Assert.assertEquals(1, tested.q(8));
    }

    @Test
    public void testMethod2() {
        TopVotedCandidate tested = new TopVotedCandidate(
                new int[]{0, 0, 0, 0, 1}, new int[]{0, 6, 39, 52, 75});
        Assert.assertEquals(0, tested.q(45));
        Assert.assertEquals(0, tested.q(49));
        Assert.assertEquals(0, tested.q(59));
        Assert.assertEquals(0, tested.q(68));
        Assert.assertEquals(0, tested.q(42));
        Assert.assertEquals(0, tested.q(37));
        Assert.assertEquals(0, tested.q(99));
        Assert.assertEquals(0, tested.q(26));
        Assert.assertEquals(0, tested.q(78));
        Assert.assertEquals(0, tested.q(43));
    }

}
