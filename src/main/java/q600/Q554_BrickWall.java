package q600;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/brick-wall/
 *
 * There is a brick wall in front of you. The wall is rectangular and has several rows of bricks. The bricks have the
 * same height but different width. You want to draw a vertical line from the top to the bottom and cross the least
 * bricks.
 *
 * The brick wall is represented by a list of rows. Each row is a list of integers representing the width of each
 * brick in this row from left to right.
 *
 * If your line go through the edge of a brick, then the brick is not considered as crossed. You need to find out how
 * to draw the line to cross the least bricks and return the number of crossed bricks.
 *
 * You cannot draw a line just along one of the two vertical edges of the wall, in which case the line will obviously
 * cross no bricks.
 *
 *
 *
 * Example:
 *
 * Input: [[1,2,2,1],
 * [3,1,2],
 * [1,3,2],
 * [2,4],
 * [3,1,2],
 * [1,3,1,1]]
 *
 * Output: 2
 *
 * Explanation:
 *
 * (图Q554_PIC.png)
 *
 * Note:
 *
 * The width sum of bricks in different rows are the same and won't exceed INT_MAX.
 * The number of bricks in each row is in range [1,10,000]. The height of wall is in range [1,10,000]. Total
 * number of bricks of the wall won't exceed 20,000.
 */
@RunWith(LeetCodeRunner.class)
public class Q554_BrickWall {

    @Answer
    public int leastBricks(List<List<Integer>> wall) {
        final int len = wall.size();
        if (len < 2) {
            return len == 1 && wall.get(0).size() == 1 ? 1 : 0;
        }
        int maxRight = wall.get(0).stream().mapToInt(i -> i).sum();
        int[] right = new int[len];
        int[] next = new int[len];
        PriorityQueue<Integer> pq = new PriorityQueue<>(len, Comparator.comparingInt(a -> right[a]));
        for (int i = 0; i < len; i++) {
            pq.add(i);
        }
        int max = 0;
        while (!pq.isEmpty()) {
            int i, rightVal, count = 0;
            do {
                i = pq.remove();
                rightVal = right[i];
                count++;
                if (next[i] < wall.get(i).size()) {
                    right[i] += wall.get(i).get(next[i]++);
                    pq.add(i);
                }
            } while (!pq.isEmpty() && rightVal == right[pq.peek()]);
            if (rightVal > 0 && rightVal < maxRight) {
                max = Math.max(max, count);
            }
        }
        return len - max;
    }

    // LeetCode 上比较快的解法, 将缝隙保存在一个map 中, 免去了排序的操作
    @Answer
    public int leastBricks2(List<List<Integer>> wall) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int max = 0;
        for (List<Integer> list : wall) {
            int sum = 0;
            for (int j = 0; j < list.size() - 1; j++) {
                sum += list.get(j);
                int count = 1 + map.getOrDefault(sum, 0);
                map.put(sum, count);
                max = Math.max(max, count);
            }
        }
        return wall.size() - max;
    }

    @TestData
    public DataExpectation exmaple = DataExpectation.create(Arrays.asList(
            Arrays.asList(1, 2, 2, 1),
            Arrays.asList(3, 1, 2),
            Arrays.asList(1, 3, 2),
            Arrays.asList(2, 4),
            Arrays.asList(3, 1, 2),
            Arrays.asList(1, 3, 1, 1)
    )).expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(Arrays.asList(
            Collections.singletonList(1),
            Collections.singletonList(1),
            Collections.singletonList(1)
    )).expect(3);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(Arrays.asList(
            Arrays.asList(1, 1),
            Collections.singletonList(2),
            Arrays.asList(1, 1)
    )).expect(1);

    @TestData
    public DataExpectation normal3 = DataExpectation.create(Arrays.asList(
            Collections.singletonList(1)
    )).expect(1);

    @TestData
    public DataExpectation normal4 = DataExpectation.create(Arrays.asList(
            Arrays.asList(79, 12, 208, 1)
    )).expect(0);

}
