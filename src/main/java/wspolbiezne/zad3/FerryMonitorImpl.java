package wspolbiezne.zad3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FerryMonitorImpl implements Ferry {
    private volatile int carsCount = 0;

    private int capacity;
    private volatile Status status;

    private final Lock lock = new ReentrantLock();

    private final Condition full = lock.newCondition();
    private final Condition loadable = lock.newCondition();
    private long maxWaitingTimeInMillis;

    public enum Status {
        EAST, WEST
    }

    public FerryMonitorImpl(int capacity, Status status, long maxWaitingTimeInMillis) {
        this.maxWaitingTimeInMillis = maxWaitingTimeInMillis;
        this.capacity = capacity;
        this.status = status;
    }

    public void embarkCar(CarThread car) throws InterruptedException {
        lock.lock();
        try {
            while (status != Status.WEST && carsCount == capacity) {
                loadable.await();
            }
            carsCount++;
            System.out.println(carsCount + " samochod: " + car.getCarId() + " wjechal na prom");
            if (carsCount == capacity) {
                full.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void transport() throws InterruptedException {
        if (status == Status.WEST) {
            moveToEast();
        } else if (status == Status.EAST) {
            moveToWest();
        }
    }

    private void moveToEast() throws InterruptedException {
        lock.lock();
        try {
            while (carsCount == 0) {
                full.await(maxWaitingTimeInMillis, TimeUnit.MILLISECONDS);
            }
            System.out.println("Odplywamy na Wschod! AHOJ!!!");
            status = Status.EAST;
        } finally {
            lock.unlock();
        }
    }

    private void moveToWest() {
        lock.lock();
        try {
            carsCount = 0;

            System.out.println("Odplywamy na Zachod! AHOJ!!!");
            status = Status.WEST;
            loadable.signal();
        } finally {
            lock.unlock();
        }
    }

}
