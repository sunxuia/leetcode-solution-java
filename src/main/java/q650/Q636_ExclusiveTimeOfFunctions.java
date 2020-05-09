package q650;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/exclusive-time-of-functions/
 *
 * On a single threaded CPU, we execute some functions.  Each function has a unique id between 0 and N-1.
 *
 * We store logs in timestamp order that describe when a function is entered or exited.
 *
 * Each log is a string with this format: "{function_id}:{"start" | "end"}:{timestamp}".  For example, "0:start:3"
 * means the function with id 0 started at the beginning of timestamp 3.  "1:end:2" means the function with id 1
 * ended at the end of timestamp 2.
 *
 * A function's exclusive time is the number of units of time spent in this function.  Note that this does not
 * include any recursive calls to child functions.
 *
 * The CPU is single threaded which means that only one function is being executed at a given time unit.
 *
 * Return the exclusive time of each function, sorted by their function id.
 *
 *
 *
 * Example 1:
 *
 * (图 Q636_PIC.png)
 *
 * Input:
 * n = 2
 * logs = ["0:start:0","1:start:2","1:end:5","0:end:6"]
 * Output: [3, 4]
 * Explanation:
 * Function 0 starts at the beginning of time 0, then it executes 2 units of time and reaches the end of time 1.
 * Now function 1 starts at the beginning of time 2, executes 4 units of time and ends at time 5.
 * Function 0 is running again at the beginning of time 6, and also ends at the end of time 6, thus executing for 1
 * unit of time.
 * So function 0 spends 2 + 1 = 3 units of total time executing, and function 1 spends 4 units of total time executing.
 *
 *
 *
 * Note:
 *
 * 1. 1 <= n <= 100
 * 2. Two functions won't start or end at the same time.
 * 3. Functions will always log when they exit.
 */
@RunWith(LeetCodeRunner.class)
public class Q636_ExclusiveTimeOfFunctions {

    @Answer
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] res = new int[n];
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        int lastTime = 0;
        for (String log : logs) {
            String[] strs = log.split(":");
            int time = Integer.parseInt(strs[2]);
            if ("start".equals(strs[1])) {
                stack.push(stack.pop() + time - lastTime);
                stack.push(0);
                lastTime = time;
            } else {
                int id = Integer.parseInt(strs[0]);
                res[id] += stack.pop() + time - lastTime + 1;
                lastTime = time + 1;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(2, Arrays.asList("0:start:0", "1:start:2", "1:end:5", "0:end:6"))
            .expect(new int[]{3, 4});

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(1, Arrays.asList("0:start:0", "0:start:2", "0:end:5", "0:start:6", "0:end:6", "0:end:7"))
            .expect(new int[]{8});

}
