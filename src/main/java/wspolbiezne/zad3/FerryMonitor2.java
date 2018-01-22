package wspolbiezne.zad3;

public class FerryMonitor2 {

    private volatile int carsCount;
    private volatile boolean loadable;

    private int capacity;
    private long maxWaitingTimeInMillis;

    public FerryMonitor2(int capacity, long maxWaitingTimeInMillis) {
        this.carsCount = 0;
        this.loadable = true;
        this.capacity = capacity;
        this.maxWaitingTimeInMillis = maxWaitingTimeInMillis;
    }

    synchronized void embarkCar() {
        while (!loadable || carsCount == capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        carsCount++;
        System.out.println(carsCount + " samochod zaookretowany");
        notifyAll();
    }

    synchronized void disembarkCar() {
        while (loadable || carsCount == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        carsCount--;
        System.out.println(carsCount + " samochod wyookretowany");
        notifyAll();
    }

    void moveFerry() {
        System.out.println("CZY odplywamy??");
        if (loadable) {
            moveToEast();
        } else {
            moveToWest();
        }
    }

    private synchronized void moveToEast() {
        do {
            try {
                wait(maxWaitingTimeInMillis);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        } while (carsCount == 0);
        System.out.println("Odplywamy na Wschod! AHOJ!!!");
        loadable = !loadable;
        notifyAll();
    }

    private synchronized void moveToWest() {
        while (carsCount != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Odplywamy na Zachod! AHOJ!!!");
        loadable = !loadable;
        notifyAll();
    }

}
