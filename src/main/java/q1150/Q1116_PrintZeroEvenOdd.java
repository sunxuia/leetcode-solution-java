package q1150;

import java.util.function.IntConsumer;
import org.junit.Assert;
import org.junit.Test;
import util.concurrency.ThreadHolder;

/**
 * [Medium] 1116. Print Zero Even Odd
 * https://leetcode.com/problems/print-zero-even-odd/
 */
public class Q1116_PrintZeroEvenOdd {

    private static class ZeroEvenOdd {

        private final int n;

        private volatile int num = 1;

        private volatile boolean zero;

        public ZeroEvenOdd(int n) {
            this.n = n;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public synchronized void zero(IntConsumer printNumber) throws InterruptedException {
            while (true) {
                while (zero) {
                    wait();
                }
                if (num > n) {
                    return;
                }
                printNumber.accept(0);
                zero = true;
                notifyAll();
            }
        }

        public synchronized void even(IntConsumer printNumber) throws InterruptedException {
            while (num <= n / 2 * 2) {
                while (!zero || num % 2 == 1) {
                    wait();
                }
                printNumber.accept(num);
                num++;
                zero = false;
                notifyAll();
            }
        }

        public synchronized void odd(IntConsumer printNumber) throws InterruptedException {
            while (num <= n - 1 + n % 2) {
                while (!zero || num % 2 == 0) {
                    wait();
                }
                printNumber.accept(num);
                num++;
                zero = false;
                notifyAll();
            }
        }
    }

    @Test
    public void testMethod() {
        runTest(2);
        runTest(5);
    }

    private void runTest(int n) {
        StringBuilder sb = new StringBuilder();
        ZeroEvenOdd zeo = new ZeroEvenOdd(n);
        threadHolder.executes(
                () -> zeo.zero(sb::append),
                () -> zeo.even(sb::append),
                () -> zeo.odd(sb::append)
        );

        StringBuilder expect = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            expect.append('0').append(i);
        }
        Assert.assertEquals(expect.toString(), sb.toString());
    }

    private ThreadHolder threadHolder = new ThreadHolder(3);

}
