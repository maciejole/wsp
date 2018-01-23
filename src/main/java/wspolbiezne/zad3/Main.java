package wspolbiezne.zad3;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        FerryMonitor2 ferry = new FerryMonitor2(5, 5000);
        Ferry ferry = new FerryMonitorImpl(5, FerryMonitorImpl.Status.WEST, 5000);
        CarThread[] cars = new CarThread[21];
        FerryMover ferryMover = new FerryMover(ferry);
        ferryMover.start();
        for (int i  = 0; i < cars.length; i++) {
            cars[i] = new CarThread(ferry, i);
            cars[i].start();
        }
    }

}
