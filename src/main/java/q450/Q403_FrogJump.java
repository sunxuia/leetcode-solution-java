package q450;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/frog-jump/
 *
 * A frog is crossing a river. The river is divided into x units and at each unit there may or may not exist a stone.
 * The frog can jump on a stone, but it must not jump into the water.
 *
 * Given a list of stones' positions (in units) in sorted ascending order, determine if the frog is able to cross the
 * river by landing on the last stone. Initially, the frog is on the first stone and assume the first jump must be 1
 * unit.
 *
 * If the frog's last jump was k units, then its next jump must be either k - 1, k, or k + 1 units. Note that the
 * frog can only jump in the forward direction.
 *
 * Note:
 *
 * The number of stones is ≥ 2 and is < 1,100.
 * Each stone's position will be a non-negative integer < 2^31.
 * The first stone's position is always 0.
 *
 * Example 1:
 *
 * [0,1,3,5,6,8,12,17]
 *
 * There are a total of 8 stones.
 * The first stone at the 0th unit, second stone at the 1st unit,
 * third stone at the 3rd unit, and so on...
 * The last stone at the 17th unit.
 *
 * Return true. The frog can jump to the last stone by jumping
 * 1 unit to the 2nd stone, then 2 units to the 3rd stone, then
 * 2 units to the 4th stone, then 3 units to the 6th stone,
 * 4 units to the 7th stone, and 5 units to the 8th stone.
 *
 * Example 2:
 *
 * [0,1,2,3,4,8,9,11]
 *
 * Return false. There is no way to jump to the last stone as
 * the gap between the 5th and 6th stone is too large.
 */
@RunWith(LeetCodeRunner.class)
public class Q403_FrogJump {

    // 计算每个点计算可能跳到的位置
    @Answer
    public boolean canCross(int[] stones) {
        final int length = stones.length;
        Map<Integer, Set<Integer>> stoneMap = new HashMap<>();
        for (int i = 0; i < length; i++) {
            stoneMap.put(stones[i], new HashSet<>());
        }
        stoneMap.get(stones[0]).add(0);

        for (int i = 0; i < length; i++) {
            for (int step : stoneMap.get(stones[i])) {
                Set<Integer> set;
                if (step > 2) {
                    set = stoneMap.get(stones[i] + step - 1);
                    if (set != null) {
                        set.add(step - 1);
                    }
                }
                if (step > 1) {
                    set = stoneMap.get(stones[i] + step);
                    if (set != null) {
                        set.add(step);
                    }
                }
                set = stoneMap.get(stones[i] + step + 1);
                if (set != null) {
                    set.add(step + 1);
                }
            }
        }
        return !stoneMap.get(stones[length - 1]).isEmpty();
    }

    /**
     * LeetCode 上比较快的一个解法. 通过dfs 的方式来寻找路径.
     */
    @Answer
    public boolean canCross2(int[] stones) {
        // 肯定跳不过去的情况
        for (int i = 3; i < stones.length; i++) {
            if (stones[i] > stones[i - 1] * 2) {
                return false;
            }
        }

        // 石头的位置
        Set<Integer> stonePositions = new HashSet<>();
        for (int stone : stones) {
            stonePositions.add(stone);
        }

        int lastStone = stones[stones.length - 1];
        // 跳过的路径
        Stack<Integer> positions = new Stack<>();
        // 跳到这个位置的距离
        Stack<Integer> jumps = new Stack<>();

        // 当前在第0 位
        positions.add(0);
        jumps.add(0);

        while (!positions.isEmpty()) {
            int position = positions.pop();
            int step = jumps.pop();
            for (int i = step - 1; i <= step + 1; i++) {
                if (i > 0) {
                    int nextPos = position + i;
                    if (nextPos == lastStone) {
                        return true;
                    } else if (stonePositions.contains(nextPos)) {
                        // 可以跳到这个石头上, 就将这个石头入栈
                        positions.push(nextPos);
                        jumps.push(i);
                    }
                }
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{0, 1, 3, 5, 6, 8, 12, 17}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{0, 1, 2, 3, 4, 8, 9, 11}).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{0, 1, 3, 6, 10, 13, 14}).expect(true);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{0, 1, 3, 4, 5, 7, 9, 10, 12}).expect(true);

}
