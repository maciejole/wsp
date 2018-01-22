package wspolbiezne.zad3;

public class FerryMover extends Thread {

    private Ferry ferry;

    public FerryMover(Ferry ferry) {
        this.ferry = ferry;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ferry.transport();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
