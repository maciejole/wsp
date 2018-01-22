package wspolbiezne.zad3;

public class FerryMover extends Thread {

    private FerryMonitor2 ferry;

    public FerryMover(FerryMonitor2 ferry) {
        this.ferry = ferry;
    }

    @Override
    public void run() {
        while (true)
            ferry.moveFerry();
    }
}
