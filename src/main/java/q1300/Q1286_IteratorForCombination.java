package q1300;

import org.junit.Assert;
import org.junit.Test;

/**
 * [Medium] 1286. Iterator for Combination
 * https://leetcode.com/problems/iterator-for-combination/
 *
 * Design an Iterator class, which has:
 *
 * A constructor that takes a string characters of sorted distinct lowercase English letters and a number
 * combinationLength as arguments.
 * A function next() that returns the next combination of length combinationLength in lexicographical order.
 * A function hasNext() that returns True if and only if there exists a next combination.
 *
 * Example:
 *
 * CombinationIterator iterator = new CombinationIterator("abc", 2); // creates the iterator.
 *
 * iterator.next(); // returns "ab"
 * iterator.hasNext(); // returns true
 * iterator.next(); // returns "ac"
 * iterator.hasNext(); // returns true
 * iterator.next(); // returns "bc"
 * iterator.hasNext(); // returns false
 *
 * Constraints:
 *
 * 1 <= combinationLength <= characters.length <= 15
 * There will be at most 10^4 function calls per test.
 * It's guaranteed that all calls of the function next are valid.
 */
public class Q1286_IteratorForCombination {

    private static class CombinationIterator {

        final char[] characters;

        final int combinationLength;

        // indexes[i] 表示当前组合结果的第i 位对应的字符是 characters[indexes[i]]
        final int[] indexes;

        public CombinationIterator(String characters, int combinationLength) {
            this.characters = characters.toCharArray();
            this.combinationLength = combinationLength;

            this.indexes = new int[combinationLength + 1];
            for (int i = 0; i < combinationLength; i++) {
                indexes[i] = i;
            }
            indexes[combinationLength] = this.characters.length;
        }

        public String next() {
            char[] sb = new char[combinationLength];
            for (int i = 0; i < combinationLength; i++) {
                sb[i] = characters[indexes[i]];
            }
            goNext();
            return new String(sb);
        }

        private void goNext() {
            int i = combinationLength - 1;
            while (i >= 0 && indexes[i] + 1 == indexes[i + 1]) {
                i--;
            }
            if (i < 0) {
                // 结束了
                indexes[0] = -1;
            } else {
                indexes[i]++;
                while (++i < combinationLength) {
                    indexes[i] = indexes[i - 1] + 1;
                }
            }
        }

        public boolean hasNext() {
            return indexes[0] != -1;
        }
    }

    @Test
    public void testMethod() {
        CombinationIterator iterator = new CombinationIterator("abc", 2);
        Assert.assertEquals("ab", iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("ac", iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("bc", iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }

}
