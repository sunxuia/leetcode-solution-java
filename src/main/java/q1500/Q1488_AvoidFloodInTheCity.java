package q1500;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1488. Avoid Flood in The City
 * https://leetcode.com/problems/avoid-flood-in-the-city/
 *
 * Your country has an infinite number of lakes. Initially, all the lakes are empty, but when it rains over the nth
 * lake, the nth lake becomes full of water. If it rains over a lake which is full of water, there will be a flood. Your
 * goal is to avoid the flood in any lake.
 *
 * Given an integer array rains where:
 *
 * rains[i] > 0 means there will be rains over the rains[i] lake.
 * rains[i] == 0 means there are no rains this day and you can choose one lake this day and dry it.
 *
 * Return an array ans where:
 *
 * ans.length == rains.length
 * ans[i] == -1 if rains[i] > 0.
 * ans[i] is the lake you choose to dry in the ith day if rains[i] == 0.
 *
 * If there are multiple valid answers return any of them. If it is impossible to avoid flood return an empty array.
 *
 * Notice that if you chose to dry a full lake, it becomes empty, but if you chose to dry an empty lake, nothing
 * changes. (see example 4)
 *
 * Example 1:
 *
 * Input: rains = [1,2,3,4]
 * Output: [-1,-1,-1,-1]
 * Explanation: After the first day full lakes are [1]
 * After the second day full lakes are [1,2]
 * After the third day full lakes are [1,2,3]
 * After the fourth day full lakes are [1,2,3,4]
 * There's no day to dry any lake and there is no flood in any lake.
 *
 * Example 2:
 *
 * Input: rains = [1,2,0,0,2,1]
 * Output: [-1,-1,2,1,-1,-1]
 * Explanation: After the first day full lakes are [1]
 * After the second day full lakes are [1,2]
 * After the third day, we dry lake 2. Full lakes are [1]
 * After the fourth day, we dry lake 1. There is no full lakes.
 * After the fifth day, full lakes are [2].
 * After the sixth day, full lakes are [1,2].
 * It is easy that this scenario is flood-free. [-1,-1,1,2,-1,-1] is another acceptable scenario.
 *
 * Example 3:
 *
 * Input: rains = [1,2,0,1,2]
 * Output: []
 * Explanation: After the second day, full lakes are  [1,2]. We have to dry one lake in the third day.
 * After that, it will rain over lakes [1,2]. It's easy to prove that no matter which lake you choose to dry in the 3rd
 * day, the other one will flood.
 *
 * Example 4:
 *
 * Input: rains = [69,0,0,0,69]
 * Output: [-1,69,1,1,-1]
 * Explanation: Any solution on one of the forms [-1,69,x,y,-1], [-1,x,69,y,-1] or [-1,x,y,69,-1] is acceptable where 1
 * <= x,y <= 10^9
 *
 * Example 5:
 *
 * Input: rains = [10,20,20]
 * Output: []
 * Explanation: It will rain over lake 20 two consecutive days. There is no chance to dry any lake.
 *
 * Constraints:
 *
 * 1 <= rains.length <= 10^5
 * 0 <= rains[i] <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1488_AvoidFloodInTheCity {

    @Answer
    public int[] avoidFlood(int[] rains) {
        final int n = rains.length;
        int[] res = new int[n];
        TreeSet<Integer> dryDays = new TreeSet<>();
        Map<Integer, Integer> fullLakes = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (rains[i] == 0) {
                dryDays.add(i);
                res[i] = 1;
            } else {
                if (fullLakes.containsKey(rains[i])) {
                    Integer prev = fullLakes.get(rains[i]);
                    Integer after = dryDays.higher(prev);
                    if (after == null) {
                        return new int[0];
                    }
                    dryDays.remove(after);
                    res[after] = rains[i];
                }
                fullLakes.put(rains[i], i);
                res[i] = -1;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = createValidTestData(new int[]{1, 2, 3, 4});

    private DataExpectation createValidTestData(int[] rains) {
        return DataExpectation.create(rains)
                .assertResult((Consumer<int[]>) res -> {
                    final int n = rains.length;
                    Assert.assertEquals(n, res.length);
                    Set<Integer> fullLakes = new HashSet<>();
                    for (int i = 0; i < n; i++) {
                        if (rains[i] == 0) {
                            Assert.assertTrue(1 <= res[i] && res[i] <= 10_0000_0000);
                            fullLakes.remove(res[i]);
                        } else {
                            Assert.assertFalse(fullLakes.contains(rains[i]));
                            Assert.assertEquals(-1, res[i]);
                        }
                    }
                });
    }

    private DataExpectation createEmptyTestData(int[] rains) {
        return DataExpectation.create(rains).expect(new int[0]);
    }

    @TestData
    public DataExpectation example2 = createValidTestData(new int[]{1, 2, 0, 0, 2, 1});

    @TestData
    public DataExpectation example3 = createEmptyTestData(new int[]{1, 2, 0, 1, 2});

    @TestData
    public DataExpectation example4 = createValidTestData(new int[]{69, 0, 0, 0, 69});

    @TestData
    public DataExpectation example5 = createEmptyTestData(new int[]{10, 20, 20});

    @TestData
    public DataExpectation normal1 = createEmptyTestData(new int[]{0, 1, 1});

    @TestData
    public DataExpectation normal2 = createValidTestData(new int[]{1, 0, 2, 0, 2, 1});

}
