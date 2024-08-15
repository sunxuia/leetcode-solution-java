package q2100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Hard] 2081. Sum of k-Mirror Numbers</h3>
 * <a href="https://leetcode.com/problems/sum-of-k-mirror-numbers/">
 * https://leetcode.com/problems/sum-of-k-mirror-numbers/
 * </a><br/>
 *
 * <p>A <strong>k-mirror number</strong> is a <strong>positive</strong> integer <strong>without leading zeros</strong>
 * that reads the same both forward and backward in base-10 <strong>as well as</strong> in base-k.</p>
 *
 * <ul>
 * 	<li>For example, <code>9</code> is a 2-mirror number. The representation of <code>9</code> in base-10 and base-2
 * 	are <code>9</code> and <code>1001</code> respectively, which read the same both forward and backward.</li>
 * 	<li>On the contrary, <code>4</code> is not a 2-mirror number. The representation of <code>4</code> in base-2 is
 * 	<code>100</code>, which does not read the same both forward and backward.</li>
 * </ul>
 *
 * <p>Given the base <code>k</code> and the number <code>n</code>, return <em>the <strong>sum</strong> of the</em>
 * <code>n</code> <em><strong>smallest</strong> k-mirror numbers</em>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> k = 2, n = 5
 * <strong>Output:</strong> 25
 * <strong>Explanation:
 * </strong>The 5 smallest 2-mirror numbers and their representations in base-2 are listed as follows:
 *   base-10    base-2
 *     1          1
 *     3          11
 *     5          101
 *     7          111
 *     9          1001
 * Their sum = 1 + 3 + 5 + 7 + 9 = 25.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> k = 3, n = 7
 * <strong>Output:</strong> 499
 * <strong>Explanation:
 * </strong>The 7 smallest 3-mirror numbers are and their representations in base-3 are listed as follows:
 *   base-10    base-3
 *     1          1
 *     2          2
 *     4          11
 *     8          22
 *     121        11111
 *     151        12121
 *     212        21212
 * Their sum = 1 + 2 + 4 + 8 + 121 + 151 + 212 = 499.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> k = 7, n = 17
 * <strong>Output:</strong> 20379000
 * <strong>Explanation:</strong> The 17 smallest 7-mirror numbers are:
 * 1, 2, 3, 4, 5, 6, 8, 121, 171, 242, 292, 16561, 65656, 2137312, 4602064, 6597956, 6958596
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>2 &lt;= k &lt;= 9</code></li>
 * 	<li><code>1 &lt;= n &lt;= 30</code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2081_SumOfKMirrorNumbers {

    @Answer
    public long kMirror(int k, int n) {
        MirrorNumber base10 = new MirrorNumber(10);
        MirrorNumber baseK = new MirrorNumber(k);
        long res = 0;
        for (int i = 0; i < n; i++) {
            long v10 = base10.next();
            long vk = baseK.next();
            // 肯定会有结果, 因此这里不用担心死循环
            while (v10 != vk) {
                if (v10 < vk) {
                    v10 = base10.next();
                } else {
                    vk = baseK.next();
                }
            }
            res += v10;
        }
        return res;
    }

    private static class MirrorNumber {

        final int k;

        // 当前值
        long value = 0;

        // 当前位数, 最高位为 1 的掩码, 中间位为 1 的掩码
        long digit = 1, mask = 1, midMask = 1;

        // 当前变更的高位和低位掩码
        long high = 1, low = 1;

        public MirrorNumber(int k) {
            this.k = k;
        }

        long next() {
            // 要改变的位数的值
            long v = value / low % k;
            if (low == high) {
                // 改变最中间一位的值, 先将这位置0
                value = value - v * high;
                if (v == k - 1) {
                    // 此位到头
                    if (low == 1) {
                        // 要进位了
                        // 这个条件对应的情况只有个位数->两位数的时候才会发生(高低位相同且需要进位)
                        digit = 2;
                        mask = k;
                        value = mask + 1;
                        // low 还是1, 高位变为第二位
                        high = k;
                    } else {
                        // 此位到头, 已经置为0 了, 外面的位数 +1
                        high *= k;
                        low /= k;
                        next();
                    }
                } else {
                    // 此位值 +1
                    v++;
                    value = value + v * high;
                }
            } else {
                // 改变对称的两位的值, 将其置0
                value = value - v * high - v * low;
                if (v == k - 1) {
                    // 此位到头
                    if (low == 1) {
                        // 要进位了
                        // 新的结果就是 10001 这样的(中间有digit-2 个0)
                        // 要变化的位重新设置为最中间的位
                        digit++;
                        mask *= k;
                        value = mask + 1;
                        if (digit % 2 == 0) {
                            // 偶数位数的中间是两位
                            low = midMask;
                            high = midMask * k;
                        } else {
                            // 相比之前的偶数位数, 这里中间位数+1
                            midMask *= k;
                            // 奇数位数的中间只有一位
                            low = high = midMask;
                        }
                    } else {
                        // 镜像位到头, 已经置为0 了, 外面的位数 +1
                        high *= k;
                        low /= k;
                        next();
                    }
                } else {
                    // 镜像位值 +1
                    v++;
                    value = value + v * high + v * low;
                    // 变化后, 需要变化的位来到中间的位
                    if (digit % 2 == 0) {
                        low = midMask;
                        high = midMask * k;
                    } else {
                        low = high = midMask;
                    }
                }
            }
            return value;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(2, 5).expect(25L);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(3, 7).expect(499L);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(7, 17).expect(20379000L);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(5, 25).expect(6849225412L);

}
