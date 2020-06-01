package q800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/max-chunks-to-make-sorted/
 *
 * Given an array arr that is a permutation of [0, 1, ..., arr.length - 1], we split the array into some number of
 * "chunks" (partitions), and individually sort each chunk.  After concatenating them, the result equals the sorted
 * array.
 *
 * What is the most number of chunks we could have made?
 *
 * Example 1:
 *
 * Input: arr = [4,3,2,1,0]
 * Output: 1
 * Explanation:
 * Splitting into two or more chunks will not return the required result.
 * For example, splitting into [4, 3], [2, 1, 0] will result in [3, 4, 0, 1, 2], which isn't sorted.
 *
 * Example 2:
 *
 * Input: arr = [1,0,2,3,4]
 * Output: 4
 * Explanation:
 * We can split into two chunks, such as [1, 0], [2, 3, 4].
 * However, splitting into [1, 0], [2], [3], [4] is the highest number of chunks possible.
 *
 * Note:
 *
 * 1. arr will have length in range [1, 10].
 * 2. arr[i] will be a permutation of [0, 1, ..., arr.length - 1].
 */
@RunWith(LeetCodeRunner.class)
public class Q769_MaxChunksToMakeSorted {

    // 和上一题一样的相加解法
    @Answer
    public int maxChunksToSorted(int[] arr) {
        int res = 0, sum = 0, sortedSum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            sortedSum += i;
            if (sum == sortedSum) {
                res++;
            }
        }
        return res;
    }

    // 网上的另一种解法.
    @Answer
    public int maxChunksToSorted2(int[] arr) {
        int res = 0, max = 0;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
            // 最大值和下标i 相等, 说明[0, i] 的数字都是 <= i 的, 后面的数字都是 >i 的.
            // 那么就可以将这个区间分隔出来.
            if (max == i) {
                res++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{4, 3, 2, 1, 0}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 0, 2, 3, 4}).expect(4);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{0, 2, 1}).expect(2);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{0}).expect(1);

}
