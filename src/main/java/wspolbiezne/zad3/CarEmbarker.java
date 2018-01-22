package wspolbiezne.zad3;

public class CarEmbarker extends Thread {

    private FerryMonitor2 ferry;

    public CarEmbarker(FerryMonitor2 ferry) {
        this.ferry = ferry;
    }

    @Override
    public void run() {
        ferry.embarkCar();
    }
}
