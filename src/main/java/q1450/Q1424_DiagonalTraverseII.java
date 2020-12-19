package q1450;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.common.json.JsonTypeWrapper;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1424. Diagonal Traverse II
 * https://leetcode.com/problems/diagonal-traverse-ii/
 *
 * Given a list of lists of integers, nums, return all elements of nums in diagonal order as shown in the below images.
 *
 * Example 1:
 * <img src="./Q1424_PIC1.png">
 * Input: nums = [[1,2,3],[4,5,6],[7,8,9]]
 * Output: [1,4,2,7,5,3,8,6,9]
 *
 * Example 2:
 * <img src="./Q1424_PIC2.png">
 * Input: nums = [[1,2,3,4,5],[6,7],[8],[9,10,11],[12,13,14,15,16]]
 * Output: [1,6,2,8,7,3,9,4,12,10,5,13,11,14,15,16]
 *
 * Example 3:
 *
 * Input: nums = [[1,2,3],[4],[5,6,7],[8],[9,10,11]]
 * Output: [1,4,2,5,3,8,6,9,7,10,11]
 *
 * Example 4:
 *
 * Input: nums = [[1,2,3,4,5,6]]
 * Output: [1,2,3,4,5,6]
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i].length <= 10^5
 * 1 <= nums[i][j] <= 10^9
 * There at most 10^5 elements in nums.
 */
@RunWith(LeetCodeRunner.class)
public class Q1424_DiagonalTraverseII {

    @Answer
    public int[] findDiagonalOrder(List<List<Integer>> nums) {
        Node first = new Node(List.of()), last = first;
        for (List<Integer> num : nums) {
            last = last.addNext(new Node(num));
        }
        last = last.addNext(new Node(List.of()));

        List<Integer> resList = new ArrayList<>();
        // 从上到下
        for (Node curr = first.next; curr != last; curr = curr.next) {
            for (Node node = curr; node != first; node = node.prev) {
                resList.add(node.nextValue());
                if (node.isEmpty()) {
                    node.remove();
                }
            }
        }
        // 底部从左到右
        while (last.prev != first) {
            for (Node node = last.prev; node != first; node = node.prev) {
                resList.add(node.nextValue());
                if (node.isEmpty()) {
                    node.remove();
                }
            }
        }
        return resList.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private static class Node {

        Node prev, next;
        List<Integer> list;
        int index = 0;

        public Node(List<Integer> list) {
            this.list = list;
        }

        Node addNext(Node node) {
            this.next = node;
            node.prev = this;
            return node;
        }

        void remove() {
            prev.next = next;
            next.prev = prev;
        }

        Integer nextValue() {
            return list.get(index++);
        }

        boolean isEmpty() {
            return index == list.size();
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(List.of(
                    List.of(1, 2, 3),
                    List.of(4, 5, 6),
                    List.of(7, 8, 9)))
            .expect(new int[]{1, 4, 2, 7, 5, 3, 8, 6, 9});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(List.of(
                    List.of(1, 2, 3, 4, 5),
                    List.of(6, 7),
                    List.of(8),
                    List.of(9, 10, 11),
                    List.of(12, 13, 14, 15, 16)))
            .expect(new int[]{1, 6, 2, 8, 7, 3, 9, 4, 12, 10, 5, 13, 11, 14, 15, 16});

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(List.of(
                    List.of(1, 2, 3),
                    List.of(4),
                    List.of(5, 6, 7),
                    List.of(8),
                    List.of(9, 10, 11)))
            .expect(new int[]{1, 4, 2, 5, 3, 8, 6, 9, 7, 10, 11});

    @TestData
    public DataExpectation example4 = DataExpectation
            .create(List.of(List.of(1, 2, 3, 4, 5, 6)))
            .expect(new int[]{1, 2, 3, 4, 5, 6});

    private TestDataFile testDataFile = new TestDataFile();

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.read(testDataFile, 1, new JsonTypeWrapper<List<List<Integer>>>() {}))
            .expect(TestDataFileHelper.read(testDataFile, 2, int[].class));

}
