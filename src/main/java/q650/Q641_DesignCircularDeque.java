package q650;

import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/design-circular-deque/
 *
 * Design your implementation of the circular double-ended queue (deque).
 *
 * Your implementation should support following operations:
 *
 * MyCircularDeque(k): Constructor, set the size of the deque to be k.
 * insertFront(): Adds an item at the front of Deque. Return true if the operation is successful.
 * insertLast(): Adds an item at the rear of Deque. Return true if the operation is successful.
 * deleteFront(): Deletes an item from the front of Deque. Return true if the operation is successful.
 * deleteLast(): Deletes an item from the rear of Deque. Return true if the operation is successful.
 * getFront(): Gets the front item from the Deque. If the deque is empty, return -1.
 * getRear(): Gets the last item from Deque. If the deque is empty, return -1.
 * isEmpty(): Checks whether Deque is empty or not.
 * isFull(): Checks whether Deque is full or not.
 *
 *
 *
 * Example:
 *
 * MyCircularDeque circularDeque = new MycircularDeque(3); // set the size to be 3
 * circularDeque.insertLast(1);			// return true
 * circularDeque.insertLast(2);			// return true
 * circularDeque.insertFront(3);			// return true
 * circularDeque.insertFront(4);			// return false, the queue is full
 * circularDeque.getRear();  			// return 2
 * circularDeque.isFull();				// return true
 * circularDeque.deleteLast();			// return true
 * circularDeque.insertFront(4);			// return true
 * circularDeque.getFront();			// return 4
 *
 *
 *
 * Note:
 *
 * All values will be in the range of [0, 1000].
 * The number of operations will be in the range of [1, 1000].
 * Please do not use the built-in Deque library.
 */
public class Q641_DesignCircularDeque {

    private static class MyCircularDeque {

        private int[] arr;

        private int size, head, tail;

        /**
         * Initialize your data structure here. Set the size of the deque to be k.
         */
        public MyCircularDeque(int k) {
            arr = new int[k];
        }

        /**
         * Adds an item at the front of Deque. Return true if the operation is successful.
         */
        public boolean insertFront(int value) {
            if (size == arr.length) {
                return false;
            }
            if (size++ > 0) {
                head = (head + 1) % arr.length;
            }
            arr[head] = value;
            return true;
        }

        /**
         * Adds an item at the rear of Deque. Return true if the operation is successful.
         */
        public boolean insertLast(int value) {
            if (size == arr.length) {
                return false;
            }
            if (size++ > 0) {
                tail = (tail - 1 + arr.length) % arr.length;
            }
            arr[tail] = value;
            return true;
        }

        /**
         * Deletes an item from the front of Deque. Return true if the operation is successful.
         */
        public boolean deleteFront() {
            if (size == 0) {
                return false;
            }
            if (--size > 0) {
                head = (head - 1 + arr.length) % arr.length;
            }
            return true;
        }

        /**
         * Deletes an item from the rear of Deque. Return true if the operation is successful.
         */
        public boolean deleteLast() {
            if (size == 0) {
                return false;
            }
            if (--size > 0) {
                tail = (tail + 1) % arr.length;
            }
            return true;
        }

        /**
         * Get the front item from the deque.
         */
        public int getFront() {
            return size == 0 ? -1 : arr[head];
        }

        /**
         * Get the last item from the deque.
         */
        public int getRear() {
            return size == 0 ? -1 : arr[tail];
        }

        /**
         * Checks whether the circular deque is empty or not.
         */
        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * Checks whether the circular deque is full or not.
         */
        public boolean isFull() {
            return size == arr.length;
        }
    }

    @Test
    public void testMethod() {
        MyCircularDeque circularDeque = new MyCircularDeque(3);
        Assert.assertTrue(circularDeque.insertLast(1));
        Assert.assertTrue(circularDeque.insertLast(2));
        Assert.assertTrue(circularDeque.insertFront(3));
        Assert.assertFalse(circularDeque.insertFront(4));
        Assert.assertEquals(2, circularDeque.getRear());
        Assert.assertTrue(circularDeque.isFull());
        Assert.assertTrue(circularDeque.deleteLast());
        Assert.assertTrue(circularDeque.insertFront(4));
        Assert.assertEquals(4, circularDeque.getFront());
    }

}
