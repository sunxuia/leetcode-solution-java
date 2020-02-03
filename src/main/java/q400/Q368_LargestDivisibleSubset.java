package q400;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    // 无法理解 https://blog.csdn.net/m0_37889928/article/details/79888301
    @Answer
    public List<Integer> largestDivisibleSubset(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        Arrays.sort(nums);
        int len = nums.length;
        if (len == 0) {
            return new ArrayList<>();
        }
        int[] record = new int[len];
        int max = -1;
        for (int i = 0; i < len; i++) {
            int temp = -1;
            for (int j = i - 1; j > -1; j--) {
                if (nums[i] % nums[j] == 0
                        && (temp == -1 || record[j] > record[temp])) {
                    temp = j;
                }
            }
            if (temp == -1) {
                record[i] = 1;
            } else {
                record[i] = record[temp] + 1;
                map.put(nums[i], nums[temp]);
            }
            if (max == -1 || record[i] > record[max]) {
                max = i;
            }
        }
        List<Integer> list = new ArrayList<>();
        list.add(nums[max]);
        max = nums[max];
        while (map.containsKey(max)) {
            max = map.get(max);
            list.add(max);
        }
        return list;
    }

    // LeetCode 上最块的解法
    @Answer
    public List<Integer> largestDivisibleSubset2(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        Arrays.sort(nums);
        sorted = nums;
        maxNum = nums[nums.length - 1];
        len = new int[nums.length];
        next = new int[nums.length];
        longestChainHead = -1;
        maxLen = 0;
        int limit = maxNum;
        for (int i = 0; i < nums.length && nums[i] <= limit; i++) {
            dp(i, 1);
            limit = maxNum >> maxLen;
        }
        while (longestChainHead != -1) {
            res.add(nums[longestChainHead]);
            longestChainHead = next[longestChainHead];
        }
        return res;
    }

    private int[] sorted;
    private int[] len;
    private int[] next;
    private int longestChainHead;
    private int maxLen;
    private int maxNum;

    private void dp(int start, int preLen) {
        if (len[start] == 0) {
            len[start] = 1;
            next[start] = -1;
        }
        if (len[start] > maxLen) {
            maxLen = len[start];
            longestChainHead = start;
        }
        int limit = maxNum >> Math.max(maxLen - preLen, 0);
        int max = 0;
        for (int i = start + 1; i < sorted.length && sorted[i] <= limit; i++) {
            if (sorted[i] % sorted[start] == 0) {
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
            .addArgument(TestDataFileHelper.readIntegerArray("Q368_LongTestData"))
            .expect(new int[]{1, 2, 4, 8, 32, 64, 128, 384, 768})
            .unorderResult()
            .build();

}
