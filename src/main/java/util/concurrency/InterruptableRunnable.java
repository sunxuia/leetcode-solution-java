package util.concurrency;

@FunctionalInterface
public interface InterruptableRunnable {

    void run() throws InterruptedException;

}
