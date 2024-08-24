package q2150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Hard] 2117. Abbreviating the Product of a Range</h3>
 * <a href="https://leetcode.com/problems/abbreviating-the-product-of-a-range/">
 * https://leetcode.com/problems/abbreviating-the-product-of-a-range/
 * </a><br/>
 *
 * <p>You are given two positive integers <code>left</code> and <code>right</code> with <code>left &lt;= right</code>.
 * Calculate the <strong>product</strong> of all integers in the <strong>inclusive</strong> range <code>[left,
 * right]</code>.</p>
 *
 * <p>Since the product may be very large, you will <strong>abbreviate</strong> it following these steps:</p>
 *
 * <ol>
 * 	<li>Count all <strong>trailing</strong> zeros in the product and <strong>remove</strong> them. Let us denote this
 * 	count as <code>C</code>.
 *
 * 	<ul>
 * 		<li>For example, there are <code>3</code> trailing zeros in <code>1000</code>, and there are <code>0</code>
 * 		trailing zeros in <code>546</code>.</li>
 * 	</ul>
 * 	</li>
 * 	<li>Denote the remaining number of digits in the product as <code>d</code>. If <code>d &gt; 10</code>, then
 * 	express the product as <code>&lt;pre&gt;...&lt;suf&gt;</code> where <code>&lt;pre&gt;</code> denotes the
 * 	<strong>first</strong> <code>5</code> digits of the product, and <code>&lt;suf&gt;</code> denotes the
 * 	<strong>last</strong> <code>5</code> digits of the product <strong>after</strong> removing all trailing zeros. If
 * 	<code>d &lt;= 10</code>, we keep it unchanged.
 * 	<ul>
 * 		<li>For example, we express <code>1234567654321</code> as <code>12345...54321</code>, but <code>1234567</code>
 * 		is represented as <code>1234567</code>.</li>
 * 	</ul>
 * 	</li>
 * 	<li>Finally, represent the product as a <strong>string</strong> <code>&quot;&lt;pre&gt;...&lt;suf&gt;eC&quot;
 * 	</code>.
 * 	<ul>
 * 		<li>For example, <code>12345678987600000</code> will be represented as <code>&quot;12345...89876e5&quot;
 * 		</code>.</li>
 * 	</ul>
 * 	</li>
 * </ol>
 *
 * <p>Return <em>a string denoting the <strong>abbreviated product</strong> of all integers in the
 * <strong>inclusive</strong> range</em> <code>[left, right]</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> left = 1, right = 4
 * <strong>Output:</strong> &quot;24e0&quot;
 * <strong>Explanation:</strong> The product is 1 &times; 2 &times; 3 &times; 4 = 24.
 * There are no trailing zeros, so 24 remains the same. The abbreviation will end with &quot;e0&quot;.
 * Since the number of digits is 2, which is less than 10, we do not have to abbreviate it further.
 * Thus, the final representation is &quot;24e0&quot;.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> left = 2, right = 11
 * <strong>Output:</strong> &quot;399168e2&quot;
 * <strong>Explanation:</strong> The product is 39916800.
 * There are 2 trailing zeros, which we remove to get 399168. The abbreviation will end with &quot;e2&quot;.
 * The number of digits after removing the trailing zeros is 6, so we do not abbreviate it further.
 * Hence, the abbreviated product is &quot;399168e2&quot;.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> left = 371, right = 375
 * <strong>Output:</strong> &quot;7219856259e3&quot;
 * <strong>Explanation:</strong> The product is 7219856259000.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= left &lt;= right &lt;= 10<sup>4</sup></code></li>
 * </ul>
 *
 * <pre>
 *     题解:
 *     最大值 10<sup>4</sup>! = 2.84625968 E+35659
 * </pre>
 */
@RunWith(LeetCodeRunner.class)
public class Q2117_AbbreviatingTheProductOfARange {

    /**
     * 1. 末尾 5 位数可以直接相乘然后取模得到, 精度需要放宽一些 (末尾的 0 会被挪到e 那边).
     * 2. e 后面的 0 的个数可以直接通过上面的末尾 5 位数计算得到. (hint 中有另一中通过计算相乘的数字中的公因数 2和 5 的个数来计算的方法).
     * 3. 前 5 位数使用 long 去精度的方式得到的结果会因为精度问题出错, 因此需要使用double 来保留一部分精度.
     * 3. 下面还有一个根据 hint 中的提示, 使用log 方式来计算的方法.
     */
    @Answer
    public String abbreviateProduct(int left, int right) {
        final long threshold = 10_0000L;
        final long threshold2 = threshold * threshold;
        // 计算的精度, Long.MAX_VALUE 最大可以表示19位十进制数字(不满),
        // 这里取 10^15 为了保证与最大的 num 10^4 相乘不溢出.
        final long precision = (long) Math.pow(10, 14);
        int c = 0;
        long product = left;
        int num = left + 1;
        for (; num <= right && product < precision; num++) {
            product *= num;
            while (product % 10 == 0) {
                product /= 10;
                c++;
            }
        }
        if (product < threshold2) {
            // 计算结果在全量表示的范围内
            return product + "e" + c;
        }

        // 这里使用double 保留一部分精度, 避免截断的精度问题
        double head = product;
        product = product % precision;
        for (; num <= right; num++) {
            product = product * num % precision;
            while (product % 10 == 0) {
                product /= 10;
                c++;
            }
            head *= num;
            // 减小数字与精度, 避免 double 值变为 Infinity
            while (head > precision) {
                head /= 10;
            }
        }
        product %= threshold;
        while (head > threshold) {
            head /= 10;
        }
        return String.format("%d...%05de%d", (int) head, product, c);
    }

    /**
     * 3. 根据 hint, 可以通过 log10 计算得到前5 位数.
     * 具体方式:[left, right] 之间的相乘的结果可以表示为 10^s, 其中 s 为所有数字的log10 之和.
     * s = long10(sum) = log10(left) + log10(left + 1) + ... + log10(right)
     * 同时s 也可以表示为 s = x.y, 其中x 为整数部分, y 为小数部分, 这样要求的结果 product = 10^s = 10^x * 10^0.y,
     * 10^x 是总位数, 10^0.y 就是具体数字部分, 前 5 位数就从这里来的, 这个数字(介于1到10之间) 再 *10000 就可以得到前 5 位数.
     */
    @Answer
    public String abbreviateProduct2(int left, int right) {
        final long threshold = 10_0000L;
        final long threshold2 = threshold * threshold;
        // 计算的精度, Long.MAX_VALUE 最大可以表示19位十进制数字(不满),
        // 这里取 10^15 为了保证与最大的 num 10^4 相乘不溢出.
        final long precision = (long) Math.pow(10, 14);
        int c = 0;
        long product = left;
        int num = left + 1;
        for (; num <= right && product < precision; num++) {
            product *= num;
            while (product % 10 == 0) {
                product /= 10;
                c++;
            }
        }
        if (product < threshold2) {
            // 计算结果在全量表示的范围内
            return product + "e" + c;
        }

        double logSum = Math.log10(product);
        product = product % precision;
        for (; num <= right; num++) {
            product = product * num % precision;
            while (product % 10 == 0) {
                product /= 10;
                c++;
            }
            // 这里要及时减去整数部分, 否则会导致精度问题
            logSum = logSum - Math.floor(logSum) + Math.log10(num);
        }
        product %= threshold;
        // +4 是因为 10^y 的结果是 <10 的数字, *10000 可以得到前 5 位整数
        logSum = logSum - Math.floor(logSum) + 4;
        int head = (int) Math.pow(10, logSum);
        return String.format("%d...%05de%d", head, product, c);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(1, 4).expect("24e0");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(2, 11).expect("399168e2");

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(371, 375).expect("7219856259e3");

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(44, 9556).expect("10205...06688e2378");

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(4838, 6186).expect("36088...36896e337");

    @TestData
    public DataExpectation normal3 = DataExpectation.createWith(5622, 5625).expect("10000...62731e4");

    @TestData
    public DataExpectation normal4 = DataExpectation.createWith(371, 375).expect("7219856259e3");

}
