package q1150;

import org.junit.Assert;
import org.junit.Test;
import util.concurrency.ThreadHolder;

/**
 * 1117. Building H2O
 * https://leetcode.com/problems/building-h2o/
 *
 * There are two kinds of threads, oxygen and hydrogen. Your goal is to group these threads to form water molecules.
 * There is a barrier where each thread has to wait until a complete molecule can be formed. Hydrogen and oxygen
 * threads will be given releaseHydrogen and releaseOxygen methods respectively, which will allow them to pass the
 * barrier. These threads should pass the barrier in groups of three, and they must be able to immediately bond with
 * each other to form a water molecule. You must guarantee that all the threads from one molecule bond before any
 * other threads from the next molecule do.
 *
 * In other words:
 *
 * If an oxygen thread arrives at the barrier when no hydrogen threads are present, it has to wait for two
 * hydrogen threads.
 * If a hydrogen thread arrives at the barrier when no other threads are present, it has to wait for an oxygen
 * thread and another hydrogen thread.
 *
 * We don’t have to worry about matching the threads up explicitly; that is, the threads do not necessarily know
 * which other threads they are paired up with. The key is just that threads pass the barrier in complete sets; thus,
 * if we examine the sequence of threads that bond and divide them into groups of three, each group should contain
 * one oxygen and two hydrogen threads.
 *
 * Write synchronization code for oxygen and hydrogen molecules that enforces these constraints.
 *
 *
 *
 * Example 1:
 *
 * Input: "HOH"
 * Output: "HHO"
 * Explanation: "HOH" and "OHH" are also valid answers.
 *
 * Example 2:
 *
 * Input: "OOHHHH"
 * Output: "HHOHHO"
 * Explanation: "HOHHHO", "OHHHHO", "HHOHOH", "HOHHOH", "OHHHOH", "HHOOHH", "HOHOHH" and "OHHOHH" are also valid
 * answers.
 *
 *
 *
 * Constraints:
 *
 * Total length of input string will be 3n, where 1 ≤ n ≤ 20.
 * Total number of H will be 2n in the input string.
 * Total number of O will be n in the input string.
 */
public class Q1117_BuildingH2O {

    private class H2O {

        public H2O() {

        }

        private volatile int h, o;

        public synchronized void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
            while (h == 2) {
                wait();
            }
            // releaseHydrogen.run() outputs "H". Do not change or remove this line.
            releaseHydrogen.run();
            h++;
            if (h == 2 && o == 1) {
                h = o = 0;
            }
            notifyAll();
        }

        public synchronized void oxygen(Runnable releaseOxygen) throws InterruptedException {
            while (o == 1) {
                wait();
            }
            // releaseOxygen.run() outputs "O". Do not change or remove this line.
            releaseOxygen.run();
            o = 1;
            if (h == 2) {
                h = o = 0;
            }
            notifyAll();
        }
    }

    @Test
    public void testMethod() {
        runTest("HOH");
        runTest("HHOHHO");
    }

    private void runTest(String str) {
        final int n = str.length();
        H2O h2o = new H2O();
        StringBuffer sb = new StringBuffer(n);
        threadHolder.executes(i -> {
            if (i < n) {
                if (str.charAt(i) == 'H') {
                    h2o.hydrogen(() -> sb.append('H'));
                } else {
                    h2o.oxygen(() -> sb.append('O'));
                }
            }
        });

        Assert.assertEquals(sb.length(), n);

        int h = 0, o = 0;
        for (int i = 0; i < n; i++) {
            if (sb.charAt(i) == 'H') {
                h++;
            } else {
                o++;
            }
            Assert.assertTrue(h <= 2 && o <= 1);
            if (h == 2 && o == 1) {
                h = o = 0;
            }
        }
    }

    private ThreadHolder threadHolder = new ThreadHolder(10);

}
