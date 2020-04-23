package q500;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/sliding-window-median/
 *
 * Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value.
 * So the median is the mean of the two middle value.
 * Examples:
 *
 * [2,3,4] , the median is 3
 *
 * [2,3], the median is (2 + 3) / 2 = 2.5
 *
 * Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the
 * very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position
 * . Your job is to output the median array for each window in the original array.
 *
 * For example,
 * Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
 *
 * Window position                Median
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       1
 * 1 [3  -1  -3] 5  3  6  7       -1
 * 1  3 [-1  -3  5] 3  6  7       -1
 * 1  3  -1 [-3  5  3] 6  7       3
 * 1  3  -1  -3 [5  3  6] 7       5
 * 1  3  -1  -3  5 [3  6  7]      6
 *
 * Therefore, return the median sliding window as [1,-1,-1,3,5,6].
 *
 * Note:
 * You may assume k is always valid, ie: k is always smaller than input array's size for non-empty array.
 * Answers within 10^-5 of the actual value will be accepted as correct.
 */
@RunWith(LeetCodeRunner.class)
public class Q480_SlidingWindowMedian {

    // LeetCode 上最快的做法是通过二叉树来做的, 这样查找和删除就不用和数组一样移动元素了.
    @Answer
    public double[] medianSlidingWindow(int[] nums, int k) {
        double[] res = new double[nums.length - k + 1];
        int[] window = Arrays.copyOf(nums, k);
        Arrays.sort(window);
        res[0] = k % 2 == 0 ? ((double) window[k / 2 - 1] + window[k / 2]) / 2 : window[k / 2];
        for (int i = k; i < nums.length; i++) {
            // 删除上一个数, 加入新一个数
            int j = Arrays.binarySearch(window, nums[i - k]);
            System.arraycopy(window, j + 1, window, j, k - j - 1);
            j = Arrays.binarySearch(window, 0, k - 1, nums[i]);
            j = j >= 0 ? j + 1 : -j - 1;
            System.arraycopy(window, j, window, j + 1, k - j - 1);
            window[j] = nums[i];

            res[i - k + 1] = k % 2 == 0 ? ((double) window[k / 2 - 1] + window[k / 2]) / 2 : window[k / 2];
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3)
            .expect(new double[]{1, -1, -1, 3, 5, 6});

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE}, 2)
            .expect(new double[]{Integer.MAX_VALUE});

}
