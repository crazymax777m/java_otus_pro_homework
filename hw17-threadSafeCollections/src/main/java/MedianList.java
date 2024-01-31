import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public final class MedianList {
    private final ConcurrentLinkedQueue<Double> elements;
    private final AtomicInteger size;
    private final ReentrantLock lock;

    public MedianList() {
        this.elements = new ConcurrentLinkedQueue<>();
        this.size = new AtomicInteger(0);
        this.lock = new ReentrantLock();
    }

    public int size() {
        return size.get();
    }

    public void add(double item) {
        lock.lock();
        try {
            elements.add(item);
            size.incrementAndGet();
        } finally {
            lock.unlock();
        }
    }

    public void remove(double item) {
        lock.lock();
        try {
            elements.remove(item);
            size.decrementAndGet();
        } finally {
            lock.unlock();
        }
    }

    public double getMedian() {
        lock.lock();
        try {
            if (size.get() == 0) {
                return Double.NaN;
            }

            int middle = size.get() / 2;

            double median;

            if (size.get() % 2 == 0) {
                double firstMiddle = findKthSmallest(middle);
                double secondMiddle = findKthSmallest(middle + 1);
                median = (firstMiddle + secondMiddle) / 2.0;
            } else {
                median = findKthSmallest(middle + 1);
            }

            return median;
        } finally {
            lock.unlock();
        }
    }

    // Метод для нахождения k-того наименьшего элемента
    private double findKthSmallest(int k) {
        if (k <= 0 || k > size.get()) {
            throw new IllegalArgumentException("Invalid value of k");
        }

        Iterator<Double> iterator = elements.iterator();
        double result = iterator.next();

        for (int i = 1; i < k; i++) {
            result = iterator.next();
        }

        return result;
    }

    public void printAllValues() {
        Double[] array = elements.toArray(new Double[0]);

        System.out.print("Values in the collection: ");
        for (Double value : array) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
