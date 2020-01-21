package q350;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/flatten-nested-list-iterator/
 *
 * Given a nested list of integers, implement an iterator to flatten it.
 *
 * Each element is either an integer, or a list -- whose elements may also be integers or other lists.
 *
 * Example 1:
 *
 * Input: [[1,1],2,[1,1]]
 * Output: [1,1,2,1,1]
 * Explanation: By calling next repeatedly until hasNext returns false,
 * the order of elements returned by next should be: [1,1,2,1,1].
 *
 * Example 2:
 *
 * Input: [1,[4,[6]]]
 * Output: [1,4,6]
 * Explanation: By calling next repeatedly until hasNext returns false,
 * the order of elements returned by next should be: [1,4,6].
 */
public class Q341_FlattenNestedListIterator {

    private interface NestedInteger {

        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return null if this NestedInteger holds a single integer
        List<NestedInteger> getList();
    }

    private class NestedIterator implements Iterator<Integer> {

        private Deque<Iterator<NestedInteger>> stack = new ArrayDeque<>();

        private Integer next;

        public NestedIterator(List<NestedInteger> nestedList) {
            stack.push(nestedList.iterator());
        }

        @Override
        public Integer next() {
            if (next != null) {
                Integer res = next;
                next = null;
                return res;
            }
            return getNext();
        }

        private Integer getNext() {
            while (!stack.isEmpty()) {
                if (stack.peek().hasNext()) {
                    NestedInteger val = stack.peek().next();
                    if (val.isInteger()) {
                        return val.getInteger();
                    }
                    stack.push(val.getList().iterator());
                } else {
                    stack.pop();
                }
            }
            return null;
        }

        @Override
        public boolean hasNext() {
            next = getNext();
            return next != null;
        }
    }

    @Test
    public void testMethod() {
        NestedIterator iterator = new NestedIterator(Arrays.asList(
                new NestedIntegerImpl(Arrays.asList(new NestedIntegerImpl(1), new NestedIntegerImpl(1))),
                new NestedIntegerImpl(2),
                new NestedIntegerImpl(Arrays.asList(new NestedIntegerImpl(1), new NestedIntegerImpl(1)))
        ));
        assertIterator(iterator, Arrays.asList(1, 1, 2, 1, 1));

        iterator = new NestedIterator(Arrays.asList(
                new NestedIntegerImpl(1),
                new NestedIntegerImpl(Arrays.asList(new NestedIntegerImpl(4),
                        new NestedIntegerImpl(Collections.singletonList(new NestedIntegerImpl(6)))))
        ));
        assertIterator(iterator, Arrays.asList(1, 4, 6));

        iterator = new NestedIterator(Arrays.asList(
                new NestedIntegerImpl(Collections.emptyList()),
                new NestedIntegerImpl(Collections.singletonList(
                        new NestedIntegerImpl(Collections.singletonList(
                                new NestedIntegerImpl(1)))))
        ));
        assertIterator(iterator, Collections.singletonList(1));
    }

    private static class NestedIntegerImpl implements NestedInteger {

        private int integer;

        private List<NestedInteger> list;

        public NestedIntegerImpl(int integer) {
            this.integer = integer;
        }

        public NestedIntegerImpl(List<NestedInteger> list) {
            this.list = list;
        }

        @Override
        public boolean isInteger() {
            return this.list == null;
        }

        @Override
        public Integer getInteger() {
            return integer;
        }

        @Override
        public List<NestedInteger> getList() {
            return list;
        }
    }

    private void assertIterator(NestedIterator iterator, List<Integer> expects) {
        for (Integer expect : expects) {
            Assert.assertTrue("expect [" + expect + "], actual is stream end.", iterator.hasNext());
            Integer act = iterator.next();
            Assert.assertEquals(expect, act);
        }
        Assert.assertFalse("expect stream end, actual is not", iterator.hasNext());
    }

}
