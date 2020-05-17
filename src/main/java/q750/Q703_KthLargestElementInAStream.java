package q750;

import java.util.PriorityQueue;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/kth-largest-element-in-a-stream/
 *
 * Design a class to find the kth largest element in a stream. Note that it is the kth largest element in the sorted
 * order, not the kth distinct element.
 *
 * Your KthLargest class will have a constructor which accepts an integer k and an integer array nums, which contains
 * initial elements from the stream. For each call to the method KthLargest.add, return the element representing the
 * kth largest element in the stream.
 *
 * Example:
 *
 * int k = 3;
 * int[] arr = [4,5,8,2];
 * KthLargest kthLargest = new KthLargest(3, arr);
 * kthLargest.add(3);   // returns 4
 * kthLargest.add(5);   // returns 5
 * kthLargest.add(10);  // returns 5
 * kthLargest.add(9);   // returns 8
 * kthLargest.add(4);   // returns 8
 *
 * Note:
 * You may assume that nums' length ≥ k-1 and k ≥ 1.
 */
public class Q703_KthLargestElementInAStream {

    private static class KthLargest {

        PriorityQueue<Integer> pq;

        final int k;

        public KthLargest(int k, int[] nums) {
            this.k = k;
            this.pq = new PriorityQueue<>(k + 1);
            for (int num : nums) {
                pq.offer(num);
                if (pq.size() > k) {
                    pq.remove();
                }
            }
        }

        public int add(int val) {
            pq.add(val);
            if (pq.size() > k) {
                pq.poll();
            }
            if (pq.size() == k) {
                return pq.peek();
            }
            return 0;
        }
    }

    @Test
    public void testMethod() {
        KthLargest kthLargest = new KthLargest(3, new int[]{4, 5, 8, 2});
        Assert.assertEquals(4, kthLargest.add(3));
        Assert.assertEquals(5, kthLargest.add(5));
        Assert.assertEquals(5, kthLargest.add(10));
        Assert.assertEquals(8, kthLargest.add(9));
        Assert.assertEquals(8, kthLargest.add(4));

        kthLargest = new KthLargest(1, new int[0]);
        Assert.assertEquals(-3, kthLargest.add(-3));
        Assert.assertEquals(-2, kthLargest.add(-2));
        Assert.assertEquals(-2, kthLargest.add(-4));
        Assert.assertEquals(0, kthLargest.add(0));
        Assert.assertEquals(4, kthLargest.add(4));
    }

}
