package q200;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/min-stack/
 *
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 *
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * getMin() -- Retrieve the minimum element in the stack.
 *
 *
 *
 * Example:
 *
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin();   --> Returns -3.
 * minStack.pop();
 * minStack.top();      --> Returns 0.
 * minStack.getMin();   --> Returns -2.
 */
public class Q155_MinStack {

    private static class MinStack {

        // 保存栈和对应的值
        private List<Integer> stack = new ArrayList<>();

        // 保存最小值在stack 中对应的下标
        private Deque<Integer> minStack = new ArrayDeque<>();

        /**
         * initialize your data structure here.
         */
        public MinStack() {
            // 哨兵
            stack.add(Integer.MAX_VALUE);
            minStack.push(0);
        }

        public void push(int x) {
            stack.add(x);
            if (x < stack.get(minStack.peek())) {
                minStack.push(stack.size() - 1);
            }
        }

        public void pop() {
            stack.remove(stack.size() - 1);
            if (minStack.peek() == stack.size()) {
                minStack.pop();
            }
        }

        public int top() {
            return stack.get(stack.size() - 1);
        }

        public int getMin() {
            return stack.get(minStack.peek());
        }
    }

    @Test
    public void testMethod() {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        Assert.assertEquals(-3, minStack.getMin());
        minStack.pop();
        Assert.assertEquals(0, minStack.top());
        Assert.assertEquals(-2, minStack.getMin());
    }

}
