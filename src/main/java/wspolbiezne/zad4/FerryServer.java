package wspolbiezne.zad4;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FerryServer extends Thread {

    private final static ConcurrentLinkedQueue<CarThread> carsInQueue = new ConcurrentLinkedQueue<CarThread>();
    private final static ConcurrentLinkedQueue<CarThread> carsOnBoard = new ConcurrentLinkedQueue<CarThread>();

    private int maxCarsToTransport;
    private volatile int carsCount;
    private volatile int carsInQueueCount;
    private long maxWaitingTimeInMillis;
    private boolean isLoadable;

    private final Lock lock = new ReentrantLock(true);
    private Condition loadable = lock.newCondition();
    private Condition wakeUpNeeded = lock.newCondition();

    public FerryServer(int maxCarsToTransport, long maxWaitingTimeInMillis) {
        this.maxCarsToTransport = maxCarsToTransport;
        this.maxWaitingTimeInMillis = maxWaitingTimeInMillis;
        this.carsCount = 0;
        this.carsInQueueCount = 0;
        this.isLoadable = true;
    }

    public void sendRequestToEmbarkFerry(CarThread car) {
        carsInQueue.add(car);
        carsInQueueCount++;
        notifyAboutRequest();
    }

    private void waitForRequest() {
        lock.lock();
        try {
            while (carsCount == 0 && carsInQueueCount == 0) {
                wakeUpNeeded.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    private void notifyAboutRequest() {
        lock.lock();
        try {
            while (!isLoadable) {
                loadable.await();
            }
            wakeUpNeeded.signal();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        System.out.println("SERVER: Prom rozpoczyna kursowanie...");
        while (true) {
            transport();
        }
    }

    private void transport() {

        if (!isLoadable) {
            moveToWest();
        } else {
            moveToEast();
        }

    }

    private void moveToWest() {
        lock.lock();
        try {
            carsCount = 0;
            while(carsOnBoard.iterator().hasNext()) {
                CarThread carThread = carsOnBoard.poll();
                carThread.notifyAboutResponse();
            }
            carsOnBoard.clear();
            System.out.println("Odplywamy na Zachod! AHOJ!!!");
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isLoadable = true;
            loadable.signal();
        } finally {
            lock.unlock();
        }
    }

    private void moveToEast() {
        long startWaitingTime = System.currentTimeMillis();
        while(true) {

            waitForRequest();
            if (carsInQueue.size() > 0) {
                if (carsOnBoard.size() < maxCarsToTransport) {
                    CarThread carToEmbark = carsInQueue.poll();
                    carsOnBoard.add(carToEmbark);
                    carsCount++;
                    System.out.println("SERVER: ACCEPT samochód " + carToEmbark.getCarId() + " wjechał na prom");
                }
            }
            if (carsOnBoard.size() == maxCarsToTransport) {
                break;
            }
            if (System.currentTimeMillis() - startWaitingTime >= maxWaitingTimeInMillis && carsCount != 0) {
                break;
            }
        }
        System.out.println("SERVER: Prom plynie na wschodni brzeg!!!!");
        try {
            sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isLoadable = false;
    }

}
