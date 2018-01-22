package wspolbiezne.zad3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class FerryImpl implements Ferry {

    private List<CarThread> cars = Collections.synchronizedList(new ArrayList<CarThread>());

    private volatile int carsCount;
    private volatile boolean loadable;

    private int capacity;
    private long maxWaitingTimeInMillis;

    public FerryImpl(int capacity, long maxWaitingTimeInMillis) {
        this.carsCount = 0;
        this.loadable = true;
        this.capacity = capacity;
        this.maxWaitingTimeInMillis = maxWaitingTimeInMillis;
    }

    public synchronized void embarkCar(CarThread car) {
        while (!loadable || carsCount == capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        cars.add(car);
        carsCount++;
        System.out.println(carsCount + " samochod: + " + car.getCarId() +" wjechal na prom");
        notifyAll();
    }

    public void transport() {
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
        ListIterator<CarThread> carsIterator = cars.listIterator(cars.size());
        while (carsIterator.hasPrevious()) {
            CarThread carMoving = carsIterator.previous();
            carsIterator.remove();
            carsCount--;
            System.out.println(carMoving.getCarId() + "zjechal z promu. Pozostalo: " + carsCount + "samochodow");
        }
        System.out.println("Odplywamy na Zachod! AHOJ!!!");
        loadable = !loadable;
        notifyAll();
    }

}
