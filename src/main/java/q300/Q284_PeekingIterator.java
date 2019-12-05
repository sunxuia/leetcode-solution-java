package q300;

import java.util.Arrays;
import java.util.Iterator;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/peeking-iterator/
 *
 * Given an Iterator class interface with methods: next() and hasNext(), design and implement a PeekingIterator that
 * support the peek() operation -- it essentially peek() at the element that will be returned by the next call to
 * next().
 *
 * Example:
 *
 * Assume that the iterator is initialized to the beginning of the list: [1,2,3].
 *
 * Call next() gets you 1, the first element in the list.
 * Now you call peek() and it returns 2, the next element. Calling next() after that still return 2.
 * You call next() the final time and it returns 3, the last element.
 * Calling hasNext() after that should return false.
 *
 * Follow up: How would you extend your design to be generic and work with all types, not just integer?
 */
public class Q284_PeekingIterator {

    // (这题不需要考虑 iterator.next() 返回 null 的情况 (无相关测试用例))
    private static class PeekingIterator implements Iterator<Integer> {

        private Integer top;

        private final Iterator<Integer> iterator;

        public PeekingIterator(Iterator<Integer> iterator) {
            // initialize any member here.
            this.iterator = iterator;
        }

        // Returns the next element in the iteration without advancing the iterator.
        public Integer peek() {
            if (top == null) {
                top = iterator.next();
            }
            return top;
        }

        // hasNext() and next() should behave the same as in the Iterator interface.
        // Override them if needed.
        @Override
        public Integer next() {
            Integer res;
            if (top == null) {
                res = iterator.next();
            } else {
                res = top;
                top = null;
            }
            return res;
        }

        @Override
        public boolean hasNext() {
            return top != null || iterator.hasNext();
        }
    }

    @Test
    public void testMethod() {
        PeekingIterator it = new PeekingIterator(Arrays.asList(1, 2, 3).iterator());
        Assert.assertEquals(1, (int) it.next());
        Assert.assertEquals(2, (int) it.peek());
        Assert.assertEquals(2, (int) it.next());
        Assert.assertEquals(3, (int) it.next());
        Assert.assertFalse(it.hasNext());
    }

}
