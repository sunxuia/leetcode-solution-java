package q2100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Easy] 2094. Finding 3-Digit Even Numbers</h3>
 * <a href="https://leetcode.com/problems/finding-3-digit-even-numbers/">
 * https://leetcode.com/problems/finding-3-digit-even-numbers/
 * </a><br/>
 *
 * <p>You are given an integer array <code>digits</code>, where each element is a digit. The array may contain
 * duplicates.</p>
 *
 * <p>You need to find <strong>all</strong> the <strong>unique</strong> integers that follow the given
 * requirements:</p>
 *
 * <ul>
 * 	<li>The integer consists of the <strong>concatenation</strong> of <strong>three</strong> elements from
 * 	<code>digits</code> in <strong>any</strong> arbitrary order.</li>
 * 	<li>The integer does not have <strong>leading zeros</strong>.</li>
 * 	<li>The integer is <strong>even</strong>.</li>
 * </ul>
 *
 * <p>For example, if the given <code>digits</code> were <code>[1, 2, 3]</code>, integers <code>132</code> and
 * <code>312</code> follow the requirements.</p>
 *
 * <p>Return <em>a <strong>sorted</strong> array of the unique integers.</em></p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> digits = [2,1,3,0]
 * <strong>Output:</strong> [102,120,130,132,210,230,302,310,312,320]
 * <strong>Explanation:</strong> All the possible integers that follow the requirements are in the output array.
 * Notice that there are no <strong>odd</strong> integers or integers with <strong>leading zeros</strong>.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> digits = [2,2,8,8,2]
 * <strong>Output:</strong> [222,228,282,288,822,828,882]
 * <strong>Explanation:</strong> The same digit can be used as many times as it appears in digits.
 * In this example, the digit 8 is used twice each time in 288, 828, and 882.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> digits = [3,7,5]
 * <strong>Output:</strong> []
 * <strong>Explanation:</strong> No <strong>even</strong> integers can be formed using the given digits.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>3 &lt;= digits.length &lt;= 100</code></li>
 * 	<li><code>0 &lt;= digits[i] &lt;= 9</code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2094_Finding3DigitEvenNumbers {

    @Answer
    public int[] findEvenNumbers(int[] digits) {
        final int n = digits.length;
        Arrays.sort(digits);
        List<Integer> list = new ArrayList<>();
        Set<Integer> exists = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (digits[i] == 0) {
                continue;
            }
            for (int j = 0; j < n; j++) {
                if (j == i) {
                    continue;
                }
                for (int k = 0; k < n; k++) {
                    if (k == i || k == j || digits[k] % 2 == 1) {
                        continue;
                    }
                    int num = digits[i] * 100 + digits[j] * 10 + digits[k];
                    if (exists.add(num)) {
                        list.add(num);
                    }
                }
            }
        }
        return list.stream()
                .mapToInt(i -> i)
                .toArray();
    }

    // 另一种使用桶排序的方式, 这个要快一些
    @Answer
    public int[] findEvenNumbers2(int[] digits) {
        int[] bucket = new int[10];
        for (int digit : digits) {
            bucket[digit]++;
        }

        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            if (bucket[i] != 0) {
                bucket[i]--;
                for (int j = 0; j < 10; j++) {
                    if (bucket[j] != 0) {
                        bucket[j]--;
                        for (int k = 0; k < 10; k += 2) {
                            if (bucket[k] != 0) {
                                list.add(i * 100 + j * 10 + k);
                            }
                        }
                        bucket[j]++;
                    }
                }
                bucket[i]++;
            }
        }

        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 1, 3, 0})
            .expect(new int[]{102, 120, 130, 132, 210, 230, 302, 310, 312, 320});

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 2, 8, 8, 2})
            .expect(new int[]{222, 228, 282, 288, 822, 828, 882});

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{3, 7, 5}).expect(new int[]{});

}
