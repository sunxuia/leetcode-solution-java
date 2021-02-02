package q1700;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.Assert;
import org.junit.Test;

/**
 * [Medium] 1670. Design Front Middle Back Queue
 * https://leetcode.com/problems/design-front-middle-back-queue/
 *
 * Design a queue that supports push and pop operations in the front, middle, and back.
 *
 * Implement the FrontMiddleBack class:
 *
 * FrontMiddleBack() Initializes the queue.
 * void pushFront(int val) Adds val to the front of the queue.
 * void pushMiddle(int val) Adds val to the middle of the queue.
 * void pushBack(int val) Adds val to the back of the queue.
 * int popFront() Removes the front element of the queue and returns it. If the queue is empty, return -1.
 * int popMiddle() Removes the middle element of the queue and returns it. If the queue is empty, return -1.
 * int popBack() Removes the back element of the queue and returns it. If the queue is empty, return -1.
 *
 * Notice that when there are two middle position choices, the operation is performed on the frontmost middle position
 * choice. For example:
 *
 * Pushing 6 into the middle of [1, 2, 3, 4, 5] results in [1, 2, 6, 3, 4, 5].
 * Popping the middle from [1, 2, 3, 4, 5, 6] returns 3 and results in [1, 2, 4, 5, 6].
 *
 * Example 1:
 *
 * Input:
 * ["FrontMiddleBackQueue", "pushFront", "pushBack", "pushMiddle", "pushMiddle", "popFront", "popMiddle", "popMiddle",
 * "popBack", "popFront"]
 * [[], [1], [2], [3], [4], [], [], [], [], []]
 * Output:
 * [null, null, null, null, null, 1, 3, 4, 2, -1]
 *
 * Explanation:
 * FrontMiddleBackQueue q = new FrontMiddleBackQueue();
 * q.pushFront(1);   // [1]
 * q.pushBack(2);    // [1, 2]
 * q.pushMiddle(3);  // [1, 3, 2]
 * q.pushMiddle(4);  // [1, 4, 3, 2]
 * q.popFront();     // return 1 -> [4, 3, 2]
 * q.popMiddle();    // return 3 -> [4, 2]
 * q.popMiddle();    // return 4 -> [2]
 * q.popBack();      // return 2 -> []
 * q.popFront();     // return -1 -> [] (The queue is empty)
 *
 * Constraints:
 *
 * 1 <= val <= 109
 * At most 1000 calls will be made to pushFront, pushMiddle, pushBack, popFront, popMiddle, and popBack.
 */
public class Q1670_DesignFrontMiddleBackQueue {

    private static class FrontMiddleBackQueue {

        Deque<Integer> front = new ArrayDeque<>();

        Deque<Integer> back = new ArrayDeque<>();

        public FrontMiddleBackQueue() {

        }

        public void pushFront(int val) {
            front.offerFirst(val);
            balance();
        }

        private void balance() {
            if (back.size() > front.size()) {
                front.offerLast(back.pollFirst());
            }
            if (front.size() > back.size() + 1) {
                back.offerFirst(front.pollLast());
            }
        }

        public void pushMiddle(int val) {
            if (front.size() > back.size()) {
                back.offerFirst(front.pollLast());
            }
            front.offerLast(val);
            balance();
        }

        public void pushBack(int val) {
            back.offerLast(val);
            balance();
        }

        public int popFront() {
            if (front.isEmpty()) {
                return -1;
            }
            int val = front.pollFirst();
            balance();
            return val;
        }

        public int popMiddle() {
            if (front.isEmpty()) {
                return -1;
            }
            int val = front.pollLast();
            balance();
            return val;
        }

        public int popBack() {
            if (back.isEmpty()) {
                return popMiddle();
            }
            int val = back.pollLast();
            balance();
            return val;
        }
    }

    @Test
    public void testMethod() {
        FrontMiddleBackQueue q = new FrontMiddleBackQueue();
        q.pushFront(1);
        q.pushBack(2);
        q.pushMiddle(3);
        q.pushMiddle(4);
        Assert.assertEquals(1, q.popFront());
        Assert.assertEquals(3, q.popMiddle());
        Assert.assertEquals(4, q.popMiddle());
        Assert.assertEquals(2, q.popBack());
        Assert.assertEquals(-1, q.popFront());
    }

}
