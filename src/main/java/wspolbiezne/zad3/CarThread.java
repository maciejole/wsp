package wspolbiezne.zad3;

public class CarThread extends Thread {

    private int carId;
    private Ferry ferryMonitor;

    public CarThread(Ferry ferryMonitor, int carId) {
        this.ferryMonitor = ferryMonitor;
        this.carId = carId;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((long)(Math.random() * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            ferryMonitor.embarkCar(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getCarId() {
        return carId;
    }
}
