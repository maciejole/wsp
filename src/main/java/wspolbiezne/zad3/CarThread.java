package wspolbiezne.zad3;

public class CarThread extends Thread {

    private Long carId;
    private FerryMonitor2 ferryMonitor;

    public CarThread(FerryMonitor2 ferryMonitor, Long carId) {
        this.ferryMonitor = ferryMonitor;
        this.carId = carId;
    }

    @Override
    public void run() {
        ferryMonitor.embarkCar();
        ferryMonitor.disembarkCar();
    }
}
