package q750;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/random-pick-with-blacklist/
 *
 * Given a blacklist B containing unique integers from [0, N), write a function to return a uniform random integer
 * from [0, N) which is NOT in B.
 *
 * Optimize it such that it minimizes the call to system’s Math.random().
 *
 * Note:
 *
 * 1 <= N <= 1000000000
 * 0 <= B.length < min(100000, N)
 * [0, N) does NOT include N. See interval notation.
 *
 * Example 1:
 *
 * Input:
 * ["Solution","pick","pick","pick"]
 * [[1,[]],[],[],[]]
 * Output: [null,0,0,0]
 *
 * Example 2:
 *
 * Input:
 * ["Solution","pick","pick","pick"]
 * [[2,[]],[],[],[]]
 * Output: [null,1,1,1]
 *
 * Example 3:
 *
 * Input:
 * ["Solution","pick","pick","pick"]
 * [[3,[1]],[],[],[]]
 * Output: [null,0,0,2]
 *
 * Example 4:
 *
 * Input:
 * ["Solution","pick","pick","pick"]
 * [[4,[2]],[],[],[]]
 * Output: [null,1,3,1]
 *
 * Explanation of Input Syntax:
 *
 * The input is two lists: the subroutines called and their arguments. Solution's constructor has two arguments, N
 * and the blacklist B. pick has no arguments. Arguments are always wrapped with a list, even if there aren't any.
 */
public class Q710_RandomPickWithBlacklist {

    /**
     * 建立实际随机值范围和结果的映射关系.
     * blacklist 中的元素将随机数分割为多个数字段,
     * pick 的时候随机产生在 [0, count) 内的随机数, 然后找 segments 中最大的比它小的数字, 确定所在的数字段,
     * 根据 map 中对应的下标找到这个数字段的起始值, 完成映射.
     *
     * 这种方法在 LeetCode 上比较慢, 因为涉及到了排序.
     */
    private static class Solution {

        Random random = new Random();

        int n, count;

        int[] segments, map;

        public Solution(int N, int[] blacklist) {
            n = N;
            count = N - blacklist.length;

            Arrays.sort(blacklist);
            int intervalCount = 2;
            for (int i = 1; i < blacklist.length; i++) {
                if (blacklist[i - 1] + 1 < blacklist[i]) {
                    intervalCount++;
                }
            }
            segments = new int[intervalCount];
            map = new int[intervalCount];
            // 哨兵, 针对 blacklist 中有 0 的情况
            segments[0] = map[0] = -1;
            for (int i = 0, next = 1; i < blacklist.length; i++) {
                if (i < blacklist.length - 1
                        && blacklist[i] + 1 == blacklist[i + 1]) {
                    continue;
                }
                segments[next] = blacklist[i] - i;
                map[next] = blacklist[i] + 1;
                next++;
            }
        }

        public int pick() {
            int r = random.nextInt(count);
            int i = Arrays.binarySearch(segments, r);
            i = i >= 0 ? i : -i - 2;
            return r - segments[i] + map[i];
        }
    }

    /**
     * LeetCode 上比较块的方法:
     * 使用 Map 保存在 [0, count) 中取值是blacklist 中的数字到 [count, N) 中数字的映射关系.
     */
    private static class Solution2 {

        Map<Integer, Integer> map = new HashMap<>();

        Random random = new Random();

        int count;

        public Solution2(int N, int[] blacklist) {
            count = N - blacklist.length;
            // 标记 [count, N) 中在blacklist 中的数字
            boolean[] blacked = new boolean[blacklist.length];
            for (int i : blacklist) {
                if (i >= count) {
                    blacked[i - count] = true;
                }
            }

            // 建立 [0, count) 中在blacklist 中数字到 [count, N) 中数字的映射关系
            int next = 0;
            for (int b : blacklist) {
                if (b < count) {
                    while (blacked[next]) {
                        next++;
                    }
                    map.put(b, next + count);
                    next++;
                }
            }
        }

        public int pick() {
            int r = random.nextInt(count);
            return map.getOrDefault(r, r);
        }
    }

    @Test
    public void testMethod() throws Throwable {
        testCase(1);
        testCase(2);
        testCase(3, 1);
        testCase(4, 2);

        testCase(3, 0, 1);
        testCase(4, 0, 1);
    }

    private void testCase(int N, int... blacklist) throws Throwable {
        testCase(Solution.class, N, blacklist);
        testCase(Solution2.class, N, blacklist);
    }

    private void testCase(Class<?> klass, int N, int[] blacklist) throws Throwable {
        final int times = 500;
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle constructor = lookup
                .findConstructor(klass, MethodType.methodType(void.class, int.class, int[].class));
        Object solution = constructor.invoke(N, blacklist);
        MethodHandle pickMethod = lookup.findVirtual(klass, "pick", MethodType.methodType(int.class));
        pickMethod = pickMethod.bindTo(solution);

        int[] counts = new int[N];
        Arrays.stream(blacklist).forEach(b -> counts[b] = -1);

        for (int i = 0; i < times; i++) {
            int res = (int) pickMethod.invoke();
            Assert.assertTrue(0 <= res && res < N);
            Assert.assertNotEquals(-1, counts[res]);
            counts[res]++;
        }

        double expect = 1.0 / (N - blacklist.length);
        for (int i = 0; i < N; i++) {
            if (counts[i] > -1) {
                double probability = (double) counts[i] / times;
                Assert.assertTrue(expect - 0.1 < probability && probability < expect + 0.1);
            }
        }
    }

}
