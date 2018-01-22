package wspolbiezne.zad3;

public interface Ferry {

    void embarkCar(CarThread carThread) throws InterruptedException;

    void transport() throws InterruptedException;

}
