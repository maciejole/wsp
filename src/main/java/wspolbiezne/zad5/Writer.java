package wspolbiezne.zad5;

public class Writer extends Thread {

    private Book book;
    private Long writerId;

    Writer(Long writerId, Book book) {
        this.writerId = writerId;
        this.book = book;
    }

    public void run() {

        for (int i = 0; i < 2; i++) {
            System.out.println("_____ Pisarz o id= " + writerId + " chce pisac " + i + " raz");

            book.getWriteLock().lock();
            try {
                book.startWriting(this);
                sleep((long)(Math.random() * 2000 + 2000));
                book.finishWriting(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                book.getWriteLock().unlock();
            }
        }

    }

    public Long getWriterId() {
        return writerId;
    }
}
