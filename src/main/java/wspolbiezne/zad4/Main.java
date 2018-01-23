package wspolbiezne.zad4;

public class Main {
    public static void main(String[] args) {
        CarThread[] carThreads = new CarThread[21];
        FerryServer ferryServer = new FerryServer(5, 500);
        ferryServer.start();
        for (int i = 0; i < carThreads.length; i++) {
            carThreads[i] = new CarThread(ferryServer, i);
            carThreads[i].start();
        }
    }
}
