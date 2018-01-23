package wspolbiezne.zad4;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CarThread extends Thread {

    private FerryServer ferryServer;

    private int carId;

    private Lock lock = new ReentrantLock();

    private boolean isWakeUpNeeded;

    public CarThread(FerryServer ferryServer, int carId) {
        this.ferryServer = ferryServer;
        this.carId = carId;
        this.isWakeUpNeeded = false;
    }

    @Override
    public void run() {
        System.out.println("THREAD: samochod " + carId + " wysyła request.");
        try {
            sleep((long)(Math.random() * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ferryServer.sendRequestToEmbarkFerry(this);
        try {
            System.out.println("THREAD: samochod " + carId + " czeka na odpowiedz serwera...");
            waitForResponse();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void waitForResponse() throws InterruptedException {
        synchronized (lock) {
            while (!isWakeUpNeeded) {
                lock.wait();
            }
        }
    }

    public void notifyAboutResponse() {
        synchronized (lock) {
            isWakeUpNeeded = true;
            System.out.println("THREAD: samochod + " + carId + " zjechal z promu");
            lock.notify();
        }
    }

    public int getCarId() {
        return carId;
    }
}
