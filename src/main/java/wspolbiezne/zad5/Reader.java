package wspolbiezne.zad5;

public class Reader extends Thread {

    private Book book;
    private Long readerId;

    Reader(Long readerId, Book book) {
        this.readerId = readerId;
        this.book = book;
    }

    public void run() {
        for (int i = 0; i < 2; i++) {
            System.out.println(".... Czytelnik o id= " + readerId + " chce czytac");
            book.getReadLock().lock();
            try {
                book.startReading(this);
                sleep((long) (Math.random() * 1000 + 1000));
                book.finishReading(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                book.getReadLock().unlock();
            }
        }
    }

    public Long getReaderId() {
        return readerId;
    }
}