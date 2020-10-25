package q1200;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.IntConsumer;
import org.junit.Assert;
import org.junit.Test;

/**
 * [Medium] 1195. Fizz Buzz Multithreaded
 *
 * Write a program that outputs the string representation of numbers from 1 to n, however:
 *
 * If the number is divisible by 3, output "fizz".
 * If the number is divisible by 5, output "buzz".
 * If the number is divisible by both 3 and 5, output "fizzbuzz".
 *
 * For example, for n = 15, we output: 1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz.
 *
 * Suppose you are given the following code:
 *
 * class FizzBuzz {
 * public FizzBuzz(int n) { ... }               // constructor
 * public void fizz(printFizz) { ... }          // only output "fizz"
 * public void buzz(printBuzz) { ... }          // only output "buzz"
 * public void fizzbuzz(printFizzBuzz) { ... }  // only output "fizzbuzz"
 * public void number(printNumber) { ... }      // only output the numbers
 * }
 *
 * Implement a multithreaded version of FizzBuzz with four threads. The same instance of FizzBuzz will be passed to
 * four different threads:
 *
 * Thread A will call fizz() to check for divisibility of 3 and outputs fizz.
 * Thread B will call buzz() to check for divisibility of 5 and outputs buzz.
 * Thread C will call fizzbuzz() to check for divisibility of 3 and 5 and outputs fizzbuzz.
 * Thread D will call number() which should only output the numbers.
 *
 * 题解: 这题要求实现一个FizzBuzz 对象, 调用的方法是同时有4 个线程调用这个对象的特定方法,
 * 要求最终的输出是 1, 2, fizz ... 这样, 需要在程序中协调不同线程的输出.
 */
public class Q1195_FizzBuzzMultithreaded {

    private static class FizzBuzz {

        private final int n;

        private volatile int number = 1;

        public FizzBuzz(int n) {
            this.n = n;
        }

        // printFizz.run() outputs "fizz".
        public void fizz(Runnable printFizz) throws InterruptedException {
            while (number <= n) {
                synchronized (this) {
                    if (number % 15 != 0 && number % 3 == 0) {
                        printFizz.run();
                        number++;
                        notifyAll();
                    } else {
                        wait();
                    }
                }
            }
        }

        // printBuzz.run() outputs "buzz".
        public void buzz(Runnable printBuzz) throws InterruptedException {
            while (number <= n) {
                synchronized (this) {
                    if (number % 15 != 0 && number % 5 == 0) {
                        printBuzz.run();
                        number++;
                        notifyAll();
                    } else {
                        wait();
                    }
                }
            }
        }

        // printFizzBuzz.run() outputs "fizzbuzz".
        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            while (number <= n) {
                synchronized (this) {
                    if (number % 15 == 0) {
                        printFizzBuzz.run();
                        number++;
                        notifyAll();
                    } else {
                        wait();
                    }
                }
            }
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void number(IntConsumer printNumber) throws InterruptedException {
            while (number <= n) {
                synchronized (this) {
                    if (number % 3 != 0 && number % 5 != 0) {
                        printNumber.accept(number);
                        number++;
                        notifyAll();
                    } else {
                        wait();
                    }
                }
            }
        }
    }

    @Test
    public void testMethod() throws InterruptedException {
        doTest(15);
        doTest(100);
    }

    private void doTest(final int n) throws InterruptedException {
        FizzBuzz fb = new FizzBuzz(n);
        List<String> outputs = new ArrayList<>(n);
        CountDownLatch latch = new CountDownLatch(n);
        invoke(() -> fb.fizz(() -> {
            synchronized (outputs) {
                outputs.add("fizz");
                latch.countDown();
            }
        }));
        invoke(() -> fb.buzz(() -> {
            synchronized (outputs) {
                outputs.add("buzz");
                latch.countDown();
            }
        }));
        invoke(() -> fb.fizzbuzz(() -> {
            synchronized (outputs) {
                outputs.add("fizzbuzz");
                latch.countDown();
            }
        }));
        invoke(() -> fb.number((int val) -> {
            synchronized (outputs) {
                outputs.add(String.valueOf(val));
                latch.countDown();
            }
        }));

        latch.await();
        Assert.assertEquals(n, outputs.size());
        for (int i = 1; i <= n; i++) {
            String actual = outputs.get(i - 1);
            String expect;
            if (i % 15 == 0) {
                expect = "fizzbuzz";
            } else if (i % 3 == 0) {
                expect = "fizz";
            } else if (i % 5 == 0) {
                expect = "buzz";
            } else {
                expect = String.valueOf(i);
            }
            Assert.assertEquals(expect, actual);
        }
    }

    private ExecutorService threadPool = Executors.newFixedThreadPool(4);

    private interface InvokeWrapper {

        void invoke() throws InterruptedException;
    }

    private Future<?> invoke(InvokeWrapper invokeWrapper) {
        return threadPool.submit(() -> {
            try {
                invokeWrapper.invoke();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
