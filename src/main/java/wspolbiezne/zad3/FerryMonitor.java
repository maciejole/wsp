package wspolbiezne.zad3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FerryMonitor {
    volatile int carsCount = 0;

    private int capacity;
    private volatile Status status;

    private final Lock lock = new ReentrantLock();

    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public enum Status {
        EAST, WEST
    }

    public FerryMonitor(int carsCount, int capacity, Status status) {
        this.carsCount = carsCount;
        this.capacity = capacity;
        this.status = status;
    }

    public void addCar() throws InterruptedException {
        lock.lock();

        try {
            while (status != Status.WEST && carsCount == capacity) {
                notFull.await();
            }
            carsCount++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public void transport() throws InterruptedException {
        lock.lock();
        if (status == Status.WEST) {
            while (carsCount != capacity) {

            }
        } else if (status == Status.EAST) {
            while (carsCount != 0) {
                notEmpty.await();
            }
        }
        lock.unlock();
    }

    public void removeCar() throws InterruptedException {
        lock.lock();
    }
}
