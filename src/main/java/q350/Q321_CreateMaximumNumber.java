package q350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/create-maximum-number
 *
 * Given two arrays of length m and n with digits 0-9 representing two numbers. Create the maximum number of length k
 * <= m + n from digits of the two. The relative order of the digits from the same array must be preserved. Return an
 * array of the k digits.
 *
 * Note: You should try to optimize your time and space complexity.
 *
 * Example 1:
 *
 * Input:
 * nums1 = [3, 4, 6, 5]
 * nums2 = [9, 1, 2, 5, 8, 3]
 * k = 5
 * Output:
 * [9, 8, 6, 5, 3]
 *
 * Example 2:
 *
 * Input:
 * nums1 = [6, 7]
 * nums2 = [6, 0, 4]
 * k = 5
 * Output:
 * [6, 7, 6, 0, 4]
 *
 * Example 3:
 *
 * Input:
 * nums1 = [3, 9]
 * nums2 = [8, 9]
 * k = 3
 * Output:
 * [9, 8, 9]
 */
@RunWith(LeetCodeRunner.class)
public class Q321_CreateMaximumNumber {

    // https://www.cnblogs.com/grandyang/p/5136749.html
    @Answer
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int[] res = new int[k];
        int[] one = new int[Math.max(k, nums1.length) + 2];
        int[] two = new int[Math.max(k, nums2.length) + 2];
        // 哨兵
        one[0] = two[0] = 10;
        for (int i = Math.max(0, k - nums2.length); i <= Math.min(k, nums1.length); i++) {
            // nums1 中取i 个数字的最大结果(放在one 中)
            setMax(nums1, one, i);
            // nums2 中取k-i 个数字的最大结果(放在 two 中)
            setMax(nums2, two, k - i);
            // 哨兵
            one[i + 1] = -2;
            two[k - i + 1] = -1;
            // 将结果输出到res 中 (如果结果比 res 大的话)
            writeMax(res, one, two);
        }
        return res;
    }

    private void setMax(int[] nums, int[] cand, int k) {
        // 需要丢弃的数字个数
        int drop = nums.length - k;
        int i = 1;
        for (int num : nums) {
            // 如果还有额外的数字可以丢弃, 且当前数字比 cand 数组末尾的数字要大,
            // 那么将当前数字替代它得到的数字就肯定比之前的数字大 (且位数不变).
            while (drop > 0 && cand[i - 1] < num) {
                i--;
                drop--;
            }
            cand[i++] = num;
        }
    }

    private void writeMax(int[] res, int[] one, int[] two) {
        int i1 = 1, i2 = 1, next = 0;
        boolean stillInComparation = true;
        while (next < res.length) {
            int i = 0;
            while (one[i1 + i] == two[i2 + i]) {
                i++;
            }
            int v = one[i1 + i] > two[i2 + i] ? one[i1++] : two[i2++];
            if (stillInComparation) {
                if (res[next] < v) {
                    stillInComparation = false;
                } else if (res[next] > v) {
                    return;
                }
            }
            res[next++] = v;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{3, 4, 6, 5}, new int[]{9, 1, 2, 5, 8, 3}, 5)
            .expect(new int[]{9, 8, 6, 5, 3});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{6, 7}, new int[]{6, 0, 4}, 5)
            .expect(new int[]{6, 7, 6, 0, 4});

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{3, 9}, new int[]{8, 9}, 3)
            .expect(new int[]{9, 8, 9});

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{2, 1, 7, 8, 0, 1, 7, 3, 5, 8, 9, 0, 0, 7, 0, 2, 2, 7, 3, 5, 5},
                    new int[]{2, 6, 2, 0, 1, 0, 5, 4, 5, 5, 3, 3, 3, 4}, 35)
            .expect(new int[]{2, 6, 2, 2, 1, 7, 8, 0, 1, 7, 3, 5, 8, 9, 0, 1, 0, 5, 4, 5, 5, 3, 3, 3, 4, 0, 0, 7, 0, 2,
                    2, 7, 3, 5, 5});

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(
                    new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 100)
            .expect(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    1, 1, 1, 1});

}
