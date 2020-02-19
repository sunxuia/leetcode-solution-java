package q450;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/queue-reconstruction-by-height/
 *
 * Suppose you have a random list of people standing in a queue. Each person is described by a pair of integers (h, k)
 * , where h is the height of the person and k is the number of people in front of this person who have a height
 * greater than or equal to h. Write an algorithm to reconstruct the queue.
 *
 * Note:
 * The number of people is less than 1,100.
 *
 *
 * Example
 *
 * Input:
 * [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
 *
 * Output:
 * [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
 *
 * 题解: 要求按照队列的真正顺序排列数组. 左边是队头, 右边是队尾.
 */
@RunWith(LeetCodeRunner.class)
public class Q406_QueueReconstructionByHeight {

    /**
     * https://www.cnblogs.com/grandyang/p/5928417.html
     */
    @Answer
    public int[][] reconstructQueue2(int[][] people) {
        // 按照升高降序排列(第1 个元素), 如果升高一样则按照前面比他高的人数(第2 个元素) 升序排列.
        Arrays.sort(people, (p1, p2) -> p1[0] == p2[0] ? p1[1] - p2[1] : p2[0] - p1[0]);
        List<int[]> res = new ArrayList<>(people.length);
        for (int[] person : people) {
            // 插入到相应位置, 这样, 前面比他高或相等的人就有这么多
            res.add(person[1], person);
        }
        return res.toArray(new int[res.size()][]);
    }

    /**
     * 对上面做法的改进, 思路一样, 比较省空间, 费时间.
     */
    @Answer
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (p1, p2) -> p1[0] == p2[0] ? p1[1] - p2[1] : p2[0] - p1[0]);
        for (int i = 1; i < people.length; i++) {
            // 遍历之前的人数, 找到该元素应该在的位置
            for (int j = 0; j < i; j++) {
                // 找到应该插入到该位置 (前面比他高的人就有这么多).
                if (j == people[i][1]) {
                    // 插入法插入元素.
                    int[] p = people[i];
                    for (int k = i - 1; k >= j; --k) {
                        people[k + 1] = people[k];
                    }
                    people[j] = p;
                    break;
                }
            }
        }
        return people;
    }

    @TestData
    public DataExpectation exmaple = DataExpectation
            .create(new int[][]{{7, 0}, {4, 4}, {7, 1}, {5, 0}, {6, 1}, {5, 2}})
            .expect(new int[][]{{5, 0}, {7, 0}, {5, 2}, {6, 1}, {4, 4}, {7, 1}});

}
