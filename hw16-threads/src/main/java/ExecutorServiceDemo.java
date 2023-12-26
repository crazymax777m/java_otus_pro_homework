import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ExecutorServiceDemo {

    public static void main(String[] args) throws InterruptedException {

        new ExecutorServiceDemo().newFixedThreadPool();
    }

    private void newFixedThreadPool() throws InterruptedException {
        var executor = Executors.newFixedThreadPool(2);

        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        executor.execute(new MyThread(1, lock, condition));
        executor.execute(new MyThread(2, lock, condition));

        Thread.sleep(300);
        executor.shutdownNow();
    }

    private static class MyThread implements Runnable {

        private static int currentTreadId = 1;
        private static int number = 1;

        private final ReentrantLock lock;
        private final Condition condition;
        private final int threadId;

        private boolean increase;

        public MyThread(int threadId, ReentrantLock lock, Condition condition) {

            this.threadId = threadId;
            this.lock = lock;
            this.condition = condition;
        }

        @Override
        public void run() {

            while (!Thread.currentThread().isInterrupted()) {

                if (number == 1)
                    increase = true;

                lock.lock();
                try {

                    while (threadId != currentTreadId) {
                        condition.await();
                    }

                    log.info(String.valueOf(number));

                    switch (threadId) {
                        case 1 -> currentTreadId = 2;
                        case 2 -> {
                            if (increase) {
                                number++;
                            } else {
                                number--;
                            }
                            currentTreadId = 1;
                        }
                    }

                    condition.signalAll();

                } catch (InterruptedException exception) {
                    log.info(exception.getMessage());
                } finally {
                    lock.unlock();
                }

                if (number == 10)
                    increase = false;
            }
        }
    }
}