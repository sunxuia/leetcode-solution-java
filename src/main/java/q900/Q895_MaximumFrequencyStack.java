package q900;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import org.junit.Assert;
import org.junit.Test;

/**
 * [Hard] 895. Maximum Frequency Stack
 * https://leetcode.com/problems/maximum-frequency-stack/
 *
 * Implement FreqStack, a class which simulates the operation of a stack-like data structure.
 *
 * FreqStack has two functions:
 *
 * push(int x), which pushes an integer x onto the stack.
 * pop(), which removes and returns the most frequent element in the stack.
 *
 * If there is a tie for most frequent element, the element closest to the top of the stack is removed and returned.
 *
 *
 *
 * Example 1:
 *
 * Input:
 * ["FreqStack","push","push","push","push","push","push","pop","pop","pop","pop"],
 * [[],[5],[7],[5],[7],[4],[5],[],[],[],[]]
 * Output: [null,null,null,null,null,null,null,5,7,5,4]
 * Explanation:
 * After making six .push operations, the stack is [5,7,5,7,4,5] from bottom to top.  Then:
 *
 * pop() -> returns 5, as 5 is the most frequent.
 * The stack becomes [5,7,5,7,4].
 *
 * pop() -> returns 7, as 5 and 7 is the most frequent, but 7 is closest to the top.
 * The stack becomes [5,7,5,4].
 *
 * pop() -> returns 5.
 * The stack becomes [5,7,4].
 *
 * pop() -> returns 4.
 * The stack becomes [5,7].
 *
 * Note:
 *
 * Calls to FreqStack.push(int x) will be such that 0 <= x <= 10^9.
 * It is guaranteed that FreqStack.pop() won't be called if the stack has zero elements.
 * The total number of FreqStack.push calls will not exceed 10000 in a single test case.
 * The total number of FreqStack.pop calls will not exceed 10000 in a single test case.
 * The total number of FreqStack.push and FreqStack.pop calls will not exceed 150000 across all test cases.
 */
public class Q895_MaximumFrequencyStack {

    private static class FreqStack {

        static class Node {

            Node peer;

            int val, no, peerNo;
        }

        int nextNo = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) ->
                a.peerNo == b.peerNo ? b.no - a.no : b.peerNo - a.peerNo);

        Map<Integer, Node> map = new HashMap<>();

        public FreqStack() {

        }

        public void push(int x) {
            Node newNode = new Node();
            newNode.val = x;
            newNode.no = nextNo++;

            if (map.containsKey(x)) {
                Node peer = map.get(x);
                newNode.peerNo = peer.peerNo + 1;
                newNode.peer = peer;
            }
            map.put(x, newNode);

            pq.add(newNode);
        }

        public int pop() {
            Node node = pq.remove();
            if (node.peer == null) {
                map.remove(node.val);
            } else {
                map.put(node.val, node.peer);
            }
            return node.val;
        }
    }

    /**
     * Your FreqStack object will be instantiated and called as such:
     * FreqStack obj = new FreqStack();
     * obj.push(x);
     * int param_2 = obj.pop();
     */

    @Test
    public void testMethod() {
        FreqStack tested = new FreqStack();
        tested.push(5);
        tested.push(7);
        tested.push(5);
        tested.push(7);
        tested.push(4);
        tested.push(5);
        Assert.assertEquals(5, tested.pop());
        Assert.assertEquals(7, tested.pop());
        Assert.assertEquals(5, tested.pop());
        Assert.assertEquals(4, tested.pop());
    }

    @Test
    public void testMethod2() {
        FreqStack tested = new FreqStack();
        tested.push(5);
        tested.push(1);
        tested.push(2);
        tested.push(5);
        tested.push(5);
        tested.push(5);
        tested.push(1);
        tested.push(6);
        tested.push(1);
        tested.push(5);
        Assert.assertEquals(5, tested.pop());
        Assert.assertEquals(5, tested.pop());
        Assert.assertEquals(1, tested.pop());
        Assert.assertEquals(5, tested.pop());
        Assert.assertEquals(1, tested.pop());
        Assert.assertEquals(5, tested.pop());
        Assert.assertEquals(6, tested.pop());
        Assert.assertEquals(2, tested.pop());
        Assert.assertEquals(1, tested.pop());
        Assert.assertEquals(5, tested.pop());
    }

}
