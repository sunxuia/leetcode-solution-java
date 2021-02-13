package q1150;

import java.util.concurrent.CountDownLatch;
import org.junit.Assert;
import org.junit.Test;
import util.concurrency.ThreadHolder;

/**
 * [Medium] 1115. Print FooBar Alternately
 * https://leetcode.com/problems/print-foobar-alternately/
 *
 * Suppose we have a class:
 *
 * public class Foo {
 * public void first() { print("first"); }
 * public void second() { print("second"); }
 * public void third() { print("third"); }
 * }
 *
 * The same instance of Foo will be passed to three different threads. Thread A will call first(), thread B will call
 * second(), and thread C will call third(). Design a mechanism and modify the program to ensure that second() is
 * executed after first(), and third() is executed after second().
 *
 *
 *
 * Example 1:
 *
 * Input: [1,2,3]
 * Output: "firstsecondthird"
 * Explanation: There are three threads being fired asynchronously. The input [1,2,3] means thread A calls first(),
 * thread B calls second(), and thread C calls third(). "firstsecondthird" is the correct output.
 *
 * Example 2:
 *
 * Input: [1,3,2]
 * Output: "firstsecondthird"
 * Explanation: The input [1,3,2] means thread A calls first(), thread B calls third(), and thread C calls second().
 * "firstsecondthird" is the correct output.
 *
 *
 *
 * Note:
 *
 * We do not know how the threads will be scheduled in the operating system, even though the numbers in the input
 * seems to imply the ordering. The input format you see is mainly to ensure our tests' comprehensiveness.
 */
public class Q1115_PrintFooBarAlternately {

    private static class FooBar {

        private int n;

        private volatile int time = 0;

        public FooBar(int n) {
            this.n = n;
        }

        public synchronized void foo(Runnable printFoo) throws InterruptedException {

            for (int i = 0; i < n; i++) {
                if (time % 2 == 1) {
                    wait();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                time++;
                notifyAll();
            }
        }

        public synchronized void bar(Runnable printBar) throws InterruptedException {

            for (int i = 0; i < n; i++) {
                if (time % 2 == 0) {
                    wait();
                }
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                time++;
                notifyAll();
            }
        }
    }

    @Test
    public void testMethod() {
        runTest(1);
        runTest(2);
    }

    private void runTest(int n) {
        CountDownLatch latch = new CountDownLatch(2);
        StringBuilder sb = new StringBuilder();
        FooBar fb = new FooBar(n);
        threadHolder.executes(
                () -> fb.foo(() -> sb.append("foo")),
                () -> fb.bar(() -> sb.append("bar")));

        StringBuilder expect = new StringBuilder();
        for (int i = 0; i < n; i++) {
            expect.append("foobar");
        }
        Assert.assertEquals(expect.toString(), sb.toString());
    }

    private ThreadHolder threadHolder = new ThreadHolder(2);

}
