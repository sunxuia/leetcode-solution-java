package q1650;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * [Hard] 1622. Fancy Sequence
 * https://leetcode.com/problems/fancy-sequence/
 *
 * Write an API that generates fancy sequences using the append, addAll, and multAll operations.
 *
 * Implement the Fancy class:
 *
 * Fancy() Initializes the object with an empty sequence.
 * void append(val) Appends an integer val to the end of the sequence.
 * void addAll(inc) Increments all existing values in the sequence by an integer inc.
 * void multAll(m) Multiplies all existing values in the sequence by an integer m.
 * int getIndex(idx) Gets the current value at index idx (0-indexed) of the sequence modulo 109 + 7. If the index is
 * greater or equal than the length of the sequence, return -1.
 *
 * Example 1:
 *
 * Input
 * ["Fancy", "append", "addAll", "append", "multAll", "getIndex", "addAll", "append", "multAll", "getIndex", "getIndex",
 * "getIndex"]
 * [[], [2], [3], [7], [2], [0], [3], [10], [2], [0], [1], [2]]
 * Output
 * [null, null, null, null, null, 10, null, null, null, 26, 34, 20]
 *
 * Explanation
 * Fancy fancy = new Fancy();
 * fancy.append(2);   // fancy sequence: [2]
 * fancy.addAll(3);   // fancy sequence: [2+3] -> [5]
 * fancy.append(7);   // fancy sequence: [5, 7]
 * fancy.multAll(2);  // fancy sequence: [5*2, 7*2] -> [10, 14]
 * fancy.getIndex(0); // return 10
 * fancy.addAll(3);   // fancy sequence: [10+3, 14+3] -> [13, 17]
 * fancy.append(10);  // fancy sequence: [13, 17, 10]
 * fancy.multAll(2);  // fancy sequence: [13*2, 17*2, 10*2] -> [26, 34, 20]
 * fancy.getIndex(0); // return 26
 * fancy.getIndex(1); // return 34
 * fancy.getIndex(2); // return 20
 *
 * Constraints:
 *
 * 1 <= val, inc, m <= 100
 * 0 <= idx <= 10^5
 * At most 10^5 calls total will be made to append, addAll, multAll, and getIndex.
 */
public class Q1622_FancySequence {

    /**
     * 路径压缩的题目
     */
    private static class Fancy {

        final int MOD = 10_0000_0007;

        List<NumNode> nums = new ArrayList<>();

        Operation dummy = new Operation();

        public Fancy() {

        }

        public void append(int val) {
            NumNode node = new NumNode();
            node.val = val;
            node.operation = dummy;
            nums.add(node);
        }

        public void addAll(int inc) {
            dummy.plus = inc;
            dummy.next = new Operation();
            dummy = dummy.next;
        }

        public void multAll(int m) {
            dummy.multiply = m;
            dummy.next = new Operation();
            dummy = dummy.next;
        }

        public int getIndex(int idx) {
            if (idx >= nums.size()) {
                return -1;
            }
            NumNode node = nums.get(idx);
            Operation oper = compress(node.operation);
            node.val = (int) ((node.val * oper.multiply % MOD + oper.plus) % MOD);
            node.operation = dummy;
            return node.val;
        }

        private Operation compress(Operation oper) {
            if (oper == dummy) {
                return oper;
            }
            Operation next = compress(oper.next);
            oper.multiply = oper.multiply * next.multiply % MOD;
            oper.plus = (oper.plus * next.multiply % MOD + next.plus) % MOD;
            oper.next = dummy;
            return oper;
        }

        class Operation {

            long multiply = 1, plus = 0;

            Operation next;
        }

        class NumNode {

            int val;

            Operation operation;

        }
    }

    @Test
    public void testMethod() {
        Fancy fancy = new Fancy();
        fancy.append(2);
        fancy.addAll(3);
        fancy.append(7);
        fancy.multAll(2);
        Assert.assertEquals(10, fancy.getIndex(0));
        fancy.addAll(3);
        fancy.append(10);
        fancy.multAll(2);
        Assert.assertEquals(26, fancy.getIndex(0));
        Assert.assertEquals(34, fancy.getIndex(1));
        Assert.assertEquals(20, fancy.getIndex(2));
    }

}
