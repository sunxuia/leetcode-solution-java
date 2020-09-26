package q1100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1089. Duplicate Zeros
 * https://leetcode.com/problems/duplicate-zeros/
 *
 * Given a fixed length array arr of integers, duplicate each occurrence of zero, shifting the remaining elements to the
 * right.
 *
 * Note that elements beyond the length of the original array are not written.
 *
 * Do the above modifications to the input array in place, do not return anything from your function.
 *
 * Example 1:
 *
 * Input: [1,0,2,3,0,4,5,0]
 * Output: null
 * Explanation: After calling your function, the input array is modified to: [1,0,0,2,3,0,0,4]
 *
 * Example 2:
 *
 * Input: [1,2,3]
 * Output: null
 * Explanation: After calling your function, the input array is modified to: [1,2,3]
 *
 * Note:
 *
 * 1 <= arr.length <= 10000
 * 0 <= arr[i] <= 9
 *
 * 题解: 把arr 中的0 元素复制一份放到后面, 后面的元素依次向后移动(超过数组边界的丢弃).
 */
@RunWith(LeetCodeRunner.class)
public class Q1089_DuplicateZeros {

    /**
     * 从前往后计算截止位置, 再从后往前计算结果
     */
    @Answer
    public void duplicateZeros(int[] arr) {
        final int n = arr.length;
        int len = 0, end = -1, last = n - 1;
        while (len < n) {
            len += arr[++end] == 0 ? 2 : 1;
        }
        if (len == n + 1) {
            // arr[end] = 0 导致 arr[n-1] = 0, arr[n] = 0 的边界条件
            arr[last--] = 0;
            end--;
        }
        for (; end >= 0; end--) {
            if (arr[end] == 0) {
                arr[last--] = 0;
                arr[last--] = 0;
            } else {
                arr[last--] = arr[end];
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[]{1, 0, 2, 3, 0, 4, 5, 0})
            .expectArgument(0, new int[]{1, 0, 0, 2, 3, 0, 0, 4})
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[]{1, 2, 3})
            .expectArgument(0, new int[]{1, 2, 3})
            .build();

}
