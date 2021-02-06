package q1700;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1700. Number of Students Unable to Eat Lunch
 * https://leetcode.com/problems/number-of-students-unable-to-eat-lunch/
 *
 * The school cafeteria offers circular and square sandwiches at lunch break, referred to by numbers 0 and 1
 * respectively. All students stand in a queue. Each student either prefers square or circular sandwiches.
 *
 * The number of sandwiches in the cafeteria is equal to the number of students. The sandwiches are placed in a stack.
 * At each step:
 *
 * - If the student at the front of the queue prefers the sandwich on the top of the stack, they will take it and leave
 * the queue.
 * - Otherwise, they will leave it and go to the queue's end.
 *
 * This continues until none of the queue students want to take the top sandwich and are thus unable to eat.
 *
 * You are given two integer arrays students and sandwiches where sandwiches[i] is the type of the i??????th sandwich in
 * the stack (i = 0 is the top of the stack) and students[j] is the preference of the j??????th student in the initial
 * queue (j = 0 is the front of the queue). Return the number of students that are unable to eat.
 *
 * Example 1:
 *
 * Input: students = [1,1,0,0], sandwiches = [0,1,0,1]
 * Output: 0
 * Explanation:
 * - Front student leaves the top sandwich and returns to the end of the line making students = [1,0,0,1].
 * - Front student leaves the top sandwich and returns to the end of the line making students = [0,0,1,1].
 * - Front student takes the top sandwich and leaves the line making students = [0,1,1] and sandwiches = [1,0,1].
 * - Front student leaves the top sandwich and returns to the end of the line making students = [1,1,0].
 * - Front student takes the top sandwich and leaves the line making students = [1,0] and sandwiches = [0,1].
 * - Front student leaves the top sandwich and returns to the end of the line making students = [0,1].
 * - Front student takes the top sandwich and leaves the line making students = [1] and sandwiches = [1].
 * - Front student takes the top sandwich and leaves the line making students = [] and sandwiches = [].
 * Hence all students are able to eat.
 *
 * Example 2:
 *
 * Input: students = [1,1,1,0,0,1], sandwiches = [1,0,0,0,1,1]
 * Output: 3
 *
 * Constraints:
 *
 * 1 <= students.length, sandwiches.length <= 100
 * students.length == sandwiches.length
 * sandwiches[i] is 0 or 1.
 * students[i] is 0 or 1.
 */
@RunWith(LeetCodeRunner.class)
public class Q1700_NumberOfStudentsUnableToEatLunch {

    @Answer
    public int countStudents(int[] students, int[] sandwiches) {
        int si = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        for (int student : students) {
            queue.offer(student);
        }
        while (!queue.isEmpty()) {
            int len = queue.size();
            for (int i = 0; i < len; i++) {
                int student = queue.poll();
                if (student == sandwiches[si]) {
                    si++;
                } else {
                    queue.offer(student);
                }
            }
            if (len == queue.size()) {
                return len;
            }
        }
        return 0;
    }

    /**
     * 另一种解法, 利用了题目的特性.
     */
    @Answer
    public int countStudents2(int[] students, int[] sandwiches) {
        int[] requires = new int[2];
        for (int student : students) {
            requires[student]++;
        }
        for (int sandwich : sandwiches) {
            requires[sandwich]--;
            if (requires[sandwich] < 0) {
                return requires[1 - sandwich];
            }
        }
        return 0;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 1, 0, 0}, new int[]{0, 1, 0, 1})
            .expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 1, 1, 0, 0, 1}, new int[]{1, 0, 0, 0, 1, 1})
            .expect(3);

}
