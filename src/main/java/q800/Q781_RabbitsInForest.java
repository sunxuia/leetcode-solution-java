package q800;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/rabbits-in-forest/
 *
 * In a forest, each rabbit has some color. Some subset of rabbits (possibly all of them) tell you how many other
 * rabbits have the same color as them. Those answers are placed in an array.
 *
 * Return the minimum number of rabbits that could be in the forest.
 *
 * Examples:
 * Input: answers = [1, 1, 2]
 * Output: 5
 * Explanation:
 * The two rabbits that answered "1" could both be the same color, say red.
 * The rabbit than answered "2" can't be red or the answers would be inconsistent.
 * Say the rabbit that answered "2" was blue.
 * Then there should be 2 other blue rabbits in the forest that didn't answer into the array.
 * The smallest possible number of rabbits in the forest is therefore 5: 3 that answered plus 2 that didn't.
 *
 * Input: answers = [10, 10, 10]
 * Output: 11
 *
 * Input: answers = []
 * Output: 0
 *
 * Note:
 *
 * 1. answers will have length at most 1000.
 * 2. Each answers[i] will be an integer in the range [0, 999].
 */
@RunWith(LeetCodeRunner.class)
public class Q781_RabbitsInForest {

    /**
     * 参考文档 https://www.cnblogs.com/grandyang/p/9043761.html
     * 如果某个兔子回答的数字是x, 那么说明森林里共有x+1个相同颜色的兔子,
     * 我们最多允许x+1个兔子同时回答x个, 一旦超过了x+1个兔子, 那么就得再增加了x+1个新兔子了
     */
    @Answer
    public int numRabbits(int[] answers) {
        int res = 0;
        // 使用map 记录相同颜色的兔子数量, 这些兔子回答的结果都是一样的.
        // 所以作为key, value 表示兔子的总数,
        // 如果key 不存在或超过了回答数量, 则说明有一群新颜色的兔子存在.
        // 这群兔子的数量就是回答的数量 + 1
        Map<Integer, Integer> map = new HashMap<>();
        for (int answer : answers) {
            int total = answer + 1;
            Integer count = map.get(total);
            if (count == null || count == total) {
                res += total;
                map.put(total, 1);
            } else {
                map.put(total, count + 1);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 1, 2}).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{10, 10, 10}).expect(11);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[0]).expect(0);

}
