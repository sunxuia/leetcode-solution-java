package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 *
 * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
 *
 * You may assume nums1 and nums2 cannot be both empty.
 *
 * Example 1:
 *
 * nums1 = [1, 3]
 * nums2 = [2]
 *
 * The median is 2.0
 *
 * Example 2:
 *
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 *
 * The median is (2 + 3)/2 = 2.5
 */
@RunWith(LeetCodeRunner.class)
public class Q004_MedianOfTwoSortedArrays {

    /**
     * 这题的目的就是寻找2 个有序数组合起来的中位数(如果中位数有2 个则取2 个数的平均值).
     * 主要的想法就是将2 个数组划分为2 块, 左边右边的数量相等, 左边一块的最大值< 右边一块的最小值,
     * 然后中间的数字(或左边最大和右边最小的平均数)就是需要的结果了.
     */
    @Answer
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 确保nums1 的大小小于nums2.
        // 这样在下面计算的时候就不会出现超过范围的情况了.
        if (nums1.length > nums2.length) {
            int[] t = nums1;
            nums1 = nums2;
            nums2 = t;
        }

        int start = 0, end = nums1.length;
        while (start <= end) {
            // 尝试划分边界, 让左右两边的数量相等, (如果总数是奇数的话, 左边界可能会比右边界多1 个数字)
            // i 表示nums1 中的右边界. i 的范围就在[0, nums1.length]
            int i = (start + end) / 2;
            // j 表示nums2 中的右边界. 由于nums1.length <= nums2.length,
            // 所以j 的范围永远在[0, nums2.length].
            int j = (nums1.length + nums2.length + 1) / 2 - i;

            // 重新确定边界, 让左边的最大值 < 右边的最小值
            if (i < end && nums2[j - 1] > nums1[i]) {
                // 左边界最大值 > 右边界最小值, 且i 还有前进空间.
                start = i + 1;
            } else if (i > start && nums1[i - 1] > nums2[j]) {
                // 左边界最大值 > 右边界最小值, 且i 还有后退空间.
                end = i - 1;
            } else {
                // 边界确定
                // 找到左边界的最大值(如果总数为奇数的话, 就是中间的中位数)
                int maxLeft;
                if (i == 0) {
                    maxLeft = nums2[j - 1];
                } else if (j == 0) {
                    maxLeft = nums1[i - 1];
                } else {
                    maxLeft = Math.max(nums1[i - 1], nums2[j - 1]);
                }
                if ((nums1.length + nums2.length) % 2 == 1) {
                    return maxLeft;
                }

                // 找到右边界的最小值.
                int minRight;
                if (i == nums1.length) {
                    minRight = nums2[j];
                } else if (j == nums2.length) {
                    minRight = nums1[i];
                } else {
                    minRight = Math.min(nums1[i], nums2[j]);
                }

                return (maxLeft + minRight) / 2.0;
            }
        }
        throw new RuntimeException("should not run to here");
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[]{1, 3})
            .addArgument(new int[]{2})
            .expect(2.0)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[]{1, 2})
            .addArgument(new int[]{3, 4})
            .expect(2.5)
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[]{1, 2, 3})
            .addArgument(new int[]{4, 5, 6})
            .expect(3.5)
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(new int[]{4, 5, 6})
            .addArgument(new int[]{1, 2, 3})
            .expect(3.5)
            .build();

    @TestData
    public DataExpectation normal3 = DataExpectation.builder()
            .addArgument(new int[]{1, 2, 3, 4})
            .addArgument(new int[]{5, 6, 7})
            .expect(4.0)
            .build();

    @TestData
    public DataExpectation normal4 = DataExpectation.builder()
            .addArgument(new int[]{1, 1})
            .addArgument(new int[]{1, 2})
            .expect(1.0)
            .build();

    @TestData
    public DataExpectation normal5 = DataExpectation.builder()
            .addArgument(new int[]{1, 3, 5, 7, 9})
            .addArgument(new int[]{2, 4, 6, 8})
            .expect(5.0)
            .build();

    @TestData
    public DataExpectation border1 = DataExpectation.builder()
            .addArgument(new int[]{1})
            .addArgument(new int[]{})
            .expect(1.0)
            .build();

    @TestData
    public DataExpectation border2 = DataExpectation.builder()
            .addArgument(new int[]{})
            .addArgument(new int[]{2, 3})
            .expect(2.5)
            .build();

}
