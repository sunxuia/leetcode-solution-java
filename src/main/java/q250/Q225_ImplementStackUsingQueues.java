package q250;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/implement-stack-using-queues/
 *
 * Implement the following operations of a stack using queues.
 *
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * empty() -- Return whether the stack is empty.
 *
 * Example:
 *
 * MyStack stack = new MyStack();
 *
 * stack.push(1);
 * stack.push(2);
 * stack.top();   // returns 2
 * stack.pop();   // returns 2
 * stack.empty(); // returns false
 *
 * Notes:
 *
 * You must use only standard operations of a queue -- which means only push to back, peek/pop from front, size,
 * and is empty operations are valid.
 * Depending on your language, queue may not be supported natively. You may simulate a queue by using a list or
 * deque (double-ended queue), as long as you use only standard operations of a queue.
 * You may assume that all operations are valid (for example, no pop or top operations will be called on an empty
 * stack).
 */
public class Q225_ImplementStackUsingQueues {

    /**
     * 就是这么简单 ╮(╯_╰)╭ , LeetCode 上最快的解法也是类似.
     */
    private static class MyStack {

        private Queue<Integer> queue = new ArrayDeque<>();

        /**
         * Initialize your data structure here.
         */
        public MyStack() {

        }

        /**
         * Push element x onto stack.
         */
        public void push(int x) {
            queue.add(x);
            for (int len = queue.size(); len > 1; len--) {
                queue.add(queue.poll());
            }
        }

        /**
         * Removes the element on top of the stack and returns that element.
         */
        public int pop() {
            return queue.poll();
        }

        /**
         * Get the top element.
         */
        public int top() {
            return queue.element();
        }

        /**
         * Returns whether the stack is empty.
         */
        public boolean empty() {
            return queue.isEmpty();
        }
    }

    @Test
    public void testMethod() {
        MyStack stack = new MyStack();
        stack.push(1);
        stack.push(2);

        Assert.assertEquals(2, stack.top());
        Assert.assertEquals(2, stack.pop());
        Assert.assertFalse(stack.empty());
    }

}
