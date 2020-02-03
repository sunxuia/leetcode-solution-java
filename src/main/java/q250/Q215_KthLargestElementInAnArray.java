package q250;

import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/kth-largest-element-in-an-array/
 *
 * Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order,
 * not the kth distinct element.
 *
 * Example 1:
 *
 * Input: [3,2,1,5,6,4] and k = 2
 * Output: 5
 *
 * Example 2:
 *
 * Input: [3,2,3,1,2,4,5,5,6] and k = 4
 * Output: 4
 *
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ array's length.
 */
@RunWith(LeetCodeRunner.class)
public class Q215_KthLargestElementInAnArray {

    // 最小堆, 时间复杂度 O(nlogk)
    @Answer
    public int findKthLargest(int[] nums, int k) {
        MinHeap heap = new MinHeap(k);
        for (int num : nums) {
            if (heap.size() < k) {
                heap.add(num);
            } else if (heap.min() < num) {
                heap.remove();
                heap.add(num);
            }
        }
        return heap.min();
    }

    private static class MinHeap {

        // 使用数组来保存节点, 当前节点下标*2就是当前下标的左子节点,再+1就是右子节点 (从下标1 开始, 这样 2/2=1, 3/2=1)
        public int[] nums;

        // 下一个新加入的节点
        private int pointer = 1;

        public MinHeap(int capacity) {
            nums = new int[capacity + 1];
        }

        // 插入新的值, 并向上移动
        public void siftUp(int newVal) {
            if (pointer < 0 || pointer >= nums.length) {
                return;
            }

            nums[pointer] = newVal;
            int i = pointer, p;
            pointer++;

            while (true) {
                if (i == 1) {
                    break;
                }
                p = i / 2;
                if (nums[p] <= nums[i]) {
                    break;
                }
                swap(p, i);
                i = p;
            }
        }

        public int min() {
            return nums[1];
        }

        public int size() {
            return pointer - 1;
        }

        public void add(int newVal) {
            nums[pointer] = newVal;
            for (int curr = pointer, upper; curr > 1; curr = upper) {
                upper = curr / 2;
                if (nums[upper] <= nums[curr]) {
                    break;
                }
                swap(curr, upper);
            }
            pointer++;
        }

        private void swap(int a, int b) {
            int t = nums[a];
            nums[a] = nums[b];
            nums[b] = t;
        }

        // 删除最小值, 并将末尾的数字移动到下标1, 向下移动.
        public int remove() {
            int res = nums[1];
            nums[1] = nums[--pointer];
            for (int curr = 1, child; curr * 2 < pointer; curr = child) {
                child = curr * 2;
                // 左子节点 > 右子节点
                if (child + 1 < pointer && nums[child] > nums[child + 1]) {
                    child++;
                }
                if (nums[curr] <= nums[child]) {
                    break;
                }
                swap(curr, child);
            }
            return res;
        }
    }

    // 在Java 中有现成的最小/大堆的实现: 优先队列.
    @Answer
    public int findKthLargest2(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int num : nums) {
            if (queue.size() < k) {
                queue.add(num);
            } else if (queue.element() < num) {
                queue.remove();
                queue.add(num);
            }
        }
        return queue.element();
    }

    // LeetCode 的解法, 使用快速排序的方式每次确定一个点, 然后按照k 的位置去继续寻找
    // 时间复杂度是 O(nlogn)
    @Answer
    public int findKthLargest3(int[] nums, int k) {
        return recursive(nums, 0, nums.length - 1, k - 1);
    }

    private int recursive(int[] nums, int start, int end, int k) {
        int sentinel = nums[start];
        int i = start, j = end + 1;
        while (i < j) {
            while (sentinel > nums[--j]) {
                // do nothing
            }
            while (i < j && nums[++i] > sentinel) {
                // do nothing
            }
            swap(nums, i, j);
        }
        swap(nums, start, j);
        if (k == j) {
            return sentinel;
        } else if (k < j) {
            return recursive(nums, start, j - 1, k);
        } else {
            return recursive(nums, j + 1, end, k);
        }
    }

    private void swap(int[] nums, int a, int b) {
        int t = nums[a];
        nums[a] = nums[b];
        nums[b] = t;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{3, 2, 1, 5, 6, 4}, 2).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4).expect(4);

}
