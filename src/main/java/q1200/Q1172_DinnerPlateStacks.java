package q1200;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * [Hard] 1172. Dinner Plate Stacks
 * https://leetcode.com/problems/dinner-plate-stacks/
 *
 * You have an infinite number of stacks arranged in a row and numbered (left to right) from 0, each of the stacks has
 * the same maximum capacity.
 *
 * Implement the DinnerPlates class:
 *
 * 1. DinnerPlates(int capacity) Initializes the object with the maximum capacity of the stacks.
 * 2. void push(int val) Pushes the given positive integer val into the leftmost stack with size less than capacity.
 * 3. int pop() Returns the value at the top of the rightmost non-empty stack and removes it from that stack, and
 * returns -1 if all stacks are empty.
 * 4. int popAtStack(int index) Returns the value at the top of the stack with the given index and removes it from that
 * stack, and returns -1 if the stack with that given index is empty.
 *
 * Example:
 *
 * Input:
 * ["DinnerPlates","push","push","push","push","push","popAtStack","push","push","popAtStack","popAtStack","pop",
 * "pop","pop","pop","pop"]
 * [[2],[1],[2],[3],[4],[5],[0],[20],[21],[0],[2],[],[],[],[],[]]
 * Output:
 * [null,null,null,null,null,null,2,null,null,20,21,5,4,3,1,-1]
 *
 * Explanation:
 * > DinnerPlates D = DinnerPlates(2);  // Initialize with capacity = 2
 * > D.push(1);
 * > D.push(2);
 * > D.push(3);
 * > D.push(4);
 * > D.push(5);         // The stacks are now:  2  4
 * >                                            1  3  5
 * >                                            ? ? ?
 * > D.popAtStack(0);   // Returns 2.  The stacks are now:     4
 * >                                                        1  3  5
 * >                                                        ? ? ?
 * > D.push(20);        // The stacks are now: 20  4
 * >                                            1  3  5
 * >                                            ? ? ?
 * > D.push(21);        // The stacks are now: 20  4 21
 * >                                            1  3  5
 * >                                            ? ? ?
 * > D.popAtStack(0);   // Returns 20.  The stacks are now:     4 21
 * >                                                         1  3  5
 * >                                                         ? ? ?
 * > D.popAtStack(2);   // Returns 21.  The stacks are now:     4
 * >                                                         1  3  5
 * >                                                         ? ? ?
 * > D.pop()            // Returns 5.  The stacks are now:      4
 * >                                                         1  3
 * >                                                         ? ?
 * > D.pop()            // Returns 4.  The stacks are now:   1  3
 * >                                                         ? ?
 * > D.pop()            // Returns 3.  The stacks are now:   1
 * >                                                         ?
 * > D.pop()            // Returns 1.  There are no stacks.
 * > D.pop()            // Returns -1.  There are still no stacks.
 *
 * Constraints:
 *
 * 1 <= capacity <= 20000
 * 1 <= val <= 20000
 * 0 <= index <= 100000
 * At most 200000 calls will be made to push, pop, and popAtStack.
 */
public class Q1172_DinnerPlateStacks {

    private static class DinnerPlates {

        List<Deque<Integer>> stacks = new ArrayList<>();

        int leftMost = 0, rightMost = 0;

        final int capacity;

        public DinnerPlates(int capacity) {
            this.capacity = capacity;
            stacks.add(new ArrayDeque<>(capacity));
        }

        public void push(int val) {
            while (leftMost < stacks.size()
                    && stacks.get(leftMost).size() == capacity) {
                leftMost++;
            }
            if (leftMost == stacks.size()) {
                stacks.add(new ArrayDeque<>(capacity));
            }
            rightMost = Math.max(rightMost, leftMost);
            stacks.get(leftMost).push(val);
        }

        public int pop() {
            while (rightMost > 0 && stacks.get(rightMost).isEmpty()) {
                rightMost--;
            }
            leftMost = Math.min(leftMost, rightMost);
            Deque<Integer> stack = stacks.get(rightMost);
            if (stack.isEmpty()) {
                return -1;
            }
            return stack.pop();
        }

        public int popAtStack(int index) {
            if (stacks.size() <= index) {
                return -1;
            }
            Deque<Integer> stack = stacks.get(index);
            if (stack.isEmpty()) {
                return -1;
            }
            leftMost = Math.min(leftMost, index);
            return stack.pop();
        }
    }

    /**
     * Your DinnerPlates object will be instantiated and called as such:
     * DinnerPlates obj = new DinnerPlates(capacity);
     * obj.push(val);
     * int param_2 = obj.pop();
     * int param_3 = obj.popAtStack(index);
     */

    @Test
    public void testMethod() {
        DinnerPlates dp = new DinnerPlates(2);
        dp.push(1);
        dp.push(2);
        dp.push(3);
        dp.push(4);
        dp.push(5);
        Assert.assertEquals(2, dp.popAtStack(0));
        dp.push(20);
        dp.push(21);
        Assert.assertEquals(20, dp.popAtStack(0));
        Assert.assertEquals(21, dp.popAtStack(2));
        Assert.assertEquals(5, dp.pop());
        Assert.assertEquals(4, dp.pop());
        Assert.assertEquals(3, dp.pop());
        Assert.assertEquals(1, dp.pop());
        Assert.assertEquals(-1, dp.pop());
    }

}
