package q1150;

import org.junit.Assert;
import org.junit.Test;
import util.concurrency.ThreadHolder;

/**
 * [Easy] 1114. Print in Order
 * https://leetcode.com/problems/print-in-order/
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
public class Q1114_PrintInOrder {

    private class Foo {

        public Foo() {

        }

        private volatile int times = 1;

        public synchronized void first(Runnable printFirst) throws InterruptedException {

            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            times++;
            notifyAll();
        }

        public synchronized void second(Runnable printSecond) throws InterruptedException {
            while (times < 2) {
                wait();
            }
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            times++;
            notifyAll();
        }

        public synchronized void third(Runnable printThird) throws InterruptedException {
            while (times < 3) {
                wait();
            }
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
    }

    @Test
    public void testMethod() {
        for (int i = 0; i < 5; i++) {
            StringBuffer sb = new StringBuffer();
            Foo foo = new Foo();
            threadHolder.executes(
                    () -> foo.first(() -> sb.append("first")),
                    () -> foo.second(() -> sb.append("second")),
                    () -> foo.third(() -> sb.append("third")));
            Assert.assertEquals("firstsecondthird", sb.toString());
        }
    }

    private ThreadHolder threadHolder = new ThreadHolder(3);

}
