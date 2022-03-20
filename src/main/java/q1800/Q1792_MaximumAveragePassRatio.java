package q1800;

import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1792. Maximum Average Pass Ratio
 * https://leetcode.com/problems/maximum-average-pass-ratio/
 *
 * There is a school that has classes of students and each class will be having a final exam. You are given a 2D integer
 * array classes, where classes[i] = [passi, totali]. You know beforehand that in the ith class, there are totali total
 * students, but only passi number of students will pass the exam.
 *
 * You are also given an integer extraStudents. There are another extraStudents brilliant students that are guaranteed
 * to pass the exam of any class they are assigned to. You want to assign each of the extraStudents students to a class
 * in a way that maximizes the average pass ratio across all the classes.
 *
 * The pass ratio of a class is equal to the number of students of the class that will pass the exam divided by the
 * total number of students of the class. The average pass ratio is the sum of pass ratios of all the classes divided by
 * the number of the classes.
 *
 * Return the maximum possible average pass ratio after assigning the extraStudents students. Answers within 10^-5 of
 * the actual answer will be accepted.
 *
 * Example 1:
 *
 * Input: classes = [[1,2],[3,5],[2,2]], extraStudents = 2
 * Output: 0.78333
 * Explanation: You can assign the two extra students to the first class. The average pass ratio will be equal to (3/4 +
 * 3/5 + 2/2) / 3 = 0.78333.
 *
 * Example 2:
 *
 * Input: classes = [[2,4],[3,9],[4,5],[2,10]], extraStudents = 4
 * Output: 0.53485
 *
 * Constraints:
 *
 * 1 <= classes.length <= 10^5
 * classes[i].length == 2
 * 1 <= passi <= totali <= 10^5
 * 1 <= extraStudents <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1792_MaximumAveragePassRatio {

    @Answer
    public double maxAverageRatio(int[][] classes, int extraStudents) {
        final int n = classes.length;
        double sum = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Double.compare(
                getClassPassRatio(classes, b[0], b[1]) - getClassPassRatio(classes, b[0], b[1] - 1),
                getClassPassRatio(classes, a[0], a[1]) - getClassPassRatio(classes, a[0], a[1] - 1)
        ));
        for (int i = 0; i < n; i++) {
            sum += getClassPassRatio(classes, i, 0);
            pq.offer(new int[]{i, 1});
        }
        for (int i = 0; i < extraStudents; i++) {
            int[] arr = pq.poll();
            int index = arr[0], extra = arr[1];
            sum += getClassPassRatio(classes, index, extra) - getClassPassRatio(classes, index, extra - 1);
            pq.offer(new int[]{index, extra + 1});
        }
        return sum / n;
    }

    private double getClassPassRatio(int[][] classes, int index, int extraStudents) {
        return (double) (classes[index][0] + extraStudents) / (classes[index][1] + extraStudents);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{1, 2}, {3, 5}, {2, 2}}, 2)
            .expectDouble(0.78333);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{2, 4}, {3, 9}, {4, 5}, {2, 10}}, 4)
            .expectDouble(0.53485);

}
