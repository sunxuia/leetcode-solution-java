package q200;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/maximum-gap/
 *
 * Given an unsorted array, find the maximum difference between the successive elements in its sorted form.
 *
 * Return 0 if the array contains less than 2 elements.
 *
 * Example 1:
 *
 * Input: [3,6,9,1]
 * Output: 3
 * Explanation: The sorted form of the array is [1,3,6,9], either
 * (3,6) or (6,9) has the maximum difference 3.
 *
 * Example 2:
 *
 * Input: [10]
 * Output: 0
 * Explanation: The array contains less than 2 elements, therefore return 0.
 *
 * Note:
 *
 * You may assume all elements in the array are non-negative integers and fit in the 32-bit signed integer range.
 * Try to solve it in linear time/space.
 *
 * 题解: 给出一个未排序的数组nums(元素都非负数), 找出这个数组排序后两个相邻元素之间的最大差值.
 * 题目要求时间复杂度 O(N), 空间复杂度 O(N).
 */
@RunWith(LeetCodeRunner.class)
public class Q164_MaximumGap {

    /**
     * 桶排序的方法, 但是需要对桶做分片, 否则容易内存溢出. 这个速度就比较慢了.
     * LeetCode 上有对应的一个优化后的解法.
     */
    @Answer
    public int maximumGap_bucket(int[] nums) {
        final int bucketSize = 100;
        Map<Integer, int[]> map = new HashMap<>();
        int max = 0, min = Integer.MAX_VALUE;
        for (int num : nums) {
            int[] range = map.computeIfAbsent(num / bucketSize, k -> new int[bucketSize]);
            range[num % bucketSize]++;
            max = Math.max(max, num);
            min = Math.min(min, num);
        }
        int res = 0;
        for (int key = min / bucketSize, count = 1; key <= max / bucketSize; key++) {
            int[] range = map.get(key);
            if (range == null) {
                count += bucketSize;
            } else {
                for (int i = 0; i < bucketSize; i++) {
                    if (range[i] == 0) {
                        count++;
                    } else {
                        if (key != min / bucketSize || i != min % bucketSize) {
                            res = Math.max(res, count);
                        }
                        count = 1;
                    }
                }
            }
        }
        return res;
    }

    /**
     * 基数排序的解法. 这里使用十进制作为基数.
     * 时间复杂度 O( 循环位数*(nums.length+循环位数) ), 因为这里的循环中的位数不会超过10 位, 所以可以认为是 O(n)
     */
    @Answer
    public int maximumGap_radixSort(int[] nums) {
        // 基数排序的内容, 这里取10 表示对10进制的每位进行比较, 方便调试.
        // 对LeetCode 的测试用例来说, 基数取100 的时候运行速度最快.
        final int radix = 10;

        // 找出最大的位数
        int max = 0;
        for (int num : nums) {
            max = Math.max(max, num);
        }
        max = (int) (Math.log(max) / Math.log(radix)) + 1;

        int[][] buckets = new int[radix][nums.length];
        int[] lengths = new int[radix];
        // digit 表示位数, offset 表示对应的值, 用来计算数字在 digit 位上的值(radix 进制).
        // 这里使用2 个数字是为了避免int 的越界情况.
        for (int offset = 1, digit = 1; digit <= max; digit++, offset *= radix) {
            Arrays.fill(lengths, 0);
            for (int num : nums) {
                int index = (num / offset) % radix;
                buckets[index][lengths[index]++] = num;
            }
            int next = 0;
            for (int i = 0; i < radix; i++) {
                for (int j = 0; j < lengths[i]; j++) {
                    nums[next++] = buckets[i][j];
                }
            }
        }

        // 从排序过的数组中获取结果
        int res = 0;
        for (int i = 1; i < nums.length; i++) {
            res = Math.max(res, nums[i] - nums[i - 1]);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 6, 9, 1}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{10}).expect(0);

    @TestData
    public DataExpectation wildRange1 = DataExpectation.create(new int[]{2, 99999999}).expect(99999999 - 2);

    @TestData
    public DataExpectation wildRange2 = DataExpectation.create(new int[]{0, Integer.MAX_VALUE})
            .expect(Integer.MAX_VALUE);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{6, 3}).expect(3);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{1, 2, 3, 1}).expect(1);

    @TestData
    public DataExpectation normal3 = DataExpectation.create(new int[]{100, 3, 2, 0}).expect(97);

    @TestData
    public DataExpectation normal4 = DataExpectation.create(new int[]{100, 3, 2, 1}).expect(97);

    @TestData
    public DataExpectation normal5 = DataExpectation
            .create(new int[]{15252, 16764, 27963, 7817, 26155, 20757, 3478, 22602, 20404, 6739, 16790, 10588, 16521,
                    6644, 20880, 15632, 27078, 25463, 20124, 15728, 30042, 16604, 17223, 4388, 23646, 32683, 23688,
                    12439, 30630, 3895, 7926, 22101, 32406, 21540, 31799, 3768, 26679, 21799, 23740})
            .expect(2901);

}
