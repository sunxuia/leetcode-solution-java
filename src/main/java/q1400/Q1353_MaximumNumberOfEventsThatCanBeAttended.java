package q1400;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeMap;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1353. Maximum Number of Events That Can Be Attended
 * https://leetcode.com/problems/maximum-number-of-events-that-can-be-attended/
 *
 * Given an array of events where events[i] = [startDayi, endDayi]. Every event i starts at startDayi and ends at
 * endDayi.
 *
 * You can attend an event i at any day d where startTimei <= d <= endTimei. Notice that you can only attend one event
 * at any time d.
 *
 * Return the maximum number of events you can attend.
 *
 * Example 1:
 * <img src="./Q1353_PIC.png">
 * Input: events = [[1,2],[2,3],[3,4]]
 * Output: 3
 * Explanation: You can attend all the three events.
 * One way to attend them all is as shown.
 * Attend the first event on day 1.
 * Attend the second event on day 2.
 * Attend the third event on day 3.
 *
 * Example 2:
 *
 * Input: events= [[1,2],[2,3],[3,4],[1,2]]
 * Output: 4
 *
 * Example 3:
 *
 * Input: events = [[1,4],[4,4],[2,2],[3,4],[1,1]]
 * Output: 4
 *
 * Example 4:
 *
 * Input: events = [[1,100000]]
 * Output: 1
 *
 * Example 5:
 *
 * Input: events = [[1,1],[1,2],[1,3],[1,4],[1,5],[1,6],[1,7]]
 * Output: 7
 *
 * Constraints:
 *
 * 1 <= events.length <= 10^5
 * events[i].length == 2
 * 1 <= startDayi <= endDayi <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1353_MaximumNumberOfEventsThatCanBeAttended {

    /**
     * 排序后记录已经被占用的区间.
     */
    @Answer
    public int maxEvents(int[][] events) {
        // 按照结束时间排序, 确保前面的尽量往前安排, 后面的有足够的空间安放.
        Arrays.sort(events, Comparator.comparingInt(a -> a[1]));
        TreeMap<Integer, Integer> map = new TreeMap<>();
        // 哨兵
        map.put(0, 0);
        int res = 0;
        // 每次新的活动都尽量占用最开始的位置, 后面的位置可能会被后面的活动占用.
        for (int[] event : events) {
            // 区间起始
            int start = map.floorKey(event[0]);
            int pos = map.get(start) + 1;
            if (pos < event[0]) {
                start = pos = event[0];
            }
            if (pos <= event[1]) {
                res++;
                Integer after = map.get(pos + 1);
                if (after == null) {
                    map.put(start, pos);
                } else {
                    // 区间合并
                    map.remove(pos + 1);
                    map.put(start, after);
                }
            }
        }
        return res;
    }

    /**
     * LeetCode 上比较快的解法, 使用优先队列.
     */
    @Answer
    public int maxEvents2(int[][] events) {
        final int n = events.length;
        // 按照开始时间排序.
        Arrays.sort(events, Comparator.comparingInt(a -> a[0]));
        // 保存活动的结束时间, 则每次取出的是结束最早的活动.
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        // day 表示有效的开始日期
        int res = 0;
        // 遍历每一天
        for (int day = 0, i = 0; !pq.isEmpty() || i < n; day++) {
            // 初始化开始天数(避免无效的day 循环)
            if (pq.isEmpty()) {
                day = events[i][0];
            }
            // 把day 天开始的都放入优先队列.
            while (i < n && events[i][0] == day) {
                pq.add(events[i++][1]);
            }
            // 取出最近结束的事件的最后时间, 这个事件安排在day 天进行.
            pq.poll();
            res++;
            // 删除过期活动
            while (!pq.isEmpty() && pq.peek() <= day) {
                pq.poll();
            }
        }
        return res;
    }

    /**
     * leetcode 上更快的解法.
     */
    @Answer
    public int maxEvents3(int[][] events) {
        // 按照开始时间和结束时间排序
        Arrays.sort(events, (a, b) -> a[1] == b[1] ? a[0] - b[0] : a[1] - b[1]);
        boolean[] seen = new boolean[100001];
        int res = 0, last = 0;
        for (int i = 0; i < events.length; i++) {
            int end = events[i][1];
            // 如果开始时间和前面的一样则取上一次的值, 否则取本次的开始
            // 这个判断用于避免重复循环遍历.
            int start = (i > 0 && events[i - 1][0] == events[i][0]) ? last : events[i][0];
            for (int j = start; j <= end; j++) {
                if (!seen[j]) {
                    seen[j] = true;
                    last = j;
                    res++;
                    break;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{1, 2}, {2, 3}, {3, 4}})
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 2}})
            .expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new int[][]{{1, 4}, {4, 4}, {2, 2}, {3, 4}, {1, 1}})
            .expect(4);

    @TestData
    public DataExpectation example4 = DataExpectation
            .create(new int[][]{{1, 100000}})
            .expect(1);

    @TestData
    public DataExpectation example5 = DataExpectation
            .create(new int[][]{{1, 1}, {1, 2}, {1, 3}, {1, 4}, {1, 5}, {1, 6}, {1, 7}})
            .expect(7);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[][]{{1, 2}, {1, 2}, {3, 3}, {1, 5}, {1, 5}})
            .expect(5);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(new int[][]{{1, 2}, {2, 2}, {3, 3}, {3, 4}, {3, 4}})
            .expect(4);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .create(new int[][]{{26, 27}, {24, 26}, {1, 4}, {1, 2}, {3, 6}, {2, 6}, {9, 13}, {6, 6}, {25, 27}, {22, 25},
                    {20, 24}, {8, 8}, {27, 27}, {30, 32}})
            .expect(14);

    @TestData
    public DataExpectation normal4 = DataExpectation
            .create(new int[][]{{1, 5}, {1, 5}, {1, 5}, {2, 3}, {2, 3}})
            .expect(5);

    private TestDataFile testDataFile = new TestDataFile();

    // 10^5 个测试数据
    @TestData
    public DataExpectation overTime1 = DataExpectation
            .create(TestDataFileHelper.read(testDataFile, 1, int[][].class))
            .expect(100000);

    // 10^5 个测试数据2
    @TestData
    public DataExpectation overTime2 = DataExpectation
            .create(TestDataFileHelper.read(testDataFile, 2, int[][].class))
            .expect(99888);

}
