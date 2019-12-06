package q300;

import java.util.PriorityQueue;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/find-median-from-data-stream/
 *
 * Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value.
 * So the median is the mean of the two middle value.
 * For example,
 *
 * [2,3,4], the median is 3
 *
 * [2,3], the median is (2 + 3) / 2 = 2.5
 *
 * Design a data structure that supports the following two operations:
 *
 * void addNum(int num) - Add a integer number from the data stream to the data structure.
 * double findMedian() - Return the median of all elements so far.
 *
 *
 *
 * Example:
 *
 * addNum(1)
 * addNum(2)
 * findMedian() -> 1.5
 * addNum(3)
 * findMedian() -> 2
 *
 *
 *
 * Follow up:
 *
 * If all integer numbers from the stream are between 0 and 100, how would you optimize it?
 * If 99% of all integer numbers from the stream are between 0 and 100, how would you optimize it?
 */
public class Q295_FindMedianFromDataStream {

    // 根据网上的提示, 这题可以使用大堆 + 小堆来做, 大堆中是小于中位数的数字, 小堆中是大于中位数的数字, 大堆的最大值和小堆的最小值中间就是中位数.
    private static class MedianFinder {

        private PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        private PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> a.equals(b) ? 0 : a > b ? -1 : 1);

        private int total = 2;

        /**
         * initialize your data structure here.
         */
        public MedianFinder() {
            minHeap.add(Integer.MAX_VALUE);
            maxHeap.add(Integer.MIN_VALUE);
        }

        public void addNum(int num) {
            if (num <= maxHeap.peek()) {
                maxHeap.add(num);
            } else {
                minHeap.add(num);
            }
            total++;
            while (maxHeap.size() > (total + 1) / 2) {
                minHeap.add(maxHeap.poll());
            }
            while (minHeap.size() > total / 2) {
                maxHeap.add(minHeap.poll());
            }
        }

        public double findMedian() {
            if (total % 2 == 1) {
                return maxHeap.peek();
            }
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }

    @Test
    public void testMethod() {
        MedianFinder f = new MedianFinder();
        f.addNum(1);
        f.addNum(2);
        Assert.assertEquals(1.5, f.findMedian(), 0);
        f.addNum(3);
        Assert.assertEquals(2, f.findMedian(), 0);
    }

}
