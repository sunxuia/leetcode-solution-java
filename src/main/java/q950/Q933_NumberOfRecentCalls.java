package q950;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.Test;
import util.asserthelper.AssertUtils;

/**
 * [Easy] 933. Number of Recent Calls
 * https://leetcode.com/problems/number-of-recent-calls/
 *
 * Write a class RecentCounter to count recent requests.
 *
 * It has only one method: ping(int t), where t represents some time in milliseconds.
 *
 * Return the number of pings that have been made from 3000 milliseconds ago until now.
 *
 * Any ping with time in [t - 3000, t] will count, including the current ping.
 *
 * It is guaranteed that every call to ping uses a strictly larger value of t than before.
 *
 * Example 1:
 *
 * Input: inputs = ["RecentCounter","ping","ping","ping","ping"], inputs = [[],[1],[100],[3001],[3002]]
 * Output: [null,1,2,3,3]
 *
 * Note:
 *
 * Each test case will have at most 10000 calls to ping.
 * Each test case will call ping with strictly increasing values of t.
 * Each call to ping will have 1 <= t <= 10^9.
 */
public class Q933_NumberOfRecentCalls {

    /**
     * Your RecentCounter object will be instantiated and called as such:
     * RecentCounter obj = new RecentCounter();
     * int param_1 = obj.ping(t);
     */

    @Test
    public void testMethod() {
        RecentCounter tested = new RecentCounter();
        AssertUtils.assertEquals(1, tested.ping(1));
        AssertUtils.assertEquals(2, tested.ping(100));
        AssertUtils.assertEquals(3, tested.ping(3001));
        AssertUtils.assertEquals(3, tested.ping(3002));
    }

    private static class RecentCounter {

        Queue<Integer> queue = new ArrayDeque<>();

        public RecentCounter() {

        }

        public int ping(int t) {
            while (!queue.isEmpty() && queue.peek() < t - 3000) {
                queue.poll();
            }
            queue.offer(t);
            return queue.size();
        }
    }

}
