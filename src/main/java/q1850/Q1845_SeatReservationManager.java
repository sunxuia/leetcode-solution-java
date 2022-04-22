package q1850;

import java.util.PriorityQueue;
import org.junit.Test;
import util.asserthelper.AssertUtils;

/**
 * [Medium] 1845. Seat Reservation Manager
 * https://leetcode.com/problems/seat-reservation-manager/
 *
 * Design a system that manages the reservation state of n seats that are numbered from 1 to n.
 *
 * Implement the SeatManager class:
 *
 * SeatManager(int n) Initializes a SeatManager object that will manage n seats numbered from 1 to n. All seats are
 * initially available.
 * int reserve() Fetches the smallest-numbered unreserved seat, reserves it, and returns its number.
 * void unreserve(int seatNumber) Unreserves the seat with the given seatNumber.
 *
 * Example 1:
 *
 * Input
 * ["SeatManager", "reserve", "reserve", "unreserve", "reserve", "reserve", "reserve", "reserve", "unreserve"]
 * [[5], [], [], [2], [], [], [], [], [5]]
 * Output
 * [null, 1, 2, null, 2, 3, 4, 5, null]
 *
 * Explanation
 * SeatManager seatManager = new SeatManager(5); // Initializes a SeatManager with 5 seats.
 * seatManager.reserve();    // All seats are available, so return the lowest numbered seat, which is 1.
 * seatManager.reserve();    // The available seats are [2,3,4,5], so return the lowest of them, which is 2.
 * seatManager.unreserve(2); // Unreserve seat 2, so now the available seats are [2,3,4,5].
 * seatManager.reserve();    // The available seats are [2,3,4,5], so return the lowest of them, which is 2.
 * seatManager.reserve();    // The available seats are [3,4,5], so return the lowest of them, which is 3.
 * seatManager.reserve();    // The available seats are [4,5], so return the lowest of them, which is 4.
 * seatManager.reserve();    // The only available seat is seat 5, so return 5.
 * seatManager.unreserve(5); // Unreserve seat 5, so now the available seats are [5].
 *
 * Constraints:
 *
 * 1 <= n <= 10^5
 * 1 <= seatNumber <= n
 * For each call to reserve, it is guaranteed that there will be at least one unreserved seat.
 * For each call to unreserve, it is guaranteed that seatNumber will be reserved.
 * At most 10^5 calls in total will be made to reserve and unreserve.
 */
public class Q1845_SeatReservationManager {

    private static class SeatManager {

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        public SeatManager(int n) {
            for (int i = 1; i <= n; i++) {
                pq.offer(i);
            }
        }

        public int reserve() {
            return pq.poll();
        }

        public void unreserve(int seatNumber) {
            pq.offer(seatNumber);
        }
    }

    /**
     * leetcode 上比较快速的解法
     */
    private static class SeatManager2 {

        int reserved = 1;

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        public SeatManager2(int n) {
        }

        public int reserve() {
            if (!pq.isEmpty()) {
                return pq.poll();
            }
            return reserved++;
        }

        public void unreserve(int seatNumber) {
            pq.offer(seatNumber);
        }
    }

    /**
     * Your SeatManager object will be instantiated and called as such:
     * SeatManager obj = new SeatManager(n);
     * int param_1 = obj.reserve();
     * obj.unreserve(seatNumber);
     */

    @Test
    public void testMethod() {
        int res;
        SeatManager seatManager = new SeatManager(5);
        res = seatManager.reserve();
        AssertUtils.assertEquals(1, res);
        res = seatManager.reserve();
        AssertUtils.assertEquals(2, res);
        seatManager.unreserve(2);
        res = seatManager.reserve();
        AssertUtils.assertEquals(2, res);
        res = seatManager.reserve();
        AssertUtils.assertEquals(3, res);
        res = seatManager.reserve();
        AssertUtils.assertEquals(4, res);
        res = seatManager.reserve();
        AssertUtils.assertEquals(5, res);
        seatManager.unreserve(5);
    }

    @Test
    public void testMethod2() {
        int res;
        SeatManager2 seatManager = new SeatManager2(5);
        res = seatManager.reserve();
        AssertUtils.assertEquals(1, res);
        res = seatManager.reserve();
        AssertUtils.assertEquals(2, res);
        seatManager.unreserve(2);
        res = seatManager.reserve();
        AssertUtils.assertEquals(2, res);
        res = seatManager.reserve();
        AssertUtils.assertEquals(3, res);
        res = seatManager.reserve();
        AssertUtils.assertEquals(4, res);
        res = seatManager.reserve();
        AssertUtils.assertEquals(5, res);
        seatManager.unreserve(5);
    }

}
