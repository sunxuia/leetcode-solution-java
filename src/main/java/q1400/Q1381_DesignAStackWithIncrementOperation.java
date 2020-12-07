package q1400;

import org.junit.Assert;
import org.junit.Test;

/**
 * [Medium] 1381. Design a Stack With Increment Operation
 * https://leetcode.com/problems/design-a-stack-with-increment-operation/
 *
 * Design a stack which supports the following operations.
 *
 * Implement the CustomStack class:
 *
 * CustomStack(int maxSize) Initializes the object with maxSize which is the maximum number of elements in the stack or
 * do nothing if the stack reached the maxSize.
 * void push(int x) Adds x to the top of the stack if the stack hasn't reached the maxSize.
 * int pop() Pops and returns the top of stack or -1 if the stack is empty.
 * void inc(int k, int val) Increments the bottom k elements of the stack by val. If there are less than k elements in
 * the stack, just increment all the elements in the stack.
 *
 * Example 1:
 *
 * Input
 * ["CustomStack","push","push","pop","push","push","push","increment","increment","pop","pop","pop","pop"]
 * [[3],[1],[2],[],[2],[3],[4],[5,100],[2,100],[],[],[],[]]
 * Output
 * [null,null,null,2,null,null,null,null,null,103,202,201,-1]
 * Explanation
 * CustomStack customStack = new CustomStack(3); // Stack is Empty []
 * customStack.push(1);                          // stack becomes [1]
 * customStack.push(2);                          // stack becomes [1, 2]
 * customStack.pop();                            // return 2 --> Return top of the stack 2, stack becomes [1]
 * customStack.push(2);                          // stack becomes [1, 2]
 * customStack.push(3);                          // stack becomes [1, 2, 3]
 * customStack.push(4);                          // stack still [1, 2, 3], Don't add another elements as size is 4
 * customStack.increment(5, 100);                // stack becomes [101, 102, 103]
 * customStack.increment(2, 100);                // stack becomes [201, 202, 103]
 * customStack.pop();                            // return 103 --> Return top of the stack 103, stack becomes [201,
 * 202]
 * customStack.pop();                            // return 202 --> Return top of the stack 102, stack becomes [201]
 * customStack.pop();                            // return 201 --> Return top of the stack 101, stack becomes []
 * customStack.pop();                            // return -1 --> Stack is empty return -1.
 *
 * Constraints:
 *
 * 1 <= maxSize <= 1000
 * 1 <= x <= 1000
 * 1 <= k <= 1000
 * 0 <= val <= 100
 * At most 1000 calls will be made to each method of increment, push and pop each separately.
 */
public class Q1381_DesignAStackWithIncrementOperation {

    private static class CustomStack {

        // 保存数据的栈
        private int[] stack;

        // 要累加的值
        private int[] plus;

        // 栈的大小
        private int size;

        public CustomStack(int maxSize) {
            this.stack = new int[maxSize];
            this.plus = new int[maxSize];
        }

        public void push(int x) {
            if (size == stack.length) {
                return;
            }
            stack[size] = x;
            size++;
        }

        public int pop() {
            if (size == 0) {
                return -1;
            }
            size--;
            int val = stack[size] + plus[size];
            if (size > 0) {
                plus[size - 1] += plus[size];
            }
            plus[size] = 0;
            return val;
        }

        public void increment(int k, int val) {
            int idx = k > size ? size - 1 : k - 1;
            if (idx >= 0) {
                plus[idx] += val;
            }
        }
    }

    @Test
    public void testMethod1() {
        CustomStack customStack = new CustomStack(3);
        customStack.push(1);
        customStack.push(2);
        Assert.assertEquals(2, customStack.pop());
        customStack.push(2);
        customStack.push(3);
        customStack.push(4);
        customStack.increment(5, 100);
        customStack.increment(2, 100);
        Assert.assertEquals(103, customStack.pop());
        Assert.assertEquals(202, customStack.pop());
        Assert.assertEquals(201, customStack.pop());
        Assert.assertEquals(-1, customStack.pop());
    }

    @Test
    public void testMethod2() {
        CustomStack customStack = new CustomStack(2);
        customStack.push(34);
        Assert.assertEquals(34, customStack.pop());
        customStack.increment(8, 100);
        Assert.assertEquals(-1, customStack.pop());
        customStack.increment(9, 91);
        customStack.push(63);
        Assert.assertEquals(63, customStack.pop());
        customStack.push(84);
        customStack.increment(10, 93);
        customStack.increment(6, 45);
        customStack.increment(10, 4);
    }
}
