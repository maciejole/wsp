package wspolbiezne.zad3;

public class CarDisembarker extends Thread {

    private FerryMonitor2 ferry;

    public CarDisembarker(FerryMonitor2 ferry) {
        this.ferry = ferry;
    }

    @Override
    public void run() {
        ferry.disembarkCar();
    }
}
