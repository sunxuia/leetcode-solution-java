package q400;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;
import q100.Q057_InsertInterval;
import util.asserthelper.AssertUtils;

/**
 * https://leetcode.com/problems/data-stream-as-disjoint-intervals/
 *
 * Given a data stream input of non-negative integers a1, a2, ..., an, ..., summarize the numbers seen so far as a
 * list of disjoint intervals.
 *
 * For example, suppose the integers from the data stream are 1, 3, 7, 2, 6, ..., then the summary will be:
 *
 * addNum 1 : [1, 1]
 * addNum 3 : [1, 1], [3, 3]
 * addNum 7 : [1, 1], [3, 3], [7, 7]
 * addNum 2 : [1, 3], [7, 7]
 * addNum 6 : [1, 3], [6, 7]
 *
 *
 *
 * Follow up:
 *
 * What if there are lots of merges and the number of disjoint intervals are small compared to the data stream's size?
 *
 * 题解: 这题需要实现一个类 SummaryRanges, 测试代码会每次调用 summaryRange.addNum(x) 方法添加一个整数 (x >= 0),
 * 然后调用 summaryRange.getIntervals() 获得一个数组, 数组元素 [a, b] 表示之前 addNum 的数字中有 a ~ b 连续整数,
 * 多个元素区间按顺序排列, 且之间不相连(如示例中一样).
 *
 * 相关题目: {@link Q057_InsertInterval}
 */
public class Q352_DataStreamAsDisjointIntervals {

    //  使用简单List 的方式, 这个因为要遍历元素, 所以比较慢
    private class SummaryRanges {

        private ArrayList<int[]> intervals = new ArrayList<>();

        /**
         * Initialize your data structure here.
         */
        public SummaryRanges() {

        }

        public void addNum(int val) {
            int i;
            for (i = 0; i < intervals.size(); i++) {
                int start = intervals.get(i)[0];
                int end = intervals.get(i)[1];
                if (val > end) {
                    // 还没到区间
                    continue;
                } else if (val < start) {
                    // 在此区间前面
                    break;
                } else {
                    // 在区间里面
                    return;
                }
            }
            int[] interval = new int[]{val, val};
            intervals.add(i, interval);
            if (i < intervals.size() - 1 && val == intervals.get(i + 1)[0] - 1) {
                interval[1] = intervals.get(i + 1)[1];
                intervals.remove(i + 1);
            }
            if (i > 0 && intervals.get(i - 1)[1] + 1 == val) {
                interval[0] = intervals.get(i - 1)[0];
                intervals.remove(i - 1);
            }
        }

        public int[][] getIntervals() {
            return intervals.toArray(new int[intervals.size()][]);
        }
    }

    // 使用TreeMap 的方式, 这个可以通过 O(logN) 的时间复杂度找到对应元素, 所以理论上会快一点
    // (在 LeetCode 上的测试用例并没有, 这个和程序具体实现有关).
    private class SummaryRanges_TreeMap {

        private TreeMap<Integer, Integer> intervals = new TreeMap<>();

        public void addNum(int val) {
            int start = val, end = val;
            Integer before = intervals.floorKey(val);
            if (before != null) {
                int beforeEnd = intervals.get(before);
                if (beforeEnd >= val) {
                    return;
                }
                if (beforeEnd + 1 == val) {
                    start = before;
                }
            }
            Integer after = intervals.ceilingKey(val);
            if (after != null) {
                if (val + 1 >= after) {
                    end = intervals.remove(after);
                }
            }
            intervals.put(start, end);
        }

        public int[][] getIntervals() {
            int[][] res = new int[intervals.size()][2];
            int i = 0;
            for (Map.Entry<Integer, Integer> entry : intervals.entrySet()) {
                res[i][0] = entry.getKey();
                res[i][1] = entry.getValue();
                i++;
            }
            return res;
        }
    }

    @Test
    public void testMethod() {
//        SummaryRanges sr = new SummaryRanges();
        SummaryRanges_TreeMap sr = new SummaryRanges_TreeMap();

        AssertUtils.assertEquals(new int[][]{}, sr.getIntervals());

        sr.addNum(1);
        AssertUtils.assertEquals(new int[][]{{1, 1}}, sr.getIntervals());

        sr.addNum(3);
        AssertUtils.assertEquals(new int[][]{
                {1, 1},
                {3, 3}
        }, sr.getIntervals());

        sr.addNum(7);
        AssertUtils.assertEquals(new int[][]{
                {1, 1},
                {3, 3},
                {7, 7}
        }, sr.getIntervals());

        sr.addNum(2);
        AssertUtils.assertEquals(new int[][]{
                {1, 3},
                {7, 7}
        }, sr.getIntervals());

        sr.addNum(6);
        AssertUtils.assertEquals(new int[][]{
                {1, 3},
                {6, 7}
        }, sr.getIntervals());

    }

}
