package util.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import util.UtilPackageHelper;

/**
 * 运行测试用多线程的类
 */
public class ThreadHolder {

    private final int threadSize;

    private ExecutorService threadPool;

    public ThreadHolder(int threadSize) {
        this.threadSize = threadSize;
        String threadNamePrefix = getThreadNamePrefix();
        threadPool = new ThreadPoolExecutor(threadSize, threadSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(threadSize),
                new CustomThreadFactory(threadNamePrefix));
    }

    private String getThreadNamePrefix() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String callerClassName = "";
        for (int i = 1; i < stackTraceElements.length; i++) {
            if (!UtilPackageHelper.isInPackage(stackTraceElements[i].getClassName())) {
                callerClassName = stackTraceElements[i].getClassName();
                break;
            }
        }
        String className = callerClassName.substring(callerClassName.lastIndexOf('.') + 1);
        String questionNo = className.substring(0, className.indexOf('_'));
        return questionNo.toLowerCase();
    }

    /**
     * 提交任务
     */
    public void executes(InterruptableRunnable... tasks) {
        Assert.assertEquals("Task size should equal thread size!", threadSize, tasks.length);
        List<Future<?>> futures = new ArrayList<>(threadSize);
        for (InterruptableRunnable task : tasks) {
            futures.add(threadPool.submit(() -> {
                try {
                    task.run();
                } catch (InterruptedException err) {
                    throw new RuntimeException(err);
                }
            }));
        }
        waitFinish(futures);
    }

    private void waitFinish(List<Future<?>> futures) {

        try {
            for (Future<?> future : futures) {
                future.get(1, TimeUnit.MINUTES);
            }
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
    }


    /**
     * 提交任务
     */
    public void executes(IntInterruptableConsumer consumer) {
        List<Future<?>> futures = new ArrayList<>(threadSize);
        for (int i = 0; i < threadSize; i++) {
            final int ii = i;
            futures.add(threadPool.submit(() -> {
                try {
                    consumer.accept(ii);
                } catch (InterruptedException err) {
                    throw new RuntimeException(err);
                }
            }));
        }
        waitFinish(futures);
    }
}
