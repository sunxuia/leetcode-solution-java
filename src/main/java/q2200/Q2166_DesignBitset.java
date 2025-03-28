package q2200;

import org.junit.Assert;
import org.junit.Test;

/**
 * <h3>[Medium] 2166. Design Bitset</h3>
 * <a href="https://leetcode.com/problems/design-bitset/">
 * https://leetcode.com/problems/design-bitset/
 * </a><br/>
 *
 * <p>A <strong>Bitset</strong> is a data structure that compactly stores bits.</p>
 *
 * <p>Implement the <code>Bitset</code> class:</p>
 *
 * <ul>
 * 	<li><code>Bitset(int size)</code> Initializes the Bitset with <code>size</code> bits, all of which are
 * 	<code>0</code>.</li>
 * 	<li><code>void fix(int idx)</code> Updates the value of the bit at the index <code>idx</code> to <code>1</code>.
 * 	If the value was already <code>1</code>, no change occurs.</li>
 * 	<li><code>void unfix(int idx)</code> Updates the value of the bit at the index <code>idx</code> to <code>0</code>.
 * 	If the value was already <code>0</code>, no change occurs.</li>
 * 	<li><code>void flip()</code> Flips the values of each bit in the Bitset. In other words, all bits with value
 * 	<code>0</code> will now have value <code>1</code> and vice versa.</li>
 * 	<li><code>boolean all()</code> Checks if the value of <strong>each</strong> bit in the Bitset is <code>1</code>.
 * 	Returns <code>true</code> if it satisfies the condition, <code>false</code> otherwise.</li>
 * 	<li><code>boolean one()</code> Checks if there is <strong>at least one</strong> bit in the Bitset with value
 * 	<code>1</code>. Returns <code>true</code> if it satisfies the condition, <code>false</code> otherwise.</li>
 * 	<li><code>int count()</code> Returns the <strong>total number</strong> of bits in the Bitset which have value
 * 	<code>1</code>.</li>
 * 	<li><code>String toString()</code> Returns the current composition of the Bitset. Note that in the resultant
 * 	string, the character at the <code>i<sup>th</sup></code> index should coincide with the value at the
 * 	<code>i<sup>th</sup></code> bit of the Bitset.</li>
 * </ul>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input</strong>
 * [&quot;Bitset&quot;, &quot;fix&quot;, &quot;fix&quot;, &quot;flip&quot;, &quot;all&quot;, &quot;unfix&quot;, &quot;flip&quot;, &quot;one&quot;, &quot;unfix&quot;, &quot;count&quot;, &quot;toString&quot;]
 * [[5], [3], [1], [], [], [0], [], [], [0], [], []]
 * <strong>Output</strong>
 * [null, null, null, null, false, null, null, true, null, 2, &quot;01010&quot;]
 *
 * <strong>Explanation</strong>
 * Bitset bs = new Bitset(5); // bitset = &quot;00000&quot;.
 * bs.fix(3);     // the value at idx = 3 is updated to 1, so bitset = &quot;00010&quot;.
 * bs.fix(1);     // the value at idx = 1 is updated to 1, so bitset = &quot;01010&quot;.
 * bs.flip();     // the value of each bit is flipped, so bitset = &quot;10101&quot;.
 * bs.all();      // return False, as not all values of the bitset are 1.
 * bs.unfix(0);   // the value at idx = 0 is updated to 0, so bitset = &quot;00101&quot;.
 * bs.flip();     // the value of each bit is flipped, so bitset = &quot;11010&quot;.
 * bs.one();      // return True, as there is at least 1 index with value 1.
 * bs.unfix(0);   // the value at idx = 0 is updated to 0, so bitset = &quot;01010&quot;.
 * bs.count();    // return 2, as there are 2 bits with value 1.
 * bs.toString(); // return &quot;01010&quot;, which is the composition of bitset.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= size &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>0 &lt;= idx &lt;= size - 1</code></li>
 * 	<li>At most <code>10<sup>5</sup></code> calls will be made <strong>in total</strong> to <code>fix</code>,
 * 	<code>unfix</code>, <code>flip</code>, <code>all</code>, <code>one</code>, <code>count</code>, and
 * 	<code>toString</code>.</li>
 * 	<li>At least one call will be made to <code>all</code>, <code>one</code>, <code>count</code>, or
 * 	<code>toString</code>.</li>
 * 	<li>At most <code>5</code> calls will be made to <code>toString</code>.</li>
 * </ul>
 */
public class Q2166_DesignBitset {

    private static class Bitset {

        final long[] bits;

        final int size;

        int one;

        boolean flip;

        public Bitset(int size) {
            bits = new long[(size + 63) / 64];
            this.size = size;
        }

        public void fix(int idx) {
            one += 1 - get(idx);
            set(idx, 1);
        }

        private int get(int idx) {
            int v = (int) (bits[idx / 64] >> (idx % 64) & 1);
            return flip ? 1 - v : v;
        }

        private void set(int idx, int value) {
            value = flip ? 1 - value : value;
            if (value == 0) {
                bits[idx / 64] &= ~(1L << (idx % 64));
            } else {
                bits[idx / 64] |= 1L << (idx % 64);
            }
        }

        public void unfix(int idx) {
            one -= get(idx);
            set(idx, 0);
        }

        public void flip() {
            one = size - one;
            flip = !flip;
        }

        public boolean all() {
            return one == size;
        }

        public boolean one() {
            return one > 0;
        }

        public int count() {
            return one;
        }

        public String toString() {
            char[] cs = new char[size];
            for (int i = 0; i < size; i++) {
                cs[i] = get(i) == 1 ? '1' : '0';
            }
            return new String(cs);
        }
    }

    /**
     * Your Bitset object will be instantiated and called as such:
     * Bitset obj = new Bitset(size);
     * obj.fix(idx);
     * obj.unfix(idx);
     * obj.flip();
     * boolean param_4 = obj.all();
     * boolean param_5 = obj.one();
     * int param_6 = obj.count();
     * String param_7 = obj.toString();
     */

    @Test
    public void testMethod() {
        Q2166_DesignBitset.Bitset bs = new Bitset(5); // bitset = "00000".
        bs.fix(3);     // the value at idx = 3 is updated to 1, so bitset = "00010".
        bs.fix(1);     // the value at idx = 1 is updated to 1, so bitset = "01010".
        bs.flip();     // the value of each bit is flipped, so bitset = "10101".
        boolean all = bs.all();      // return False, as not all values of the bitset are 1.
        Assert.assertFalse(all);
        bs.unfix(0);   // the value at idx = 0 is updated to 0, so bitset = "00101".
        bs.flip();     // the value of each bit is flipped, so bitset = "11010".
        boolean one = bs.one();      // return True, as there is at least 1 index with value 1.
        Assert.assertTrue(one);
        bs.unfix(0);   // the value at idx = 0 is updated to 0, so bitset = "01010".
        int count = bs.count();    // return 2, as there are 2 bits with value 1.
        Assert.assertEquals(2, count);
        String str = bs.toString(); // return "01010", which is the composition of bitset.
        Assert.assertEquals("01010", str);
    }

}
