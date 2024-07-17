package q1850;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1815. Maximum Number of Groups Getting Fresh Donuts
 * https://leetcode.com/problems/maximum-number-of-groups-getting-fresh-donuts/
 *
 * There is a donuts shop that bakes donuts in batches of batchSize. They have a rule where they must serve all of the
 * donuts of a batch before serving any donuts of the next batch. You are given an integer batchSize and an integer
 * array groups, where groups[i] denotes that there is a group of groups[i] customers that will visit the shop. Each
 * customer will get exactly one donut.
 *
 * When a group visits the shop, all customers of the group must be served before serving any of the following groups. A
 * group will be happy if they all get fresh donuts. That is, the first customer of the group does not receive a donut
 * that was left over from the previous group.
 *
 * You can freely rearrange the ordering of the groups. Return the maximum possible number of happy groups after
 * rearranging the groups.
 *
 * Example 1:
 *
 * Input: batchSize = 3, groups = [1,2,3,4,5,6]
 * Output: 4
 * Explanation: You can arrange the groups as [6,2,4,5,1,3]. Then the 1st, 2nd, 4th, and 6th groups will be happy.
 *
 * Example 2:
 *
 * Input: batchSize = 4, groups = [1,3,2,5,2,2,1,6]
 * Output: 4
 *
 * Constraints:
 *
 * 1 <= batchSize <= 9
 * 1 <= groups.length <= 30
 * 1 <= groups[i] <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1815_MaximumNumberOfGroupsGettingFreshDonuts {

    /**
     * 就是要尽可能凑batchSize
     * dfs 解法, 会超时
     */
//    @Answer
    public int maxHappyGroups(int batchSize, int[] groups) {
        // 统计数量和余数, 减少计算量 (这个步骤没什么用)
        int[] counts = new int[batchSize];
        for (int group : groups) {
            counts[group % batchSize]++;
        }
        return counts[0] + dfs(batchSize, counts, 0);
    }

    private int dfs(int batchSize, int[] counts, int remainder) {
        int res = 0;
        for (int i = 1; i < batchSize; i++) {
            if (counts[i] > 0) {
                counts[i]--;
                // 上一轮剩下来的客人为0, 则这次匹配的就是happy 的组
                int happy = (remainder == 0 ? 1 : 0)
                        + dfs(batchSize, counts, (i + remainder) % batchSize);
                res = Math.max(res, happy);
                counts[i]++;
            }
        }
        return res;
    }

    /**
     * 带缓存的dfs (位缓存)
     */
    @Answer
    public int maxHappyGroups2(int batchSize, int[] groups) {
        int[] counts = new int[batchSize];
        for (int group : groups) {
            counts[group % batchSize]++;
        }

        // 用位缓存来表示各个数字被使用的情况,
        // 使用maskOffset[i] 来表示数字为i 的counts[i] 个数字的使用情况
        int[] maskOffset = new int[batchSize];
        int offset = 0;
        for (int i = 1; i < batchSize; i++) {
            maskOffset[i] = offset;
            offset += counts[i];
        }

        // 缓存 caches[remainder][mask] 表示余数为remainder 组合为mask 的结果
        Map<Integer, Integer>[] caches = new Map[batchSize];
        for (int i = 0; i < batchSize; i++) {
            caches[i] = new HashMap<>();
            // (哨兵), 当mask=0 的时候就不需要继续寻找组合了.
            caches[i].put(0, 0);
        }

        return counts[0] + dfs(caches, (1 << offset) - 1, maskOffset, 0, batchSize, counts);
    }

    private int dfs(Map<Integer, Integer>[] caches, int mask, int[] maskOffset,
            int remainder, int batchSize, int[] counts) {
        if (caches[remainder].containsKey(mask)) {
            return caches[remainder].get(mask);
        }
        int res = 0;
        for (int i = 1; i < batchSize; i++) {
            if (counts[i] > 0) {
                counts[i]--;
                mask ^= 1 << (maskOffset[i] + counts[i]);
                int happy = (remainder == 0 ? 1 : 0)
                        + dfs(caches, mask, maskOffset, (i + remainder) % batchSize, batchSize, counts);
                res = Math.max(res, happy);
                mask ^= 1 << (maskOffset[i] + counts[i]);
                counts[i]++;
            }
        }
        caches[remainder].put(mask, res);
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(3, new int[]{1, 2, 3, 4, 5, 6}).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(4, new int[]{1, 3, 2, 5, 2, 2, 1, 6}).expect(4);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(3, new int[]{2, 2, 2}).expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(6, new int[]{369205928, 981877451, 947462486, 899465743, 737778942, 573732515,
                    520226542, 824581298, 571789442, 251943251, 70139785, 778962318, 43379662, 90924712, 142825931,
                    182207697, 178834435, 978165687})
            .expect(10);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(3,
                    new int[]{977925843, 406172122, 865959215, 582961757, 976270471, 878696742, 315705417, 590782505,
                            272097063, 764086436, 512185692, 337648577, 107512856, 222924498, 700133576, 340823202})
            .expect(11);

}
