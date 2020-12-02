package q400;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/largest-divisible-subset/
 *
 * Given a set of distinct positive integers, find the largest subset such that every pair (Si, Sj) of elements in
 * this subset satisfies:
 *
 * Si % Sj = 0 or Sj % Si = 0.
 *
 * If there are multiple solutions, return any subset is fine.
 *
 * Example 1:
 *
 * Input: [1,2,3]
 * Output: [1,2] (of course, [1,3] will also be ok)
 *
 * Example 2:
 *
 * Input: [1,2,4,8]
 * Output: [1,2,4,8]
 */
@RunWith(LeetCodeRunner.class)
public class Q368_LargestDivisibleSubset {

    // 修改自 https://blog.csdn.net/m0_37889928/article/details/79888301
    @Answer
    public List<Integer> largestDivisibleSubset(int[] nums) {
        Arrays.sort(nums);

        // 可被当前元素整除的元素的最大数量. 0 是哨兵
        int[] count = new int[nums.length + 1];
        // 可被当前元素整除的元素数量最大时, 当前元素的前一个元素. 0 是哨兵
        int[] prev = new int[nums.length + 1];
        // 数量最长的序列(结果) 中最大的数字的下标
        int maxIdx = 0;

        // 从前往后(从小到大) 遍历元素, 为count 和prev 填值.
        for (int i = 1; i <= nums.length; i++) {
            int curMaxIdx = 0;
            // 向前遍历小于它的元素, 找出能够整数的数中 count 数目最大的那个数
            for (int j = i - 1; j > 0; j--) {
                if (nums[i - 1] % nums[j - 1] == 0 && count[j] > count[curMaxIdx]) {
                    curMaxIdx = j;
                }
            }
            count[i] = count[curMaxIdx] + 1;
            prev[i] = curMaxIdx;
            maxIdx = count[maxIdx] > count[i] ? maxIdx : i;
        }

        List<Integer> res = new ArrayList<>(count[maxIdx]);
        for (int i = maxIdx; i > 0; i = prev[i]) {
            res.add(nums[i - 1]);
        }
        return res;
    }

    /**
     * LeetCode 上最块的解法.
     * 相比上面的解法, 这个解法是从前往后查找的.
     */
    @Answer
    public List<Integer> largestDivisibleSubset2(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        Arrays.sort(nums);
        sortedNums = nums;
        maxNum = nums[nums.length - 1];
        len = new int[nums.length];
        next = new int[nums.length];
        longestChainHead = -1;
        maxLen = 0;

        // limit 表示能找到的最大值不会超过这个limit 这个数
        int limit = maxNum;
        for (int i = 0; i < nums.length && nums[i] <= limit; i++) {
            dp(i, 1);
            limit = maxNum >> maxLen;
        }

        // 组装结果
        while (longestChainHead != -1) {
            res.add(nums[longestChainHead]);
            longestChainHead = next[longestChainHead];
        }
        return res;
    }

    // 排序后的数组
    private int[] sortedNums;
    // 可被当前元素整除的元素的最大数量
    private int[] len;
    // 序列中的下一个元素下标, -1 表示没有
    private int[] next;
    // 最长序列的起始序列
    private int longestChainHead;
    // 最长的序列的长度
    private int maxLen;
    // 最长序列中的最大数
    private int maxNum;

    // start 表示从这里开始向后查找最长序列,
    // preLen 表示当前序列的之前的最大长度
    private void dp(int start, int preLen) {
        // 初始化当前元素的相关值
        if (len[start] == 0) {
            len[start] = 1;
            next[start] = -1;
        }
        // 更新最长序列相关的值
        if (len[start] > maxLen) {
            maxLen = len[start];
            longestChainHead = start;
        }
        // limit 表示能找到的最大值不会超过这个limit 这个数
        int limit = maxNum >> Math.max(maxLen - preLen, 0);
        int max = 0;
        for (int i = start + 1; i < sortedNums.length && sortedNums[i] <= limit; i++) {
            if (sortedNums[i] % sortedNums[start] == 0) {
                // 初始化当前元素的相关值
                if (len[i] == 0) {
                    dp(i, preLen + 1);
                }
                if (len[i] > max) {
                    max = len[i];
                    len[start] = len[i] + 1;
                    next[start] = i;
                    if (len[start] > maxLen) {
                        maxLen = len[start];
                        longestChainHead = start;
                        limit = maxNum >> Math.max(maxLen - preLen, 0);
                    }
                }
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[]{1, 2, 3})
            .expect(new int[]{1, 2})
            .orExpect(new int[]{1, 3})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[]{1, 2, 4, 8})
            .expect(new int[]{1, 2, 4, 8})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation border0 = DataExpectation.create(new int[0]).expect(new int[0]);

    @TestData
    public DataExpectation border1 = DataExpectation.create(new int[]{1}).expect(new int[]{1});

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(new int[]{1, 2, 3, 4, 6, 24})
            .expect(new int[]{1, 2, 4, 24})
            .orExpect(new int[]{1, 3, 6, 24})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal3 = DataExpectation.builder()
            .addArgument(new int[]{1, 2, 4, 8})
            .expect(new int[]{1, 2, 4, 8})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal4 = DataExpectation.builder()
            .addArgument(new int[]{4, 8, 10, 240})
            .expect(new int[]{4, 8, 240})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal5 = DataExpectation.builder()
            .addArgument(TestDataFileHelper.read("Q368_LongTestData", int[].class))
            .expect(new int[]{1, 2, 4, 8, 32, 64, 128, 384, 768})
            .unorderResult()
            .build();

}
