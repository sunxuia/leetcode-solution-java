package q700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/number-of-longest-increasing-subsequence/
 *
 * Given an unsorted array of integers, find the number of longest increasing subsequence.
 *
 * Example 1:
 *
 * Input: [1,3,5,4,7]
 * Output: 2
 * Explanation: The two longest increasing subsequence are [1, 3, 4, 7] and [1, 3, 5, 7].
 *
 * Example 2:
 *
 * Input: [2,2,2,2,2]
 * Output: 5
 * Explanation: The length of longest continuous increasing subsequence is 1, and there are 5 subsequences' length is
 * 1, so output 5.
 *
 * Note: Length of the given array will be not exceed 2000 and the answer is guaranteed to be fit in 32-bit signed int.
 */
@RunWith(LeetCodeRunner.class)
public class Q673_NumberOfLongestIncreasingSubsequence {

    // dp
    @Answer
    public int findNumberOfLIS(int[] nums) {
        final int n = nums.length;
        // size[i] 表示结尾是i 的子数组的最大长度
        int[] size = new int[n];
        // count[i] 表示结尾是i 的最长子数组的数量
        int[] count = new int[n];
        for (int i = 0; i < n; i++) {
            size[i] = 1;
            count[i] = 1;
            // 遍历前面的数字看看能够组成多少最长的子数组
            for (int j = i - 1; j >= 0; j--) {
                if (nums[j] < nums[i]) {
                    int newSize = 1 + size[j];
                    if (newSize >= size[i]) {
                        if (newSize > size[i]) {
                            // 新的最长子数组
                            size[i] = newSize;
                            count[i] = count[j];
                        } else if (newSize == size[i]) {
                            // 更新最长子数组长度
                            count[i] += count[j];
                        }
                    }
                }
            }
        }

        int max = Integer.MIN_VALUE, res = 0;
        for (int i = 0; i < n; i++) {
            if (max < size[i]) {
                res = count[i];
                max = size[i];
            } else if (max == size[i]) {
                res += count[i];
            }
        }
        return res;
    }

    // LeetCode 上最快的方法: 通过线段树实现.
    @Answer
    public int findNumberOfLIS2(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        // 构建线段树
        Node root = new Node(min, max);
        for (int num : nums) {
            Value v = query(root, num - 1);
            insert(root, num, new Value(v.length + 1, v.count));
        }
        return root.val.count;
    }

    private static final class Node {

        // [start, end]
        private final int start;
        private final int end;

        private Value val;

        private Node left;
        private Node right;

        private Node(int start, int end) {
            this.start = start;
            this.end = end;
            val = new Value(1, 1);
        }

        private int mid() {
            return start + (end - start) / 2;
        }

        // lazy init, also avoids cycle in ctr
        private Node left() {
            if (left == null) {
                left = new Node(start, mid());
            }
            return left;
        }

        private Node right() {
            if (right == null) {
                right = new Node(mid() + 1, end);
            }
            return right;
        }
    }

    private static final class Value {

        private final int length;
        private final int count;

        private Value(int length, int count) {
            this.length = length;
            this.count = count;
        }
    }

    private Value query(Node node, int key) {
        if (node.end <= key) {
            return node.val;
        } else if (node.start > key) {
            return new Value(1, 1);
        } else {
            return merge(query(node.left(), key), query(node.right(), key));
        }
    }

    private void insert(Node node, int key, Value v) {
        if (node.start == node.end) {
            node.val = merge(v, node.val);
            return;
        } else {
            if (key <= node.mid()) {
                insert(node.left(), key, v);
            } else {
                insert(node.right(), key, v);
            }
        }
        node.val = merge(node.left().val, node.right().val);
    }

    private Value merge(Value v1, Value v2) {
        if (v1.length == v2.length) {
            if (v1.length == 1) {
                return new Value(1, 1);
            } else {
                return new Value(v1.length, v1.count + v2.count);
            }
        }
        return v1.length > v2.length ? v1 : v2;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 3, 5, 4, 7}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 2, 2, 2, 2}).expect(5);

    @TestData
    public DataExpectation overTime1 = DataExpectation.create(new int[]{
            4, 20, 7, 22, 14, 63, 58, 75, 32, 35, 30, 86, 91, 23, 38, 31, 5, 93, 37, 96, 49, 91, 96, 53, 23, 1, 93, 59,
            20, 7, 39, 49, 37, 97, 87, 19, 78, 100, 30, 50, 78, 80, 41, 42, 91, 92, 26, 72, 21, 52, 51, 57, 31, 58, 29,
            72, 55, 64, 97, 90, 76, 80, 69, 11, 3, 56, 90, 98, 46, 83, 91, 46, 20, 17, 84, 2, 16, 64, 25, 99
    }).expect(384);

    @TestData
    public DataExpectation overTime2 = DataExpectation.create(new int[]{
            6, 32, 80, 30, -59, 49, 99, 15, 55, 86, 13, 5, 37, 8, 19, 21, 18, 38, 50, 82, 26, 91, 55, 43, 14, 87, 24,
            97, 65, 67, 56, 1, 9, 56, 49, 17, 79, 33, 13, 98, 98, 44, 48, 61, 96, 13, 15, 76, 61, 57, 62, 24, 31, 13,
            11, 57, 16, 35, 58, -4, 16, -22, 63, -83, 38, 50, 93, -26, 68, -45, 2, 12, 41, 54, 67, 36, 84, 55, -97, -76,
            37, 16, 55, 42, 4, 15, 47, 50, 3, 23, -7, 77, -59, 88, 39, 46, -25, 7, 56, 88, -58, 68, 5, -71, 67, 59, -77,
            62, -5, 2, 11, 45, 2, 96, 97, 2, 32, 79, 58
    }).expect(4);

}
