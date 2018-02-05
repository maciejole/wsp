package wspolbiezne.zad5;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Book {

    private final ReentrantReadWriteLock RW = new ReentrantReadWriteLock(true);
    private final Lock writeLock = RW.writeLock();
    private final Lock readLock = RW.readLock();

    private AtomicInteger readersCount = new AtomicInteger();

    public void startReading(Reader reader) {
        int readerNumber = readersCount.incrementAndGet();
        System.out.println("++++ " + readerNumber + " czytelnik o id= " + reader.getReaderId() + " rozpoczal czytanie");
    }

    public void finishReading(Reader reader) {
        int readerNumber = readersCount.getAndDecrement();
        System.out.println("%%%% " + readerNumber + " czytelnik o id= " + reader.getReaderId() + " zakonczyl czytanie");
    }

    public void startWriting(Writer writer) {
        System.out.println("#### Pisarz o id= " + writer.getWriterId() + " rozpoczal pisanie");
    }

    public void finishWriting(Writer writer) {
        System.out.println("$$$$ Pisarz o id= " + writer.getWriterId() + " zakonczyl pisanie");
    }

    public Lock getWriteLock() {
        return writeLock;
    }

    public Lock getReadLock() {
        return readLock;
    }
}
