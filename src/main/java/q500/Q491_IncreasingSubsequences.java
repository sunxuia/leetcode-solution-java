package q500;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.runner.RunWith;
import util.common.json.JsonTypeWrapper;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/increasing-subsequences/
 *
 * Given an integer array, your task is to find all the different possible increasing subsequences of the given
 * array, and the length of an increasing subsequence should be at least 2.
 *
 *
 *
 * Example:
 *
 * Input: [4, 6, 7, 7]
 * Output: [[4, 6], [4, 7], [4, 6, 7], [4, 6, 7, 7], [6, 7], [6, 7, 7], [7,7], [4,7,7]]
 *
 *
 *
 * Note:
 *
 * The length of the given array will not exceed 15.
 * The range of integer in the given array is [-100,100].
 * The given array may contain duplicates, and two equal integers should also be considered as a special case of
 * increasing sequence.
 */
@RunWith(LeetCodeRunner.class)
public class Q491_IncreasingSubsequences {

    @Answer
    public List<List<Integer>> findSubsequences(int[] nums) {
        // 改成 LinkedHashSet 是为了提升本机上结果判断的速度
        Set<List<Integer>> set = new LinkedHashSet<>();
        find(nums, 0, set, new ArrayList<>());
        return new ArrayList<>(set);
    }

    private void find(int[] nums, int start, Set<List<Integer>> set, ArrayList<Integer> list) {
        if (set.contains(list)) {
            return;
        }
        if (list.size() >= 2) {
            set.add((List<Integer>) list.clone());
        }
        int last = list.isEmpty() ? Integer.MIN_VALUE : list.get(list.size() - 1);
        while (start < nums.length) {
            if (last <= nums[start]) {
                list.add(nums[start]);
                find(nums, start + 1, set, list);
                list.remove(list.size() - 1);
            }
            start++;
        }
    }

    // LeetCode 上比较快的方式: 在Set 中判断数据是否已存在.
    @Answer
    public List<List<Integer>> findSubsequences2(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(nums, 0, res, new ArrayList<>());
        return res;
    }

    void dfs(int[] nums, int start, List<List<Integer>> res, List<Integer> list) {
        if (list.size() >= 2) {
            res.add(new ArrayList<>(list));
        }

        int last = list.isEmpty() ? Integer.MIN_VALUE : list.get(list.size() - 1);
        Set<Integer> set = new HashSet<>();
        while (start < nums.length) {
            // 在这里判断是否存在重复数字
            if (set.add(nums[start])) {
                if (last <= nums[start]) {
                    list.add(nums[start]);
                    dfs(nums, start + 1, res, list);
                    list.remove(list.size() - 1);
                }
            }
            start++;
        }
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[]{4, 6, 7, 7})
            .expect(Arrays.asList(
                    Arrays.asList(4, 6),
                    Arrays.asList(4, 7),
                    Arrays.asList(4, 6, 7),
                    Arrays.asList(4, 6, 7, 7),
                    Arrays.asList(6, 7),
                    Arrays.asList(6, 7, 7),
                    Arrays.asList(7, 7),
                    Arrays.asList(4, 7, 7)
            )).unorderResult("")
            .build();

    private TestDataFile testDataFile = new TestDataFile("Q491_LongTestData_Result");

    @TestData
    public DataExpectation overTime = DataExpectation.builder()
            .addArgument(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
            .expect(TestDataFileHelper.read(testDataFile, 1, new JsonTypeWrapper<List<List<Integer>>>() {}))
            .unorderResult("")
            .build();

}
