package wspolbiezne.zad3;

public class Main {

    public static void main(String[] args) {
        FerryMonitor2 ferry = new FerryMonitor2(5, 500);
        CarEmbarker[] carEmbarkers = new CarEmbarker[10];
        CarDisembarker[] carDisembarkers = new CarDisembarker[10];
        for (int i  = 0; i < carEmbarkers.length; i++) {
            carEmbarkers[i] = new CarEmbarker(ferry);
            carDisembarkers[i] = new CarDisembarker(ferry);
            carEmbarkers[i].start();
            carDisembarkers[i].start();
        }
        FerryMover ferryMover = new FerryMover(ferry);
        ferryMover.start();
    }

}
