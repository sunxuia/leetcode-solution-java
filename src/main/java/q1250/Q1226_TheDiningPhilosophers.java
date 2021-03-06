package q1250;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.junit.Assert;
import org.junit.Test;
import util.concurrency.ThreadHolder;

/**
 * [Medium] 1226. The Dining Philosophers
 * https://leetcode.com/problems/the-dining-philosophers/
 *
 * Five silent philosophers sit at a round table with bowls of spaghetti. Forks are placed between each pair of
 * adjacent philosophers.
 *
 * Each philosopher must alternately think and eat. However, a philosopher can only eat spaghetti when they have both
 * left and right forks. Each fork can be held by only one philosopher and so a philosopher can use the fork only if
 * it is not being used by another philosopher. After an individual philosopher finishes eating, they need to put
 * down both forks so that the forks become available to others. A philosopher can take the fork on their right or
 * the one on their left as they become available, but cannot start eating before getting both forks.
 *
 * Eating is not limited by the remaining amounts of spaghetti or stomach space; an infinite supply and an infinite
 * demand are assumed.
 *
 * Design a discipline of behaviour (a concurrent algorithm) such that no philosopher will starve; i.e., each can
 * forever continue to alternate between eating and thinking, assuming that no philosopher can know when others may
 * want to eat or think.
 * <img src="Q1226_PIC.png">
 * The problem statement and the image above are taken from wikipedia.org
 *
 * The philosophers' ids are numbered from 0 to 4 in a clockwise order. Implement the function void wantsToEat
 * (philosopher, pickLeftFork, pickRightFork, eat, putLeftFork, putRightFork) where:
 *
 * philosopher is the id of the philosopher who wants to eat.
 * pickLeftFork and pickRightFork are functions you can call to pick the corresponding forks of that philosopher.
 * eat is a function you can call to let the philosopher eat once he has picked both forks.
 * putLeftFork and putRightFork are functions you can call to put down the corresponding forks of that philosopher.
 * The philosophers are assumed to be thinking as long as they are not asking to eat (the function is not being
 * called with their number).
 *
 * Five threads, each representing a philosopher, will simultaneously use one object of your class to simulate the
 * process. The function may be called for the same philosopher more than once, even before the last call ends.
 *
 * Example 1:
 *
 * Input: n = 1
 * Output: [[4,2,1],[4,1,1],[0,1,1],[2,2,1],[2,1,1],[2,0,3],[2,1,2],[2,2,2],[4,0,3],[4,1,2],[0,2,1],[4,2,2],[3,2,1],
 * [3,1,1],[0,0,3],[0,1,2],[0,2,2],[1,2,1],[1,1,1],[3,0,3],[3,1,2],[3,2,2],[1,0,3],[1,1,2],[1,2,2]]
 * Explanation:
 * n is the number of times each philosopher will call the function.
 * The output array describes the calls you made to the functions controlling the forks and the eat function, its
 * format is:
 * output[i] = [a, b, c] (three integers)
 * - a is the id of a philosopher.
 * - b specifies the fork: {1 : left, 2 : right}.
 * - c specifies the operation: {1 : pick, 2 : put, 3 : eat}.
 *
 * Constraints:
 *
 * 1 <= n <= 60
 */
public class Q1226_TheDiningPhilosophers {

    private static class DiningPhilosophers {

        private Object[] forks = new Object[5];

        public DiningPhilosophers() {
            for (int i = 0; i < forks.length; i++) {
                forks[i] = new Object();
            }
        }

        // call the run() method of any runnable to execute its code
        public void wantsToEat(int philosopher,
                Runnable pickLeftFork,
                Runnable pickRightFork,
                Runnable eat,
                Runnable putLeftFork,
                Runnable putRightFork) throws InterruptedException {
            int left = philosopher;
            int right = (philosopher + 1) % 5;
            if (left % 2 == 0) {
                synchronized (forks[left]) {
                    synchronized (forks[right]) {
                        // LeetCode 中就是要求在获取到左右2 个锁之后才能
                        // 执行 pick fork 的操作, 否则会被判定为错误
                        pickLeftFork.run();
                        pickRightFork.run();
                        eat.run();
                        putRightFork.run();
                        putLeftFork.run();
                    }
                }
            } else {
                synchronized (forks[right]) {
                    synchronized (forks[left]) {
                        pickRightFork.run();
                        pickLeftFork.run();
                        eat.run();
                        putLeftFork.run();
                        putRightFork.run();
                    }
                }
            }
        }
    }

    private ThreadHolder threadHolder = new ThreadHolder(5);

    @Test
    public void test1() {
        doTest(1);
    }

    @Test
    public void test10() {
        doTest(10);
    }

    private void doTest(final int n) {
        DiningPhilosophers dps = new DiningPhilosophers();
        BlockingQueue<int[]> queue = new ArrayBlockingQueue<>(25 * n);
        threadHolder.executes(philosopher -> {
            Runnable pickLeftFork = () -> queue.offer(new int[]{philosopher, 1, 1});
            Runnable pickRightFork = () -> queue.offer(new int[]{philosopher, 2, 1});
            Runnable eat = () -> queue.offer(new int[]{philosopher, 0, 3});
            Runnable putLeftFork = () -> queue.offer(new int[]{philosopher, 1, 2});
            Runnable putRightFork = () -> queue.offer(new int[]{philosopher, 2, 2});
            for (int j = 0; j < n; j++) {
                dps.wantsToEat(philosopher, pickLeftFork, pickRightFork, eat, putLeftFork, putRightFork);
            }
        });

        Assert.assertEquals("Operations count wrong.", 25 * n, queue.size());
        boolean[][][] visited = new boolean[5][3][4];
        int[] counts = new int[5];
        while (!queue.isEmpty()) {
            int[] action = queue.poll();
            int philosopher = action[0];
            int fork = action[1];
            int operation = action[2];
            if (operation == 1) {
                Assert.assertTrue(counts[philosopher] < 2);
            } else if (operation == 2) {
                Assert.assertTrue(counts[philosopher] > 2);
            } else {
                Assert.assertEquals(2, counts[philosopher]);
            }
            Assert.assertFalse(visited[philosopher][fork][operation]);
            visited[philosopher][fork][operation] = true;
            counts[philosopher]++;
            if (counts[philosopher] == 5) {
                counts[philosopher] = 0;
                Arrays.fill(visited[philosopher][0], false);
                Arrays.fill(visited[philosopher][1], false);
                Arrays.fill(visited[philosopher][2], false);
            }
        }
    }

}
