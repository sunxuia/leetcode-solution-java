package q800;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/max-chunks-to-make-sorted-ii/
 *
 * This question is the same as "Max Chunks to Make Sorted" except the integers of the given array are not necessarily
 * distinct, the input array could be up to length 2000, and the elements could be up to 10**8.
 *
 * Given an array arr of integers (not necessarily distinct), we split the array into some number of "chunks"
 * (partitions), and individually sort each chunk.  After concatenating them, the result equals the sorted array.
 *
 * What is the most number of chunks we could have made?
 *
 * Example 1:
 *
 * Input: arr = [5,4,3,2,1]
 * Output: 1
 * Explanation:
 * Splitting into two or more chunks will not return the required result.
 * For example, splitting into [5, 4], [3, 2, 1] will result in [4, 5, 1, 2, 3], which isn't sorted.
 *
 * Example 2:
 *
 * Input: arr = [2,1,3,4,4]
 * Output: 4
 * Explanation:
 * We can split into two chunks, such as [2, 1], [3, 4, 4].
 * However, splitting into [2, 1], [3], [4], [4] is the highest number of chunks possible.
 *
 * Note:
 *
 * 1. arr will have length in range [1, 2000].
 * 2. arr[i] will be an integer in range [0, 10**8].
 */
@RunWith(LeetCodeRunner.class)
public class Q768_MaxChunksToMakeSortedII {

    /**
     * 开始没什么思路, 根据hint 中的提示比较arr 的前i 位之和与排序后的前i 位之和,
     * 如果和相等那么这段区间就是一个孤立的区间.
     */
    @Answer
    public int maxChunksToSorted(int[] arr) {
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        int res = 0;
        long sum = 0, sortedSum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            sortedSum += sorted[i];
            if (sum == sortedSum) {
                res++;
            }
        }
        return res;
    }

    /**
     * LeetCode 中最快的解法.
     */
    @Answer
    public int maxChunksToSorted2(int[] arr) {
        final int n = arr.length;

        // leftMax[i] 表示 [0, i] 中最大的值,
        // rightMin[i] 表示 [i, n) 中最小的值
        int[] leftMax = new int[n], rightMin = new int[n];
        leftMax[0] = arr[0];
        rightMin[n - 1] = arr[n - 1];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], arr[i]);
        }
        for (int i = n - 2; i >= 0; i--) {
            rightMin[i] = Math.min(rightMin[i + 1], arr[i]);
        }

        // 左边最大的值 <= 右边最小的值, 说明该位置左边的值 <= 右边的值,
        // 类似快排中的哨兵, 这个哨兵的位置不必移动.
        int res = 1;
        for (int i = 0; i < n - 1; i++) {
            if (leftMax[i] <= rightMin[i + 1]) {
                res++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{5, 4, 3, 2, 1}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 1, 3, 4, 4}).expect(4);

}
