package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/reverse-pairs/
 *
 * Given an array nums, we call (i, j) an important reverse pair if i < j and nums[i] > 2*nums[j].
 *
 * You need to return the number of important reverse pairs in the given array.
 *
 * Example1:
 *
 * Input: [1,3,2,3,1]
 * Output: 2
 *
 * Example2:
 *
 * Input: [2,4,3,5,1]
 * Output: 3
 *
 * Note:
 *
 * The length of the given array will not exceed 50,000.
 * All the numbers in the input array are in the range of 32-bit integer.
 */
@RunWith(LeetCodeRunner.class)
public class Q493_ReversePairs {

    // 暴力枚举的方式时间复杂度是 O(n^2), 会超时.
    // 下面的这种方式能通过测试, 不过也比较慢, 因为System.arraycopy 其实也是O(n) 时间复杂度的操作.
    @Answer
    public int reversePairs(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int idx = findIndex(nums, i, 2L * num);
            res += i - idx;
            idx = findIndex(nums, i, num);
            System.arraycopy(nums, idx, nums, idx + 1, i - idx);
            nums[idx] = num;
        }
        return res;
    }

    private int findIndex(int[] nums, int length, long num) {
        int start = 0, end = length;
        while (start < end) {
            int middle = (start + end) / 2;
            if (nums[middle] > num) {
                end = middle;
            } else {
                start = middle + 1;
            }
        }
        return end;
    }

    // LeetCode 上比较快的方式, 使用分治的方式, 这样的时间复杂度在 O(NlogN)
    // 详情见 https://www.cnblogs.com/grandyang/p/6657956.html
    @Answer
    public int reversePairs2(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }
        count = 0;
        customMergeSort(nums, 0, nums.length - 1);
        return count;
    }

    private int count;

    private void customMergeSort(int[] nums, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = (start + end) / 2;

        // 将mid 左右两边排序
        customMergeSort(nums, start, mid);
        customMergeSort(nums, mid + 1, end);

        // 计算区间内符合条件 [start, mid] > 2 * [mid + 1, end] 的数量
        // 这个2 个区间内部的数量在上面的递归调用中已经计算过了.
        int i = start, j = mid + 1;
        while (i <= mid && j <= end) {
            long l1 = nums[i];
            long l2 = 2 * (long) nums[j];
            if (l1 > l2) {
                count += (mid - i + 1);
                j++;
            } else {
                i++;
            }
        }

        // 合并2 个区间为1 个有序区间 (数组创建的时间开销现在已经很小了)
        int[] helper = new int[end - start + 1];
        i = start;
        j = mid + 1;
        int ind = 0;
        while (i <= mid && j <= end) {
            if (nums[i] <= nums[j]) {
                helper[ind++] = nums[i];
                i++;
            } else {
                helper[ind++] = nums[j];
                j++;
            }
        }
        while (i <= mid) {
            helper[ind++] = nums[i];
            i++;
        }

        i = start;
        j = 0;
        while (j < ind) {
            nums[i++] = helper[j++];
        }
    }

    @TestData
    public DataExpectation exmaple1 = DataExpectation.create(new int[]{1, 3, 2, 3, 1}).expect(2);

    @TestData
    public DataExpectation exmaple2 = DataExpectation.create(new int[]{2, 4, 3, 5, 1}).expect(3);

    @TestData
    public DataExpectation largeNumber = DataExpectation
            .create(new int[]{2147483647, 2147483647, 2147483647, 2147483647, 2147483647, 2147483647}).expect(0);

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.readIntegerArray("Q493_LongTestData")).expect(625284395);

}
