package q1850;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.TreeMap;
import org.junit.Test;
import org.w3c.dom.css.Counter;
import util.asserthelper.AssertUtils;

/**
 * [Hard] 1825. Finding MK Average
 * https://leetcode.com/problems/finding-mk-average/
 *
 * You are given two integers, m and k, and a stream of integers. You are tasked to implement a data structure that
 * calculates the MKAverage for the stream.
 *
 * The MKAverage can be calculated using these steps:
 *
 * 1. If the number of the elements in the stream is less than m you should consider the MKAverage to be -1. Otherwise,
 * copy the last m elements of the stream to a separate container.
 * 2. Remove the smallest k elements and the largest k elements from the container.
 * 3. Calculate the average value for the rest of the elements rounded down to the nearest integer.
 *
 * Implement the MKAverage class:
 *
 * MKAverage(int m, int k) Initializes the MKAverage object with an empty stream and the two integers m and k.
 * void addElement(int num) Inserts a new element num into the stream.
 * int calculateMKAverage() Calculates and returns the MKAverage for the current stream rounded down to the nearest
 * integer.
 *
 * Example 1:
 *
 * Input
 * ["MKAverage", "addElement", "addElement", "calculateMKAverage", "addElement", "calculateMKAverage", "addElement",
 * "addElement", "addElement", "calculateMKAverage"]
 * [[3, 1], [3], [1], [], [10], [], [5], [5], [5], []]
 * Output
 * [null, null, null, -1, null, 3, null, null, null, 5]
 *
 * Explanation
 * MKAverage obj = new MKAverage(3, 1);
 * obj.addElement(3);        // current elements are [3]
 * obj.addElement(1);        // current elements are [3,1]
 * obj.calculateMKAverage(); // return -1, because m = 3 and only 2 elements exist.
 * obj.addElement(10);       // current elements are [3,1,10]
 * obj.calculateMKAverage(); // The last 3 elements are [3,1,10].
 * // After removing smallest and largest 1 element the container will be [3].
 * // The average of [3] equals 3/1 = 3, return 3
 * obj.addElement(5);        // current elements are [3,1,10,5]
 * obj.addElement(5);        // current elements are [3,1,10,5,5]
 * obj.addElement(5);        // current elements are [3,1,10,5,5,5]
 * obj.calculateMKAverage(); // The last 3 elements are [5,5,5].
 * // After removing smallest and largest 1 element the container will be [5].
 * // The average of [5] equals 5/1 = 5, return 5
 *
 * Constraints:
 *
 * 3 <= m <= 10^5
 * 1 <= k*2 < m
 * 1 <= num <= 10^5
 * At most 10^5 calls will be made to addElement and calculateMKAverage.
 */
public class Q1825_FindingMkAverage {

    private static class MKAverage {

        final int m, k;

        int sum;

        final Queue<Integer> queue = new ArrayDeque<>();

        final Counter maxK = new Counter();

        final Counter maxRest = new Counter();

        final Counter minK = new Counter();

        final Counter minRest = new Counter();

        public MKAverage(int m, int k) {
            this.m = m;
            this.k = k;
        }

        public void addElement(int num) {
            // 删除队头的元素
            if (queue.size() == m) {
                int remove = queue.poll();
                sum -= remove;
                if (maxK.contains(remove)) {
                    maxK.decrease(remove);
                    int restMax = maxRest.max();
                    maxRest.decrease(restMax);
                    maxK.increase(restMax);
                } else {
                    maxRest.decrease(remove);
                }
                if (minK.contains(remove)) {
                    minK.decrease(remove);
                    int restMin = minRest.min();
                    minRest.decrease(restMin);
                    minK.increase(restMin);
                } else {
                    minRest.decrease(remove);
                }
            }

            // 添加新元素
            queue.offer(num);
            sum += num;
            if (maxK.size < k) {
                maxK.increase(num);
            } else if (maxK.min() < num) {
                int maxMin = maxK.min();
                maxK.decrease(maxMin);
                maxK.increase(num);
                maxRest.increase(maxMin);
            } else {
                maxRest.increase(num);
            }
            if (minK.size < k) {
                minK.increase(num);
            } else if (minK.max() > num) {
                int minMax = minK.max();
                minK.decrease(minMax);
                minK.increase(num);
                minRest.increase(minMax);
            } else {
                minRest.increase(num);
            }
        }

        public int calculateMKAverage() {
            if (queue.size() < m) {
                return -1;
            }
            return (sum - maxK.sum - minK.sum) / (queue.size() - 2 * k);
        }

        static class Counter {

            TreeMap<Integer, Integer> map = new TreeMap<>();

            int size, sum;

            int min() {
                return map.ceilingKey(Integer.MIN_VALUE);
            }

            int max() {
                return map.floorKey(Integer.MAX_VALUE);
            }

            void increase(int num) {
                size++;
                sum += num;
                map.merge(num, 1, Integer::sum);
            }

            boolean contains(int num) {
                return map.containsKey(num);
            }

            void decrease(int num) {
                size--;
                sum -= num;
                Integer count = map.get(num);
                if (count == 1) {
                    map.remove(num);
                } else {
                    map.put(num, count - 1);
                }
            }
        }
    }

    /**
     * Your MKAverage object will be instantiated and called as such:
     * MKAverage obj = new MKAverage(m, k);
     * obj.addElement(num);
     * int param_2 = obj.calculateMKAverage();
     */
    @Test
    public void testMethod() {
        int average;
        MKAverage obj = new MKAverage(3, 1);
        obj.addElement(3);
        obj.addElement(1);
        average = obj.calculateMKAverage();
        AssertUtils.assertEquals(-1, average);

        obj.addElement(10);
        average = obj.calculateMKAverage();
        AssertUtils.assertEquals(3, average);

        obj.addElement(5);
        obj.addElement(5);
        obj.addElement(5);
        average = obj.calculateMKAverage();
        AssertUtils.assertEquals(5, average);
    }

}
