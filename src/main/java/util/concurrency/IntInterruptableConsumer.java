package util.concurrency;

@FunctionalInterface
public interface IntInterruptableConsumer {

    void accept(int i) throws InterruptedException;

}
